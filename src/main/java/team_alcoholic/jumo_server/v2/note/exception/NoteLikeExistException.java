package team_alcoholic.jumo_server.v2.note.exception;


import team_alcoholic.jumo_server.global.error.exception.NotFoundException;

import java.util.UUID;

public class NoteLikeExistException extends NotFoundException {
    public NoteLikeExistException(Long noteId, UUID userUuid) {
        super("이미 좋아요 표시한 게시물입니다. note: " + noteId + ", user: " + userUuid);
    }
}