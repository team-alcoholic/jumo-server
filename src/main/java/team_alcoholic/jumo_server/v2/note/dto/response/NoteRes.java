package team_alcoholic.jumo_server.v2.note.dto.response;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class NoteRes {

    private Long id;
    private UserRes user;
    private LiquorRes liquor;
    private List<NoteImageRes> noteImages = new ArrayList<>();
}
