package team_alcoholic.jumo_server.v1.auth.dto;

/**
 * OAuth2 인증을 통해 받은 사용자 정보를 담는 DTO 인터페이스

 */
public interface OAuth2Response {

    String getProvider();
    String getProviderId();
    String getProfileNickname();
    String getProfileImage();
    String getProfileThumbnailImage();

}
