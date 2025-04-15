import callApi, { getCommunes, getDistricts, getProvinces } from "./api.js";

$(document).ready(function () {
	$(".address-item__action--set-default--disabled").on(
		"click",
		function (event) {
			event.preventDefault();
			event.stopPropagation();
		}
	);

	const provinceSelect = $("#calc_shipping_provinces").next();
	const provinceTitle = provinceSelect.find(".current");
	const provinceList = provinceSelect.find(".list");

	const districtSelect = $("#calc_shipping_district").next();
	const districtTitle = districtSelect.find(".current");
	const districtList = districtSelect.find(".list");

	const communeSelect = $("#calc_shipping_commune").next();
	const communeTitle = communeSelect.find(".current");
	const communeList = communeSelect.find(".list");

	[provinceList, districtList, communeList].forEach((list) => {
		list.css({ width: "100%", "max-height": "300px", "overflow-y": "auto" });
	});

	districtSelect.addClass("disabled").css("pointer-events", "none");
	communeSelect.addClass("disabled").css("pointer-events", "none");

	function renderList(data, kind) {
		function renderHTML(data) {
			return data?.data?.content
				?.map(
					(item) =>
						`<option value="${item.name}" data-id="${item.id}" class="$${
							kind === 1
								? "province-option"
								: kind === 2
								? "district-option"
								: "commune-option"
						}">${item.name}</option>`
				)
				.join("");
		}

		if (kind === 1) {
			provinceList.html(renderHTML(data));
			provinceList.children().each((index, item) => {
				$(item).click(() => {
					provinceTitle.text($(item).text());
					$("#calc_shipping_provinces").data("id", $(item).data("id"));
					districtList.html("");
					communeList.html("");
					districtSelect.addClass("disabled").css("pointer-events", "none");
					communeSelect.addClass("disabled").css("pointer-events", "none");
					loadDistricts($(item).data("id"));
				});
			});
		} else if (kind === 2) {
			districtList.html(renderHTML(data));
			districtSelect.removeClass("disabled").css("pointer-events", "auto");
			districtList.children().each((index, item) => {
				$(item).click(() => {
					districtTitle.text($(item).text());
					$("#calc_shipping_district").data("id", $(item).data("id"));
					communeList.html("");
					communeSelect.addClass("disabled").css("pointer-events", "none");
					loadCommunes($(item).data("id"));
				});
			});
		} else if (kind === 3) {
			communeList.html(renderHTML(data));
			communeSelect.removeClass("disabled").css("pointer-events", "auto");
			communeList.children().each((index, item) => {
				$(item).click(() => {
					communeTitle.text($(item).text());
					$("#calc_shipping_commune").data("id", $(item).data("id"));
				});
			});
		}
	}

	function loadProvinces() {
		getProvinces((data) => renderList(data, 1));
	}

	function loadDistricts(provinceId) {
		getDistricts(provinceId, (data) => renderList(data, 2));
	}

	function loadCommunes(districtId) {
		getCommunes(districtId, (data) => renderList(data, 3));
	}

	function loadAddresses() {
		const token = localStorage.getItem("authToken");
		callApi(
			"/address/list",
			"GET",
			null,
			function (res) {
				if (res.result && res.data && res.data.content) {
					renderAddresses(res.data.content);
				}
			},
			function (err) {
				console.error("Lỗi khi lấy danh sách địa chỉ:", err);
			},
			token
		);
	}

	function renderAddresses(addresses) {
		const container = $(".address-list");
		container.empty();
		addresses.forEach((address) => {
			const isDefault = address.isDefault === "true";
			const customerName =
				address.customer.firstName + " " + address.customer.lastName;
			const html = `
			<div class="col-lg-12">
				<div class="address-item mb-4 d-flex justify-content-between">
					<div class="address-item__info">
						<h4 class="address-item__title">
							<span class="address-item__name">${customerName || ""}</span>
							<span class="address-item__separator"> | </span>
							<span class="address-item__email">${address.customer.email || ""}</span>
						</h4>
						<p class="address-item__location">
							${address.commune.name}, ${address.district.name}, ${address.province.name}
						</p>
						<p class="address-item__details">${address.details}</p>
						${isDefault ? '<div class="address-item__default-label">Mặc định</div>' : ""}
					</div>
					<div class="address-item__actions">
						<div class="address-item__actions-row">
							<a href="#" class="address-item__action address-item__action--update">Cập nhật</a>
							<a href="#" class="address-item__action address-item__action--delete">Xóa</a>
						</div>
						<a href="#" class="address-item__action address-item__action--set-default ${
							isDefault ? "address-item__action--set-default--disabled" : ""
						}">Thiết lập mặc định</a>
					</div>
				</div>
				</div>
			`;
			container.append(html);
		});
	}

	$("#addressForm").on("submit", function (e) {
		e.preventDefault();

		const provinceId = $("#calc_shipping_provinces").data("id");
		const districtId = $("#calc_shipping_district").data("id");
		const communeId = $("#calc_shipping_commune").data("id");
		const details = $("#billing_address_detail").val();
		const isDefault = $("#setDefaultAddress").is(":checked");
		const token = localStorage.getItem("authToken");

		const requestData = {
			provinceId,
			districtId,
			communeId,
			details,
			isDefault,
		};
		console.log(requestData);

		callApi(
			"/address/create",
			"POST",
			requestData,
			function (res) {
				alert("Địa chỉ đã được thêm thành công!");
				$("#addressModal").modal("hide");
				$("#addressForm")[0].reset();
				loadAddresses();
			},
			function (err) {
				alert("Thêm địa chỉ thất bại!");
				console.error("Lỗi:", err);
			},
			token
		);
	});

	loadAddresses();
	loadProvinces();
});
