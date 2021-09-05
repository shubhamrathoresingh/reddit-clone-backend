package com.redditclone.webapp.service;

import com.redditclone.webapp.dto.VoteDto;
import com.redditclone.webapp.exception.PostNotFoundException;
import com.redditclone.webapp.exception.RedditWebAppException;
import com.redditclone.webapp.mapper.VoteMapper;
import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.Vote;
import com.redditclone.webapp.repository.PostRepository;
import com.redditclone.webapp.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.redditclone.webapp.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    private final PostRepository postRepository;

    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {

        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent()
            && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new RedditWebAppException("You have already " + voteDto.getVoteType().toString().toLowerCase() + "d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(voteMapper.map(voteDto, post, authService.getCurrentUser()));
        postRepository.save(post);
    }
}
