package com.katumbela.bankManagement.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = User.USER_TABLE)
public class User {
    public interface CreateUser {
    }

    public interface UpdateUser {
    }

    public static final String USER_TABLE = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "full_name", nullable = false)
    @NotNull(groups = { CreateUser.class })
    @NotEmpty(groups = { CreateUser.class })
    private String fullName;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    @NotNull(groups = { CreateUser.class, UpdateUser.class })
    @NotEmpty(groups = { CreateUser.class, UpdateUser.class })
    @Size(min = 3, max = 100, groups = { CreateUser.class, UpdateUser.class })
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull(groups = { CreateUser.class })
    @NotEmpty(groups = { CreateUser.class })
    @Email(groups = { CreateUser.class, UpdateUser.class })
    private String email;

    @Column(name = "phone", nullable = true)
    private String phone;
    
    @Column(name = "address", nullable = true)
    private String address;
    
    @Column(name = "document_id", nullable = true)
    private String documentId;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    @NotNull(groups = { CreateUser.class, UpdateUser.class })
    @NotEmpty(groups = { CreateUser.class, UpdateUser.class })
    @Size(min = 6, max = 100, groups = { CreateUser.class, UpdateUser.class })
    private String password;

    public User() {
    }

    public User(String fullName, String username, String email, String phone, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
