package com.favmovie.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // add identifier to the column
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
}
