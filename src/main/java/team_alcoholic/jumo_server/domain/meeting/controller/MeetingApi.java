package team_alcoholic.jumo_server.domain.meeting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingResDto;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListResDto;
import java.time.LocalDateTime;


@Tag(name = "모임 API", description = "모임에 대한 CRUD를 수행하는 API입니다.")
public interface MeetingApi {

    @Operation(summary = "id에 해당하는 모임 상세정보를 응답하는 API입니다.")
    @ApiResponse(
            responseCode = "200",
            description = "모임 조회 성공",
            content = @Content(schema = @Schema(implementation = MeetingResDto.class))
    )
    public MeetingResDto getMeetingById(Long id);

    @Operation(summary = "모임 목록에 대한 pagination API입니다. 정렬방식, 개수, 커서를 지정할 수 있습니다.")
    @ApiResponse(
            responseCode = "200",
            description = "모임 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = MeetingListResDto.class))
    )
    public MeetingListResDto getLatestMeetingList(String sort, int limit, Long cursorId, LocalDateTime cursorDate);

}

