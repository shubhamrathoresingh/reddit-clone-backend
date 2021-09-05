package com.redditclone.webapp.service;

import com.redditclone.webapp.dto.SubRedditDto;
import com.redditclone.webapp.exception.RedditWebAppException;
import com.redditclone.webapp.mapper.SubRedditMapper;
import com.redditclone.webapp.model.SubReddit;
import com.redditclone.webapp.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SubRedditService {

    private final SubRedditRepository subredditRepository;

    private final SubRedditMapper subredditMapper;

    @Transactional
    public SubRedditDto save(SubRedditDto subredditDto) {

        SubReddit saved = subredditRepository.save(subredditMapper.mapDtoToSubReddit(subredditDto));
        subredditDto.setId(saved.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll() {

        return subredditRepository.findAll()
                .stream().map(subredditMapper::mapSubRedditToDto)
                .collect(Collectors.toList());
    }

    public SubRedditDto getSubreddit(Long id) {

        SubReddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RedditWebAppException("No subreddit found with given id " + id));
        return subredditMapper.mapSubRedditToDto(subreddit);
    }
}
