package team_alcoholic.jumo_server.v1.liquor.exception;


import team_alcoholic.jumo_server.global.error.exception.NotFoundException;

public class LiquorNotFoundException extends NotFoundException {
    public LiquorNotFoundException(Long id) {
        super("해당하는 아이디의 주류를 찾지 못함 id: " + id);
    }
}