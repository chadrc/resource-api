package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.options.CreateOptions;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {

    public Object create(CreateOptions options) throws ResourceServiceException;
}
