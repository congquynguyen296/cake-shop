package com.cakeshop.api_main.dto.response.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
public class ReviewStatsResponse {
    String productId;
    Long total;
    Double average;
    Map<Integer, Long> reviewCountByRate;
}
