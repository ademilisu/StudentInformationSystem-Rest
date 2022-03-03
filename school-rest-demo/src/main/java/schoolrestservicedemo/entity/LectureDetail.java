package schoolrestservicedemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="lecture_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LectureDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="credit")
	private int credit;
	
	@Column(name="semester")
	private String semester;
	
	@Column(name="grade")
	private String grade;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private LectureStatus type;
	
	public LectureDetail() {
		
	}

	public LectureDetail(String description, int credit, String semester, String grade, LectureStatus type) {
		this.description = description;
		this.credit = credit;
		this.semester = semester;
		this.grade = grade;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public LectureStatus getType() {
		return type;
	}

	public void setType(LectureStatus type) {
		this.type = type;
	}
	
}


