package likelion.festival.domain.booth.repository;


import likelion.festival.domain.booth.Booth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoothRepository extends JpaRepository<Booth, Long> {

    List<Booth> findByLocation(String location);

    List<Booth> findByTitle(String title);

    // TODO : 메뉴검색 문 추가
    List<Booth> findByMenusName(String menu);
}
