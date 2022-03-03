package schoolrestservicedemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="lecture")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="lecture_name")
	private String lectureName;
	
	@Column(name="language")
	private String language;
	
	@Enumerated(EnumType.STRING)
	@Column(name="lecture_selection")
	private LectureSelection lectureSelection;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="lecture_detail_id")
	@JsonIgnore
	private LectureDetail lectureDetail;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.PERSIST)
	@JoinTable(name="result",
		joinColumns = @JoinColumn(name="lecture_id"),
		inverseJoinColumns = @JoinColumn(name= "student_id"))
	@JsonIgnore
	private List<Student> students;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name="instructor_lecture",
			joinColumns = @JoinColumn(name="lecture_id"),
			inverseJoinColumns =  @JoinColumn(name="instructor_id"))
	@JsonIgnore
	private List<Instructor> instructors;
	
	public Lecture() {
		
	}
	
	public Lecture(String lectureName, String language) {
		this.lectureName = lectureName;
		this.language = language;
	}

	public Lecture(String lectureName, String language, LectureSelection lectureSelection) {
		this.lectureName = lectureName;
		this.language = language;
		this.lectureSelection = lectureSelection;
	}

	public void addStudent(Student student) {
		if(students == null) {
			students = new ArrayList<>();
		}
		students.add(student);
	}
		
	public void addInstructor(Instructor instructor) {
		if(instructors == null) {
			instructors = new ArrayList<>();
		}
		instructors.add(instructor);
	}
	
	public void removeInstructor(Instructor instructor) {
		List<Instructor> newList = new ArrayList<>();
		for(int i=0; i<instructors.size(); i++) {
			if(instructors.get(i).getEmail().equals(instructor.getEmail())) {
				continue;
			}
			newList.add(instructors.get(i));
		}
		instructors = newList;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLectureName() {
		return lectureName;
	}
	
	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public LectureDetail getLectureDetail() {
		return lectureDetail;
	}

	public void setLectureDetail(LectureDetail lectureDetail) {
		this.lectureDetail = lectureDetail;
	}

	public List<Instructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<Instructor> instructors) {
		this.instructors = instructors;
	}
	
	public LectureSelection getLectureSelection() {
		return lectureSelection;
	}

	public void setLectureSelection(LectureSelection lectureSelection) {
		this.lectureSelection = lectureSelection;
	}

}

