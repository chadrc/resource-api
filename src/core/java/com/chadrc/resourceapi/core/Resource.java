package com.chadrc.resourceapi.core;

public class Resource {

    public static Result result(Object result) {
        return new Result(result);
    }

    public static Result emptyResult() {
        return new Result(null);
    }

    public static ResourceServiceThrowable notFound() {
        return new ResourceServiceThrowable(404);
    }
}
