package com.tadanoluka.project1.database.services.types.pageable;

import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;

public interface PageableForUserService<T, S> extends PageableService<T, S> {

    PageResponse<S> findAllForUserPaged(Pageable pageRequest, User user) throws PropertyReferenceException;
    PageResponse<S> findAllForUserFilteredPaged(Pageable pageRequest, Specification<T> specification, User user) throws PropertyReferenceException;
}
