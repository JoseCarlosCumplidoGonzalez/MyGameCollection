package dam.finalproyect.mygamecollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "dam.finalproyect.mygamecollection")
@EntityScan(basePackages = "dam.finalproyect.mygamecollection.model")
@EnableJpaRepositories(basePackages = "dam.finalproyect.mygamecollection.repositories")
public class MygamecollectionApplication {
	public static void main(String[] args) {
		SpringApplication.run(MygamecollectionApplication.class, args);
	}
}
