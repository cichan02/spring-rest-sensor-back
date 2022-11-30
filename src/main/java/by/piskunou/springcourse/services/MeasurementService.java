package by.piskunou.springcourse.services;

import java.time.LocalDate;
import java.util.Iterator;
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
		long rainyDays = 0;
		Iterator<Measurement> iterator = findAll().iterator();
		Measurement measurement = null;
		LocalDate currentDate = null;
		if(iterator.hasNext()) {
			measurement = iterator.next();
			currentDate = measurement.getCreatedAt();
		}
		while(iterator.hasNext()) {
			long sum = measurement.isRaining() ? -1 : 1;
			while(iterator.hasNext()) {
				measurement = iterator.next();
				LocalDate otherDate = measurement.getCreatedAt();
				if(currentDate.equals(otherDate)) {
					sum = measurement.isRaining() ? --sum : ++sum;
				} else {
					currentDate = otherDate;
					break;
				}
			}
			if(sum < 0) {
				rainyDays++;
			}
		}
		return rainyDays;
	}
		
	@Transactional
	public void add(Measurement measurement) {
		measurement.setCreatedAt(LocalDate.now());
		
		measurementsRepository.save(measurement);
	}
}
