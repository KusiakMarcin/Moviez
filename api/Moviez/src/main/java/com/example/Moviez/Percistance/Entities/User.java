package com.example.Moviez.Percistance.Entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "keycloakid", unique = true, length = 100)
    private String keycloakId;

    // Custom constructor for convenience when creating a user in the service
    public User(String email, String keycloakId) {
        this.email = email;
        this.keycloakId = keycloakId;
    }
}
