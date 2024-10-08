package com.web.stard.domain.board.global.api;

import com.web.stard.domain.member.domain.Member;
import com.web.stard.domain.board.global.domain.Report;
import com.web.stard.domain.board.global.domain.ReportDetail;
import com.web.stard.domain.board.global.domain.enums.ReportReason;
import com.web.stard.domain.member.application.MemberService;
import com.web.stard.domain.board.global.application.ReportService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final MemberService memberService;

    // string을 enum으로 변환
    public ReportReason reportReason(String reason) {
        ReportReason reasonType = null;
        if (reason.equals("ABUSE")) {
            reasonType = ReportReason.ABUSE;
        } else if (reason.equals("SPAM")) {
            reasonType = ReportReason.SPAM;
        } else if (reason.equals("PROMOTION")) {
            reasonType = ReportReason.PROMOTION;
        } else if (reason.equals("ADULT")) {
            reasonType = ReportReason.ADULT;
        } else if (reason.equals("ETC")) {
            reasonType = ReportReason.ETC;
        }
        return reasonType;
    }

    // Post 글 신고
    @PostMapping("/posts")
    public ReportDetail createPostReport(@RequestBody Map<String, Object> requestPayload, Authentication authentication) {
        Integer targetIdStr = (Integer) requestPayload.get("id");
        //Integer replyId = Integer.parseInt(targetIdStr);

        String reason = (String) requestPayload.get("reason");
        String customReason = (String) requestPayload.get("customReason");

        Long targetIdLong = targetIdStr.longValue();
        ReportReason reasonType = reportReason(reason);

        return reportService.createPostReport(targetIdLong, reasonType, customReason, authentication);
    }

    // Study 글 신고
    @PostMapping("/studies")
    public ReportDetail createStudyReport(@RequestBody Map<String, Object> requestPayload, Authentication authentication) {
        Integer targetIdStr = (Integer) requestPayload.get("id");
        //Integer replyId = Integer.parseInt(targetIdStr);

        String reason = (String) requestPayload.get("reason");
        String customReason = (String) requestPayload.get("customReason");

        Long targetIdLong = targetIdStr.longValue();
        ReportReason reasonType = reportReason(reason);

        return reportService.createStudyReport(targetIdLong, reasonType, customReason, authentication);
    }

    // Study Post 글 신고
    @PostMapping("/studyposts")
    public ReportDetail createStudyPostReport(@RequestBody Map<String, Object> requestPayload, Authentication authentication) {
        Integer targetIdStr = (Integer) requestPayload.get("id");
        //Integer replyId = Integer.parseInt(targetIdStr);

        String reason = (String) requestPayload.get("reason");
        String customReason = (String) requestPayload.get("customReason");

        Long targetIdLong = targetIdStr.longValue();
        ReportReason reasonType = reportReason(reason);

        return reportService.createStudyPostReport(targetIdLong, reasonType, customReason, authentication);
    }


    // 댓글 신고
    @PostMapping("/replies")
    public ReportDetail createReplyReport(@RequestBody Map<String, Object> requestPayload, Authentication authentication) {
        Integer targetIdStr = (Integer) requestPayload.get("id");
        //Integer replyId = Integer.parseInt(targetIdStr);

        String reason = (String) requestPayload.get("reason");
        String customReason = (String) requestPayload.get("customReason");

        Long targetIdLong = targetIdStr.longValue();
        ReportReason reasonType = reportReason(reason);

        return reportService.createReplyReport(targetIdLong, reasonType, customReason, authentication);
    }

    // 특정 report의 신고 횟수 조회
    @GetMapping("/report-count/{reportId}")
    public Long getReportCounts(@PathVariable(name = "reportId") Long reportId, Authentication authentication) {
        return reportService.getReportCountForReport(reportId);
    }

    // 신고 목록 조회 (누적 신고 수가 5회 이상인)
    @GetMapping()
    public List<Report> getReports(Authentication authentication) {
        return reportService.getReports(authentication);
    }

    // 특정 report의 신고 사유 조회
    @GetMapping("/reason/{reportId}")
    public Map<String, Integer> getReportReasons(@PathVariable(name = "reportId") Long reportId, Authentication authentication) {
        return reportService.getReportReasons(reportId, authentication);
    }

    // 신고 반려
    @DeleteMapping("/{reportId}")
    public void rejectReport(@PathVariable(name = "reportId") Long reportId, Authentication authentication) {
        reportService.rejectReport(reportId, authentication);
    }

    // 신고 승인
    @PostMapping("/accept/{reportId}")
    public void acceptReport(@PathVariable(name = "reportId") Long reportId, Authentication authentication) {
        reportService.acceptReport(reportId, authentication);
    }

    @GetMapping("/members")
    // 회원 목록 조회 (누적 신고 횟수가 1 이상인)
    public List<Member> getMemberReports(Authentication authentication) {
        return reportService.getMemberReports(authentication);
    }

    @PostMapping("/members/{memberId}")
    // 강제 탈퇴
    public ResponseEntity<String> forceDeleteMember(@PathVariable(name = "memberId") String memberId, Authentication authentication) {
//        reportService.forceDeleteMember(memberId, authentication);
        return memberService.deleteMember(memberId, null, authentication);
    }

}