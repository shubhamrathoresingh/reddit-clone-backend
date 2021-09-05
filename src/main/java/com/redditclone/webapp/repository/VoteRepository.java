package com.redditclone.webapp.repository;

import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.User;
import com.redditclone.webapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
