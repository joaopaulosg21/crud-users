package projeto.api.reactive.crudusers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.api.reactive.crudusers.dto.ResponseDTO;
import projeto.api.reactive.crudusers.dto.UpdateUserDTO;
import projeto.api.reactive.crudusers.exceptions.EmailAlreadyInUseException;
import projeto.api.reactive.crudusers.exceptions.UserNotFoundException;
import projeto.api.reactive.crudusers.model.User;
import projeto.api.reactive.crudusers.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
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
                .defaultIfEmpty(new User())
                .flatMap(item -> {
                    if(user.getEmail().equals(item.getEmail())) {
                        return Mono.error(EmailAlreadyInUseException::new);
                    }
                    return userRepository.save(user);
                });
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return userRepository.findById(id)
                .defaultIfEmpty(new User())
                .flatMap(user -> {
                    if(Objects.isNull(user.getEmail())) {
                        return Mono.error(UserNotFoundException::new);
                    }
                    return Mono.just(user);
                });
    }

    public Mono<User> update(UpdateUserDTO updateUser, Long id) {
        return this.findById(id)
                .flatMap(user -> {
                    user.setName(updateUser.name());
                    user.setAge(updateUser.age());
                    return userRepository.save(user);
                });
    }

    public Mono<ResponseDTO> delete(Long id) {
        return this.findById(id)
                .then(userRepository.deleteById(id))
                .then(Mono.just(new ResponseDTO(String.format("User with id: %d, deleted",id))));
    }
}
