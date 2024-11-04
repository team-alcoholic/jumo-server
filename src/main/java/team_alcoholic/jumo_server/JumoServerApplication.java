package team_alcoholic.jumo_server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@OpenAPIDefinition(servers = {
	@Server(url = "/api", description = "Default Server URL")
})
@EnableJpaAuditing
@SpringBootApplication
public class JumoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumoServerApplication.class, args);
	}



}
