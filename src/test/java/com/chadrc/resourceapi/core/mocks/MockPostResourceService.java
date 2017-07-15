package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.models.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(method = RequestMethod.POST)
public class MockPostResourceService implements ResourceService<PostRequest> {
    private List<Book> books = new ArrayList<>();

    @Override
    public Result fulfill(Class resourceType, PostRequest request) throws ResourceServiceThrowable {
        if (request == null || request.getFieldValues() == null) {
            return Resource.result(new DataResponse(null));
        }
        String title = (String) request.getFieldValues().get("title");
        String author = (String) request.getFieldValues().get("author");
        books.add(new Book(title, author));
        Map<String, Object> result = new HashMap<>();
        result.put("id", books.size() - 1);
        return Resource.result(result);
    }
}
