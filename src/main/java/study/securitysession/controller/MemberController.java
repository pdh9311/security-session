package study.securitysession.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.securitysession.model.Member;
import study.securitysession.model.Role;
import study.securitysession.model.dto.MemberDto;
import study.securitysession.repository.MemberRepository;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        System.out.println("login()");
        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(MemberDto dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        memberRepository.save(member);
        return "redirect:/login";
    }

//    @GetMapping("/logout")
//    public String logout() {
//        return "logout";
//    }
}
