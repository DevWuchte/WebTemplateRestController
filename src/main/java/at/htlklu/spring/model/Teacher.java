package at.htlklu.spring.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "TEACHER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends RepresentationModel<Teacher> implements Serializable
{
	//region static Properties
	public static final Comparator<Teacher> BY_SURNAME = Comparator.comparing(Teacher::getSurname);
	public static final Comparator<Teacher> BY_Firstname = Comparator.comparing(Teacher::getFirstname);
	public static final Comparator<Teacher> BY_SurnameFirstname = BY_SURNAME.thenComparing(Teacher::getFirstname);


	private static final long serialVersionUID = -7790802681322726971L;
	private static final Logger logger = LogManager.getLogger(Teacher.class);
	private static final String className = "Teacher";
	//endregion


	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)				
	@Column(name = "TEACHER_ID")	
	private Integer teacherId;
	
	@NotBlank														// https://www.baeldung.com/hibernate-validator-constraints, https://www.baeldung.com/javax-validation
	@Length(min = 3, max = 25)
	private String surname;
	@NotBlank
	private String firstname;
	@NotBlank
	@Column(name = "SHORT_NAME")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String shortName;
	@NotNull
	private Character sex;
	private String title;
	@Email 
	private String email;
	private String phone;
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", 								// JPA (mappedBy gibt das Feld in der Klasse "teacher" an)
			   cascade = CascadeType.MERGE, 	
			   orphanRemoval = true, 
			   fetch = FetchType.LAZY)
	private Set<SchoolClass> schoolClasses = new HashSet<SchoolClass>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", 								// JPA (mappedBy gibt das Feld in der Klasse "teacher" an)
			   cascade = CascadeType.MERGE, 	
			   orphanRemoval = true, 
			   fetch = FetchType.LAZY)
	private Set<Department> departments = new HashSet<Department>();
	//endregion


	//region Constructors
	public Teacher()
	{
	}
	
		
	public Teacher(String surname, 
				   String firstname, 
				   String shortName,
				   Character sex, 
				   String title, 
				   String email, 
				   String phone)
	{
		super();
//		logger.info(LogUtils.info(className, "Teacher(sfsstep)"));

		this.surname = surname;
		this.firstname = firstname;
		this.shortName = shortName;
		this.sex = sex;
		this.title = title;
		this.email = email;
		this.phone = phone;
	}
	
	
	public Teacher(String surname, 
				   String firstname, 
				   String shortName,
				   Character sex)
	{
		this(surname, firstname, shortName, sex, null, null, null);
//		logger.info(LogUtils.info(className, "Teacher(sfss)"));
	}
	//endregion


	//region Methods
//	@JsonGetter("toString")
	@Override
	public String toString()
	{
		return String.format("%s %s", this.surname, this.firstname);
	}

	@JsonGetter("fullName")
	public String fullName()
	{
		return String.format("%s %s %s", this.title, this.firstname, this.surname);
	}
	//endregion


	//region Getter and Setter
	public Integer getTeacherId()
	{
		return teacherId;
	}
	public void setTeacherId(Integer teacherId)
	{
		this.teacherId = teacherId;
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

	public String getShortName()
	{
		return shortName;
	}
	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public Character getSex()
	{
		return sex;
	}
	public void setSex(Character sex)
	{
		this.sex = sex;
	}

	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	
	
	public Set<SchoolClass> getSchoolClasses()
	{
		return schoolClasses;
	}
	public void setSchoolClasses(Set<SchoolClass> schoolClasses)
	{
		this.schoolClasses = schoolClasses;
	}

	public Set<Department> getDepartments()
	{
		return departments;
	}
	public void setDepartments(Set<Department> departments)
	{
		this.departments = departments;
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
		Teacher teacher1 = this;
		if (teacher1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Teacher)))
		{
			equal = false;
		}
		else
		{
			Teacher teacher2 = (Teacher)obj;
			equal = teacher1.teacherId != null && Objects.equals(teacher1.teacherId, teacher2.getTeacherId());
		}
		return equal;
	}
	//endregion
	
}
