package team_alcoholic.jumo_server.domain.meeting.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.domain.region.domain.Region;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Meeting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String host;
    private String name;
    private String status;
    private LocalDateTime meetingAt;
    private LocalDateTime fixAt;

    @ManyToOne
    @JoinColumn(name = "region", referencedColumnName = "id")
    private Region region;

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

    @OneToMany(mappedBy = "meeting")
    private List<MeetingImage> images;
}
