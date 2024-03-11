package com.tadanoluka.project1.database.services.types.pageable;

import com.tadanoluka.project1.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;

public interface PageableService<T, S> {

    PageResponse<S> findAllPaged(Pageable pageRequest) throws PropertyReferenceException;
    PageResponse<S> findAllFilteredPaged(Pageable pageRequest, Specification<T> specification) throws PropertyReferenceException;
}
