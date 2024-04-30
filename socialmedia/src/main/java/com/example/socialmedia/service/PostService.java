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

import java.time.LocalDate;
import java.util.*;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private CommentRepo commentRepo;

    public Object createPost(String postBody, Long userID) {
        Optional<UserEntity> user = userRepository.findById(Math.toIntExact(userID));
        if (!user.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "User does not exist");
        }

//        UserEntity userEntity = user.get();
        PostEntity post = new PostEntity();
        post.setPostBody(postBody);
        post.setUserID(Math.toIntExact(userID));
        post.setDate(LocalDate.now());
        postRepository.save(post);
        return "Post created successfully";
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String errorMessage) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("Error", errorMessage);
        return ResponseEntity.status(status).body(errorBody);
    }

    public Object getPost(Long postID) {
        Optional<PostEntity> post = postRepository.findById(Math.toIntExact(postID));
        if (!post.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Post does not exist");
        }
        PostEntity p = post.get();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("postID", (p.getPostID()));
        responseBody.put("postBody", p.getPostBody());
        responseBody.put("date", p.getDate());

        List<CommentEntity> allUsers = new ArrayList<>();
        commentRepo.findAll().forEach(allUsers::add);

        List<Object> coms = new ArrayList<>();

        for(CommentEntity c : allUsers) {
            if(c.getPostID() == postID){
                Map<String, Object> abc = new HashMap<>();
                abc.put("commentID", c.getCommentID());
                abc.put("commentBody", c.getCommentBody());

                Map<String, Object> abc1 = new HashMap<>();
                Optional<UserEntity> post1 = userRepository.findById(Math.toIntExact(c.getUserID()));
                if (post1.isPresent()) {
                    UserEntity poi = post1.get();
                    abc1.put("name", poi.getName());
                }
                abc1.put("userID", c.getUserID());
                abc.put("commentCreator", abc1);
                coms.add(abc);
            }
        }
        responseBody.put("comments", coms);
        return ResponseEntity.ok(responseBody);

    }

    public Object editPost(Long postID, String postBody) {
        Optional<PostEntity> post = postRepository.findById(Math.toIntExact(postID));
        if (!post.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Post does not exist");
        }
        PostEntity post1 = post.get();
        post1.setPostBody(postBody);
        postRepository.save(post1);
        return "Post edited successfully";
    }

    public Object deletePost(Long postID) {
        Optional<PostEntity> post = postRepository.findById(Math.toIntExact(postID));
        if (!post.isPresent()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Post does not exist");
        }
        postRepository.deleteById(Math.toIntExact(postID));
        return "Post deleted";
    }
}

