package com.example.socialmedia.service;

import com.example.socialmedia.entity.CommentEntity;
import com.example.socialmedia.entity.PostEntity;
import com.example.socialmedia.entity.UserEntity;
import com.example.socialmedia.repository.CommentRepo;
import com.example.socialmedia.repository.PostRepo;
import com.example.socialmedia.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private PostRepo postRepository;

    public Object createComment(String commentBody, Long postID, Long userID) {
        //check user exist
        //check post exist

        Optional<UserEntity> user = userRepository.findById(Math.toIntExact(userID));
        Optional<PostEntity> post = postRepository.findById(Math.toIntExact(postID));

        if (!user.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "User does not exist");
        }
        if (!post.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Post does not exist");
        }

        CommentEntity comment = new CommentEntity();
        comment.setCommentBody(commentBody);
        comment.setPostID(Math.toIntExact(postID));
        comment.setUserID(Math.toIntExact(userID));
        commentRepository.save(comment);
        return "Comment created successfully";
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String errorMessage) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("Error", errorMessage);
        return ResponseEntity.status(status).body(errorBody);
    }

    public Object getComment(Long commentID) {
        Optional<CommentEntity> comment = commentRepository.findById(Math.toIntExact(commentID));
        if (!comment.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Comment does not exist");
        }
        CommentEntity c = comment.get();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("commentID", (c.getCommentID()));
        responseBody.put("commentBody", c.getCommentBody());

        Map<String, Object> responseBody1 = new HashMap<>();
        responseBody1.put("userID", (c.getUserID()));

        Optional<UserEntity> user1 = userRepository.findById(Math.toIntExact(c.getUserID()));
        if (!user1.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "User does not exist");
        }
        UserEntity u = user1.get();
        responseBody1.put("name", String.valueOf(u.getName()));

        responseBody.put("commentCreator", responseBody1);
        return ResponseEntity.ok(responseBody);
    }

    public Object editComment(Long commentID, String commentBody) {
        Optional<CommentEntity> comment = commentRepository.findById(Math.toIntExact(commentID));

        if (!comment.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Comment does not exist");
        }

        CommentEntity com1 = comment.get();
        com1.setCommentBody(commentBody);
        commentRepository.save(com1);
        return "Comment edited successfully";
    }

    public Object deleteComment(Long commentID) {
        Optional<CommentEntity> comment = commentRepository.findById(Math.toIntExact(commentID));
        if (!comment.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Comment does not exist");
        }
        commentRepository.findById(Math.toIntExact(commentID));
        return "Comment deleted";
    }
}

