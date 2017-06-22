package com.chadrc.resourceapi;

import com.chadrc.resourceapi.annotations.ResourceModel;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.*;

@RestController("/resource")
public class ResourceController {

    private static Logger log = Logger.getLogger(ResourceController.class);

    private static Map<String, Class> resourcesByName = new HashMap<>();

    static {
        Reflections reflections = new Reflections("com.chadrc.resourceapi");
        Set<Class<?>> resourceModels = reflections.getTypesAnnotatedWith(ResourceModel.class);

        for (Class model : resourceModels) {
            resourcesByName.put(model.getName(), model);
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
}
