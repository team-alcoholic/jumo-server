package team_alcoholic.jumo_server.v2.note.dto.response;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.note.domain.Note;
import team_alcoholic.jumo_server.v2.note.domain.PurchaseNote;
import team_alcoholic.jumo_server.v2.note.domain.TastingNote;

@Getter @Setter
public class GeneralNoteListRes {

    private String type;
    private PurchaseNoteListRes purchaseNote;
    private TastingNoteListRes tastingNote;

    public static GeneralNoteListRes from(Note note) {
        GeneralNoteListRes noteRes = new GeneralNoteListRes();
        if (note instanceof PurchaseNote purchaseNote) {
            noteRes.setType(purchaseNote.getClass().getAnnotation(DiscriminatorValue.class).value());
            noteRes.setPurchaseNote(PurchaseNoteListRes.from(purchaseNote));
        }
        else if (note instanceof TastingNote tastingNote) {
            noteRes.setType(tastingNote.getClass().getAnnotation(DiscriminatorValue.class).value());
            noteRes.setTastingNote(TastingNoteListRes.from(tastingNote));
        }
        return noteRes;
    }

    public static GeneralNoteListRes from(PurchaseNote note) {
        GeneralNoteListRes noteRes = new GeneralNoteListRes();
        noteRes.setType(note.getClass().getAnnotation(DiscriminatorValue.class).value());
        noteRes.setPurchaseNote(PurchaseNoteListRes.from(note));
        return noteRes;
    }

    public static GeneralNoteListRes from(TastingNote note) {
        GeneralNoteListRes noteRes = new GeneralNoteListRes();
        noteRes.setType(note.getClass().getAnnotation(DiscriminatorValue.class).value());
        noteRes.setTastingNote(TastingNoteListRes.from(note));
        return noteRes;
    }
}
