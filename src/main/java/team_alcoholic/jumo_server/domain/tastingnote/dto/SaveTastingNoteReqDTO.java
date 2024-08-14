package team_alcoholic.jumo_server.domain.tastingnote.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveTastingNoteReqDTO extends BaseTastingNoteReqDTO {
    private Long liquorId;
}