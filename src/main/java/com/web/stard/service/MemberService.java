package com.web.stard.service;

import com.web.stard.domain.Authority;
import com.web.stard.domain.Interest;
import com.web.stard.domain.Member;
import com.web.stard.domain.Role;
import com.web.stard.repository.AuthorityRepository;
import com.web.stard.repository.InterestRepository;
import com.web.stard.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter @Setter
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member find(String id) {
        Optional<Member> result = memberRepository.findById(id);

        if(result.isPresent())
            return result.get();
        return null;
    }

    // 회원 정보 저장
    public void saveMember(Member member) {
        // 회원가입 시 기본적으로 'USER' 권한을 부여
        Authority userAuthority = authorityRepository.findByAuthorityName("USER");
        if (userAuthority == null) {
            // "USER" 값을 가진 권한 객체가 데이터베이스에 존재하지 않을 경우 새로 생성하여 저장합니다.
            userAuthority = new Authority("USER");
            authorityRepository.save(userAuthority);
        }
        member.setRoles(userAuthority);

        System.out.println("saveMember(): " + userAuthority.getAuthorityName());

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        memberRepository.save(member);
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers = memberRepository.findById(member.getId());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /* 비밀번호 확인 */
    public boolean checkPw(String id, String password) {
        String storedPassword = memberRepository.findPasswordById(id).getPassword(); // 사용자 pw
        if (passwordEncoder.matches(password, storedPassword)) // 입력한 비밀번호와 사용자 비밀번호 같음
            return true;
        return false;
    }

    /* 닉네임 중복 확인 */
    public boolean checkNickname(String nickname) {
        return memberRepository.existsByNickname(nickname); // true -> 있음 (사용불가)
    }

    /* 정보 수정 (닉네임, 이메일, 전화번호, 비밀번호) */
    public void updateMember(String info, String id, String information) {
        Member member = find(id);

        if (info.equals("nickname")) { // 닉네임 변경
            member.setNickname(information);
        } else if (info.equals("email")) { // 이메일 변경
            member.setEmail(information);
        } else if (info.equals("phone")) { // 전화번호 변경
            member.setPhone(information);
        } else if (info.equals("password")) { // 비밀번호 변경
            String encodedPassword = passwordEncoder.encode(information);
            member.setPassword(encodedPassword);
        }

        memberRepository.save(member);
    }

    /* 거주지 변경 */
    public void updateAddress(String id, String city, String district) {
        Member member = find(id);

        member.setCity(city);
        member.setDistrict(district);

        memberRepository.save(member);
    }

    /* 관심분야 수정 */
    @Transactional
    public void updateInterest(String id, List<String> interests) {
        Member member = find(id);

        // 기존 관심분야 delete 후 새로 insert
        interestRepository.deleteAllByMember(member);

        List<Interest> interestList = new ArrayList<>();
        for (String s : interests) {
            Interest interest = new Interest();
            interest.setMember(member);
            interest.setField(s);
            interestList.add(interest);
        }
        interestRepository.saveAll(interestList);
    }

    /* 회원 탈퇴 */
    public void deleteMember(String id) {

    }
}