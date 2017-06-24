package com.chadrc.resourceapi.options;

class GetOneOptionsParam {
    private String resourceName = null;
    private String id = null;

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetOneOptions toImmutable() {
        return new GetOneOptions(resourceName, id);
    }
}
