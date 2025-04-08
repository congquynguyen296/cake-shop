$(document).ready(function () {
	function loadProducts() {
		getProducts(function (response) {
			if (response.result && response.data && response.data.content) {
				displayProducts(response.data.content);
			} else {
				console.error("Không tìm thấy dữ liệu sản phẩm trong phản hồi API.");
			}
		});
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
																data-setbg="https://res.cloudinary.com/dcxgx3ott/image/upload/v1744129296/product-10_q4cevx.jpg">
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

	loadProducts();
});
