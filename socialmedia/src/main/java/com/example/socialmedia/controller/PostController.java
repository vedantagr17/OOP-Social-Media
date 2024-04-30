package com.example.socialmedia.controller;

import com.example.socialmedia.entity.PostEntity;
import com.example.socialmedia.repository.PostRepo;
import com.example.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    PostRepo storage;

    @PostMapping
    public Object createPost(@RequestBody PostEntity postEntity) {
        return postService.createPost(postEntity.getPostBody(), (long) postEntity.getUserID());
    }

    @GetMapping
    public Object getPost(@RequestParam Long postID) {
        return postService.getPost(postID);
    }

    @PatchMapping
    public Object editPost(@RequestBody PostEntity postEntity) {
        return postService.editPost((long) postEntity.getPostID(), postEntity.getPostBody());
    }

    @DeleteMapping
    public Object deletePost(@RequestParam Long postID) {
        return postService.deletePost(postID);
    }
}

