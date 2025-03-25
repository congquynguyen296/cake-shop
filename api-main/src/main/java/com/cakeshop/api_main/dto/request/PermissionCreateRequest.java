package com.cakeshop.api_main.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PermissionCreateRequest {

    String name;

    String description;

    String code;

    String action;
}
