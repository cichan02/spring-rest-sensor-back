package by.piskunou.springcourse.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
	@Min(value = -100, message = "Value should be greater than -100")
	@Max(value = 100, message = "Value should be less than 100")
	private double value;
	
	private boolean raining;
	
	@NotNull
	private SensorDTO sensorDTO;
}
