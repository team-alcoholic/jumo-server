package team_alcoholic.jumo_server.domain.tastingnote.exception;


import team_alcoholic.jumo_server.global.error.exception.NotFoundException;

public class TastingNoteNotFoundException extends NotFoundException {
    public TastingNoteNotFoundException(Long id) {
        super("해당하는 아이디의 테이스팅 노트 일지를 찾지 못함 id: " + id);
    }
}