package team_alcoholic.jumo_server.v2.note.exception;


import team_alcoholic.jumo_server.global.error.exception.NotFoundException;

public class NoteNotFoundException extends NotFoundException {
    public NoteNotFoundException(Long id) {
        super("해당 id의 Note를 찾지 못함: id " + id);
    }
}