package by.piskunou.springcourse.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.piskunou.springcourse.models.Sensor;
import by.piskunou.springcourse.repositories.SensorsRepository;

@Service
@Transactional(readOnly = true)
public class SensorsService {
	private final SensorsRepository sensorsRepository;

	@Autowired
	public SensorsService(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	public Optional<Sensor> findByName(String name) {
		return sensorsRepository.findByName(name);
	}
	
	@Transactional
	public void save(Sensor sensor) {
		sensorsRepository.save(sensor);
	}
}
