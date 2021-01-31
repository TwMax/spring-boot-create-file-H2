package dev.Krzysztof.cardownload.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cars")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nazwa")
    private String name;

    @Column(name = "Data_zakupu")
    private LocalDate dateOfPurchase;

    @Column(name = "Color")
    private String color;

}
