package cv.pn.processmanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Microservice Name",
		description = "Description of Micro Services",
		version = "v1",
		contact = @Contact(name = "Policia Nacional",
				email = "ady@gov.cv",
				url = "http://local")))
public class OccurrenceManagement {

	public static void main(String[] args) {
		SpringApplication.run(OccurrenceManagement.class, args);

	}

}
