package team_alcoholic.jumo_server.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MeetingListDto {

    private String uuid;
//    private String host;
    private String name;
    private String status;
    private LocalDateTime meetingAt;
    private LocalDateTime fixAt;
    private String region;
//    private String place;
    private String liquor;
    private Integer participatesMin;
    private Integer participatesMax;
    private Integer payment;
//    private boolean paymentMethod;
    private boolean byob;
//    private Integer byobMin;
//    private Integer byobMax;
//    private String description;
    private String externalService;
//    private String externalLink;
}
