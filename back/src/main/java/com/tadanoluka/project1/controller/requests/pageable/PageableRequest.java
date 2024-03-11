package com.tadanoluka.project1.controller.requests.pageable;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Map;
import java.util.Optional;


public class PageableRequest {
    // Model fields
    @Setter
    @Getter
    @PositiveOrZero
    private int page;
    @Setter
    @Getter
    @Positive
    private int size;
    private String[] sort;
    private String[] filter;

    // Not model fields
    Sort sorts = null;
    Map<String, String> filters = null;

    public void setSort(String[] sort) {
        this.sorts = Sorts.getSort(sort);
    }

    public void setFilter(String[] filter) {
        this.filters = Filters.getFiltersMap(filter);
    }

    public Optional<Sort> getSorts() {
        return Optional.ofNullable(sorts);
    }

    public Optional<Map<String, String>> getFilters() {
        return Optional.ofNullable(filters);
    }
}
