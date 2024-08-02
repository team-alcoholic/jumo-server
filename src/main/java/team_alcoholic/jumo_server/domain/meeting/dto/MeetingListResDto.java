package team_alcoholic.jumo_server.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MeetingListResDto {
    private List<MeetingListDto> meetings;
    private Long cursorId;
    private boolean eof;
    private LocalDateTime cursorDate;

    public MeetingListResDto(List<MeetingListDto> meetings, Long cursorId, boolean eof, LocalDateTime cursorDate) {
        this.meetings = meetings;
        this.cursorId = cursorId;
        this.eof = eof;
        this.cursorDate = cursorDate;
    }

}
