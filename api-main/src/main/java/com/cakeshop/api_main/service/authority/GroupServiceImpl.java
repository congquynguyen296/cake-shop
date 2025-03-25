package com.cakeshop.api_main.service.authority;

import com.cakeshop.api_main.dto.request.GroupCreateRequest;
import com.cakeshop.api_main.dto.response.GroupResponse;
import com.cakeshop.api_main.model.Group;
import com.cakeshop.api_main.model.Permission;
import com.cakeshop.api_main.repository.internal.IGroupRepository;
import com.cakeshop.api_main.repository.internal.IPermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupServiceImpl implements IGroupService {

    IGroupRepository groupRepository;
    IPermissionRepository permissionRepository;

    @Override
    public GroupResponse createGroup(GroupCreateRequest request) {
        Group newGroup = Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isSystemRole(request.getIsSystemRole())
                .kind(request.getKind())
                .build();

        List<Permission> permissions = new ArrayList<>();
        request.getPermissions().forEach(permissionCode -> {
            permissions.add(permissionRepository.findByCode(permissionCode));
        });
        newGroup.setPermissions(permissions);
        groupRepository.save(newGroup);

        return GroupResponse.builder()
                .name(newGroup.getName())
                .description(newGroup.getDescription())
                .isSystemRole(newGroup.getIsSystemRole())
                .kind(newGroup.getKind())
                .permissions(permissions.stream().map(Permission::getCode).toList())
                .build();
    }
}
