package by.piskunou.springcourse.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Measurement")
@Data
@NoArgsConstructor
public class Measurement {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "value")
	@NotNull
	@Min(value = -100, message = "Value should be greater than -100")
	@Max(value = 100, message = "Value should be less than 100")
	private double value;
	
	@Column(name = "raining")
	@NotNull
	private boolean raining;
	
	@Column(name = "created_at")
	private LocalDate createdAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sensor_name", referencedColumnName = "name")
	@NotNull
	private Sensor sensor;
}
