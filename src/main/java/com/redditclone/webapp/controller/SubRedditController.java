package com.redditclone.webapp.controller;

import com.redditclone.webapp.dto.SubRedditDto;
import com.redditclone.webapp.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubRedditController {

    private final SubRedditService subredditService;

    @PostMapping
    public ResponseEntity<SubRedditDto> createSubreddit(@RequestBody SubRedditDto subredditDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubRedditDto>> getAllSubreddits() {

        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubRedditDto> getSubreddit(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));
    }

}
