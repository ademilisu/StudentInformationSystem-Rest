package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

}
