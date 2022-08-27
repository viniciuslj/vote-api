package viniciuslj.vote.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Configuration
@EnableScheduling
@EnableWebMvc
@EnableFeignClients
public class VoteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteApiApplication.class, args);
	}

}
