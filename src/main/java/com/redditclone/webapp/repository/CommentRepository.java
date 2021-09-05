package com.redditclone.webapp.repository;

import com.redditclone.webapp.model.Comment;
import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
