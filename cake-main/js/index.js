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

		// 📌 Bắt sự kiện click
		$(".categories__item").on("click", function () {
			const categoryId = $(this).data("id");
			loadProducts(categoryId);
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

	function loadProducts(categoryId = null) {
		getProducts(function (response) {
			if (response.result && response.data && response.data.content) {
				displayProducts(response.data.content);
			} else {
				console.error("Không tìm thấy dữ liệu sản phẩm trong phản hồi API.");
			}
		}, categoryId);
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
                      <div class="product__item">
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
		} else {
			productContainer.html("<p>Không có sản phẩm nào để hiển thị.</p>");
		}
	}
	loadCategories();
	loadProducts();
});
