package com.chadrc.resourceapi.store;

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
