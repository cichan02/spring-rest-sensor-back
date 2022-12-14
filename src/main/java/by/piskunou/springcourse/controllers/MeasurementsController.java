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

import by.piskunou.springcourse.dto.MeasurementDTO;
import by.piskunou.springcourse.models.Measurement;
import by.piskunou.springcourse.services.MeasurementService;
import by.piskunou.springcourse.util.MeasurementErrorResponse;
import by.piskunou.springcourse.util.MeasurementNotAddException;
import by.piskunou.springcourse.util.MeasurementValidator;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
	private final MeasurementService measurementService;
	private final MeasurementValidator measurementValidator;
	private final ModelMapper modelMapper;
	
	@Autowired
	public MeasurementsController(MeasurementService measurementService, MeasurementValidator measurementValidator,
								  ModelMapper modelMapper) {
		this.measurementService = measurementService;
		this.measurementValidator = measurementValidator;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping
	public List<MeasurementDTO> findAll() {
		return measurementService.findAll()
								 .stream()
								 .map(this::convertToMeasurementDTO)
								 .toList();
	}
	
	@GetMapping("/rainy-days")
	public long rainyDaysCount() {
		return measurementService.rainyDaysCount();
	}

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
		if(bindingResult.hasFieldErrors("sensor")) {
			FieldError error = bindingResult.getFieldError("sensor");
			throw new MeasurementNotAddException(error.getField() + " - " + error.getDefaultMessage() + ". "); 
		}
		
		Measurement measurement = convertToMeasurement(measurementDTO);
		measurementValidator.validate(measurement, bindingResult);
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorMessage.append(error.getField() + " - " + error.getDefaultMessage() + ". ");
			}
			
			throw new MeasurementNotAddException(errorMessage.toString());
		}
		
		measurementService.add(convertToMeasurement(measurementDTO));
		
		return ResponseEntity.ok(HttpStatus.CREATED);
	}
	
	@ExceptionHandler
	private ResponseEntity<MeasurementErrorResponse> handleExcepiton(MeasurementNotAddException measurementNotAddException) {
		MeasurementErrorResponse measurementErrorResponse =
				new MeasurementErrorResponse(measurementNotAddException.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(measurementErrorResponse, HttpStatus.NOT_FOUND);
	}
	
	private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
		return modelMapper.map(measurementDTO, Measurement.class);
	}
	
	private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
		return modelMapper.map(measurement, MeasurementDTO.class);
	}
}
