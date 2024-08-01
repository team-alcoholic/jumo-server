package team_alcoholic.jumo_server.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MeetingListResDto {
    private List<MeetingListDto> meetings;
    private Long lastId;
    private boolean eof;
}
