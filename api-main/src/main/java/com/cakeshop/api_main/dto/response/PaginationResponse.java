package com.cakeshop.api_main.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> implements Serializable {
    @Schema(description = "content")
    List<T> content;

    @Schema(description = "total elements")
    long totalElements;

    @Schema(description = "total pages")
    int totalPages;
}
