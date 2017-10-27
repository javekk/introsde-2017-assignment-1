package jAxBTools;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import entity.PeopleStore;

public class JSONDoThings {
	
	public static void main(String [] args) throws SAXException, JAXBException, IOException {

		/*
		  We are busy people so instead of write to much code we use the UnMarshall class
		  for fill the people list of person object in the tinypeople.xml
		*/	
		JAXBUnMarshaller jx = new JAXBUnMarshaller();
		PeopleStore people = jx.peopleXmlToPeopleJava();
		
		JSONDoThings mJsonDoThings = new JSONDoThings();
		mJsonDoThings.jsonAndPrint(people);
		
		System.out.println("people.json file was created");
	}
	
	public void jsonAndPrint(PeopleStore people) throws IOException, SAXException {
		
		// Jackson Object Mapper 
		ObjectMapper mapper = new ObjectMapper();
		
		// Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        
		// configure as necessary
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File("people.json"), people);
}
}
