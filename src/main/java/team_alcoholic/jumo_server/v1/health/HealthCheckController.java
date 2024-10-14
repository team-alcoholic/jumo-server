package team_alcoholic.jumo_server.v1.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController("health/v1")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}