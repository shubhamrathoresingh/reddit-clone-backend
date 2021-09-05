package com.redditclone.webapp.dto;

import com.redditclone.webapp.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

    private VoteType voteType;

    private Long postId;
}