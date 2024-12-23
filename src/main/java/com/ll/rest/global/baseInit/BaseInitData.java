package com.ll.rest.global.baseInit;

import com.ll.rest.domain.member.member.entity.Member;
import com.ll.rest.domain.member.member.service.MemberService;
import com.ll.rest.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final MemberService memberService;
    private final PostService postService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.count() > 0) {
            return;
        }

        memberService.join("system", "1234", "시스템");
        memberService.join("admin", "1234", "관리자");
        memberService.join("user1", "1234", "유저1");
        memberService.join("user2", "1234", "유저2");
        memberService.join("user3", "1234", "유저3");
        memberService.join("user4", "1234", "유저4");
        memberService.join("user5", "1234", "유저5");
    }

    @Transactional
    public void work2() {
        if (postService.count() > 0) {
            return;
        }

        Member memberUser1 = memberService.findByUsername("user1").get();
        Member memberUser2 = memberService.findByUsername("user2").get();
        Member memberUser3 = memberService.findByUsername("user3").get();
        Member memberUser4 = memberService.findByUsername("user4").get();

        postService.write(memberUser1, "축구 하실 분?", "14시까지 22명을 모아야 합니다.");
        postService.write(memberUser1, "배구 하실 분?", "15시까지 12명을 모아야 합니다.");
        postService.write(memberUser2, "농구 하실 분?", "16시까지 10명을 모아야 합니다.");
        postService.write(memberUser3, "발야구 하실 분?", "17시까지 14명을 모아야 합니다.");
        postService.write(memberUser4, "피구 하실 분?", "18시까지 18명을 모아야 합니다.");
    }
}