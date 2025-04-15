import { getCategories, getProducts } from "./api.js";
import { addToCart } from "./cart.js";

$(document).ready(function () {
	function loadCategories() {
		getCategories(function (response) {
			if (response.result && response.data && response.data.content) {
				displayCategories(response.data.content);
			} else {
				console.error("Không tìm thấy dữ liệu danh mục trong phản hồi API.");
			}
		});
	}
	function displayCategories(categories) {
		const categoryContainer = $(".categories__slider");
		categoryContainer.empty();

		categories.forEach(function (category, index) {
			const iconClass = getCategoryIcon(index);

			const html = `
				<div class="categories__item" data-id="${category.id}">
					<div class="categories__item__icon">
						<span class="${iconClass}"></span>
						<h5>${category.name}</h5>
					</div>
				</div>
			`;

			categoryContainer.append(html);
		});

		// Re-init carousel
		if (categoryContainer.hasClass("owl-carousel")) {
			categoryContainer.trigger("destroy.owl.carousel");
			categoryContainer.owlCarousel({
				loop: true,
				margin: 10,
				items: 4,
				autoplay: true,
				autoplayTimeout: 3000,
				nav: false,
				dots: false,
				responsive: {
					0: { items: 2 },
					600: { items: 3 },
					1000: { items: 5 },
				},
			});
		}

		$(".categories__item").on("click", function () {
			const categoryId = $(this).data("id");
			hasMoreProducts = true;
			$("#load-more-btn").show(); // Hiện lại nút load nếu đang ẩn
			loadProducts(categoryId, false);
		});
	}

	function getCategoryIcon(index) {
		const iconClasses = [
			"flaticon-029-cupcake-3",
			"flaticon-034-chocolate-roll",
			"flaticon-005-pancake",
			"flaticon-030-cupcake-2",
			"flaticon-006-macarons",
			"flaticon-007-pie",
		];
		return iconClasses[index % iconClasses.length];
	}

	let currentPage = 0;
	const pageSize = 8;
	let selectedCategoryId = null;
	let hasMoreProducts = true;

	function loadProducts(categoryId = null, append = false) {
		if (!hasMoreProducts) return;

		// Gán category đang chọn
		if (!append) {
			selectedCategoryId = categoryId;
			currentPage = 0;
			hasMoreProducts = true;
		}

		getProducts(
			function (response) {
				if (response.result && response.data?.content) {
					const products = response.data.content;
					if (append) {
						appendProducts(products);
					} else {
						displayProducts(products);
					}

					// Kiểm tra còn dữ liệu để load nữa không
					if (products.length < pageSize) {
						hasMoreProducts = false;
						$("#load-more-btn").hide(); // Ẩn nút Load More
					} else {
						$("#load-more-btn").show(); // Hiện nếu còn sản phẩm
					}

					currentPage++;
				} else {
					console.error("Không tìm thấy dữ liệu sản phẩm.");
					hasMoreProducts = false;
					$("#load-more-btn").hide();
				}
			},
			selectedCategoryId,
			currentPage,
			pageSize
		);
	}

	function displayProducts(products) {
		const productContainer = $(".product.spad .row");
		productContainer.empty();

		if (products && products.length > 0) {
			products.forEach(function (product) {
				const basePrice = product.price;
				const discount = product.discount
					? product.discount.discountPercentage
					: 0;
				const discountAmount = (basePrice * discount) / 100.0;
				const totalPrice = basePrice - discountAmount;
				const formattedPrice = totalPrice.toLocaleString("vi-VN") + " VNĐ";
				let discountHtml = "";
				if (product.discount != null) {
					discountHtml = `
                        <div class="product__discount">
                            <span>- ${product.discount.discountPercentage}%</span>
                        </div>
                    `;
				}
				const productHtml = `
                  <div class="col-lg-3 col-md-6 col-sm-6">
                      <div class="product__item" data-id="${product.id}">
                          <div class="product__item__pic set-bg" 
																data-setbg=${product.image}>
                              <div class="product__label">
                                  <span>${product.category.name}</span>
                              </div>
                              ${discountHtml}
                          </div>
                          <div class="product__item__text">
                              <h6><a href="#">${product.name}</a></h6>
                              <div class="product__item__price">${formattedPrice}</div>
                              <div class="cart_add">
																<a href="#" class="add-to-cart-btn" data-id="${product.id}">Add to cart</a>
															</div>
                          </div>
                      </div>
                  </div>
              `;
				productContainer.append(productHtml);
			});

			$(".set-bg").each(function () {
				const bg = $(this).data("setbg");
				$(this).css("background-image", `url(${bg})`);
			});
			$(".add-to-cart-btn").on("click", function (e) {
				e.preventDefault();
				const productId = $(this).data("id");
				addToCart(productId, 1);
			});
		} else {
			productContainer.html("<p>Không có sản phẩm nào để hiển thị.</p>");
		}
	}

	function appendProducts(products) {
		const productContainer = $(".product.spad .row");

		if (products && products.length > 0) {
			products.forEach(function (product) {
				const basePrice = product.price;
				const discount = product.discount
					? product.discount.discountPercentage
					: 0;
				const discountAmount = (basePrice * discount) / 100.0;
				const totalPrice = basePrice - discountAmount;
				const formattedPrice = totalPrice.toLocaleString("vi-VN") + " VNĐ";

				let discountHtml = "";
				if (product.discount != null) {
					discountHtml = `
						<div class="product__discount">
							<span>- ${product.discount.discountPercentage}%</span>
						</div>
					`;
				}
				const productHtml = `
					<div class="col-lg-3 col-md-6 col-sm-6">
						<div class="product__item">
							<div class="product__item__pic set-bg" data-setbg=${product.image}>
								<div class="product__label">
									<span>${product.category.name}</span>
								</div>
								${discountHtml}
							</div>
							<div class="product__item__text">
								<h6><a href="#">${product.name}</a></h6>
								<div class="product__item__price">${formattedPrice}</div>
								<div class="cart_add">
									<a href="#">Add to cart</a>
								</div>
							</div>
						</div>
					</div>
				`;

				productContainer.append(productHtml);
			});

			$(".set-bg").each(function () {
				const bg = $(this).data("setbg");
				$(this).css("background-image", `url(${bg})`);
			});
		}
	}

	document
		.getElementById("load-more-btn")
		.addEventListener("click", function () {
			loadProducts(selectedCategoryId, true);
		});

	loadCategories();
	loadProducts();
});
