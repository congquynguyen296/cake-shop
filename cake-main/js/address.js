$(document).ready(function () {
	$(".address-item__action--set-default--disabled").on(
		"click",
		function (event) {
			event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ <a>
			event.stopPropagation(); // Ngăn chặn sự kiện click lan ra các phần tử cha
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

	// Disable dropdowns khi chưa có dữ liệu
	districtSelect.addClass("disabled").css("pointer-events", "none");
	communeSelect.addClass("disabled").css("pointer-events", "none");

	function renderList(data, kind) {
		function renderHTML(data) {
			return data?.data?.content
				?.map(
					(item) =>
						`<option class="${
							kind === 1 ? "province" : kind === 2 ? "district" : "commune"
						}-option" data-id=${item.id} style="padding-inline: 10px;" value="${
							item.name
						}">${item.name}</option>`
				)
				.join("");
		}

		if (kind === 1) {
			provinceList.html(renderHTML(data));
			provinceList.children().each((index, item) => {
				$(item).click(() => {
					provinceTitle.text($(item).text());
					districtList.html(""); // Xóa danh sách cũ
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

	loadProvinces();
});
