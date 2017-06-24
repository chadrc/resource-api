package com.chadrc.resourceapi.service;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.controller.FieldValue;
import com.chadrc.resourceapi.controller.PagingInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceService {

    OptionsResult options(String resourceName);

    ActionResult action(ActionClause clause) throws ResourceServiceException;

    CreateResult create(String resourceName, List<FieldValue> arguments) throws ResourceServiceException;

    GetResult get(String resourceName, String id) throws ResourceServiceException;

    ListResult getList(String resourceName, PagingInfo pagingInfo) throws ResourceServiceException;
}
