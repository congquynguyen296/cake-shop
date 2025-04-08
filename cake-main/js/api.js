// 📌 api.js - Xử lý API chung
const API_BASE_URL = "http://localhost:8080/api/v1";

function getAuthToken() {
	console.log(localStorage.getItem("authToken"));
	return localStorage.getItem("authToken") || "";
}

function callApi(url, method = "GET", data = null, callback) {
	$.ajax({
		url: `${API_BASE_URL}${url}`,
		type: method,
		headers: {
			Authorization: `Bearer ${getAuthToken()}`,
			"Content-Type": "application/json",
		},
		data: data ? JSON.stringify(data) : null,
		success: function (response) {
			console.log(`API Success: ${url}`, response);
			callback(response);
		},
		error: function (err) {
			console.error(`API Error: ${url}`, err);
		},
	});
}

function getProvinces(callback) {
	callApi("/nation/list?kind=1", "GET", null, callback);
}

function getDistricts(provinceId, callback) {
	callApi(`/nation/list?kind=2&parentId=${provinceId}`, "GET", null, callback);
}

function getCommunes(districtId, callback) {
	callApi(`/nation/list?kind=3&parentId=${districtId}`, "GET", null, callback);
}

function getProducts(callback) {
	callApi("/product/list", "GET", null, callback);
}
