package com.redditclone.webapp.repository;

import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.SubReddit;
import com.redditclone.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(SubReddit subReddit);

    List<Post> findByUser(User user);

}
