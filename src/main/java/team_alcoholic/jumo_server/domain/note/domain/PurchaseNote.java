package team_alcoholic.jumo_server.domain.note.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue(value = "PURCHASE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseNote extends Note {

    private LocalDate purchaseAt;
    private String place;
    private Integer price;
    private Integer volume;
    private String content;
}
