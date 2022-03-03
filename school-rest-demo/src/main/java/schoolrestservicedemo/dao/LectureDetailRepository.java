package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.LectureDetail;

public interface LectureDetailRepository extends JpaRepository<LectureDetail, Integer> {

}
