import { login } from "./api.js";

$(document).ready(function () {
	$("#loginForm").on("keypress", function (e) {
		if (e.key === "Enter") {
			e.preventDefault();
			$("#loginForm").submit();
		}
	});
	// Xử lý form đăng nhập
	$("#loginForm").on("submit", function (e) {
		e.preventDefault();
		const username = $("#username").val().trim();
		const password = $("#password").val().trim();
		const $errorMsg = $("#loginError");
		$errorMsg.hide().text("");

		if (!username || !password) {
			$errorMsg.text("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.").show();
			return;
		}

		login(
			username,
			password,
			function () {
				window.location.href = "index.html"; // Điều hướng sau khi login
			},
			function (errorMessage) {
				$errorMsg.text("❌ " + errorMessage).show();
			}
		);
	});

	// Xử lý nút đăng nhập với Google/Facebook (chỉ là demo)
	$(".social-btn").on("click", function () {
		const provider = $(this).find("i").hasClass("fa-google")
			? "Google"
			: "Facebook";
		// alert(`Login with ${provider} clicked! (This is a demo)`);
	});
});
