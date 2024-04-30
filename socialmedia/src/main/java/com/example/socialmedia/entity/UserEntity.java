package com.example.socialmedia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String password;
}

