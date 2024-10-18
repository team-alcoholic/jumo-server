package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v2.note.domain.NoteImage;
import team_alcoholic.jumo_server.v2.note.domain.PurchaseNote;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;

import java.time.LocalDate;

@Getter @Setter
public class PurchaseNoteRes extends NoteRes {

    private LocalDate purchaseAt;
    private String place;
    private Integer price;
    private Integer volume;
    private String content;

    public static PurchaseNoteRes from(PurchaseNote note) {
        PurchaseNoteRes purchaseNoteRes = new PurchaseNoteRes();
        purchaseNoteRes.setId(note.getId());
        purchaseNoteRes.setUser(UserRes.from(note.getUser()));
        purchaseNoteRes.setLiquor(LiquorRes.from(note.getLiquor()));
        for (NoteImage noteImage : note.getNoteImages()) {
            purchaseNoteRes.getNoteImages().add(NoteImageRes.from(noteImage));
        }

        purchaseNoteRes.setPurchaseAt(note.getPurchaseAt());
        purchaseNoteRes.setPlace(note.getPlace());
        purchaseNoteRes.setPrice(note.getPrice());
        purchaseNoteRes.setVolume(note.getVolume());
        purchaseNoteRes.setContent(note.getContent());
        return purchaseNoteRes;
    }
}
