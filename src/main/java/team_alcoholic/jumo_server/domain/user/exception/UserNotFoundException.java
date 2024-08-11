package team_alcoholic.jumo_server.domain.user.exception;


import team_alcoholic.jumo_server.global.error.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("해당하는 아이디의 유저를 찾지 못함 id: " + id);
    }
}