package bankingsystem.management;

import bankingsystem.management.Models.Role;
import bankingsystem.management.Models.User;
import bankingsystem.management.Repository.RoleRepository;
import bankingsystem.management.Repository.UserRepository;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@SecurityScheme(name = "Bearer token", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class ManagementApplication {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public ManagementApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			Role roleAdmin = Role.builder().id(1L).name("ADMIN").build();
			roleRepository.save(roleAdmin);
			Role roleBanker = Role.builder().id(2L).name("BANKER").build();
			roleRepository.save(roleBanker);
			Role roleClient = Role.builder().id(3L).name("CLIENT").build();
			roleRepository.save(roleClient);

			userRepository.save(new User(1L,"admin", passwordEncoder.encode("admin"), roleAdmin));
			userRepository.save(new User(2L,"banker", passwordEncoder.encode("banker"), roleBanker));
			userRepository.save(new User(3L,"client", passwordEncoder.encode("client"), roleClient));
		};
	}
}
