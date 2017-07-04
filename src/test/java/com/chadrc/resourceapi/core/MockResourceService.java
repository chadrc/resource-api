package com.chadrc.resourceapi.core;

import org.springframework.http.ResponseEntity;

public class MockResourceService implements ResourceService {
    @Override
    public ResponseEntity<GetResponse> fulfill(GetRequest request) {
        return ResponseEntity.ok(new GetResponse());
    }
}
