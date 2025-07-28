package ValentinaDale.ProyectoModulo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoModuloApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().ignoreIfMissinf().load();
		dotenv.entries().foreEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
				);

		SpringApplication.run(ProyectoModuloApplication.class, args);
	}

}
