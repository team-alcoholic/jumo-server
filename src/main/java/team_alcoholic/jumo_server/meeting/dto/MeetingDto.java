package team_alcoholic.jumo_server.meeting.dto;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.meeting.domain.Meeting;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MeetingDto {

    private Long id;
    private String uuid;
    private String host;
    private String name;
    private String status;
    private LocalDateTime meetingAt;
    private LocalDateTime fixAt;
    private String region;
    private String place;
    private String liquor;
    private Integer participatesMin;
    private Integer participatesMax;
    private Integer payment;
    private boolean paymentMethod;
    private boolean byob;
    private Integer byobMin;
    private Integer byobMax;
    private String description;
    private String externalService;
    private String externalLink;
    private List<String> images;

    public MeetingDto(Meeting meeting, List<String> images) {
        this.id = meeting.getId();
        this.uuid = meeting.getUuid();
        this.host = meeting.getHost();
        this.name = meeting.getName();
        this.status = meeting.getStatus();
        this.meetingAt = meeting.getMeetingAt();
        this.fixAt = meeting.getFixAt();
        this.region = meeting.getRegion();
        this.place = meeting.getPlace();
        this.liquor = meeting.getLiquor();
        this.participatesMin = meeting.getParticipatesMin();
        this.participatesMax = meeting.getParticipatesMax();
        this.payment = meeting.getPayment();
        this.paymentMethod = meeting.isPaymentMethod();
        this.byob = meeting.isByob();
        this.byobMin = meeting.getByobMin();
        this.byobMax = meeting.getByobMax();
        this.description = meeting.getDescription();
        this.externalService = meeting.getExternalService();
        this.externalLink = meeting.getExternalLink();
        this.images = images;
    }
}