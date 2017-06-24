package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.options.FieldValue;
import com.chadrc.resourceapi.options.PagingInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ResourceService {

    Map<String, Object> options(String resourceName);

    Object create(String resourceName, List<FieldValue> arguments) throws ResourceServiceException;

    Object get(String resourceName, String id) throws ResourceServiceException;

    Object getList(String resourceName, PagingInfo pagingInfo) throws ResourceServiceException;
}
