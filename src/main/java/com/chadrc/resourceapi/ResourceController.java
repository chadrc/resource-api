package com.chadrc.resourceapi;

import com.chadrc.resourceapi.annotations.ResourceModel;
import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.options.CreateOptions;
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

    @Autowired
    private ResourceService resourceService;

    static {
        Reflections reflections = new Reflections("com.chadrc.resourceapi");
        Set<Class<?>> resourceModels = reflections.getTypesAnnotatedWith(ResourceModel.class);

        for (Class model : resourceModels) {
            log.info("Registering Model: " + model.getName() + " as " + model.getSimpleName());
            resourcesByName.put(model.getSimpleName(), model);
        }
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
}
