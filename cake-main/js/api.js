// 📌 api.js - Xử lý API chung
const API_BASE_URL = "http://localhost:8080/api/v1";

function getAuthToken() {
	console.log(localStorage.getItem("authToken"));
	return localStorage.getItem("authToken") || "";
}

export default function callApi(
	url,
	method = "GET",
	data = null,
	callback,
	errorCallback
) {
	let baseHeader = {
		"Content-Type": "application/json",
	};
	if (getAuthToken()) {
		baseHeader = { ...baseHeader, Authorization: `Bearer ${getAuthToken()}` };
	}
	$.ajax({
		url: `${API_BASE_URL}${url}`,
		type: method,
		headers: baseHeader,
		data: data ? JSON.stringify(data) : null,
		success: function (response) {
			console.log(`API Success: ${url}`, response);
			callback && callback(response);
		},
		error: function (err) {
			console.error(`API Error: ${url}`, err);
			errorCallback && errorCallback(err);
		},
	});
}

export function getProvinces(callback) {
	callApi("/nation/list?kind=1", "GET", null, callback);
}

export function getDistricts(provinceId, callback) {
	callApi(`/nation/list?kind=2&parentId=${provinceId}`, "GET", null, callback);
}

export function getCommunes(districtId, callback) {
	callApi(`/nation/list?kind=3&parentId=${districtId}`, "GET", null, callback);
}

export function getProducts(callback, categoryId = null, page = 0, size = 8) {
	let url = `/product/list?page=${page}&size=${size}`;
	if (categoryId) {
		url += `&categoryId=${categoryId}`;
	}
	callApi(url, "GET", null, callback);
}

export function getCategories(callback) {
	callApi("/category/list", "GET", null, callback);
}

export function login(username, password, onSuccess, onError) {
	const requestData = { username, password };

	callApi(
		"/auth/login",
		"POST",
		requestData,
		function (response) {
			if (response.result && response.data?.token) {
				const token = response.data.token;
				localStorage.setItem("authToken", token);
				console.log("✅ Token saved:", token);
				onSuccess && onSuccess(response);
			} else {
				const msg =
					response.message || "Đăng nhập thất bại. Không tìm thấy token.";
				console.error("❌", msg);
				onError && onError(msg);
			}
		},
		function (xhr) {
			let msg = "Đã xảy ra lỗi trong quá trình đăng nhập.";
			if (xhr.status === 500) {
				msg = "Sai username hoặc password.";
			} else if (xhr.responseJSON?.message) {
				msg = xhr.responseJSON.message;
			}
			console.error("❌", msg);
			onError && onError(msg);
		}
	);
}
