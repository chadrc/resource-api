package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.options.CreateOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ResourceService {

    Map<String, Object> options(String resourceName);

    Object create(CreateOptions options) throws ResourceServiceException;

    Object getById(String resourceName, String id) throws ResourceServiceException;
}
