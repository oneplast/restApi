package com.ll.rest.domain.post.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.rest.domain.post.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostDto {
    private long id;
    @JsonProperty("createdDateTime")
    private LocalDateTime createDate;
    @JsonProperty("modifiedDateTime")
    private LocalDateTime modifyDate;
    private String title;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}