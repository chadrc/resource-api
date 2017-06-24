package com.chadrc.resourceapi.options;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.services.ResourceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping(path = "/resource")
public class ResourceController {

    private static Logger log = Logger.getLogger(ResourceController.class);

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public Map<String, Object> options(@RequestParam(required = false) String resourceName) {
        return resourceService.options(resourceName);
    }

    @PutMapping
    public ResponseEntity<Object> create(@RequestBody CreateOptions options) {
        log.info("Attempting to create: " + options.getResourceName());
        log.info("\tWith arguments: " + options.getArguments());

        if (StringUtils.isEmpty(options.getResourceName())) {
            return ResponseEntity.badRequest().body("Resource Name Required.");
        }

        try {
            Object obj = resourceService.create(options.getResourceName(), options.getArguments());
            return ResponseEntity.ok(obj);
        } catch (ResourceServiceException resourceException) {
            return ResponseEntity.badRequest().body(resourceException.getMessage());
        } catch (Exception exception) {
            log.info("Error", exception);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> get(GetOptions options) {
        log.info("Attempting to retrieve resource " + options.getResourceName() + " with id " + options.getId());

        if (StringUtils.isEmpty(options.getResourceName())) {
            return ResponseEntity.badRequest().body("Param 'resourceName' required.");
        }

        if (StringUtils.isEmpty(options.getId())) {
            return ResponseEntity.badRequest().body("Param 'id' required.");
        }

        try {
            Object obj = resourceService.getById(options.getResourceName(), options.getId());
            if (obj == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(obj);
        } catch (ResourceServiceException resourceException) {
            return ResponseEntity.badRequest().body(resourceException.getMessage());
        } catch (Exception exception) {
            log.info("Error", exception);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Object> list(ListOptions options) {
        log.info("Attempting to retrieve list of resource " + options.getResourceName());

        if (StringUtils.isEmpty(options.getResourceName())) {
            return ResponseEntity.badRequest().body("Param 'resourceName' required.");
        }

        try {
            Object obj = resourceService.getList(options.getResourceName(), options.getPagingInfo());
            if (obj == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(obj);
        } catch (ResourceServiceException resourceException) {
            return ResponseEntity.badRequest().body(resourceException.getMessage());
        } catch (Exception exception) {
            log.error("Error", exception);
            return ResponseEntity.status(500).body(null);
        }
    }
}
