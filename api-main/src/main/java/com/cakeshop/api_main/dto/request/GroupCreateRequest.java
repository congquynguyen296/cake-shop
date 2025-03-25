package com.cakeshop.api_main.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GroupCreateRequest {

    String name;

    String description;

    Integer kind;

    Boolean isSystemRole;

    List<String> permissions = new ArrayList<>();
}
