package at.htlklu.spring.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

@Entity																
@Table(name = "STUDENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends RepresentationModel<Student> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -6574326723164905323L;

	private static final Comparator<Student> BY_SURNAME = Comparator.comparing(Student::getSurname);
	private static final Comparator<Student> BY_FIRSTNAME = Comparator.comparing(Student::getFirstname);
	public static final Comparator<Student> BY_SURNAME_FIRSTNAME = BY_SURNAME.thenComparing(BY_FIRSTNAME);
	//endregion


	//region Properties
	@Id																
	@GeneratedValue(strategy = GenerationType.IDENTITY)				
	@Column(name = "STUDENT_ID")		
	private Integer studentId;
	@NotBlank
	private String surname;
	@NotBlank
	private String firstname;
	@NotNull
	private Character sex;
	@Column(name = "BIRTHDATE")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthDate;
	private Integer height;
	private Double weight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOLCLASS_ID")
	private SchoolClass schoolClass;

	@JsonIgnore
	@OneToMany(mappedBy = "student",
			   cascade = CascadeType.MERGE,
			   orphanRemoval = true,
			   fetch = FetchType.LAZY)
	private Set<StudentSubject> studentSubjects = new HashSet<StudentSubject>();

	@JsonIgnore
	@OneToMany(mappedBy = "student", 								
			   cascade = CascadeType.MERGE, 	
			   orphanRemoval = true, 
			   fetch = FetchType.LAZY)
	private Set<Address> addresses = new HashSet<Address>();

	@JsonIgnore
	@OneToMany(mappedBy = "student",
			   cascade = CascadeType.MERGE,
			   orphanRemoval = true,
			   fetch = FetchType.LAZY)
	private Set<Absence> absences = new HashSet<Absence>();
	//endregion


	//region Constructors
	public Student()
	{
	}	
		
	public Student(SchoolClass schoolClass,
				   String surname, 
				   String firstname, 
				   char sex, 
				   LocalDate birthDate, 
				   Integer height, 
				   Double weight)
	{
		super();
		this.surname = surname;
		this.firstname = firstname;
		this.sex = sex;
		this.birthDate = birthDate;
		this.height = height;
		this.weight = weight;
		this.schoolClass = schoolClass;					// unidirektionale Verknüpfung
		this.schoolClass.getStudents().add(this);		// unidirektionale Verknüpfung 	-> 	bidirektionale Verknüpfung
	}
	//endregion


	//region Methods
	@JsonGetter("toString")
	@Override
	public String toString()
	{
		return String.format("%1$s %2$s", this.surname, this.firstname);
	}

	public boolean isMale()
	{
		return Character.toUpperCase(this.sex) == 'M';
	}

	public boolean isFemale()
	{
		return !this.isMale();
	}
	//endregion


	//region Getter and Setter
	public Integer getStudentId()
	{
		return studentId;
	}
	public void setStudentId(Integer studentId)
	{
		this.studentId = studentId;
	}

	public String getSurname()
	{
		return surname;
	}
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getFirstname()
	{
		return firstname;
	}
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public Character getSex()
	{
		return sex;
	}
	public void setSex(Character sex)
	{
		this.sex = sex;
	}

	public LocalDate getBirthDate()
	{
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate)
	{
		this.birthDate = birthDate;
	}

	public Integer getHeight()
	{
		return height;
	}
	public void setHeight(Integer height)
	{
		this.height = height;
	}

	public Double getWeight()
	{
		return weight;
	}
	public void setWeight(Double weight)
	{
		this.weight = weight;
	}


	public SchoolClass getSchoolClass()
	{
		return schoolClass;
	}	
	public void setSchoolClass(SchoolClass schoolClass)
	{
		this.schoolClass = schoolClass;
	}

	public Set<StudentSubject> getStudentSubjects()
	{
		return studentSubjects;
	}
	public void setStudentSubjects(Set<StudentSubject> studentSubjects)
	{
		this.studentSubjects = studentSubjects;
	}
	
	public Set<Address> getAddresses()
	{
		return addresses;
	}
	public void setAddresses(Set<Address> addresses)
	{
		this.addresses = addresses;
	}

	public Set<Absence> getAbsences()
	{
		return absences;
	}
	public void setAbsences(Set<Absence> absences)
	{
		this.absences = absences;
	}
	//endregion


	//region HashCode and Equals
	@Override
	public int hashCode() {
	    return getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal;
		Student student1 = this;
		if (student1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Student)))
		{
			equal = false;
		}
		else
		{
			Student student2 = (Student)obj;
			equal = student1.studentId != null && Objects.equals(student1.studentId, student2.getStudentId());
		}
		return equal;
	}
	//endregion
}
