package team_alcoholic.jumo_server.v2.note.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class PurchaseNoteUpdateReq {

    private List<MultipartFile> noteImages;

    private LocalDate purchaseAt;
    private String place;
    private Integer price;
    private Integer volume;
    private String content;
}
