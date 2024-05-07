package at.htlklu.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "ABSENCE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Absence extends RepresentationModel<Absence> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -3679184412479019314L;

	public static final Comparator<Absence> BY_TIME_FROM = Comparator.comparing(Absence::getTimeFrom);
	//endregion


	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ABSENCE_ID")
	private Integer absenceId;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
	private Student student;

	@Column(name = "TIME_FROM", columnDefinition = "TIMESTAMP")
	private LocalDateTime timeFrom;
	@Column(name = "TIME_TO", columnDefinition = "TIMESTAMP")
	private LocalDateTime timeTo;
	private int period;
	private String reason;
	private String description;
	//endregion


	//region Constructors
	public Absence()
	{
	}

	public Absence(Student student,
				   LocalDateTime timeFrom,
				   LocalDateTime timeTo,
				   int period,
				   String reason,
                   String description,
                   String country)
	{
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.period = period;
		this.reason = reason;
		this.description = description;
		
		this.student = student;							// unidirektionale Verknüpfung
		this.student.getAbsences().add(this);			// unidirektionale Verknüpfung 	-> 	bidirektionale Verknüpfung
	}
	//endregion


	//region Methods
	@Override
	public String toString()
	{
		return String.format("%1$s bis %2$s: %3$s", this.timeFrom, this.timeTo, this.reason, this.absenceId);
	}
	//endregion


	//region Getter and Setter
	public Integer getAbsenceId()
	{
		return absenceId;
	}
	public void setAbsenceId(Integer absenceId)
	{
		this.absenceId = absenceId;
	}

	public LocalDateTime getTimeFrom()
	{
		return timeFrom;
	}
	public void setTimeFrom(LocalDateTime timeFrom)
	{
		this.timeFrom = timeFrom;
	}

	public LocalDateTime getTimeTo()
	{
		return timeTo;
	}
	public void setTimeTo(LocalDateTime timeTo)
	{
		this.timeTo = timeTo;
	}

	public int getPeriod()
	{
		return period;
	}
	public void setPeriod(int period)
	{
		this.period = period;
	}

	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	public Student getStudent()
	{
		return this.student;							
	}
	public void setStudent(Student student)
	{
		this.student = student;
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
		Absence absence1 = this;
		if (absence1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Absence)))
		{
			equal = false;
		}
		else
		{
			Absence absence2 = (Absence)obj;
			equal = absence1.absenceId != null && Objects.equals(absence1.absenceId, absence2.getAbsenceId());
		}
		return equal;
	}
	//endregion
}
