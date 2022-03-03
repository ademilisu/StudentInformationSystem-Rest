 package schoolrestservicedemo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="result")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Result {
	/*When student select a lecture this object will create. 
	 * 'INSTURCTOR' and 'ADMIN' roles can manipulate examOne, examTwo and FinalExam. 
	 * When first one and second one manipulated, a Transcript object will create.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="student_id")
	private int studentId;
	
	@Column(name="lecture_id")
	private int lectureId;
	
	@Column(name="exam_one")
	private String examOne;
	
	@Column(name="exam_two")
	private String examTwo;
	
	@Column(name="final_exam")
	private String finalExam;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name="transcript_id")
	@JsonIgnore
	private Transcript transcript;
	
	public Result() {
		
	}

	public Result(String examOne, String examTwo, String finalExam) {
		this.examOne = examOne;
		this.examTwo = examTwo;
		this.finalExam = finalExam;
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

	public String getExamOne() {
		return examOne;
	}

	public void setExamOne(String examOne) {
		this.examOne = examOne;
	}

	public String getExamTwo() {
		return examTwo;
	}

	public void setExamTwo(String examTwo) {
		this.examTwo = examTwo;
	}

	public String getFinalExam() {
		return finalExam;
	}

	public void setFinalExam(String finalExam) {
		this.finalExam = finalExam;
	}

	public Transcript getTranscript() {
		return transcript;
	}

	public void setTranscript(Transcript transcript) {
		this.transcript = transcript;
	}

}
