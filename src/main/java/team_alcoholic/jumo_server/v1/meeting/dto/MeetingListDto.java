package team_alcoholic.jumo_server.v1.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v1.meeting.domain.Meeting;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MeetingListDto {

    private Long id;
    private String uuid;
    //    private String host;
    private String name;
    private String status;
    private LocalDateTime meetingAt;
    private LocalDateTime fixAt;
    private String region;
    //    private String place;
    private String liquors;
    private Integer participatesMin;
    private Integer participatesMax;
    private Integer payment;
    //    private String paymentMethod;
    private boolean byob;
    //    private Integer byobMin;
//    private Integer byobMax;
//    private String description;
    private String thumbnail;
    private String externalService;
    //    private String externalLink;
    private LocalDateTime createdAt;

    public MeetingListDto(Meeting meeting) {
        this.id = meeting.getId();
        this.uuid = meeting.getUuid();
        this.name = meeting.getName();
        this.status = meeting.getStatus();
        this.meetingAt = meeting.getMeetingAt();
        this.fixAt = meeting.getFixAt();
        if (meeting.getRegion() != null) {
            this.region = meeting.getRegion().getName();
        }
        this.liquors = meeting.getLiquors();
        this.participatesMin = meeting.getParticipatesMin();
        this.participatesMax = meeting.getParticipatesMax();
        this.payment = meeting.getPayment();
        this.byob = meeting.isByob();
        this.thumbnail = meeting.getThumbnailImage();
        this.externalService = meeting.getExternalService();
        this.createdAt = meeting.getCreatedAt();
    }
}
