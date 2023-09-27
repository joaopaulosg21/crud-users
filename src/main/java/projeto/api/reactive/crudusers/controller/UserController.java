package projeto.api.reactive.crudusers.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.api.reactive.crudusers.dto.UpdateUserDTO;
import projeto.api.reactive.crudusers.model.User;
import projeto.api.reactive.crudusers.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Mono<User>> create(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @GetMapping
    public ResponseEntity<Flux<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Mono<User>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Mono<User>> update(@PathVariable Long id, @RequestBody UpdateUserDTO user) {
        return ResponseEntity.ok(userService.update(user,id));
    }
}
