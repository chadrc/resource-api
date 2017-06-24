package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.options.FieldValue;
import com.chadrc.resourceapi.options.PagingInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceService {

    OptionsResult options(String resourceName);

    CreateResult create(String resourceName, List<FieldValue> arguments) throws ResourceServiceException;

    GetResult get(String resourceName, String id) throws ResourceServiceException;

    ListResult getList(String resourceName, PagingInfo pagingInfo) throws ResourceServiceException;
}
