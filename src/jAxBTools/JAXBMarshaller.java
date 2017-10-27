package jAxBTools;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.SAXException;

import entity.PeopleStore;

public class JAXBMarshaller {
	
	/*
	 	In this object we store all our people object
	 */
	
	
	public static void main(String [] args) throws SAXException, JAXBException {

		/*
		  We are busy people so instead of write to much code we use the UnMarshall class
		  for fill the people list of person object in the tinypeople.xml
		*/	
		JAXBUnMarshaller jx = new JAXBUnMarshaller();
		PeopleStore people = jx.peopleXmlToPeopleJava();
		
		JAXBMarshaller mJaxbMarshaller = new JAXBMarshaller();
		mJaxbMarshaller.marshallAndPrint(people);
		
		System.out.println("newpeople.xml file was created");

	}
		
	public void marshallAndPrint(PeopleStore people) throws JAXBException, SAXException {	
		
		
		/*
		 	The JAXBContext class provides the client's entry point to the JAXB API. 
		 	It provides an abstraction for managing the XML/Java binding information 
		 	necessary to implement the JAXB binding framework operations: unmarshal, 
		 	marshal and validate. 
		 */
	    JAXBContext jaxbContext = JAXBContext.newInstance(PeopleStore.class);
	    
	    /*
	     	The Marshaller class is responsible for governing the process of serializing Java 
			content trees back into XML data. It provides the basic marshalling methods
	     */
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     
	    //Marshal the employees list in console
	    jaxbMarshaller.marshal(people, System.out);
	     
	    //Marshal the employees list in file
	    jaxbMarshaller.marshal(people, new File("newpeople.xml"));
	}
}
