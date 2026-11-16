
package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.repository.MemberRepository;

//[10주차]
import com.example.demo.model.repository.PostRepository;
import com.example.demo.model.repository.CommentRepository;

@Service // 서비스 등록
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    // SecurityConfig 에 등록한 BCryptPasswordEncoder 주입
    private PasswordEncoder passwordEncoder;

    @Autowired // 1. 필드 추가
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    // 관리자가 부여할 수 있는 권한
    private static final List<String> ROLES = List.of("USER", "ADMIN");

    // 회원가입
    public Member signup(MemberForm form) {

        // 아이디, 비밀번호, 이름 필수 입력 검사
        if (isBlank(form.getUsername())
                || isBlank(form.getPassword())
                || isBlank(form.getName())) {
            throw new IllegalArgumentException("아이디, 비밀번호, 이름은 필수입니다.");
        }

        if (memberRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        Member member = new Member();

        member.setUsername(form.getUsername());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        member.setName(form.getName());
        member.setRole("USER"); // 기본 권한

        return memberRepository.save(member);
    }

    // 로그인 시 시큐리티가 자동 호출
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("회원 없음 : " + username));

        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }

    // 로그인한 아이디로 회원 조회
    public Member findByUsername(String username) {

        return memberRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "회원이 존재하지 않습니다 : " + username));
    }

    // 전체 회원 조회
    public List<Member> findAll() {

        return memberRepository.findAll(Sort.by("id"));
    }

    // 관리자만 권한 변경 가능
    @PreAuthorize("hasRole('ADMIN')")
    public Member changeRole(Long id, String role) { // 1.void -> Member

        if (!ROLES.contains(role)) {
            throw new IllegalArgumentException(
                    "허용되지 않는 권한입니다 : " + role);
        }

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "회원이 존재하지 않습니다."));

        member.setRole(role);

        return memberRepository.save(member); // 2. 마지막 줄 : 바뀐 회원을 돌려준다
    }

    // 관리자만 회원 삭제 가능
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMember(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "회원이 존재하지 않습니다."));
        if (postRepository.existsByAuthor(member) || commentRepository.existsByAuthor(member)) {
            throw new IllegalArgumentException("작성한 글이나 댓글이 있는 회원은 삭제할 수 없습니다.");
        }

        memberRepository.delete(member); // DELETE
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    // null 이거나 빈 문자열 또는 공백만 있는지 확인
    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
