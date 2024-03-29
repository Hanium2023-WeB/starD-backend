package com.web.stard.domain.board.study.repository;

import com.web.stard.domain.board.study.domain.Study;
import com.web.stard.domain.board.study.domain.StudyMember;
import com.web.stard.domain.member.domain.Member;
import com.web.stard.domain.board.study.domain.enums.ProgressStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    /* 특정 스터디에 특정 회원이 있는지 */
    boolean existsByStudyAndMember(Study study, Member member);
    StudyMember findByStudyAndMember(Study study, Member member);

    /* 특정 회원으로 검색 */
    List<StudyMember> findByMember(Member member);

    Page<StudyMember> findByMember(Member member, Pageable pageable);

    List<StudyMember> findByStudy(Study study);

    @Query("select s.member from StudyMember s where s.study = :study")
    List<Member> findMembersByStudy(@Param("study") Study study);

    /* 특정 회원의 진행 중인 스터디가 있는지 */
    List<StudyMember> findByMemberAndStudyProgressStatusIn(Member member, List<ProgressStatus> progressStatusList);
    /* 진행 완료된 스터디로 검색 */
    List<StudyMember> findByMemberAndStudyProgressStatusOrderByIdDesc(Member member, ProgressStatus progressStatus);
}
