package com.redditclone.webapp.service;

import com.redditclone.webapp.dto.CommentsDto;
import com.redditclone.webapp.exception.PostNotFoundException;
import com.redditclone.webapp.mapper.CommentMapper;
import com.redditclone.webapp.model.Comment;
import com.redditclone.webapp.model.NotificationEmail;
import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.User;
import com.redditclone.webapp.repository.CommentRepository;
import com.redditclone.webapp.repository.PostRepository;
import com.redditclone.webapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final CommentMapper commentMapper;

    private final AuthService authService;

    private final MailContentBuilder mailContentBuilder;

    private final MailService mailService;

    @Transactional
    public void save(CommentsDto commentsDto) {

        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername()
                                                  + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {

        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post",
                user.getEmail(), message));
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForPost(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream().map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream().map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
