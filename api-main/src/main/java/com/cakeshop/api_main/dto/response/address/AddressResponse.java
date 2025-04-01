package com.cakeshop.api_main.dto.response.address;

import com.cakeshop.api_main.dto.response.nation.NationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AddressResponse {
    @Schema(description = "id")
    String id;

    @Schema(description = "province")
    NationResponse province;

    @Schema(description = "district")
    NationResponse district;

    @Schema(description = "commune")
    NationResponse commune;

    @Schema(description = "details")
    String details;

    @Schema(description = "isDefault")
    String isDefault;
}
