package com.web.stard.domain.board.global.api;

import com.web.stard.domain.admin.application.FaqService;
import com.web.stard.domain.admin.application.NoticeService;
import com.web.stard.domain.board.community.application.CommunityService;
import com.web.stard.domain.board.community.application.QnaService;
import com.web.stard.domain.board.global.application.StarScrapService;
import com.web.stard.domain.board.global.domain.Post;
import com.web.stard.domain.board.global.domain.enums.PostType;
import com.web.stard.domain.board.global.domain.StarScrap;
import com.web.stard.domain.board.study.application.StudyPostService;
import com.web.stard.domain.board.study.domain.Study;
import com.web.stard.domain.board.study.domain.StudyPost;
import com.web.stard.domain.member.application.MemberService;
import com.web.stard.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RestController
public class StarScrapController {

    private final StarScrapService starScrapService;
    private final MemberService memberService;
    private final CommunityService communityService;
    private final NoticeService noticeService;
    private final FaqService faqService;
    private final QnaService qnaService;
    private final StudyPostService studyPostService;


    /* Post(community) 공감 추가 */
    @PostMapping("/star/post/{id}")
    public StarScrap addPostStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.addPostStar(id, authentication);
    }

    /* Post(community) 공감 여부 확인 */
    @GetMapping("/star/post/{id}")
    public Boolean getPostStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        Member member = memberService.find(authentication.getName());
        Post post = communityService.findById(id);
        StarScrap starScrap = starScrapService.existsCommStar(member, post);
        if (starScrap == null) {
            return false;
        }
        return true;
    }

    /* Post(Community) 공감 삭제 */
    @DeleteMapping("/star/post/{id}")
    public boolean deletePostStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.deletePostStar(id, authentication);
    }


    /* 특정 스터디 공감 여부 */
    @GetMapping("/star/study/{id}")
    public Boolean getStudyStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.getStudyStar(id, authentication);
    }

    /* ScrapStudySlide 공감 추가 */
    @PostMapping("/star/study/{id}")
    public StarScrap addStudyStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.addStudyStar(id, authentication);
    }

    /* ScrapStudySlide 공감 삭제 */
    @DeleteMapping("/star/study/{id}")
    public boolean deleteStudyStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.deleteStudyStar(id, authentication);
    }

    // Notice, FAQ, QNA
    /* Post(notice,faq,qna) 공감 추가 */
    @PostMapping("/star/notice/{id}")
    public StarScrap addNoticeStar(@PathVariable(name = "id") Long id, @RequestParam(name = "type") String type, Authentication authentication) {
        return starScrapService.addNoticeStar(id, type, authentication);
    }

    /* Post(notice,faq,qna) 공감 여부 확인 */
    @GetMapping("/star/notice/{id}")
    public Boolean getNoticeStar(@PathVariable(name = "id") Long id, @RequestParam(name = "type") String type, Authentication authentication) {
        Member member = memberService.find(authentication.getName());
        Post post = null;
        PostType postType = null;

        if (type.equals("NOTICE")) {
            post = noticeService.getNoticeDetail(id, null);
            postType = PostType.NOTICE;
        }
        else if (type.equals("FAQ")) {
            post = faqService.getFaqDetail(id, null);
            postType = PostType.FAQ;
        }
        else if (type.equals("QNA")) {
            post = qnaService.getQnaDetail(id, null);
            postType = PostType.QNA;
        }

        StarScrap starScrap = starScrapService.existsNoticeStar(member, post, postType);

        if (starScrap == null) {
            return false;
        }
        return true;
    }

    /* Post(notice,faq,qna) 공감 삭제 */
    @DeleteMapping("/star/notice/{id}")
    public boolean deleteNoticeStar(@PathVariable(name = "id") Long id, @RequestParam(name = "type") String type, Authentication authentication) {
        return starScrapService.deleteNoticeStar(id, type, authentication);
    }



    /* Post(community) 스크랩한 게시글 전체 조회 */
    @GetMapping("/scrap/post")
    public List<Post> allPostScrapList(Authentication authentication) {
        return starScrapService.allPostScrapList(authentication);
    }

    /* Post(community) 스크랩 여부 확인 */
    @GetMapping("/scrap/post/{id}")
    public Boolean getPostScrap(@PathVariable(name = "id") Long id, Authentication authentication) {
        Member member = memberService.find(authentication.getName());
        Post post = communityService.findById(id);
        StarScrap starScrap = starScrapService.existsCommScrap(member, post);
        if (starScrap == null) {
            return false;
        }
        return true;
    }

    /* Post(community) 스크랩 추가 */
    @PostMapping("/scrap/post/{id}")
    public StarScrap addPostScrap(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.addPostScrap(id, authentication);
    }

    /* Post(Community) 스크랩 삭제 */
    @DeleteMapping("/scrap/post/{id}")
    public boolean deletePostScrap(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.deletePostScrap(id, authentication);
    }



    /* ScrapStudySlide 스크랩한 게시글 전체 조회 */
    @GetMapping("/scrap/study")
    public List<Study> allStudyScrapList(Authentication authentication) {
        return starScrapService.allStudyScrapList(authentication);
    }

    /* 특정 스터디 스크랩 여부 */
    @GetMapping("/scrap/study/{id}")
    public Boolean getStudyScrap(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.getStudyScrap(id, authentication);
    }

    /* ScrapStudySlide 스크랩 추가 */
    @PostMapping("/scrap/study/{id}")
    public StarScrap addStudyScrap(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.addStudyScrap(id, authentication);
    }

    /* ScrapStudySlide 스크랩 삭제 */
    @DeleteMapping("/scrap/study/{id}")
    public boolean deleteStudyScrap(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.deleteStudyScrap(id, authentication);
    }



    /* 스터디 페이지의 공감 여부 조회 */
    @GetMapping("/study/stars")
    public List<Boolean> getStudyPageStar(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                          Authentication authentication) {
        return starScrapService.getStudyPageStar(page, authentication);
    }

    /* 스터디 페이지의 스크랩 여부 조회 */
    @GetMapping("/study/scraps")
    public List<Boolean> getStudyPageScrap(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                           Authentication authentication) {
        return starScrapService.getStudyPageScrap(page, authentication);
    }

    /* 스크랩한 스터디의 공감 여부 조회 */
    @GetMapping("/study/stars/scraps")
    public List<Boolean> getStudyPageStarByScrap(Authentication authentication) {
        return starScrapService.getStudyPageStarByScrap(authentication);
    }

    /* 마이페이지 - 스터디 공감, 스크랩 조회 */
    @GetMapping("/mypage/study/star-scrap")
    public List<Boolean> getMyPageStudyStarScraps(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                  Authentication authentication,
                                                  @RequestParam(name = "status") String status, @RequestParam(name = "type") String type) {
        return starScrapService.getMyPageStudyStarScrap(page, authentication, status, type);
    }

    /* 스터디 검색 결과 (제목) - 공감, 스크랩 조회 */
    @GetMapping("/study/search/title/star-scrap")
    public List<Boolean> getStudySearchTitleStarScraps(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                  Authentication authentication,
                                                  @RequestParam(name = "keyword") String keyword, @RequestParam(name = "type") String type) {
        return starScrapService.getStudySearchStarScraps(page, authentication, "title", keyword, type);
    }

    /* 스터디 검색 결과 (내용) - 공감, 스크랩 조회 */
    @GetMapping("/study/search/content/star-scrap")
    public List<Boolean> getStudySearchContentStarScraps(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                  Authentication authentication,
                                                  @RequestParam(name = "keyword") String keyword, @RequestParam(name = "type") String type) {
        return starScrapService.getStudySearchStarScraps(page, authentication, "content", keyword, type);
    }

    /* 스터디 검색 결과 (작성자) - 공감, 스크랩 조회 */
    @GetMapping("/study/search/recruiter/star-scrap")
    public List<Boolean> getStudySearchRecruiterStarScraps(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                         Authentication authentication,
                                                         @RequestParam(name = "keyword") String keyword, @RequestParam(name = "type") String type) {
        return starScrapService.getStudySearchStarScraps(page, authentication, "recruiter", keyword, type);
    }


    /* StudyPost 공감 여부 확인 */
    @GetMapping("/star/studypost/{id}")
    public Boolean getStudyPostStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        Member member = memberService.find(authentication.getName());
        StudyPost studyPost = studyPostService.getStudyPost(id, null);
        StarScrap starScrap = starScrapService.existsStudyPostStar(member, studyPost);
        if (starScrap == null) {
            return false;
        }
        return true;
    }

    /* StudyPost 공감 추가 */
    @PostMapping("/star/studypost/{id}")
    public StarScrap addStudyPostStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        System.out.println("아이디 : " + id);
        return starScrapService.addStudyPostStar(id, authentication);
    }

    /* StudyPost 공감 삭제 */
    @DeleteMapping("/star/studypost/{id}")
    public boolean deleteStudyPostStar(@PathVariable(name = "id") Long id, Authentication authentication) {
        return starScrapService.deleteStudyPostStar(id, authentication);
    }

    /* 특정 회원의 공감, 스크랩 내역 전체 삭제(탈퇴 시 사용) */
    @DeleteMapping("/star/all/{id}")
    public void deleteAllStarAndStudy(@PathVariable(name = "id") String id) {
        starScrapService.deleteAllStarAndStudy(id);
    }

}
