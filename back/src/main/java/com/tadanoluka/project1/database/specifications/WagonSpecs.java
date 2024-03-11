package com.tadanoluka.project1.database.specifications;

import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.entity.Wagon;

import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public class WagonSpecs {


    public static Specification<Wagon> getAllForUser(User user) {
        Set<Station> operationStationsAccesses = user.getOperationStationsAccesses();
        return (root, query, cb) -> root.get("operationStation").in(operationStationsAccesses);
    }
}
