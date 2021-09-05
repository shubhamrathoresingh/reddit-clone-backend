package com.redditclone.webapp.mapper;

import com.redditclone.webapp.dto.VoteDto;
import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.User;
import com.redditclone.webapp.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "voteId", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Vote map(VoteDto voteDto, Post post, User user);

}
