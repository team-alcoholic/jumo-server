package team_alcoholic.jumo_server.global.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {
    @CreatedBy
    @Column(updatable = false)
    private String createdBy = "spring_server";  // 기본값 설정

    @LastModifiedBy
    private String updatedBy = "spring_server";  // 기본값 설정
}
