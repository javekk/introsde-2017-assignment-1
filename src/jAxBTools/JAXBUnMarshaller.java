package jAxBTools;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import entity.PeopleStore;
import entity.Person;

public class JAXBUnMarshaller {
	
	public static void main(String [] args) throws SAXException {
		JAXBUnMarshaller mJaxbUnMarshaller = new JAXBUnMarshaller();
		mJaxbUnMarshaller.uNMarshallAndPrint();
		System.out.println("All the above people have been unmarshalled");
	}
	
	public void uNMarshallAndPrint() throws SAXException {
		
		try {
			/*
			  	Get the tight xml file
			*/
			File file = new File("people.xml");
			
			/*
			 	The JAXBContext class provides the client's entry point to the JAXB API. 
			 	It provides an abstraction for managing the XML/Java binding information 
			 	necessary to implement the JAXB binding framework operations: unmarshal, 
			 	marshal and validate. 
			 */
			JAXBContext jaxbContext = JAXBContext.newInstance(PeopleStore.class);
			
			/*
			 	The Unmarshaller class governs the process of deserializing XML data into 
			 	newly created Java content trees, optionally validating the XML data as it 
			 	is unmarshalled. It provides an overloading of unmarshal methods for many 
			 	different input kinds. 
			 */
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			
			/*
			  Validate this xml with the fancy schema provided for validate it
			 */
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File("people.xsd"));
			jaxbUnMarshaller.setSchema(schema);
			
			/*
			 	 Finally unMarsall it and put the result in an PeopleStore object
			 */
			PeopleStore people= (PeopleStore) jaxbUnMarshaller.unmarshal(file);
			for(Person p : people.getData()) {
				System.out.println("The person with id='" + p.getId() +"' is:");
				System.out.println("\tFirstname: " +p .getFirstname());
				System.out.println("\tLastname: " + p.getLastname());
				System.out.println("\tBirthdate: " + p.getBirthdate());
				System.out.println("\tThe activitypreference is: ");
				System.out.println("\t\tId: " + p.getActivity().getId());
				System.out.println("\t\tName: " + p.getActivity().getName());
				System.out.println("\t\tDescription: " + p.getActivity().getDescription());
				System.out.println("\t\tPlace: " + p.getActivity().getLocation());
				System.out.println("\t\tStart date: " + p.getActivity().getStart_date());
			}
			
		  } catch (JAXBException e) {
			  e.printStackTrace();
		  }
	}
	

	/*
	 	Useful method to fill a PeopleStore object with the data stored in the people.xml
	 */
	public PeopleStore peopleXmlToPeopleJava() throws SAXException {
		
		
		
		try {
			File file = new File("tinypeople.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(PeopleStore.class);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();

			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File("people.xsd"));
			jaxbUnMarshaller.setSchema(schema);
			
			return (PeopleStore) jaxbUnMarshaller.unmarshal(file);
			
			
		  } catch (JAXBException e) {
			  e.printStackTrace();
			  return null;
		  }
	}
}
