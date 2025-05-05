package com.samplepacks.digital_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samplepacks.digital_store.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * User for authentication with our website.
 */
@Setter
@Entity
@Table(name = "local_user")
public class LocalUser {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER; // Default role

    @Getter
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Getter
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @Getter
    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;

    @Getter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    /** Has the users email been verified? */
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    public Boolean isEmailVerified() {
        return emailVerified;
    }
}