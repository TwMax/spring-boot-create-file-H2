package dev.Krzysztof.cardownload.repository;

import dev.Krzysztof.cardownload.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

}
