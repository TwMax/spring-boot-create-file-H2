package dev.Krzysztof.cardownload.repository;

import dev.Krzysztof.cardownload.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

}
