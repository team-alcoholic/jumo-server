package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v2.note.domain.NoteImage;
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

    public static TastingNoteRes from(TastingNote note) {
        TastingNoteRes tastingNoteRes = new TastingNoteRes();
        tastingNoteRes.setId(tastingNoteRes.getId());
        tastingNoteRes.setUser(UserRes.from(note.getUser()));
        tastingNoteRes.setLiquor(LiquorRes.from(note.getLiquor()));
        for (NoteImage noteImage : note.getNoteImages()) {
            tastingNoteRes.getNoteImages().add(NoteImageRes.from(noteImage));
        }

        tastingNoteRes.setTastingAt(note.getTastingAt());
        tastingNoteRes.setMethod(note.getMethod());
        tastingNoteRes.setPlace(note.getPlace());
        tastingNoteRes.setScore(note.getScore());
        tastingNoteRes.setContent(note.getContent());
        tastingNoteRes.setIsDetail(note.getIsDetail());
        tastingNoteRes.setNose(note.getNose());
        tastingNoteRes.setPalate(note.getPalate());
        tastingNoteRes.setFinish(note.getFinish());
        return tastingNoteRes;
    }
}
