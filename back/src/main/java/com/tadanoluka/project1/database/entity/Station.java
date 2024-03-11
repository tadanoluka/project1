package com.tadanoluka.project1.database.entity;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "esr")
    private int esr;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    public Station(int esr, String name, String shortName) {
        this.esr = esr;
        this.name = name;
        this.shortName = shortName;
    }
}
