package com.inexture.graphql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@Table(name = "PASSENGER_INFOS")
@NoArgsConstructor
public class PassengerInfo implements Serializable {
    @Id
    @GeneratedValue
    private int pId;
    private String name;
    private String email;
    private String source;
    private String destination;
    private LocalDate travelDate;
    private String pickupTime;
    private String arrivalTime;
    private double fare;
}
