package team_alcoholic.jumo_server.domain.tastingnote.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GenerateTastingNotesResDTO {

    private List<String> noseNotes;
    private List<String> palateNotes;
    private List<String> finishNotes;

}
