package com.tadanoluka.project1.controller.requests.pageable;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class PageableRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void newPageableRequest_PageAndSizeParamsAreValid_ValidatorReturnsEmptyViolationsSet() {
        int page = 0;
        int size = 5;
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setPage(page);
        pageableRequest.setSize(size);

        Set<ConstraintViolation<PageableRequest>> violations = validator.validate(pageableRequest);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void newPageableRequest_PageParamIsValidAndSizeParamIsNotValid_ValidatorReturnsViolation() {
        int page = 0;
        int size = 0;
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setPage(page);
        pageableRequest.setSize(size);

        List<ConstraintViolation<PageableRequest>> violations = validator.validate(pageableRequest).stream().toList();

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(violations.get(0).getInvalidValue(), size);
    }

    @Test
    void newPageableRequest_PageParamIsNotValidAndSizeParamIsValid_ValidatorReturnsViolation() {
        int page = -1;
        int size = 5;
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setPage(page);
        pageableRequest.setSize(size);

        List<ConstraintViolation<PageableRequest>> violations = validator.validate(pageableRequest).stream().toList();

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(violations.get(0).getInvalidValue(), page);
    }

    @Test
    void newPageableRequest_PageAndSizeParamsAreNotValid_ValidatorReturnsViolations() {
        int page = -1;
        int size = 0;
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setPage(page);
        pageableRequest.setSize(size);

        List<ConstraintViolation<PageableRequest>> violations = validator.validate(pageableRequest).stream().toList();

        Assertions.assertTrue(violations.size() > 1);
    }
}
