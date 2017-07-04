package com.chadrc.resourceapi.core;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ResourceController {
    private static Logger log = Logger.getLogger(ResourceController.class);

    private ResourceService resourceService;

    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<Result> get() {
        return ResponseEntity.ok(resourceService.fulfill());
    }

    @PostMapping
    public ResponseEntity<Result> post() {
        return ResponseEntity.ok(resourceService.fulfill());
    }
}
