package team_alcoholic.jumo_server.domain.tastingnote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TastingNoteReqDTO {

    private Long liquorId;

    private Integer noseScore;
    private Integer palateScore;
    private Integer finishScore;

    private String noseMemo;
    private String palateMemo;
    private String finishMemo;

    private String overallNote;
    private String mood;

    private String noseNotes;
    private String palateNotes;
    private String finishNotes;

}