package team_alcoholic.jumo_server.domain.note.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "tasting_note_new")
@DiscriminatorValue(value = "TASTING")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TastingNote extends Note {

    private LocalDate tastingAt;
    private String method;
    private String place;
    private Integer score;
    private Boolean isDetail;
    private String content;
    private String nose;
    private String palate;
    private String finish;
}
