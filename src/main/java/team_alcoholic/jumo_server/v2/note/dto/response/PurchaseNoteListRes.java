package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.liquor.dto.LiquorSimpleRes;
import team_alcoholic.jumo_server.v2.note.domain.NoteImage;
import team_alcoholic.jumo_server.v2.note.domain.PurchaseNote;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;

import java.time.LocalDate;

@Getter @Setter
public class PurchaseNoteListRes extends NoteListRes {

    private LocalDate purchaseAt;
    private String place;
    private Integer price;
    private Integer volume;
    private String content;

    public static PurchaseNoteListRes from(PurchaseNote note) {
        PurchaseNoteListRes purchaseNoteRes = new PurchaseNoteListRes();

        purchaseNoteRes.setId(note.getId());
        purchaseNoteRes.setCreatedAt(note.getCreatedAt());
        purchaseNoteRes.setUpdatedAt(note.getUpdatedAt());
        purchaseNoteRes.setUser(UserRes.from(note.getUser()));
        purchaseNoteRes.setLiquor(LiquorSimpleRes.from(note.getLiquor()));
        for (NoteImage noteImage : note.getNoteImages()) {
            purchaseNoteRes.getNoteImages().add(NoteImageRes.from(noteImage));
        }
        purchaseNoteRes.setLikes(note.getLikes());
        purchaseNoteRes.setComments(note.getComments());

        purchaseNoteRes.setPurchaseAt(note.getPurchaseAt());
        purchaseNoteRes.setPlace(note.getPlace());
        purchaseNoteRes.setPrice(note.getPrice());
        purchaseNoteRes.setVolume(note.getVolume());
        purchaseNoteRes.setContent(note.getContent());

        return purchaseNoteRes;
    }
}
