package com.tadanoluka.project1.database.specifications;


import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.entity.Wagon;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class StationSpecs {

    public static Specification<Station> getAllForUser(User user) {
        Set<Station> operationStationsAccesses = user.getOperationStationsAccesses();
        return (root, query, cb) -> root.in(operationStationsAccesses);
    }
}
