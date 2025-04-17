import callApi from "./api.js";

$(document).ready(function () {
	const token = localStorage.getItem("authToken");

	if (token) {
		callApi(
			"/order/list",
			"GET",
			null,
			function (res) {
				if (res.result && res.data && res.data.content) {
					renderOrders(res.data.content);
				}
			},
			function (err) {
				console.error("Lỗi khi lấy danh sách đơn hàng:", err);
			},
			token
		);
	} else {
		console.warn("Không tìm thấy token, người dùng chưa đăng nhập.");
	}
});

function renderOrders(orders) {
	const container = $(".order__list");
	container.empty();

	orders.forEach((order) => {
		const getStatusText = (statusCode) => {
			switch (statusCode) {
				case 1:
					return { text: "Chờ thanh toán", class: "text-warning" };
				case 2:
					return { text: "Đang xử lý", class: "text-primary" };
				case 3:
					return { text: "Đang giao hàng", class: "text-info" };
				case 4:
					return { text: "Giao hàng thành công", class: "text-success" };
				case 5:
					return { text: "Đã huỷ", class: "text-danger" };
				default:
					return { text: "Không xác định", class: "text-muted" };
			}
		};

		const statusInfo = getStatusText(order.status.status);

		const orderItemsHtml = order.orderItems
			.map((item, index, array) => {
				const product = item.product;
				const finalPrice =
					product.price * (1 - (item.discountPercentage || 0) / 100);
				const categoryName = product.category ? product.category.name : "-";
				const tagNames =
					product.tags && product.tags.length > 0
						? product.tags.map((tag) => tag.name).join(", ")
						: "-";

				return `
					<div class="d-flex mb-3 justify-content-between align-items-start">
						<div class="d-flex">
							<img
								src="${product.image}"
								alt="${product.name}"
								style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;"
								class="mr-3"
							/>
							<div>
								<h6 class="mb-1">${product.name}</h6>
								<p class="mb-1"><small>Phân loại hàng: ${categoryName}</small></p>
								<p class="mb-1"><small>Vị: ${tagNames}</small></p>
							</div>
						</div>
						<div class="text-right">
							<p class="mb-1">x${item.quantity}</p>
							<p>
								<del class="text-muted">₫${product.price.toLocaleString("vi-VN")}</del>
								<span class="text-danger font-weight-bold ml-2">₫${finalPrice.toLocaleString(
									"vi-VN",
									{ minimumFractionDigits: 1 }
								)}</span>
							</p>
						</div>
					</div>
					${index !== array.length - 1 ? '<hr class="my-2" style="opacity: 0.3;">' : ""}
				`;
			})
			.join("");

		const actionButtons = `
			${
				order.status.status === 4 || order.status.status === 5
					? '<a href="#" class="btn btn-outline-secondary">Mua lại</a>'
					: `<a href="#" class="btn btn-outline-secondary cancel-order-btn" data-id="${order.id}">Hủy đơn</a>`
			}
			<a href="#" class="btn btn-primary ml-3">Xem chi tiết</a>
		`;

		const orderHtml = `
			<div class="order-item mb-4 p-4 border rounded">
				<div class="d-flex justify-content-end align-items-start mb-3">
					<span class="${statusInfo.class}">${statusInfo.text}</span>
				</div>
				<hr />
				${orderItemsHtml}
				<hr />
				<div class="d-flex justify-content-end align-items-center mt-3">
					<h5 class="mb-0 mr-3">Thành tiền:</h5>
					<h4 class="text-danger font-weight-bold mb-0">₫${order.totalAmount.toLocaleString(
						"vi-VN",
						{
							minimumFractionDigits: 1,
						}
					)}</h4>
				</div>
				<div class="d-flex justify-content-end gap-2 mt-3">
					${actionButtons}
				</div>
			</div>
		`;

		container.append(orderHtml);
	});

	$(document).on("click", ".cancel-order-btn", function (e) {
		e.preventDefault();
		const orderId = $(this).data("id");
		const $button = $(this);
		const $orderItem = $button.closest(".order-item");

		const token = localStorage.getItem("authToken");
		if (!token) {
			alert("Bạn cần đăng nhập để hủy đơn.");
			return;
		}

		if (!confirm("Bạn có chắc chắn muốn hủy đơn hàng này?")) return;

		callApi(
			`/order/cancel/${orderId}`,
			"PUT",
			null,
			function () {
				$orderItem
					.find(
						"span.text-warning, span.text-primary, span.text-info, span.text-success"
					)
					.removeClass()
					.addClass("text-danger")
					.text("Đã huỷ");

				$button.replaceWith(
					'<a href="#" class="btn btn-outline-secondary">Mua lại</a>'
				);
			},
			function (err) {
				alert("Hủy đơn hàng thất bại!");
				console.error("Lỗi khi huỷ:", err);
			},
			token
		);
	});
}
