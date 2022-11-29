package by.piskunou.springcourse.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
	@NotNull
	@Min(value = -100, message = "Value should be higher or equal than -100")
	@Max(value = 100, message = "Value should be lower or equal than 100")
	private double value;
	
	@NotNull
	private boolean raining;
	
	@NotNull
	private SensorDTO sensor;
}
