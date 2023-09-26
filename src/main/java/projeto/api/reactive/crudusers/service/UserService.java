package projeto.api.reactive.crudusers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.api.reactive.crudusers.exceptions.EmailAlreadyInUseException;
import projeto.api.reactive.crudusers.model.User;
import projeto.api.reactive.crudusers.repository.UserRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> create(User user) {
        return userRepository.findByEmail(user.getEmail())
                .map(User::new)
                .defaultIfEmpty(new User())
                .flatMap(item -> {
                    if(user.getEmail().equals(item.getEmail())) {
                        return Mono.error(EmailAlreadyInUseException::new);
                    }
                    return userRepository.save(user);
                });
    }
}
