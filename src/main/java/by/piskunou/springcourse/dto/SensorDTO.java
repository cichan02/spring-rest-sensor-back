package by.piskunou.springcourse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {
	@NotBlank
	@Size(min = 3, max = 30, message = "String should be between 3 and 30 chars")
	private String name;
}
