package team_alcoholic.jumo_server.meeting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.meeting.domain.Meeting;
import team_alcoholic.jumo_server.meeting.dto.MeetingDto;
import team_alcoholic.jumo_server.meeting.dto.MeetingListDto;
import team_alcoholic.jumo_server.meeting.dto.MeetingListResponseDto;
import team_alcoholic.jumo_server.meeting.repository.MeetingRepository;
import team_alcoholic.jumo_server.region.domain.Region;
import team_alcoholic.jumo_server.region.repository.RegionRepository;

import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final RegionRepository regionRepository;

    @Autowired
    public MeetingService(
        MeetingRepository meetingRepository,
        RegionRepository regionRepository
    ) {
        this.meetingRepository = meetingRepository;
        this.regionRepository = regionRepository;
    }

    public MeetingDto findMeetingById(Long id) {
        MeetingDto meeting = meetingRepository.findMeetingById(id);
        Region region = regionRepository.findByAdmcd(meeting.getRegion());
        meeting.setRegion(region.getAdmnm());
        return meeting;
    }

    public MeetingListResponseDto findLatestMeetingList(int limit, Long cursor) {
        List<MeetingListDto> meetings;
        if (cursor==0) meetings = meetingRepository.findLatestMeetingList(limit+1);
        else meetings = meetingRepository.findLatestMeetingListById(limit+1, cursor);
        boolean eof = (meetings.size()<limit+1);
        if (!eof) meetings.remove(meetings.size()-1);
        return new MeetingListResponseDto(meetings, meetings.get(meetings.size()-1).getId(), eof);
    }
}
