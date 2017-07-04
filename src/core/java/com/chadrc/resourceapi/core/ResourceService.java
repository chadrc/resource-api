package com.chadrc.resourceapi.core;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {
    ResponseEntity<GetResponse> fulfill(GetRequest request);
}
