package com.slembers.alarmony.alarm.exception;

import com.slembers.alarmony.global.util.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AlertErrorCode implements ErrorCode {

    ALERT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알림 설정 중 오류가 발생했습니다."),

    ALERT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 알림 요청입니다."),

    ALERT_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알림 삭제 중 오류가 발생했습니다."),

    ALERT_INVITE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "초대 알림 설정 중 에러가 발생했습니다."),

    ALERT_NOT_FOUND(HttpStatus.NOT_FOUND, "알림이 존재하지 않거나 조회 중 에러가 발생했습니다."),

    ALERT_MEMBER_NOT_EQUAL(HttpStatus.BAD_REQUEST, "알림 수신자가 요청자와 다릅니다."),

    ENUM_NOT_ALLOW(HttpStatus.BAD_REQUEST, "올바르지 않은 enum 타입입니다.")

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public String getName() {
        return name();
    }
}
