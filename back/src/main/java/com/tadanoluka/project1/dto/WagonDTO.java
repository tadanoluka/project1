package com.tadanoluka.project1.dto;

import com.tadanoluka.project1.database.entity.Wagon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class WagonDTO {
    private long id;
    private StationDTO destinationStation;
    private StationDTO operationStation;
    private FreightDTO freight;
    private int consignee;
    private int weight;
    private WagonStatusDTO wagonStatus;

    public WagonDTO(Wagon wagon) {
        this(
            wagon.getId(),
            new StationDTO(wagon.getDestinationStation()),
            new StationDTO(wagon.getOperationStation()),
            new FreightDTO(wagon.getFreight()),
            wagon.getConsignee().getId(),
            wagon.getWeight(),
            new WagonStatusDTO(wagon.getWagonStatus())
        );
    }
}
