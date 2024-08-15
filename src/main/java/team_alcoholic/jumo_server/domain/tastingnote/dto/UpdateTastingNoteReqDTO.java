package team_alcoholic.jumo_server.domain.tastingnote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTastingNoteReqDTO extends BaseTastingNoteReqDTO {
    private Long id;
}