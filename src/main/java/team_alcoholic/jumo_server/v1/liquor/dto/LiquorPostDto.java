package team_alcoholic.jumo_server.v1.liquor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiquorPostDto {
    @NotBlank(message = "주류명은 필수입니다")
    @Size(max = 200, message = "주류명은 200자를 초과할 수 없습니다")
    private String koName;

    @Min(value = 0, message = "알코올 도수는 0 이상이어야 합니다")
    @Max(value = 100, message = "알코올 도수는 100 이하여야 합니다")
    private Double abv;

    @Size(max = 200, message = "국가명은 200자를 초과할 수 없습니다")
    private String country;

    @Size(max = 200, message = "주종은 200자를 초과할 수 없습니다")
    private String type;

    @Min(value = 0, message = "용량은 0보다 커야 합니다")
    @Max(value = 100000, message = "용량이 너무 큽니다")
    private Integer volume;

    @Size(max = 200, message = "지역명은 200자를 초과할 수 없습니다")
    private String region;
}