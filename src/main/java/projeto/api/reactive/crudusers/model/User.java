package projeto.api.reactive.crudusers.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "users")
public class User {
    @Id
    private Long id;

    @NotBlank(message = "name cannot be null")
    private String name;

    @NotBlank(message = "email cannot be null")
    private String email;

    @NotNull(message = "age cannot be null")
    private Integer age;

    public User(){}

    public User(String name,String email,Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }


    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        User obj = (User) o;

        if(obj.getId() == null || obj.getEmail() == null) return false;

        return this.email.equals(obj.getEmail()) || this.id.equals(obj.getId());
    }
}
