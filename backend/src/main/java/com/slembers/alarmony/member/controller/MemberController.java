package com.slembers.alarmony.member.controller;


import com.slembers.alarmony.global.security.util.SecurityUtil;
import com.slembers.alarmony.member.dto.ChangePasswordDto;
import com.slembers.alarmony.member.dto.MemberInfoDto;
import com.slembers.alarmony.member.dto.request.*;
import com.slembers.alarmony.member.dto.response.*;
import com.slembers.alarmony.member.service.EmailService;
import com.slembers.alarmony.member.service.MemberService;
import com.slembers.alarmony.report.dto.ModifiedMemberInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        memberService.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 아이디 중복 체크
     **/
    @GetMapping("/check-id")
    public ResponseEntity<CheckDuplicateDto> checkForDuplicateId(@RequestParam("username") String username) {
        return ResponseEntity.ok(memberService.checkForDuplicateId(username));
    }

    /**
     * 이메일 중복 체크
     */
    @GetMapping("/check-email")
    public ResponseEntity<CheckDuplicateDto> checkForDuplicateEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(memberService.checkForDuplicateEmail(email));
    }

    /**
     * 닉네임 중복 체크
     */
    @GetMapping("/check-nickname")
    public ResponseEntity<CheckDuplicateDto> checkForDuplicateNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(memberService.checkForDuplicateNickname(nickname));
    }


    @GetMapping("/verify/{key}")
    public ResponseEntity<String> confirmSignUp(@PathVariable String key) {
        emailService.confirmSignUp(key);
        return new ResponseEntity<>("인증에 성공하였습니다.", HttpStatus.OK);
    }


    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> reissueToken(@RequestBody ReissueTokenDto reissueTokenDto) {
        return  ResponseEntity.ok(memberService.reissueToken(reissueTokenDto));
    }

    /**
     * 등록 토큰
     */
    @PutMapping("/regist-token")
    public ResponseEntity<Void> putRegistrationToken(@RequestBody PutRegistrationTokenRequestDto registrationTokenRequestDto) {
        memberService.putRegistrationToken(SecurityUtil.getCurrentUsername(), registrationTokenRequestDto.getRegistrationToken());
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * 아이디 찾기
     */
    @PostMapping("/find-id")
    public ResponseEntity<Void> findId(@RequestBody FindMemberIdDto findMemberIdDto) throws MessagingException {
        memberService.findMemberId(findMemberIdDto);
        return ResponseEntity.noContent()
                .build();
    }


    /**
     * 비밀번호 찾기
     */
    @PostMapping("/find-pw")
    public ResponseEntity<Void> findPassword(@RequestBody FindPasswordDto findPasswordDto) {
        memberService.findMemberPassword(findPasswordDto);
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * 회원 정보 조회하기
     */
    @GetMapping("/info")
    public ResponseEntity<MemberResponseDto> getMemberInfo() {
        return ResponseEntity.ok(memberService.getMemberInfo(SecurityUtil.getCurrentUsername()));
    }


    /**
     * 회원 탈퇴 (비활성화)
     */
    @DeleteMapping()
    public ResponseEntity<Void> deleteMember() {
        memberService.deleteMember(SecurityUtil.getCurrentUsername());
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * 회원 정보 수정
     */
    @PatchMapping()
    public ResponseEntity<MemberInfoDto> modifyMemberInfo(@ModelAttribute ModifiedMemberInfoDto modifiedMemberInfoDto) throws IOException {
        return ResponseEntity.ok((memberService.modifyMemberInfo(SecurityUtil.getCurrentUsername(), modifiedMemberInfoDto)));
    }

    /**
     * 이미지 변경
     */
    @PatchMapping("/image")
    public ResponseEntity<ImageResponseDto> modifyMemberImage(@ModelAttribute MultipartFile imgProfileFile) {
        return ResponseEntity.ok(memberService.modifyMemberImage(SecurityUtil.getCurrentUsername(), imgProfileFile));
    }

    /**
     * 닉네임 변경
     */
    @PostMapping("/nickname")
    public ResponseEntity<NicknameResponseDto> modifyMemberNickname(@RequestBody @Valid ChangeNicknameRequestDto changeNicknameRequestDto) {
        return ResponseEntity.ok(memberService.modifyMemberNickname(SecurityUtil.getCurrentUsername(), changeNicknameRequestDto.getChangeName()));
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/change-pwd")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        memberService.changePassword(SecurityUtil.getCurrentUsername(), changePasswordDto);
        return ResponseEntity.noContent()
                .build();
    }
}
