package entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  * Class with the prefer activity of a Person.
  * Attributes: id (int), name (string), description (string), location (string),
  * start_date (date), tag (Tag).
  */
@XmlRootElement(name = "activitypreference")
@XmlAccessorType (XmlAccessType.FIELD)
public class Activity {
	@XmlAttribute
	private int id;
	
	private String name;
	private String description;
	@XmlElement(name= "place")
	private String location;
	@XmlElement(name="startdate")
	private Date start_date;
	
	
	/*CONTSTRUCTORS*/
	
	public Activity(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Activity() {
		this.name = "generic";
		this.description = "doSomething";
	}
	
	/*SETTER AND GETTER*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/*public methods*/
	
	// Modify to String to add the missing attributes.
	public String toString() {
		return "Name="+name+", Description="+description;
	}


}
