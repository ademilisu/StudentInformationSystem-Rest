 package schoolrestservicedemo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="instructor_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InstructorDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Embedded
	private Address address;
	
	@OneToOne(mappedBy="instructorDetail", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JsonIgnore
	private Instructor instructor;
	
	public InstructorDetail(){
		
	}
	
	public InstructorDetail(String phoneNumber, String email, Address address) {
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

}
