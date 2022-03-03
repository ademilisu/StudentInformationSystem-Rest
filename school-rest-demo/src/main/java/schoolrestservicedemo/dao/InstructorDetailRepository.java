package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.InstructorDetail;

public interface InstructorDetailRepository extends JpaRepository<InstructorDetail, Integer> {

}
