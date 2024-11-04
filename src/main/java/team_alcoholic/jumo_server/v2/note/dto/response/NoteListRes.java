package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.note.domain.Note;

import java.util.List;

@Getter @Setter
public class NoteListRes {

    private Long cursor;
    private boolean eof;
    private List<GeneralNoteRes> notes;

    public static NoteListRes of(Long cursor, boolean eof, List<GeneralNoteRes> notes) {
        NoteListRes noteListRes = new NoteListRes();
        noteListRes.cursor = cursor;
        noteListRes.eof = eof;
        noteListRes.notes = notes;
        return noteListRes;
    }
}
