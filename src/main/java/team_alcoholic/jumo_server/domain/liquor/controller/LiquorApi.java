package team_alcoholic.jumo_server.domain.liquor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import team_alcoholic.jumo_server.domain.region.domain.Region;

import java.util.List;

@Tag(name = "주류 api", description = "주류 단건 조회입니다.")
public interface LiquorApi {

    @Operation(summary = "지역코드에 해당하는 지역 객체를 응답하는 API입니다.")
    @ApiResponse(
            responseCode = "200",
            description = "지역 조회 성공",
            content = @Content(schema = @Schema(implementation = Region.class))
    )
    public Region getRegionById(String admcd);

    @Operation(summary = "대분류 지역 목록을 응답하는 API입니다.")
    @ApiResponse(
            responseCode = "200",
            description = "대분류 지역 목록 조회 성공",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Region.class)))
    )
    public List<Region> getMajorRegionList();

    @Operation(summary = "지역코드에 해당하는 상위 분류 지역에 포함된 하위 지역 목록을 응답하는 API입니다.")
    @ApiResponse(
            responseCode = "200",
            description = "하위 지역 목록 조회 성공",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Region.class)))
    )
    public List<Region> getSubRegionListById(String admcd);
}
