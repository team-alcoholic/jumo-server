package team_alcoholic.jumo_server.v2.note.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.note.domain.NoteImage;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class TastingNoteCreateReq {

    private Long liquorId;
    private List<NoteImage> noteImages;

    private LocalDate tastingAt;
    private String method;
    private String place;
    private Integer score;
    private Boolean isDetail;

    @Size(max = 1500)
    private String content;
    @Size(max = 1500)
    private String nose;
    @Size(max = 1500)
    private String palate;
    @Size(max = 1500)
    private String finish;
}
