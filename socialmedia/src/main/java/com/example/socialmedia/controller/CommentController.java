package com.example.socialmedia.controller;

import com.example.socialmedia.entity.CommentEntity;
import com.example.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public Object createComment(@RequestBody CommentEntity commentEntity) {

        return commentService.createComment(commentEntity.getCommentBody(), (long) commentEntity.getPostID(), (long) commentEntity.getUserID());
    }

    @GetMapping
    public Object getComment(@RequestParam Long commentID) {
        return commentService.getComment(commentID);
    }

    @PatchMapping
    public Object editComment(@RequestBody CommentEntity commentEntity) {
        return commentService.editComment((long) commentEntity.getCommentID(), commentEntity.getCommentBody());
    }

    @DeleteMapping
    public Object deleteComment(@RequestParam Long commentID) {
        return commentService.deleteComment(commentID);
    }
}

