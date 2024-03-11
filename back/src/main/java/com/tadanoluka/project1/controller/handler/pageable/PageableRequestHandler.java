package com.tadanoluka.project1.controller.handler.pageable;


import com.tadanoluka.project1.controller.requests.pageable.PageableRequest;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.repository.UserRepository;
import com.tadanoluka.project1.database.services.types.pageable.PageableForUserService;
import com.tadanoluka.project1.database.services.types.pageable.PageableService;
import com.tadanoluka.project1.database.specifications.GenericSpecs;
import com.tadanoluka.project1.dto.PageResponse;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetails;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetailsImpl;
import java.util.Optional;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PageableRequestHandler<T, S> {

    private final UserRepository userRepository;
    private final GenericSpecs<T> genericSpecs;

    @Autowired
    public PageableRequestHandler(UserRepository userRepository, GenericSpecs<T> genericSpecs) {
        this.userRepository = userRepository;
        this.genericSpecs = genericSpecs;
    }

    public ResponseEntity<?> getResponseEntity(PageableService<T, S> pageableService, PageableRequest request) {
        try {
            if (pageableService instanceof PageableForUserService<T, S>) {
                return getResponseEntityForPageableForUserService(
                        (PageableForUserService<T, S>) pageableService, request);
            } else {
                return getResponseEntityForPageableService(pageableService, request);
            }
        } catch (InvalidDataAccessApiUsageException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filter param error.");
        } catch (PropertyReferenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private Pageable getPageable(PageableRequest request) {
        return request
            .getSorts()
            .map(sorts -> PageRequest.of(request.getPage(), request.getSize(), sorts))
            .orElseGet(() -> PageRequest.of(request.getPage(), request.getSize()));
    }

    private ResponseEntity<?> getResponseEntityForPageableService(
            PageableService<T, S> pageableService, PageableRequest request
    ) {
        Pageable pageRequest = getPageable(request);

        PageResponse<S> pageResponse;
        if (request.getFilters().isPresent()) {
            Specification<T> specification = genericSpecs.getAllILikeFiltered(request.getFilters().get());
            pageResponse = pageableService.findAllFilteredPaged(pageRequest, specification);
        } else {
            pageResponse = pageableService.findAllPaged(pageRequest);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pageResponse);
    }

    private ResponseEntity<?> getResponseEntityForPageableForUserService(
        PageableForUserService<T, S> pageableForUserService,
        PageableRequest request
    ) {
        CustomLdapUserDetails ldapUserDetails = (CustomLdapUserDetailsImpl) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        Optional<User> user = userRepository.findUserByUsername(ldapUserDetails.getUsername());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The user has not been found, try to re-login.");
        }

        Pageable pageRequest = getPageable(request);

        PageResponse<S> pageResponse;
        if (request.getFilters().isPresent()) {
            Specification<T> specification = genericSpecs.getAllILikeFiltered(request.getFilters().get());
            pageResponse = pageableForUserService.findAllForUserFilteredPaged(pageRequest, specification, user.get());
        } else {
            pageResponse = pageableForUserService.findAllForUserPaged(pageRequest, user.get());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pageResponse);
    }
}
