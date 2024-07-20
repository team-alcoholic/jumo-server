package team_alcoholic.jumo_server.meeting.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Meeting {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String host;
    private String name;
    private String status;
    private LocalDateTime meetingAt;
    private LocalDateTime fixAt;
    private String region;
    private String place;
    private String liquors;
    private Integer participatesMin;
    private Integer participatesMax;
    private Integer payment;
    private String paymentMethod;
    private boolean byob;
    private Integer byobMin;
    private Integer byobMax;
    private String description;
    private String thumbnailImage;
    private String externalService;
    private String externalLink;
}
