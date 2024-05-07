package at.htlklu.spring.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

@Entity																
@Table(name = "ADDRESS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address extends RepresentationModel<Address> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -2159878227993048767L;
	//endregion


	//region Properties
	@Id																
	@GeneratedValue(strategy = GenerationType.IDENTITY)				
	@Column(name = "ADDRESS_ID")									
	private Integer addressId;
	
	@ManyToOne(fetch = FetchType.LAZY)								
    @JoinColumn(name = "STUDENT_ID")								
	private Student student;
		
	private String street;
	@Column(name = "ZIPCODE")	
	private String zipCode;
	private String city;
	private String country;
	//endregion


	//region Constructors
	public Address()
	{
	}
			
	public Address(Student student,
				   String street, 
		           String zipCode, 
		           String city, 
		           String country)
	{
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
		
		this.student = student;							// unidirektionale Verknüpfung
		this.student.getAddresses().add(this);			// unidirektionale Verknüpfung 	-> 	bidirektionale Verknüpfung
	}
	//endregion


	//region Methods
	@Override
	public String toString()
	{
		return String.format("%1$s, %2$s %3$s (%4$d)", this.street, this.zipCode, this.city, this.addressId);
	}
	//endregion


	//region Getter and Setter
	public Integer getAddressId()
	{
		return addressId;
	}
	public void setAddressId(Integer addressId)
	{
		this.addressId = addressId;
	}

	public String getStreet()
	{
		return street;
	}
	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getZipCode()
	{
		return zipCode;
	}
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
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
		Address address1 = this;
		if (address1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Address)))
		{
			equal = false;
		}
		else
		{
			Address address2 = (Address)obj;
			equal = address1.addressId != null && Objects.equals(address1.addressId, address2.getAddressId());
		}
		return equal;
	}
	//endregion

}
