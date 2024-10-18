package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tasting_note_new")
@DiscriminatorValue(value = "TASTING")
@Getter
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

    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY)
    private List<NoteAroma> noteAromas = new ArrayList<>();

    protected TastingNote() {}
    public TastingNote(TastingNoteCreateReq noteCreateReq, NewUser user, Liquor liquor) {
        super(user, liquor);
        this.tastingAt = noteCreateReq.getTastingAt();
        this.method = noteCreateReq.getMethod();
        this.place = noteCreateReq.getPlace();
        this.score = noteCreateReq.getScore();
        this.isDetail = noteCreateReq.getIsDetail();
        this.content = noteCreateReq.getContent();
        this.nose = noteCreateReq.getNose();
        this.palate = noteCreateReq.getPalate();
        this.finish = noteCreateReq.getFinish();
    }
}
