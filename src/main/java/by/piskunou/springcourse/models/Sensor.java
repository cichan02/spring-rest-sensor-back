package by.piskunou.springcourse.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Sensor")
@Data
@NoArgsConstructor
public class Sensor {
	@Id
	@Column(name = "name")
	@NotBlank
	@Size(min = 3, max = 30, message = "String should be between 3 and 30 chars")
	String name;
	
	@OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
	private List<Measurement> measurements;
}
