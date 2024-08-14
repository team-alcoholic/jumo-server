package team_alcoholic.jumo_server.domain.tastingnote.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseTastingNoteReqDTO {

    private Integer noseScore;
    private Integer palateScore;
    private Integer finishScore;

    @Size(max = 1500)
    private String noseMemo;
    @Size(max = 1500)
    private String palateMemo;
    @Size(max = 1500)
    private String finishMemo;
    @Size(max = 1500)
    private String overallNote;
    private String mood;

    @Size(max = 255)
    private String noseNotes;
    @Size(max = 255)
    private String palateNotes;
    @Size(max = 255)
    private String finishNotes;
}