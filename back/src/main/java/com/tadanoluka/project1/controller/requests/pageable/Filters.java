package com.tadanoluka.project1.controller.requests.pageable;

import java.util.HashMap;
import java.util.Map;

public class Filters {

    public static Map<String, String> getFiltersMap(String[] filterStringArray) {
        Map<String, String> filtersMap = new HashMap<>();
        if (filterStringArray[0].contains(",")) {
            for (String filter : filterStringArray) {
                String[] filterParams = filter.split(",");
                if (filterParams.length != 2) continue;
                filtersMap.put(filterParams[0], filterParams[1]);
            }
        } else {
            filtersMap.put(filterStringArray[0], filterStringArray[1]);
        }
        return filtersMap;
    }
}
