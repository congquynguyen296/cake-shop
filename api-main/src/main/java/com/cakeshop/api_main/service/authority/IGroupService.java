package com.cakeshop.api_main.service.authority;

import com.cakeshop.api_main.dto.request.GroupCreateRequest;
import com.cakeshop.api_main.dto.response.GroupResponse;

public interface IGroupService {

    GroupResponse createGroup(GroupCreateRequest request);
}
