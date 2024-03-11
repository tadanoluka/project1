package com.tadanoluka.project1.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;


@AllArgsConstructor
@Getter
public class PageResponse<T> {

    private final List<T> content;
    private final Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getNumberOfElements() != 0 ? ((long) page.getNumber() * page.getSize()) + 1 : 0,
                page.getNumberOfElements() != 0 ? ((long) page.getNumber() * page.getSize()) + page.getNumberOfElements() : 0);
        return new PageResponse<>(page.getContent(), metadata);
    }

    public record Metadata(
            int page,
            int size,
            long totalElements,
            int totalPages,
            int numberOfElementsInPage,
            long firstElementOrderNumber,
            long lastElementOrderNumber) {
    }
}
