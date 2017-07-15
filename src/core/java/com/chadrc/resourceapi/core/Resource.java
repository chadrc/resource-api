package com.chadrc.resourceapi.core;

public final class Resource {

    private Resource() {}

    public static ResourceOptionsSection options() {
        return new ResourceOptionsSection();
    }

    public static Result result(Object result) {
        return new Result(result);
    }

    public static Result emptyResult() {
        return new Result(null);
    }

    public static ResourceServiceThrowable badRequest() {
        return new ResourceServiceThrowable(400);
    }

    public static ResourceServiceThrowable notFound() {
        return new ResourceServiceThrowable(404);
    }
}
