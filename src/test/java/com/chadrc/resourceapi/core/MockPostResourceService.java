package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MockPostResourceService implements PostResourceService<PostRequest> {
    private List<Book> books = new ArrayList<>();

    @Override
    public Result fulfill(PostRequest request) {
        if (request == null || request.getResourceName() == null || request.getFieldValues() == null) {
            return new Result(null);
        }
        String title = (String) request.getFieldValues().get("title");
        String author = (String) request.getFieldValues().get("author");
        books.add(new Book(title, author));
        Map<String, Object> result = new HashMap<>();
        result.put("id", books.size() - 1);
        return new Result(result);
    }

    @Override
    public Class getRequestClass() {
        return PostRequest.class;
    }
}
