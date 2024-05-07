package at.htlklu.spring.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.RepresentationModel;

@Entity																
@Table(name = "STUDENT_SUBJECT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentSubject extends RepresentationModel<StudentSubject> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -6283415825230759166L;
	//endregion


	//region Properties
	@Id																
	@GeneratedValue(strategy = GenerationType.IDENTITY)				
	@Column(name = "STUDENT_SUBJECT_ID")							
	private Integer studentSubjectId;

	private int grade;

	@ManyToOne(fetch = FetchType.LAZY)								
    @JoinColumn(name = "STUDENT_ID")								
	private Student student;
	
	@ManyToOne(fetch = FetchType.LAZY)								
    @JoinColumn(name = "SUBJECT_ID")								
	private Subject subject;
	//endregion


	//region Constructors
	public StudentSubject()
	{
	}
	
	public StudentSubject(Integer studentSubjectId, 
						  int studentId, 
						  int subjectId, 
						  int grade)
	{
		super();
		this.studentSubjectId = studentSubjectId;
		this.grade = grade;
	}
	
	public StudentSubject(int studentId, 
						  int subjectId, 
						  int grade)
	{
		this(null, studentId, subjectId, grade);
	}
	//endregion


	//region Methods
	@Override
	public String toString()
	{
		return String.format("%s", this.grade);
	}
	//endregion


	//region Getter and Setter
	public Integer getStudentSubjectId()
	{
		return studentSubjectId;
	}
	public void setStudentSubjectId(Integer studentSubjectId)
	{
		this.studentSubjectId = studentSubjectId;
	}

	public int getGrade()
	{
		return grade;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}


	public Student getStudent()
	{
		return this.student;
	}
	public void setStudent(Student student)
	{
		this.student = student;
	}

	public Subject getSubject()
	{
		return this.subject;
	}
	public void setSubject(Subject subject)
	{
		this.subject = subject;
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
		StudentSubject studentSubject1 = this;
		if (studentSubject1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof StudentSubject)))
		{
			equal = false;
		}
		else
		{
			StudentSubject studentSubject2 = (StudentSubject)obj;
			equal = studentSubject1.studentSubjectId != null && Objects.equals(studentSubject1.studentSubjectId, studentSubject2.getStudentSubjectId());
		}
		return equal;
	}
	//endregion
}
