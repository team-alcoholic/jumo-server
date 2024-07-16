package team_alcoholic.jumo_server.meeting.domain;

import jakarta.annotation.Nullable;
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
}
