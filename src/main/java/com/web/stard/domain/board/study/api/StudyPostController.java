package com.web.stard.domain.board.study.api;

import com.web.stard.domain.member.domain.Role;
import com.web.stard.domain.board.study.domain.StudyPost;
import com.web.stard.domain.member.application.MemberService;
import com.web.stard.domain.board.study.application.StudyPostService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@RestController
@RequestMapping("/study/post")
public class StudyPostController {

    private final StudyPostService studyPostService;
    private final MemberService memberService;


    /* 게시글 전체 조회 */
    @GetMapping
    public List<StudyPost> getStudyPostList(@RequestParam(name = "studyId") Long studyId) {
        return studyPostService.getStudyPostList(studyId);
    }

    /* 게시글 상세 조회 */
    @GetMapping("/{postId}")
    public StudyPost getStudyPost(@PathVariable(name = "postId") Long postId) {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (!authentication.getName().equals("anonymousUser")) {
                Role userRole = memberService.find(authentication.getName()).getRoles();
                if (userRole != Role.ADMIN) { // 관리자가 아닐 때
                    userId = authentication.getName(); // 사용자 아이디
                }
            }
        }
        return studyPostService.getStudyPost(postId, userId);
    }

    /* 게시글 등록 */
    @PostMapping
    public StudyPost registerPost(@RequestParam(name = "studyId") Long studyId, @RequestParam(name = "title") String title,
                                  @RequestParam(name = "content", required = false) String content,
                                  @RequestParam(name = "file", required = false) MultipartFile file,
                                  Authentication authentication) {
        return studyPostService.registerPost(studyId, title, content, file, authentication);
    }

    /* 게시글 수정 */
    @PostMapping("/{postId}")
    public StudyPost updatePost(@PathVariable(name = "postId") Long postId, @RequestParam(name = "title") String title,
                                @RequestParam(name = "content", required = false) String content,
                                @RequestParam(name = "file", required = false) MultipartFile file,
                                @RequestParam Boolean fileUpdateStatus, Authentication authentication) {
        return studyPostService.updatePost(postId, title, content, file, fileUpdateStatus);
    }

    /* 게시글 삭제 */
    @DeleteMapping("{postId}")
    public boolean deletePost(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        return studyPostService.deletePost(postId);
    }

    /* 검색 */
    @GetMapping("/search/{studyId}")
    public List<StudyPost> searchStudyPost(@PathVariable(name = "studyId") Long studyId,
                                           @RequestParam(name = "searchType") String searchType, @RequestParam(name = "searchWord") String searchWord,
                                           Authentication authentication) {
        return studyPostService.searchStudyPost(studyId, searchType, searchWord);
    }

    /* 파일 다운로드 */
    @GetMapping("/download/{studyId}")
    public ResponseEntity<Resource> download(@PathVariable(name = "studyId") Long studyId, Authentication authentication) {
        return studyPostService.download(studyId);
    }
}
