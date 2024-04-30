package com.example.socialmedia.controller;

import com.example.socialmedia.entity.CommentEntity;
import com.example.socialmedia.entity.PostEntity;
import com.example.socialmedia.entity.UserEntity;
import com.example.socialmedia.repository.CommentRepo;
import com.example.socialmedia.repository.PostRepo;
import com.example.socialmedia.repository.UserRepo;
import com.example.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserEntity loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/signup")
    public Object signup(@RequestBody UserEntity signupRequest){
        return userService.signup(signupRequest.getEmail(), signupRequest.getName(), signupRequest.getPassword());
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Object> getUserDetails(@RequestParam Long userID) {
        return userService.getUserDetails(userID);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/")
    public Object feed(){
        List<PostEntity> pqr = new ArrayList<>();
        postRepo.findAll().forEach(pqr::add);
        List<Object> xyz = new ArrayList<>();
        for(PostEntity post : pqr){
            Map<String, Object> ghj = new HashMap<>();
            ghj.put("postID", post.getPostID());
            ghj.put("postBody", post.getPostBody());
            ghj.put("date", post.getDate());

            List<CommentEntity> comss = new ArrayList<>();
            commentRepo.findAll().forEach(comss::add);
            List<Object> uyt = new ArrayList<>();
            for(CommentEntity comment : comss){
                if(comment.getPostID() == post.getPostID()){
                    Map<String, Object> bhu = new HashMap<>();
                    bhu.put("commentID", comment.getCommentID());
                    bhu.put("commentBody", comment.getCommentBody());
                    Map<String, Object> asd = new HashMap<>();
                    Optional<UserEntity> ops = userRepo.findById(comment.getUserID());
                    if(ops.isPresent()){
                        UserEntity user = ops.get();
                        asd.put("name", user.getName());
                    }
                    asd.put("userID", comment.getUserID());
                    bhu.put("commentCreator", asd);
                    uyt.add(bhu);
                }
            }
            ghj.put("comments", xyz);
            xyz.add(ghj);

        }
        Collections.reverse(xyz);
        return new ResponseEntity<>(xyz, HttpStatus.CREATED);
    }
}

