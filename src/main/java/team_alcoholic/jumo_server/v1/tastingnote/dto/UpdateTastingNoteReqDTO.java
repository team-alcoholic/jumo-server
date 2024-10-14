package team_alcoholic.jumo_server.v1.tastingnote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTastingNoteReqDTO extends BaseTastingNoteReqDTO {
    private Long id;
}