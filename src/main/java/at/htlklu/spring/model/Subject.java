package at.htlklu.spring.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.RepresentationModel;

@Entity																
@Table(name = "SUBJECT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subject extends RepresentationModel<Subject> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -4570495411166636654L;
	//endregion


	//region Properties
	@Id																
	@GeneratedValue(strategy = GenerationType.IDENTITY)				
	@Column(name = "SUBJECT_ID")									
	private Integer subjectId;
	@NotBlank
	private String name;
	@NotBlank
	@Column(name = "NAME_SHORT")									
	private String nameShort;
	private boolean main;

	@JsonIgnore
	@OneToMany(mappedBy = "subject", 						
			   cascade = CascadeType.MERGE, 	
			   orphanRemoval = true, 
			   fetch = FetchType.LAZY)
	private Set<StudentSubject> studentSubjects = new HashSet<StudentSubject>();
	//endregion


	//region Constructors
	public Subject()
	{	
	}
	
	public Subject(Integer subjectId, 
				   String name, 
				   String nameShort, 
				   boolean main)
	{
		super();
		this.subjectId = subjectId;
		this.name = name;
		this.nameShort = nameShort;
		this.main = main;
	}
	
	public Subject(String name, 
				   String nameShort, 
				   boolean main)
	{
		this(null, name, nameShort, main);
	}
	//endregion


	//region Methods
	@Override
	public String toString()
	{
		return String.format("%s", this.name);
	}
	//endregion


	//region Getter and Setter
	public Integer getSubjectId()
	{
		return subjectId;
	}
	public void setSubjectId(Integer subjectId)
	{
		this.subjectId = subjectId;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getNameShort()
	{
		return nameShort;
	}
	public void setNameShort(String nameShort)
	{
		this.nameShort = nameShort;
	}

	public boolean getMain()
	{
		return main;
	}
	public void setMain(boolean main)
	{
		this.main = main;
	}


	public Set<StudentSubject> getStudentSubjects()
	{
		return this.studentSubjects;
	}
	public void setStudentSubjects(Set<StudentSubject> studentSubjects)
	{
		this.studentSubjects = studentSubjects;
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
		Subject subject1 = this;
		if (subject1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Subject)))
		{
			equal = false;
		}
		else
		{
			Subject subject2 = (Subject)obj;
			equal = subject1.subjectId != null && Objects.equals(subject1.subjectId, subject2.getSubjectId());
		}
		return equal;
	}
	//endregion

}
