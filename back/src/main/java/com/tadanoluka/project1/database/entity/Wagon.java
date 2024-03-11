package com.tadanoluka.project1.database.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "wagons")
public class Wagon {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id")
    private Station destinationStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_station_id")
    private Station operationStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freight_id")
    private Freight freight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consignee_id")
    private Consignee consignee;

    @Column(name = "weight")
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wagon_status_id")
    private WagonStatus wagonStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id")
    private User updatedByUser;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdateDateTime;

    @Builder
    public Wagon(Long id,
                 Station destinationStation,
                 Station operationStation,
                 Freight freight,
                 Consignee consignee,
                 int weight,
                 WagonStatus wagonStatus,
                 User updatedByUser) {
        this.id = id;
        this.destinationStation = destinationStation;
        this.operationStation = operationStation;
        this.freight = freight;
        this.consignee = consignee;
        this.weight = weight;
        this.wagonStatus = wagonStatus;
        this.updatedByUser = updatedByUser;
    }
}
