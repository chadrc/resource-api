package com.chadrc.resourceapi.options;

import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> options(@RequestParam(required = false) String resourceName) {
        return ResponseEntity.ok(resourceService.options(resourceName).getOptions());
    }

    @PostMapping
    public ResponseEntity<Object> action(@RequestBody ActionOptions options) {
        log.info("Attempting to perform action: " + options.getActionName() + " on resource " + options.getResourceName());

        if (StringUtils.isEmpty(options.getResourceName())) {
            return ResponseEntity.badRequest().body("Resource Name Required.");
        }

        if (StringUtils.isEmpty(options.getActionName())) {
            return ResponseEntity.badRequest().body("Action Name Required.");
        }

        try {
            ActionResult obj = resourceService.action(new ActionClause(
                    options.getActionName(), options.getResourceName(), options.getResourceId(), options.getArguments()
            ));
            return ResponseEntity.ok(obj.getResult());
        } catch (ResourceServiceException resourceException) {
            return ResponseEntity.badRequest().body(resourceException.getMessage());
        } catch (Exception exception) {
            log.info("Error", exception);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> create(@RequestBody CreateOptions options) {
        log.info("Attempting to create: " + options.getResourceName());
        log.debug("\tWith arguments: " + options.getArguments());

        if (StringUtils.isEmpty(options.getResourceName())) {
            return ResponseEntity.badRequest().body("Resource Name Required.");
        }

        try {
            CreateResult obj = resourceService.create(options.getResourceName(), options.getArguments());
            return ResponseEntity.ok(obj.getCreatedResource());
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
            GetResult obj = resourceService.get(options.getResourceName(), options.getId());
            if (obj == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(obj.getResource());
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
            ListResult obj = resourceService.getList(options.getResourceName(), options.getPagingInfo());
            if (obj == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(obj.getResourcePage());
        } catch (ResourceServiceException resourceException) {
            return ResponseEntity.badRequest().body(resourceException.getMessage());
        } catch (Exception exception) {
            log.error("Error", exception);
            return ResponseEntity.status(500).body(null);
        }
    }
}
