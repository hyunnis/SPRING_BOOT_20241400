
package com.example.demo.model.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Member;
import com.example.demo.model.domain.Post;
import com.example.demo.model.dto.PostForm;
import com.example.demo.model.repository.MemberRepository;
import com.example.demo.model.repository.PostRepository;


// [9주차] 게시글 상세 조회 및 404 오류 처리
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
// [9주차] 게시글 수정·삭제 권한 확인
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service // 게시판 서비스
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Post> findAll() { // 전체 목록 (최신 글이 위로)
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // [9주차] 글 1개 조회 (없으면 404)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "글이 존재하지 않습니다."));
    }

    public Post write(PostForm form, String username) {
        // 글쓰기 (작성자 = 로그인한 회원)
        validate(form);

        Member author = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Post post = new Post();
        post.setTitle(form.getTitle().trim());
        post.setContent(form.getContent());
        post.setAuthor(author); // 작성자는 화면이 아니라 서버가 정한다
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post); // INSERT
    }

    private void validate(PostForm form) {
        // 빈 값 검사 (5주차 회원가입과 같은 방식)
        if (form.getTitle() == null || form.getTitle().isBlank()
                || form.getContent() == null || form.getContent().isBlank()) {
            throw new IllegalArgumentException("제목과 내용을 모두 입력하세요.");
        }

        if (form.getTitle().trim().length() > 100) {
            throw new IllegalArgumentException("제목은 100자까지 입력할 수 있습니다.");
        }
    }
    public Post findForEdit(Long id) { // 수정 화면에 보여줄 글(본인 글만)
        Post post = findById(id);
        checkWriter(post); // ★ 추가
        return post;
    }

    public Post edit(Long id, PostForm form) { // 글 수정
        Post post = findForEdit(id);
        validate(form);
        post.setTitle(form.getTitle().trim());
        post.setContent(form.getContent());
        post.setUpdatedAt(LocalDateTime.now()); // 수정 시간 기록
        return postRepository.save(post); // id 가 있으니 UPDATE
    }

    public void delete(Long id) { // 글 삭제
        Post post = findById(id);
        if (!isAdmin()) checkWriter(post); // 추가 : 관리자는 누구 글이든 삭제 가능
        postRepository.delete(post); // DELETE
    }
    // [9주차] 로그인한 사람이 글쓴이인지 확인
    private void checkWriter(Post post) {
        String loginUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!post.getAuthor().getUsername().equals(loginUsername)) {
            throw new AccessDeniedException("본인이 쓴 글만 수정·삭제할 수 있습니다.");
        }
    }
    // [9주차] 로그인한 사람이 관리자인지 확인
    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
