package com.chadrc.resourceapi;

import com.chadrc.resourceapi.annotations.ResourceModel;
import com.chadrc.resourceapi.options.CreateOptions;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.*;

@RestController("/resource")
public class ResourceController {

    private static Logger log = Logger.getLogger(ResourceController.class);

    private static Map<String, Class> resourcesByName = new HashMap<>();

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

        Class c = resourcesByName.get(options.getResourceName());
        Constructor<?>[] constructors = c.getDeclaredConstructors();

        Constructor<?> selectedConstructor = null;
        for (Constructor<?> constructor : constructors) {
            Type[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length != options.getArguments().size()) {
                continue;
            }

            boolean allMatch = true;
            for (int i=0; i<paramTypes.length; i++) {
                if (paramTypes[i] != options.getArguments().get(i).getValue().getClass()) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                selectedConstructor = constructor;
                break;
            }
        }

        if (selectedConstructor != null) {
            Object[] args = new Object[options.getArguments().size()];
            for (int i=0; i<options.getArguments().size(); i++) {
                args[i] = options.getArguments().get(i).getValue();
            }

            try {
                Object obj = selectedConstructor.newInstance(args);
                log.info("Created: " + obj);
                return ResponseEntity.ok(obj);
            } catch (Exception e) {
                log.error("Failed to create resource.", e);
            }
        } else {
            return ResponseEntity.badRequest().body("Create action doesn't exist.");
        }

        return ResponseEntity.status(500).body(null);
    }
}
