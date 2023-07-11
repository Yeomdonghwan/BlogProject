package com.example.blogproject.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(new PostDto(post), HttpStatus.OK);
    }

//    @GetMapping(value = "/posts")
//    public ResponseEntity<List<PostDto>> getPostList(Pageable pageable) {
//        Page<Post> posts = postService.findAllByOrderByCreatedDateDescPageable(pageable);
//        Page<PostDto> postDto = posts.map(post -> new PostDto((post)));
//        return new ResponseEntity<>(postDto.getContent(), HttpStatus.OK);
//    }


}
