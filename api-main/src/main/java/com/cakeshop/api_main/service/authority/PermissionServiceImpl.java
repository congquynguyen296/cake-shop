package com.cakeshop.api_main.service.authority;

import com.cakeshop.api_main.dto.request.PermissionCreateRequest;
import com.cakeshop.api_main.dto.response.BaseResponse;
import com.cakeshop.api_main.model.Permission;
import com.cakeshop.api_main.repository.internal.IPermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements IPermissionService {

    IPermissionRepository permissionRepository;

    @Override
    public String createPermission(PermissionCreateRequest request) {
        Permission newPermission = Permission.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .action(request.getAction())
                .build();
        return permissionRepository.save(newPermission).getCode();
    }
}
