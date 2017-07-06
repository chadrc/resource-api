package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
interface ResourceService<T> {
    Object fulfill(String resourceName, T request);

    RequestMethod getRequestMethod();
}
