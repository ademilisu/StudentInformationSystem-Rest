package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.Instructor;


public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

}
