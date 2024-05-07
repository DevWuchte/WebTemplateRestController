package at.htlklu.spring.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "SCHOOLCLASS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolClass extends RepresentationModel<SchoolClass> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = 6319534678615031709L;
	public static final Comparator<SchoolClass> BY_NAME = Comparator.comparing(SchoolClass::getName);
	public static final Comparator<SchoolClass> BY_LEVEL = Comparator.comparing(SchoolClass::getLevel);
	//endregion

	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHOOLCLASS_ID")
	private Integer schoolClassId;

	@NotBlank
	private String name;
	@Min(value = 9)
    @Max(value = 13)
	private Integer level;
	private String description;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID")
	private Teacher teacher;

	@JsonIgnore
	@OneToMany(mappedBy = "schoolClass",                             // JPA (mappedBy gibt das Feld in der Klasse "Student" an)
			cascade = CascadeType.MERGE,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private Set<Student> students = new HashSet<Student>();

	@JsonIgnore
	@OneToMany(mappedBy = "schoolClass",                             // JPA (mappedBy gibt das Feld in der Klasse "Student" an)
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private Set<Event> events = new HashSet<Event>();
	//endregion


	//region Constructors
	public SchoolClass() {
	}

	public SchoolClass(String name,
					   int level,
					   String description) {
		super();
		this.name = name;
		this.level = level;
		this.description = description;
	}
	//endregion


	//region Methods
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}
	//endregion


	//region Getter and Setter
	public Integer getSchoolClassId() {
		return schoolClassId;
	}

	public void setSchoolClassId(Integer schoolClassId) {
		this.schoolClassId = schoolClassId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public long countStudentMale(){
		return this.getStudents().stream().filter(student -> student.isMale()).count();
	}
	public long countStudentFemale(){
		return this.getStudents().stream().filter(student -> student.isFemale()).count();
	}
	//endregion


	//region HashCode and Equals
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean equal;
		SchoolClass schoolClass1 = this;
		if (schoolClass1 == obj) {
			equal = true;
		} else if ((obj == null) || (!(obj instanceof SchoolClass))) {
			equal = false;
		} else {
			SchoolClass schoolClass2 = (SchoolClass)obj;
			equal = schoolClass1.schoolClassId != null && Objects.equals(schoolClass1.schoolClassId, schoolClass2.getSchoolClassId());
		}
		return equal;
	}
	//endregion

}
