package com.tadanoluka.project1.database.specifications;

import com.tadanoluka.project1.database.entity.Role;
import com.tadanoluka.project1.database.entity.Station;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.entity.Wagon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class StationSpecsTest {

    @Test
    void getAllForUser_ReturnsSpecification() {
        Station station1 = new Station(1,1234567890, "TEST STATION 1", "TEST 1");
        Station station2 = new Station(2,1234567891, "TEST STATION 2", "TEST 1");
        Station station3 = new Station(3,1234567892, "TEST STATION 3", "TEST 1");
        Role role = new Role(1, "testUser");
        User user = new User(1L, "TestUser 1 Username", "TestUser 1 Fristname",
                "TestUser 1 Lastname", role, Set.of(station1, station2, station3));


        Specification<Station> specification = StationSpecs.getAllForUser(user);


        Assertions.assertNotNull(specification);
    }
}
