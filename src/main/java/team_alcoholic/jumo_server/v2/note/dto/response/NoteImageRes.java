package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.note.domain.NoteImage;

@Getter @Setter
public class NoteImageRes {
    private Long id;
    private String fileName;
    private String fileUrl;

    public static NoteImageRes from(NoteImage noteImage) {
        NoteImageRes noteImageRes = new NoteImageRes();
        noteImageRes.setId(noteImage.getId());
        noteImageRes.setFileName(noteImage.getFileName());
        noteImageRes.setFileUrl(noteImage.getFileUrl());
        return noteImageRes;
    }
}
