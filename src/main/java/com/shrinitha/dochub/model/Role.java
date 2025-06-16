package com.shrinitha.dochub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // ADMIN, EDITOR, VIEWER

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}
