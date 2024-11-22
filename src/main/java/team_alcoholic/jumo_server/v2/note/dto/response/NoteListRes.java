package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.liquor.dto.LiquorSimpleRes;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class NoteListRes {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserRes user;
    private LiquorSimpleRes liquor;
    private List<NoteImageRes> noteImages = new ArrayList<>();
    private Long likes;
    private Long comments;
}
