package team_alcoholic.jumo_server.domain.meeting.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class MeetingImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "meeting", nullable = false)
    private Meeting meeting;
    private String url;
}
