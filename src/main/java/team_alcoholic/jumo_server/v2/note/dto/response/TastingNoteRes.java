package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v2.note.domain.TastingNote;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;

import java.time.LocalDate;

@Getter @Setter
public class TastingNoteRes extends NoteRes{

    private LocalDate tastingAt;
    private String method;
    private String place;
    private Integer score;
    private Boolean isDetail;
    private String content;
    private String nose;
    private String palate;
    private String finish;

    public static TastingNoteRes from(TastingNote tastingNote) {
        TastingNoteRes tastingNoteRes = new TastingNoteRes();
        tastingNoteRes.setId(tastingNoteRes.getId());
        tastingNoteRes.setUser(UserRes.from(tastingNote.getUser()));
        tastingNoteRes.setLiquor(LiquorRes.from(tastingNote.getLiquor()));
        tastingNoteRes.setTastingAt(tastingNote.getTastingAt());
        tastingNoteRes.setMethod(tastingNote.getMethod());
        tastingNoteRes.setPlace(tastingNote.getPlace());
        tastingNoteRes.setScore(tastingNote.getScore());
        tastingNoteRes.setContent(tastingNote.getContent());
        tastingNoteRes.setIsDetail(tastingNote.getIsDetail());
        tastingNoteRes.setNose(tastingNote.getNose());
        tastingNoteRes.setPalate(tastingNote.getPalate());
        tastingNoteRes.setFinish(tastingNote.getFinish());
        return tastingNoteRes;
    }
}
