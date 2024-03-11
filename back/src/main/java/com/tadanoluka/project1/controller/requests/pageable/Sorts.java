package com.tadanoluka.project1.controller.requests.pageable;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class Sorts {

    public static Sort.Direction getSortDirection(String direction) {
        return switch (direction) {
            case "asc" -> Sort.Direction.ASC;
            case "desc" -> Sort.Direction.DESC;
            default -> Sort.Direction.DESC;
        };
    }

    public static Sort getSort(String[] sorts) {
        List<Sort.Order> ordersList = new ArrayList<>();
        if (sorts[0].contains(",")) {
            for (String sortOrder : sorts) {
                String[] sortOrderParams = sortOrder.split(",");
                if (sortOrderParams.length != 2) continue;
                ordersList.add(new Sort.Order(getSortDirection(sortOrderParams[1]), sortOrderParams[0]));
            }
        } else {
            ordersList.add(new Sort.Order(getSortDirection(sorts[1]), sorts[0]));
        }
        return Sort.by(ordersList);
    }
}
