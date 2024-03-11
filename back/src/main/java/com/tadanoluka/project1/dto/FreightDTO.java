package com.tadanoluka.project1.dto;

import com.tadanoluka.project1.database.entity.Freight;
import lombok.Data;

@Data
public class FreightDTO {
    private int etsng;
    private String name;
    private String shortName;
    private String groupName;

    public FreightDTO(Freight freight) {
        this.etsng = freight.getEtsng();
        this.name = freight.getName();
        this.shortName = freight.getShortName();
        this.groupName = freight.getFreightsGroup().getName();
    }
}
