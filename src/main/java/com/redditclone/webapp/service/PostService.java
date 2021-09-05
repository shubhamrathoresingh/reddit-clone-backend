package com.redditclone.webapp.service;

import com.redditclone.webapp.dto.PostRequest;
import com.redditclone.webapp.dto.PostResponse;
import com.redditclone.webapp.exception.PostNotFoundException;
import com.redditclone.webapp.exception.SubRedditNotFoundException;
import com.redditclone.webapp.mapper.PostMapper;
import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.SubReddit;
import com.redditclone.webapp.model.User;
import com.redditclone.webapp.repository.PostRepository;
import com.redditclone.webapp.repository.SubRedditRepository;
import com.redditclone.webapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostService {

    private final SubRedditRepository subRedditRepository;

    private final AuthService authService;

    private final PostMapper postMapper;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public Post save(PostRequest postRequest) {

        SubReddit subReddit = subRedditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubRedditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();
        return postMapper.map(postRequest, subReddit, currentUser);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {

        return postRepository.findAll()
                .stream().map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubReddit(Long subRedditId) {

        SubReddit subReddit = subRedditRepository.findById(subRedditId)
                .orElseThrow(() -> new SubRedditNotFoundException(subRedditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subReddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream().map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
