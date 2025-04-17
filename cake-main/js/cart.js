import callApi from "./api.js";

$(document).ready(function () {
	const token = localStorage.getItem("authToken");

	if (token) {
		callApi(
			"/cart/get",
			"GET",
			null,
			function (res) {
				if (res.result && res.data && res.data.cartItems) {
					renderCartItems(res.data.cartItems);
				}
			},
			function (err) {
				console.error("Lỗi khi lấy giỏ hàng:", err);
			},
			token
		);
	} else {
		console.warn("Không tìm thấy token, người dùng chưa đăng nhập.");
	}
});

function renderCartItems(cartItems) {
	const cartTableBody = $(".shopping__cart__table tbody");
	cartTableBody.empty();
	cartItems.forEach((item) => {
		const product = item.product;
		const quantity = item.quantity;
		const basePrice = product.price;
		const discountPercent = product.discount
			? product.discount.discountPercentage
			: 0;
		const discountAmount = (basePrice * discountPercent) / 100;
		const finalPrice = basePrice - discountAmount;
		const totalPrice = finalPrice * quantity;

		const html = `
									<tr>
										<td class="cart__checkbox">
											<input type="checkbox" class="select-item" data-id="${
												item.id
											}" data-product-id="${product.id}" />
										</td>
										<td class="product__cart__item">
											<div class="product__cart__item__pic">
												<img src="${product.image}" alt="${
			product.name
		}" style="width: 90px; height: auto;" />
											</div>
											<div class="product__cart__item__text">
												<h6>${product.name}</h6>
												<h5>${finalPrice.toLocaleString("vi-VN")} VNĐ</h5>
											</div>
										</td>
										<td class="quantity__item">
											<div class="quantity">
												<div class="pro-qty" data-cart-item-id="${item.id}">
													<span class="dec qtybtn">-</span>
													<input type="text" value="${quantity}" readonly />
													<span class="inc qtybtn">+</span>
												</div>
											</div>
										</td>
										<td class="cart__price">${totalPrice.toLocaleString("vi-VN")} VNĐ</td>
										<td class="cart__close">
											<span class="icon_close" data-id="${item.id}" style="cursor: pointer;"></span>
										</td>
									</tr>
									`;

		cartTableBody.append(html);
	});
}

const debounceMap = new Map();

$(document).on("click", ".qtybtn", function () {
	const $btn = $(this);
	const $proQty = $btn.closest(".pro-qty");
	const $input = $proQty.find("input");
	let currentQty = parseInt($input.val());
	const isIncrease = $btn.hasClass("inc");
	const isDecrease = $btn.hasClass("dec");

	const cartItemId = $proQty.data("cart-item-id");

	if (isIncrease) {
		currentQty += 1;
	} else if (isDecrease && currentQty > 1) {
		currentQty -= 1;
	} else {
		return;
	}

	$input.val(currentQty);

	if (!debounceMap.has(cartItemId)) {
		debounceMap.set(cartItemId, debounce(updateCartItem, 400));
	}

	debounceMap.get(cartItemId)(cartItemId, currentQty, function () {});
});

$(document).on("change", ".select-item", function () {
	updateCartSummary();
});

$(document).on("click", "#makeOrder", function (e) {
	e.preventDefault();

	const selectedItems = [];
	$(".select-item:checked").each(function () {
		const $row = $(this).closest("tr");
		const productId = $(this).data("product-id");
		const quantity = parseInt($row.find(".pro-qty input").val());

		if (!productId || isNaN(quantity)) return;

		selectedItems.push({
			productId: productId.toString(),
			quantity: quantity,
			note: "",
		});
	});

	if (selectedItems.length === 0) {
		alert("Vui lòng chọn ít nhất một sản phẩm để đặt hàng.");
		return;
	}

	const body = {
		shippingFee: 30000,
		orderItems: selectedItems,
	};

	const token = localStorage.getItem("authToken");
	if (!token) {
		alert("Bạn cần đăng nhập để đặt hàng.");
		return;
	}

	callApi(
		"/order/create",
		"POST",
		body,
		function (res) {
			alert("Đặt hàng thành công!");
			console.log("Order response:", res);
		},
		function (err) {
			alert("Đặt hàng thất bại!");
			console.error("Lỗi đặt hàng:", err);
		},
		token
	);
});

function updateCartSummary() {
	let totalQuantity = 0;
	let totalAmount = 0;

	$(".select-item:checked").each(function () {
		const $row = $(this).closest("tr");
		const quantity = parseInt($row.find(".pro-qty input").val());
		const priceText = $row.find(".cart__price").text().trim();
		const numericPrice = parseFloat(
			priceText
				.replace(/\./g, "")
				.replace(",", ".")
				.replace(/[^\d.]/g, "")
		);

		if (!isNaN(quantity)) totalQuantity += quantity;
		if (!isNaN(numericPrice)) totalAmount += numericPrice;
	});

	$("#selected-count").text(totalQuantity);
	$("#selected-total").text(
		totalAmount.toLocaleString("vi-VN", {
			minimumFractionDigits: 1,
			maximumFractionDigits: 1,
		}) + " VNĐ"
	);
}

export function addToCart(productId, quantity) {
	const token = localStorage.getItem("authToken");
	if (!token) {
		alert("Bạn cần đăng nhập để thêm vào giỏ hàng.");
		return;
	}

	const body = { productId, quantity };
	console.log(body);

	callApi(
		"/cart/add-item",
		"POST",
		body,
		function (res) {
			alert("Đã thêm vào giỏ hàng!");
			console.log("Thêm giỏ hàng thành công:", res);
		},
		function (err) {
			alert("Thêm vào giỏ hàng thất bại!");
			console.error("Lỗi:", err);
		},
		token
	);
}

function debounce(func, delay) {
	let timeout;
	return function (...args) {
		clearTimeout(timeout);
		timeout = setTimeout(() => func.apply(this, args), delay);
	};
}

function updateCartItem(cartItemId, quantity, callback) {
	const token = localStorage.getItem("authToken");
	if (!token) {
		alert("Bạn cần đăng nhập để cập nhật giỏ hàng.");
		return;
	}

	const body = { cartItemId, quantity };

	callApi(
		"/cart-item/update",
		"PUT",
		body,
		function (res) {
			console.log("Cập nhật giỏ hàng thành công:", res);
			if (callback) callback();
		},
		function (err) {
			alert("Cập nhật số lượng thất bại!");
			console.error("Lỗi:", err);
		},
		token
	);
}
