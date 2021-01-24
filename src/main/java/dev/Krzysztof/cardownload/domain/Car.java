package dev.Krzysztof.cardownload.domain;

        import lombok.*;
        import javax.persistence.*;
        import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cars")
public class Car {

    public Car(Long id, String name, LocalDate dateOfPurchase, String color) {
        this.id = id;
        this.name = name;
        this.dateOfPurchase = dateOfPurchase;
        this.color = color;
    }

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
