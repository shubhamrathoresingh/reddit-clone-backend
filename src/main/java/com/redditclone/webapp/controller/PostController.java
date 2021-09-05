package com.redditclone.webapp.controller;

import com.redditclone.webapp.dto.PostRequest;
import com.redditclone.webapp.dto.PostResponse;
import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {

        Post post = postService.save(postRequest);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {

        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {

        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubReddit(Long subRedditId) {

        return status(HttpStatus.OK).body(postService.getPostsBySubReddit(subRedditId));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {

        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }

}