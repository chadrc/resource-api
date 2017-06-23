package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.options.CreateOptions;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {

    Object create(CreateOptions options) throws ResourceServiceException;

    Object getById(String resourceName, String id) throws ResourceServiceException;
}
