package com.example.socialmedia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="Posts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    @Column
    private String postBody;

    @Column
    private int userID;

    @Column
    private LocalDate date;
}

