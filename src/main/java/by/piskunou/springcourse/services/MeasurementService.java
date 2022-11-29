package by.piskunou.springcourse.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.piskunou.springcourse.models.Measurement;
import by.piskunou.springcourse.repositories.MeasurementsRepository;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
	private final MeasurementsRepository measurementsRepository;

	@Autowired
	public MeasurementService(MeasurementsRepository measurementsRepository) {
		this.measurementsRepository = measurementsRepository;
	}
	
	public List<Measurement> findAll() {
		return measurementsRepository.findAll();
	}
	
	public long rainyDaysCount() {
		return measurementsRepository.findAll()
									 .stream()
									 .filter(Measurement::isRaining)
									 .count();
	}
		
	@Transactional
	public void add(Measurement measurement) {
		measurement.setCreatedAt(LocalDateTime.now());
		
		measurementsRepository.save(measurement);
	}
}
