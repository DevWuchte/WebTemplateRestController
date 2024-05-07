package at.htlklu.spring.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "EVENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event extends RepresentationModel<SchoolClass> implements Serializable
{
    //region static Properties
    public static final Comparator<Event> BY_DATE_FROM = Comparator.comparing(Event::getDateFrom);
    public static final Comparator<Event> BY_DATE_TO = Comparator.comparing(Event::getDateTo);

    public static final Comparator<Event> BY_DATE_FROM_TO = BY_DATE_FROM.thenComparing(BY_DATE_TO);
    //endregion


    //region Properties
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_ID")
    private Integer eventId;

    @Column(name = "DATE_FROM")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateFrom;
    @Column(name = "DATE_TO")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateTo;
    private String destination;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOLCLASS_ID")
	private SchoolClass schoolClass;
    //endregion


    //region Constructors
	public Event()
	{
	}
    //endregion


    //region Methods
    @JsonGetter("toString")
	@Override
	public String toString()
	{
		return String.format("%3$s: %1$s - %2$s (%4$s)", this.dateFrom, this.dateTo, this.destination, this.description);
	}
    //endregion


    //region Getter and Setter
    public Integer getEventId()
    {
        return eventId;
    }
    public void setEventId(Integer eventId)
    {
        this.eventId = eventId;
    }

    public SchoolClass getSchoolClass()
    {
        return schoolClass;
    }
    public void setSchoolClass(SchoolClass schoolClass)
    {
        this.schoolClass = schoolClass;
    }

    public LocalDate getDateFrom()
    {
        return dateFrom;
    }
    public void setDateFrom(LocalDate dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo()
    {
        return dateTo;
    }
    public void setDateTo(LocalDate dateTo)
    {
        this.dateTo = dateTo;
    }

    public String getDestination()
    {
        return destination;
    }
    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
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
		Event event1 = this;
		if (event1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Event)))
		{
			equal = false;
		}
		else
		{
			Event event2 = (Event)obj;
			equal = event1.eventId != null && Objects.equals(event1.eventId, event2.getEventId());
		}
		return equal;
	}
	//endregion


}
