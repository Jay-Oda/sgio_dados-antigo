package cv.pn.processmanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Sigo dados - antigo",
		description = "Sigo dados - antigo (API v1) - antecedentes, ocorrencias e cadastros do SIGO antigo.",
		version = "v1",
		contact = @Contact(name = "Policia Nacional",
				email = "ady@gov.cv",
				url = "http://local")))
public class OccurrenceManagement {

	public static void main(String[] args) {
		SpringApplication.run(OccurrenceManagement.class, args);

	}

}
