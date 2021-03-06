package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.models.Book;
import com.chadrc.resourceapi.models.Saga;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping(method = RequestMethod.GET)
public class MockGetResourceService implements ResourceService<GetRequest> {

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

    private List<Saga> sagas = new ArrayList<Saga>() {{
        add(new Saga("Harry Potter", Arrays.asList(0, 1, 2, 3, 4, 5, 6)));
        add(new Saga("Lord of the Rings", Arrays.asList(8, 9, 10)));
        add(new Saga("A Song of Ice and Fire", Arrays.asList(11, 12, 13, 14, 15, 16, 17)));
    }};

    public MockGetResourceService() throws Throwable {

    }

    @Override
    public Result fulfill(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        if (request == null || request.getId() == null) {
            return Resource.emptyResult();
        }
        int index = Integer.parseInt(request.getId());
        if (index >= books.size()) {
            throw Resource.notFound();
        }
        switch (resourceType.getSimpleName()) {
            case "Book":
                return Resource.result(books.get(Integer.parseInt(request.getId())));
            case "Saga":
                return Resource.result(sagas.get(Integer.parseInt(request.getId())));
        }
        return Resource.emptyResult();
    }
}
