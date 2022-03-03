package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
