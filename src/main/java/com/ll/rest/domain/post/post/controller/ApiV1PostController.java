package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.dto.PostDto;
import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import com.ll.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping
    public List<PostDto> getItems() {
        return postService
                .findAllByOrderByIdDesc()
                .stream()
                .map(PostDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getItem(@PathVariable long id) {
        return postService.findById(id)
                .map(PostDto::new)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<Void>> deleteItem(@PathVariable long id) {
        Post post = postService.findById(id).get();

        postService.delete(post);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new RsData<>("200-1", "%d번 글이 삭제되었습니다.".formatted(id)));
    }

    record PostModifyReqBody(
            @NotBlank
            @Length(min = 2)
            String title,
            @NotBlank
            @Length(min = 2)
            String content
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<PostDto> modifyItem(@PathVariable long id, @RequestBody @Valid PostModifyReqBody reqBody) {
        Post post = postService.findById(id).get();

        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData<>("200-1", "%d번 글이 수정되었습니다.".formatted(id), new PostDto(post));
    }

    record PostWriteReqBody(
            @NotBlank
            @Length(min = 2)
            String title,
            @NotBlank
            @Length(min = 2)
            String content
    ) {
    }

    record PostWriteResBody(
            PostDto item,
            long totalCount
    ) {
    }

    @PostMapping
    public RsData<PostWriteResBody> writeItem(@RequestBody @Valid PostWriteReqBody reqBody) {
        Post post = postService.write(reqBody.title, reqBody.content);

        return new RsData<>("201-1", "%d번 글이 작성되었습니다.".formatted(post.getId()),
                new PostWriteResBody(new PostDto(post), postService.count())
        );
    }

}
