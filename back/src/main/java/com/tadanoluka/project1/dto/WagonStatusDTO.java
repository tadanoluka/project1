package com.tadanoluka.project1.dto;

import com.tadanoluka.project1.database.entity.WagonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WagonStatusDTO {

    private String name;

    public WagonStatusDTO(WagonStatus wagonStatus) {
        this(wagonStatus.getName());
    }
}
