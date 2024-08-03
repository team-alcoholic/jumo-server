package team_alcoholic.jumo_server.domain.meeting.exception;


import team_alcoholic.jumo_server.global.error.exception.NotFoundException;

public class MeetingNotFoundException extends NotFoundException {
    public MeetingNotFoundException(Long id) {
        super("해당하는 아이디의 Meeting을 찾지 못함 id: " + id);
    }
}