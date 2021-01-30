package dev.Krzysztof.cardownload.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
@Builder
public class CarEntity {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "Nazwa")
    private String name;

    @Column(name = "Data_zakupu")
    private LocalDate dateOfPurchase;

    @Column(name = "Color")
    private String color;

}
