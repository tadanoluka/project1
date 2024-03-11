package com.tadanoluka.project1.dto;

import com.tadanoluka.project1.database.entity.Station;
import lombok.Data;

@Data
public class StationDTO {
    private int esr;
    private String name;
    private String shortName;

    public StationDTO(Station station) {
        this.esr = station.getEsr();
        this.name = station.getName();
        this.shortName = station.getShortName();
    }
}
