package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.models.Book;
import com.chadrc.resourceapi.core.PostResourceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPostResourceService implements PostResourceService<PostRequest> {
    private List<Book> books = new ArrayList<>();

    @Override
    public Result fulfill(String resourceName, PostRequest request) throws ResourceServiceThrowable {
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
