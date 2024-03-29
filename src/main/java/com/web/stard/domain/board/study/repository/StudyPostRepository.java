package com.web.stard.domain.board.study.repository;

import com.web.stard.domain.member.domain.Member;
import com.web.stard.domain.board.study.domain.enums.ProgressStatus;
import com.web.stard.domain.board.study.domain.Study;
import com.web.stard.domain.board.study.domain.StudyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {

    /* 게시글 전체 조회 (업로드 순) */
    List<StudyPost> findByStudyOrderByCreatedAtDesc(Study study);

    /* 완료된 스터디로 검색 */
    List<StudyPost> findByMemberAndStudyProgressStatus(Member member, ProgressStatus progressStatus);
    
    /* 제목 검색 */
    List<StudyPost> findByStudyAndTitleContainingOrderByCreatedAtDesc(Study study, String title);
    /* 내용 검색 */
    List<StudyPost> findByStudyAndContentContainingOrderByCreatedAtDesc(Study study, String content);
    /* 닉네임 검색 */
    List<StudyPost> findByStudyAndMemberOrderByCreatedAtDesc(Study study, Member member);

    Page<StudyPost> findByMember(Member member, Pageable pageable);
}
