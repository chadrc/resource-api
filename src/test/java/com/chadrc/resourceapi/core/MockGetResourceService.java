package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockGetResourceService implements ResourceService {

    private List<Book> books = new ArrayList<Book>() {{
        add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling"));
        add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling"));
        add(new Book("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling"));
        add(new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling"));
        add(new Book("Harry Potter and the Order of the Phoenix", "J.K. Rowling"));
        add(new Book("Harry Potter and the Half-blood Prince", "J.K. Rowling"));
        add(new Book("Harry Potter and the Deathly Hollows", "J.K. Rowling")); // 6

        add(new Book("The Hobbit: There and Back Again", "J.R.R. Tolkien"));
        add(new Book("Lord of the Rings: Fellowship of the Ring", "J.R.R. Tolkien"));
        add(new Book("Lord of the Rings: Fellowship of the Ring", "J.R.R. Tolkien"));
        add(new Book("Lord of the Rings: Fellowship of the Ring", "J.R.R. Tolkien")); // 10

        add(new Book("A Game of Thrones", "George R. Martin"));
        add(new Book("A Clash of Kings", "George R. Martin"));
        add(new Book("A Storm of Swords", "George R. Martin"));
        add(new Book("A Feast for Crows", "George R. Martin"));
        add(new Book("A Dance with Dragons", "George R. Martin"));
        add(new Book("The Winds of Winter", "George R. Martin"));
        add(new Book("A Dream of Spring", "George R. Martin"));
    }};

    @Override
    public Result fulfill(GetRequest request) {
        return new Result(books.get(Integer.parseInt(request.getId())));
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }
}