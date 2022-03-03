package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {

}
