package team_alcoholic.jumo_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class JumoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumoServerApplication.class, args);
	}



}
