package by.piskunou.springcourse.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.piskunou.springcourse.models.Measurement;
import by.piskunou.springcourse.models.Sensor;
import by.piskunou.springcourse.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {
	private final SensorsService sensorsService;

	public MeasurementValidator(SensorsService sensorsService) {
		this.sensorsService = sensorsService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Measurement.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Sensor sensor = ((Measurement) target).getSensor();
		
		if(sensor.getName() == null) {
			errors.rejectValue("sensor", "500", "The given sensor's name must not be null");
			return;
		}
		
		if(sensorsService.findByName(sensor.getName()).isEmpty()) {
			errors.rejectValue("sensor", "404", "Can't find a sensor");
		}
	}
}
