import callApi from "./api.js";

document.addEventListener("DOMContentLoaded", function () {
	const token = localStorage.getItem("authToken");

	if (token) {
		callApi(
			"/customer/get-profile",
			"GET",
			null,
			function (res) {
				if (res.result && res.data) {
					const user = res.data;

					document.getElementById("avatar").src =
						user.avatarPath ||
						"https://res.cloudinary.com/dcxgx3ott/image/upload/v1744349601/Avatar_UTE_hddqyo.png";
					document.getElementById("username").value = user.username || "";
					document.getElementById("lastName").value = user.lastName || "";
					document.getElementById("firstName").value = user.firstName || "";
					document.getElementById("email").value = user.email || "";
					document.getElementById("phone").value = user.phone || "";

					if (user.dateOfBirth) {
						const dob = new Date(user.dateOfBirth);
						const formatted = dob.toISOString().split("T")[0];
						document.getElementById("dob").value = formatted;
					}
				} else {
					console.warn("❌ Không lấy được thông tin user");
				}
			},
			function (err) {
				console.error("❌ Lỗi khi gọi API:", err);
			},
			token
		);
	}
});
