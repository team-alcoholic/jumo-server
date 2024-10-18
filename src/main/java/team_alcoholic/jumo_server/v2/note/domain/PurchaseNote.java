package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteCreateReq;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

import java.time.LocalDate;

@Entity
@DiscriminatorValue(value = "PURCHASE")
@Getter
public class PurchaseNote extends Note {

    private LocalDate purchaseAt;
    private String place;
    private Integer price;
    private Integer volume;
    private String content;

    protected PurchaseNote() {}
    public PurchaseNote(PurchaseNoteCreateReq noteCreateReq, NewUser user, Liquor liquor) {
        super(user, liquor);
        this.purchaseAt = noteCreateReq.getPurchaseAt();
        this.place = noteCreateReq.getPlace();
        this.price = noteCreateReq.getPrice();
        this.volume = noteCreateReq.getVolume();
        this.content = noteCreateReq.getContent();
    }
}
