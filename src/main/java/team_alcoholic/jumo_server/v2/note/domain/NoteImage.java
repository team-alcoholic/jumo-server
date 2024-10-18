package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class NoteImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Note note;

    private String fileName;
    private String fileUrl;

    protected NoteImage() {}
    public NoteImage(Note note, String fileName, String fileUrl) {
        this.note = note;
        this.note.getNoteImages().add(this);
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
