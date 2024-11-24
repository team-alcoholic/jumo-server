package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GeneralNotePageRes {

    private Long cursor;
    private boolean eof;
    private List<GeneralNoteListRes> notes;

    public static GeneralNotePageRes of(Long cursor, boolean eof, List<GeneralNoteListRes> notes) {
        GeneralNotePageRes noteListRes = new GeneralNotePageRes();
        noteListRes.cursor = cursor;
        noteListRes.eof = eof;
        noteListRes.notes = notes;
        return noteListRes;
    }
}
