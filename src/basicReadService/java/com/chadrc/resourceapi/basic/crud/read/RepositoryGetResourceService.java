package com.chadrc.resourceapi.basic.crud.read;

import com.chadrc.resourceapi.basic.CRUDResult;
import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(method = RequestMethod.GET)
public class RepositoryGetResourceService implements ResourceService<GetRequest> {

    private ResourceRepositorySet resourceRepositorySet;

    @Autowired
    public void setResourceRepositorySet(ResourceRepositorySet resourceRepositorySet) {
        this.resourceRepositorySet = resourceRepositorySet;
    }

    @Override
    public Result fulfill(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        Object result;
        if (StringUtils.isEmpty(request.getId())) {
            result = getPage(resourceType, request);
        } else {
            result = getOne(resourceType, request);
        }
        return Resource.result(new CRUDResult(new GetResult(result)));
    }

    private Object getOne(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        Object resource = resourceRepositorySet.getRepository(resourceType).findOne(request.getId());
        if (resource == null) {
            throw Resource.notFound();
        }
        return new ArrayList<Object>() {{
            add(resource);
        }};
    }

    private Object getPage(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        ResourceRepository resourceRepository = resourceRepositorySet.getRepository(resourceType);
        Integer page = request.getPage();
        if (page == null) {
            page = 0;
        }
        Integer count = request.getCount();
        if (count == null) {
            count = 10;
        }
        if (request.getSort() == null || request.getSort().isEmpty()) {
            PageRequest pageRequest = new PageRequest(page, count);
            Page p = resourceRepository.findAll(pageRequest);
            return p.getContent();
        }
        List<Sort.Order> orderList = new ArrayList<>();
        for (GetSort getSort : request.getSort()) {
            orderList.add(new Sort.Order(convertDirection(getSort.getDirection()), getSort.getField()));
        }
        Sort sort = new Sort(orderList);
        PageRequest pageRequest = new PageRequest(page, count, sort);
        Page p = resourceRepository.findAll(pageRequest);
        return p.getContent();
    }

    private Sort.Direction convertDirection(SortDirection sortDirection) {
        return sortDirection == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}

