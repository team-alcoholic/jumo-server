package team_alcoholic.jumo_server.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_alcoholic.jumo_server.domain.meeting.domain.Meeting;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingResDto;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListDto;
import team_alcoholic.jumo_server.domain.meeting.exception.MeetingNotFoundException;
import team_alcoholic.jumo_server.domain.meeting.repository.MeetingRepository;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListResDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingResDto findMeetingById(Long id) {
        Meeting meeting = meetingRepository.findMeetingById(id);
        if (meeting == null) {
            throw new MeetingNotFoundException(id);
        }
        return new MeetingResDto(meeting);
    }

    @Transactional(readOnly = true)
    public MeetingListResDto findLatestMeetingList(int limit, Long cursor) {
        List<Meeting> meetings;
        Pageable pageable = PageRequest.of(0, limit + 1);

        // cursor가 0이면 Long.MAX_VALUE를 사용
        Long effectiveCursor = (cursor == 0) ? Long.MAX_VALUE : cursor;

        meetings = meetingRepository.findByIdLessThanOrderByIdDesc(effectiveCursor, pageable);

        boolean eof = (meetings.size() < limit + 1);
        if (!eof) {
            meetings.remove(meetings.size() - 1);
        }

        List<MeetingListDto> meetingList = meetings.stream().map(MeetingListDto::new).toList();
        return new MeetingListResDto(meetingList, meetings.get(meetings.size() - 1).getId(), eof);
    }

}


