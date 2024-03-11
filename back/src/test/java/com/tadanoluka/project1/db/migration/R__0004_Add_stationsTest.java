package com.tadanoluka.project1.db.migration;

import db.migration.R__0004_Add_stations;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class R__0004_Add_stationsTest {

    @Test
    public void getStationsFromRzdApi() {
        R__0004_Add_stations r__0004AddStations = new R__0004_Add_stations();
        List<R__0004_Add_stations.RzdStation> list = r__0004AddStations.getRzdStations();
        assertNotNull(list);
        R__0004_Add_stations.RzdStation rzdStation = list.getFirst();
        assertNotNull(rzdStation);
        assertNotNull(rzdStation.getName());
        assertNotNull(rzdStation.getShort_name());
    }
}
