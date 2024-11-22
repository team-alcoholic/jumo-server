package team_alcoholic.jumo_server.v2.note.exception;


import team_alcoholic.jumo_server.global.error.exception.NotFoundException;

import java.util.UUID;

public class NoteLikeNotFoundException extends NotFoundException {
    public NoteLikeNotFoundException(Long noteId, UUID userUuid) {
        super("좋아요 표시하지 않은 게시물입니다. note: " + noteId + ", user: " + userUuid);
    }
}