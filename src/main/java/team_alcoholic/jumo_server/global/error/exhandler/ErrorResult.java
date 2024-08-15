package team_alcoholic.jumo_server.global.error.exhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;              // 에러 코드
    private String message;           // 에러 메시지
    private List<ErrorDetail> errors; // 필드별 에러 상세 정보 리스트

    @Data
    @AllArgsConstructor
    public static class ErrorDetail {
        private String field;   // 에러가 발생한 필드 이름
        private String message; // 해당 필드에 대한 에러 메시지
    }
}