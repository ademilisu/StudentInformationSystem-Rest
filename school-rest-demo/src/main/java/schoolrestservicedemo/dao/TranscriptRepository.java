package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.Transcript;

public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {

}
