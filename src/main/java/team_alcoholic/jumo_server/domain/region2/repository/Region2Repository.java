package team_alcoholic.jumo_server.domain.region2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.domain.region2.domain.Region2;

import java.util.List;

public interface Region2Repository extends JpaRepository<Region2, Long> {

    // 대분류 코드를 넘겨주면, 해당 대분류 지역의 하위 중분류 지역 목록 반환
    @Query("select r from Region2 r where r.codeMajor = :codeMajor and r.codeMinor = '000'")
    List<Region2> findRegionsByCodeMajor(String codeMajor);

    // 대분류+중분류 코드를 넘겨주면, 해당 중분류 지역의 하위 소분류 지역 목록 반환
    @Query("select r from Region2 r where r.codeMajor = :codeMajor and r.codeMiddle = :codeMiddle")
    List<Region2> findRegionsByCodeMiddle(String codeMajor, String codeMiddle);

    // 대분류 지역 목록 반환
    @Query("select r from Region2 r where r.codeMiddle = '000'")
    List<Region2> findMajorRegions();
}
