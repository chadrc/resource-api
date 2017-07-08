package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.ResourceServiceThrowable;

import java.util.Map;

class PatchError extends ResourceServiceThrowable {
    PatchError(Map<String, String> fieldErrors) {
        super(400, fieldErrors);
    }
}
