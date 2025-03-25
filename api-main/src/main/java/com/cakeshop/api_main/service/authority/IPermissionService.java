package com.cakeshop.api_main.service.authority;

import com.cakeshop.api_main.dto.request.PermissionCreateRequest;

public interface IPermissionService {
    String createPermission(PermissionCreateRequest request);
}
