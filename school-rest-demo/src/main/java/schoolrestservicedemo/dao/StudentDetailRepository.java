package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.StudentDetail;

public interface StudentDetailRepository extends JpaRepository<StudentDetail, Integer> {

}
