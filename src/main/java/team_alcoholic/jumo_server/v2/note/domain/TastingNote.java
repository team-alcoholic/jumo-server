package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;
import team_alcoholic.jumo_server.v2.liquor.domain.NewLiquor;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteUpdateReq;
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
    @BatchSize(size = 20)
    private List<NoteAroma> noteAromas = new ArrayList<>();

    protected TastingNote() {}
    public TastingNote(TastingNoteCreateReq noteCreateReq, NewUser user, NewLiquor liquor) {
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

    public void update(TastingNoteUpdateReq noteUpdateReq) {
        if (noteUpdateReq.getTastingAt() != null) { this.tastingAt = noteUpdateReq.getTastingAt(); }
        if (noteUpdateReq.getMethod() != null) { this.method = noteUpdateReq.getMethod(); }
        if (noteUpdateReq.getPlace() != null) { this.place = noteUpdateReq.getPlace(); }
        if (noteUpdateReq.getScore() != null) { this.score = noteUpdateReq.getScore(); }
        if (noteUpdateReq.getContent() != null) { this.content = noteUpdateReq.getContent(); }

        // 상세작성 활성화된 경우
        if (noteUpdateReq.getIsDetail() != null && noteUpdateReq.getIsDetail()) {
            this.isDetail = true;
            this.nose = noteUpdateReq.getNose();
            this.palate = noteUpdateReq.getPalate();
            this.finish = noteUpdateReq.getFinish();
        }
        // 상세작성 비활성화된 경우
        if (noteUpdateReq.getIsDetail() != null && !noteUpdateReq.getIsDetail()) { this.isDetail = false; }
        // 상세작성 변경 없는 경우
        if (noteUpdateReq.getIsDetail() == null) {
            if (noteUpdateReq.getNose() != null) { this.nose = noteUpdateReq.getNose(); }
            if (noteUpdateReq.getPalate() != null) { this.palate = noteUpdateReq.getPalate(); }
            if (noteUpdateReq.getFinish() != null) { this.finish = noteUpdateReq.getFinish(); }
        }
    }
}
