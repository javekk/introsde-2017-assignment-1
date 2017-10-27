// classes are grouped together in 'packages'
// Classes in the same pakage already see each other. 
// If a class is in another package, in other to see it, you need to import it
package entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


// This is a typical Java Class. 
@XmlRootElement(name = "person")
@XmlAccessorType (XmlAccessType.FIELD)

public class Person {

	// As with any other object oriente language, classes have attributes (i.e. the properties of the class). 
	// Each attribute is in turn of another class
	private String firstname;	// this is an attribute of the class String, and it is 'private'
								// private attributes are only accesible inside the object
	private String lastname;	// this is an attribute of the class String
	

	private Date birthdate;

	@XmlElement(name="activitypreference")
	private Activity activity;	// this is an attribute of the class HealthProfile 
	
	
	@XmlAttribute
	private Long id;
	// constructors in java are used to create an object of the class 
	// (a java program basically plays with objects of different classes)
	// this constructor creates a Person object with a particular firstname, lastname and health profile
	public Person(String fname, String lname, Activity activity) {
		this.setFirstname(fname);
		this.setLastname(lname);
		this.setActivity(activity);
		this.id=this.strToLong(fname,lname);
	}
	public Person(String fname, String lname, Date birthdate) {
		this.setFirstname(fname);
		this.setLastname(lname);
		this.birthdate=birthdate;
		this.id=this.strToLong(fname,lname);
	}
	public Person(String fname, String lname) {
		this.setFirstname(fname);
		this.setLastname(lname);
		this.id=this.strToLong(fname,lname);
	}
	public Person() {
		this.firstname="Pinco";
		this.lastname="Pallino";
		this.id=this.strToLong(this.firstname,this.lastname);
	}

	private long strToLong(String fname,String lname ) {
		return Long.parseLong(fname, 36) + Long.parseLong(lname, 36);
	}

	// classes have methods, which are basically pieces of programs that can be executed on objects of the class
	// this dummy class, has only 'accesor' methods (i.e. methods to access its properties, which are all private)
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public long getId() {
		return id;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
