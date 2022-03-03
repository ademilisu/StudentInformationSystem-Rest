package schoolrestservicedemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="instructor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Instructor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="instructor_detail_id")
	@JsonIgnore
	private InstructorDetail instructorDetail;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name="instructor_lecture",
			joinColumns = @JoinColumn(name="instructor_id"),
			inverseJoinColumns =  @JoinColumn(name="lecture_id"))
	@JsonIgnore
	private List<Lecture> lectures;
	
	@OneToOne(mappedBy="instructor")
	@JsonIgnore
	private AppUser user;
	
	public Instructor() {
		
	}

	public Instructor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Instructor(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void addLecture(Lecture lecture) {
		if(lectures == null) {
			lectures = new ArrayList<>();
		}
		lectures.add(lecture);
	}
	
	public void removeLecture(Lecture lecture) {
		List<Lecture> newLectures = new ArrayList<>();
		for(int i=0; i<lectures.size(); i++) {
			if(lectures.get(i).getLectureName().equals(lecture.getLectureName())) {
				continue;
			}
			newLectures.add(lectures.get(i));
		}
		lectures = newLectures;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public InstructorDetail getInstructorDetail() {
		return instructorDetail;
	}

	public void setInstructorDetail(InstructorDetail instructorDetail) {
		this.instructorDetail = instructorDetail;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

}
