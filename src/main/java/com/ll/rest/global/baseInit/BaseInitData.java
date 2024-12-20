package com.ll.rest.global.baseInit;

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
    private final PostService postService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (postService.count() > 0) {
            return;
        }

        postService.write("축구 하실 분?","14시까지 22명을 모아야 합니다.");
        postService.write("배구 하실 분?","15시까지 12명을 모아야 합니다.");
        postService.write("농구 하실 분?","16시까지 10명을 모아야 합니다.");
    }
}