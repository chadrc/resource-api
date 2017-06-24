package com.chadrc.resourceapi.options;

import com.chadrc.resourceapi.annotations.ResourceModel;
import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.services.ResourceService;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("/resource")
public class ResourceController {

    private static Logger log = Logger.getLogger(ResourceController.class);

    private static Map<String, Class> resourcesByName = new HashMap<>();

    private final ResourceService resourceService;

    static {
        Reflections reflections = new Reflections("com.chadrc.resourceapi");
        Set<Class<?>> resourceModels = reflections.getTypesAnnotatedWith(ResourceModel.class);

        for (Class model : resourceModels) {
            log.info("Registering Model: " + model.getName() + " as " + model.getSimpleName());
            resourcesByName.put(model.getSimpleName(), model);
        }
    }

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public String options(@RequestParam(required = false) String resource) {
        List<String> models = new ArrayList<>();

        for (Class model : resourcesByName.values()) {
            models.add(model.getCanonicalName());
        }

        return "Models:\n" + StringUtils.arrayToDelimitedString(models.toArray(), "\n");
    }

    @PutMapping
    public ResponseEntity<Object> create(@RequestBody CreateOptions options) {
        log.info("Attempting to create: " + options.getResourceName());
        log.info("\tWith arguments: " + options.getArguments());

        if (StringUtils.isEmpty(options.getResourceName())) {
            return ResponseEntity.badRequest().body("Resource Name Required.");
        }

        try {
            Object obj = resourceService.create(options);
            return ResponseEntity.ok(obj);
        } catch (ResourceServiceException resourceException) {
            return ResponseEntity.badRequest().body(resourceException.getMessage());
        } catch (Exception exception) {
            log.info("Error", exception);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> get(GetOneOptionsParam param) {
        GetOneOptions options = param.toImmutable();
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
}
