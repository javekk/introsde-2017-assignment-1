package xPathTools;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathDoThings {
	/*
	  	GLOBAL VARIABLES
	 */
	Document doc = null;
	XPath xpath = null;
	
	public static void main(String []args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		
		XPathDoThings mXPathDoThings = new XPathDoThings();
		mXPathDoThings.loadXML();
		
		int argCount = args.length;
		if (argCount == 0) {
			System.out.println("Choose a Task:\n "
							 + "-Dtask=A (get all people)\n "
							 + "-Dtask=C -Darg0=<date> -Darg1=<condition> (get all people that satisfy the condition wrt date) \n "
							 + "-Dtask=B -Darg0=<personId> (print the activityprefence of a certain person)");
		} else if (argCount >= 1) {
			String task = args[0];
			
			switch(task) {
				case "A": 
					mXPathDoThings.printAll();
					break;
				case "B":
					try {
						String idStr = args[1];
						int id = Integer.parseInt(idStr);
						mXPathDoThings.printActivityPreference(id);
					}
					catch (Exception e) {
						System.out.println("Something was wrong: ");
						e.printStackTrace();
						System.exit(-1);
					}
					break;
				case "C":
					try {
						String dateStr = args[1];
						String condition = args[2];
						SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
						Date date = sDF.parse(dateStr);
						mXPathDoThings.printPeopleThatFulfillACertainCondition(date, condition);
					} catch (Exception e) {
						System.out.println("Something was wrong: ");
						e.printStackTrace();
						System.exit(-1);
					}
					break;
				default:
					System.out.println("Choose a Task:\n "
							 + "-Dtask=A (get all people)\n "
							 + "-Dtask=C -Darg0=<date> -Darg1=<condition> (get all people that satisfy the condition wrt date) \n "
							 + "-Dtask=B -Darg0=<personId> (print the activityprefence of a certain person)");
					System.exit(-1);
					break;
			}
		}
		
	}
	
	
	/*------------------------------------------------------*
	 * --------------------MAIN-METHODS---------------------*
	 * -----------------------------------------------------*/
	
	/* This method create a DOMFactory used to parse the .xml file, then call the method 
	 * getXPath*/
	private void loadXML() throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		doc = builder.parse("people.xml");

		// creating xpath object
		getXPathObj();
	}
	
	/* This method provide for creating the xpath Object to parse the .xml*/
	
	private XPath getXPathObj() {

		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		return xpath;
	}

	/*------------------------------------------------------------*
	 * ----------------------USEFUL-METHODS-----------------------*
	 * -----------------------------------------------------------*/
	
	/*------------------PRINT-ALL---------------------*/
	public void printAll() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
	
		/* With the following two lines we retrieve all the person objects*/
		XPathExpression expr = xpath.compile("people/person");
		NodeList people = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		/*
			-------EASIER-WAY-------
			the following string is enough for print all the persons
			System.out.println(people.item(i).getTextContent());
			-------ALTERNATIVE(BETTER?)-WAY-------
			using JAXB 
			------FUNNIER-WAY------
			Use xpath only, because this task is based on lab03
		*/

		System.out.println(people.getLength() + " people founded");
		
		for(int i = 0; i< people.getLength(); i++) {
			
			/*
			 	funnier way
				Get i-th person, print
			*/
			Node mNodePerson = (Node) people.item(i);
			System.out.println("\nThe information of person " + (i+1) + ": ");
			
			/*
			 	Get its id, 
			 	we will use here below in xpath in a REALLY not-efficient way:
			 	for each simple element of the xml, we try to retrive is text() calling
			 	the xPathToString() function, that perform a new query each time we call it
			 	In that way we can print the document showing the power of xpath and
			 	in a human-readable mode labeling each element with is name
			 */
			String mIdStr = mNodePerson.getAttributes().getNamedItem("id").getTextContent();
			int mId = Integer.parseInt(mIdStr);
			
			System.out.println("\tId: " + mId);
			System.out.println("\tFirst Name: " + xPathToString("people/person[@id=" + mId + "]/firstname"));
			System.out.println("\tLast Name: " + xPathToString("people/person[@id=" + mId + "]/lastname"));
			System.out.println("\tBirth date: " + xPathToString("people/person[@id=" + mId + "]/birthdate"));
			
		
			expr = xpath.compile("people/person[@id=" + mId + "]/activitypreference");
			Node mNodeCurAttr= (Node) expr.evaluate(doc, XPathConstants.NODE);
			System.out.println("\tThe activity is:");
			
			/*
			  	Similar to here above, we now use the activityId to make query
			 */
			String mActivityId = mNodeCurAttr.getAttributes().getNamedItem("id").getTextContent();
			System.out.println("\t\tActivityId: " + mActivityId);
			System.out.println("\t\tName: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/name"));
			System.out.println("\t\tDescription: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/description"));
			System.out.println("\t\tPlace: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/place"));
			System.out.println("\t\tStart Date: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/startdate"));
			

		}
	}
	
	
	/*Make a function which accepts a date and an operator (=, > , <) as 
	 * parameters and prints people  which preferred activity  start_date 
	 * fulfill that condition (i.e., startdate>2017-13-10, startdate=2017-13-10, 
	 * etc.).*/
	public void printPeopleThatFulfillACertainCondition(Date date, String condition) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{

		/*
		 	we will use two types of query: 
		 	-if we have < or > "/people/person/activitypreference[number(translate(substring(startdate,1,10),'-',''))>'2017-10-13T10:30:00.0' and not(starts-with(startdate,'2017-10-13'))]/parent::person"
		 	the several functions number, translate, substring is used to compare the dates, because the
		 	xpath 1.0 doens't allow the strign comparing with < and >
		 	-otherwise if we have = "/people/person/activitypreference[starts-with(startdate, "2017-10-13") ]/parent::people"
		*/
		/*
		 	It use two types of dateformat
		 */
		System.out.println(condition);
		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sDF.format(date);
		SimpleDateFormat sDF2 = new SimpleDateFormat("yyyyMMdd");
		String dateStr2 = sDF2.format(date);
		
		XPathExpression expr = null;
		
		if(condition.equals(">") || condition.equals("<")) {
			expr = xpath.compile("/people/person/activitypreference[number(translate(substring(startdate,1,10),'-',''))"
					   + condition
					   + "" + dateStr2 + ""
					   + " and not(starts-with(startdate,"
					   + "'"
					   + dateStr
					   + "'))]/parent::person");

			
		}
		else if(condition.equals("=")){
			expr = xpath.compile("/people/person/activitypreference[starts-with(startdate,"
					   + "'"
					   + dateStr
					   + "')]/parent::person");
		}
		if(expr != null) {
			/*
			 	And now Print type:
				-------EASIER-WAY-------
				the following string is enough for print all the persons
				System.out.println(people.item(i).getTextContent());
				-------ALTERNATIVE(BETTER?)-WAY-------
				using JAXB 
				------FUNNIER-WAY------
				Use xpath only, because this task is based on lab03
			*/
			
			NodeList people = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

			System.out.println(people.getLength() + " people founded");
			
			for(int i = 0; i< people.getLength(); i++) {
				
				/*funnier way*/
				Node mNodePerson = (Node) people.item(i);
				System.out.println("\nThe information of person " + (i+1) + ": ");
				
				String mIdStr = mNodePerson.getAttributes().getNamedItem("id").getTextContent();
				int mId = Integer.parseInt(mIdStr);
				System.out.println("\tId: " + mId);
				System.out.println("\tFirst Name: " + xPathToString("people/person[@id=" + mId + "]/firstname"));
				System.out.println("\tLast Name: " + xPathToString("people/person[@id=" + mId + "]/lastname"));
				System.out.println("\tBirth date: " + xPathToString("people/person[@id=" + mId + "]/birthdate"));
				
			
				expr = xpath.compile("people/person[@id=" + mId + "]/activitypreference");
				Node mNodeCurAttr= (Node) expr.evaluate(doc, XPathConstants.NODE);
				System.out.println("\tThe activity is:");
				
				String mActivityId = mNodeCurAttr.getAttributes().getNamedItem("id").getTextContent();
				System.out.println("\t\tActivityId: " + mActivityId);
				System.out.println("\t\tName: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/name"));
				System.out.println("\t\tDescription: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/description"));
				System.out.println("\t\tPlace: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/place"));
				System.out.println("\t\tStart Date: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/startdate"));
				
	
			}
		}
		else {
			System.out.println("Are you sure you have used the right condition? (<,>,=)");
		}
		
	}
	
	
	/*Method that permor a query as string and return the result also as string*/
	private String xPathToString(String path) throws XPathExpressionException {
		XPathExpression expr = xpath.compile(path);
		Node mNodeCurAttr= (Node) expr.evaluate(doc, XPathConstants.NODE);
		return mNodeCurAttr.getTextContent().toString();
	}
	
	
	/*----------HANDLING WITH ACTIVITY----------------*/
	
	/*Some function as required in the 2nd instruction*/
	
	/*get the activity description given the personID*/
	private Node getActivityDescription(int personId) throws XPathExpressionException {

		XPathExpression expr = xpath.compile(
										 "//people/person[@id=" 
										 + personId
										 + "]/activitypreference/description");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}
	
	/*get the activity startDate given the personID*/
	
	private Node getActivityStartDate(int personId)throws XPathExpressionException {

		XPathExpression expr = xpath.compile(
										 "//people/person[@id=" 
										 + personId
										 + "]/activitypreference/startdate");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}
	
	/*get the activity place given the personID*/

	private Node getActivityPlace(int personId)throws XPathExpressionException {

		XPathExpression expr = xpath.compile(
										 "//people/person[@id=" 
										 + personId
										 + "]/activitypreference/place");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}
	
	/*get the activity given the personID*/

	private Node getActivity(int personId)throws XPathExpressionException {

		XPathExpression expr = xpath.compile(
										 "//people/person[@id=" 
										 + personId
										 + "]/activitypreference");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}
	
	/*print  The Preference Activity */

	public void printActivityPreference(int mId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
	
		/*
		-------EASIER-WAY-------
		the following string is enough
		System.out.println(people.item(i).getTextContent());
		*/
		/*funnier way*/

		Node node = getActivity(mId);
		System.out.println("The Activity Prefence of "
						 + xPathToString("people/person[@id=" + mId + "]/firstname")
						 + " "
						 + xPathToString("people/person[@id=" + mId + "]/lastname")
						 + " is: ");
		
		String mIdStr = node.getAttributes().getNamedItem("id").getTextContent();
		System.out.println("\tActivityId: " + mIdStr);
		System.out.println("\t\tName: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/name"));
		System.out.println("\t\tDescription: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/description"));
		System.out.println("\t\tPlace: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/place"));
		System.out.println("\t\tStart Date: " + xPathToString("people/person[@id=" + mId + "]/activitypreference/startdate"));
		
		
	}
}
