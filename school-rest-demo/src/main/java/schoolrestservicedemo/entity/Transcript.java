package schoolrestservicedemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="transcript")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transcript {
	
	/* The examOne field of the Result class is saved as the first grade.
	 * The greater of examTwo and fnalExam is recorded as the second grade.
	 * The Score field in this class is the average of the first and second grades.
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="tstudent_id")
	private int studentId;
	
	@Column(name="tlecture_id")
	private int lectureId;
	
	@Column(name="score")
	private String score;
	
	public Transcript() {
		
	}
	
	public Transcript(String score) {
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getLectureId() {
		return lectureId;
	}

	public void setLectureId(int lectureId) {
		this.lectureId = lectureId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Transcript [score=" + score + "]";
	}
	
}
