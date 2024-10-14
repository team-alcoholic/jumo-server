package team_alcoholic.jumo_server.v1.tastingnote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveTastingNoteReqDTO extends BaseTastingNoteReqDTO {
    private Long liquorId;
}