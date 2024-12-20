package com.ll.rest.domain.member.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.rest.domain.member.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberDto {
    private long id;

    @JsonProperty("createdDateTime")
    private LocalDateTime createDate;

    @JsonProperty("modifiedDateTime")
    private LocalDateTime modifyDate;

    private String username;

    private String password;

    private String nickname;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
    }
}
