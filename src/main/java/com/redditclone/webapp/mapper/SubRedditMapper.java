package com.redditclone.webapp.mapper;

import com.redditclone.webapp.dto.SubRedditDto;
import com.redditclone.webapp.model.Post;
import com.redditclone.webapp.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubRedditDto mapSubRedditToDto(SubReddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubReddit mapDtoToSubReddit(SubRedditDto subredditDto);

}
