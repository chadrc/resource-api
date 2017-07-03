package com.chadrc.resourceapiunit.store;

import com.chadrc.resourceapi.store.RepositoryResourceStore;
import com.chadrc.resourceapi.store.ResourceRepository;
import org.junit.Test;

import java.util.ArrayList;

public class StoreTests {

    @Test(expected = Exception.class)
    public void constructWithRepositoryWithoutResourceClassFails() {
        RepositoryResourceStore store = new RepositoryResourceStore(new ArrayList<ResourceRepository>() {{
            add(new TestRepository());
        }});
    }
}
