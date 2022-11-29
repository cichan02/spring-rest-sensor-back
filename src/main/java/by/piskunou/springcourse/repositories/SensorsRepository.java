package by.piskunou.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.piskunou.springcourse.models.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, String> {}
