package by.piskunou.springcourse;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringRestSensorBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringRestSensorBackApplication.class, args);
	}

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
