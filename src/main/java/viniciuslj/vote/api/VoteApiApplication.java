package viniciuslj.vote.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
public class VoteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteApiApplication.class, args);
	}

}
