package com.tadanoluka.project1.database.specifications;


import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.entity.Wagon;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Component
public class GenericSpecs<T> {

    public Specification<T> getAllILikeFiltered(Map<String, String> filterMap) throws PathElementException {
        return ((root, query, cb) -> {
            try {
                Object[] predicatesObjects = filterMap
                        .entrySet()
                        .stream()
                        .map(entry -> {
                            String[] pathsToColumn = entry.getKey().split("_");
                            Path<Object> pathToColumn = root.get(pathsToColumn[0]);

                            pathsToColumn = Arrays.copyOfRange(pathsToColumn, 1, pathsToColumn.length);
                            for (String columnName : pathsToColumn) {
                                pathToColumn = pathToColumn.get(columnName);
                            }

                            return cb.like(
                                    cb.lower(pathToColumn.as(String.class)),
                                    "%" + entry.getValue().toLowerCase(Locale.ROOT) + "%");
                        })
                        .toArray();
                Predicate[] predicates = Arrays.copyOf(predicatesObjects, predicatesObjects.length, Predicate[].class);
                return cb.and(predicates);
            } catch (PathElementException e) {
                throw new PathElementException(e.getMessage());
            }
        });
    }
}
