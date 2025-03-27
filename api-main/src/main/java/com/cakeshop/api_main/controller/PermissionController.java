package com.cakeshop.api_main.controller;

import com.cakeshop.api_main.dto.request.PermissionCreateRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.service.authority.IPermissionService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionController {

    IPermissionService permissionService;

    @PostMapping("/create")
    BaseResponse<String> createPermission(@RequestBody PermissionCreateRequest request) {
        return BaseResponse.<String>builder()
                .result(true)
                .code(200)
                .message("Create group successfully")
                .data(permissionService.createPermission(request))
                .build();
    }
}
