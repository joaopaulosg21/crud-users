package projeto.api.reactive.crudusers.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import projeto.api.reactive.crudusers.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User,Long> {
    Mono<User> findByEmail(String email);
}
