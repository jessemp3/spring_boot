package jesse.spring;

import jesse.spring.model.User;
import jesse.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartApp implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello Spring Boot");


        User user = new User();
        user.setName("kaique");
        user.setUsername("kaique.alves");
        user.setPassword("senha");

        userRepository.save(user);


        for(User u : userRepository.findAll()){
            System.out.println(u);
        }
    }
}
