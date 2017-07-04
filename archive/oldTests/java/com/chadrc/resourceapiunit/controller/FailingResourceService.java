package com.chadrc.resourceapiunit.controller;

import com.chadrc.resourceapi.controller.FieldValue;
import com.chadrc.resourceapi.controller.PagingInfo;
import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.service.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

class FailingResourceService implements ResourceService {
    @Override
    public OptionsResult options() {
        throw new NotImplementedException();
    }

    @Override
    public ActionResult action(ActionClause clause) throws ResourceServiceException {
        throw new NotImplementedException();
    }

    @Override
    public CreateResult create(String resourceName, List<FieldValue> arguments) throws ResourceServiceException {
        throw new NotImplementedException();
    }

    @Override
    public GetResult get(String resourceName, String id) throws ResourceServiceException {
        throw new NotImplementedException();
    }

    @Override
    public ListResult getList(String resourceName, PagingInfo pagingInfo) throws ResourceServiceException {
        throw new NotImplementedException();
    }
}
