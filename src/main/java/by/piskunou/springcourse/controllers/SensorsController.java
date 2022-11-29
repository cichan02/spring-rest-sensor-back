package by.piskunou.springcourse.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.piskunou.springcourse.dto.SensorDTO;
import by.piskunou.springcourse.models.Sensor;
import by.piskunou.springcourse.services.SensorsService;
import by.piskunou.springcourse.util.SensorValidator;
import by.piskunou.springcourse.util.SensorErrorResponse;
import by.piskunou.springcourse.util.SensorNotRegistrateException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
	private final SensorsService sensorsService;
	private final SensorValidator sensorValidator;
	private final ModelMapper modelMapper;
	
	@Autowired
	public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator,
							 ModelMapper modelMapper) {
		this.sensorsService = sensorsService;
		this.sensorValidator = sensorValidator;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping
	public List<SensorDTO> findAll() {
		return sensorsService.findAll()
							 .stream()
							 .map(this::convertToSensorDTO)
							 .toList();
	}

	@PostMapping("/registration")
	public ResponseEntity<HttpStatus> registrate(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
		Sensor sensor = convertToSensor(sensorDTO);
		sensorValidator.validate(sensor, bindingResult);
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorMessage.append(error.getField() + " - " + error.getDefaultMessage() + ". ");
			}
			
			throw new SensorNotRegistrateException(errorMessage.toString());
		}
		
		sensorsService.save(convertToSensor(sensorDTO));
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@ExceptionHandler
	private ResponseEntity<SensorErrorResponse> handleException(SensorNotRegistrateException sensorNotRegistrateException) {
		SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(sensorNotRegistrateException.getMessage(),
																		  System.currentTimeMillis());
		return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
	}
	
	private Sensor convertToSensor(SensorDTO sensorDTO) {
		return modelMapper.map(sensorDTO, Sensor.class);
	}
	
	private SensorDTO convertToSensorDTO(Sensor sensor) {
		return modelMapper.map(sensor, SensorDTO.class);
	}
}
