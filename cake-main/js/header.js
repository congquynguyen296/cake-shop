import callApi from "./api.js";

$(document).ready(function () {
	const token = localStorage.getItem("authToken");
	const $userSection = $("#user-section");

	if (token && $userSection.length) {
		callApi(
			"/customer/get-profile",
			"GET",
			null,
			function (res) {
				if (res.result && res.data) {
					const user = res.data;
					const userHtml = `
						<a href="#" class="user-email">${user.fullName || user.email}</a>
						<span class="arrow_carrot-down"></span>
						<ul class="user-dropdown">
							<li><a href="./profile.html">Tài khoản của tôi</a></li>
							<li><a href="./order.html">Đơn mua</a></li>
							<li><a href="#" id="logout-link">Đăng xuất</a></li>
						</ul>
					`;

					$userSection.html(userHtml);

					$("#logout-link").on("click", function (e) {
						e.preventDefault();

						callApi(
							"/auth/logout",
							"POST",
							null,
							function (res) {
								console.log("Logout success:", res);
								localStorage.removeItem("authToken");
								window.location.href = "index.html";
							},
							function (err) {
								console.error("Logout failed:", err);
								localStorage.removeItem("authToken");
								window.location.href = "index.html";
							}
						);
					});
				} else {
					console.warn("Không lấy được thông tin người dùng.");
				}
			},
			function (err) {
				console.error("Lỗi khi lấy thông tin user:", err);
			},
			token
		);
	}
});
