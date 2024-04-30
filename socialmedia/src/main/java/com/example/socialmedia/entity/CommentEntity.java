package com.example.socialmedia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Comments")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentID;

    @Column
    private String commentBody;

    @Column
    private int postID;

    @Column
    private int userID;
}

