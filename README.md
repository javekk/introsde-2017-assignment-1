# IntroSDE assignment 1

## 1. Identification
* Perini Raffaele 196339
* raffaele.perini@studenti.unitn.it

## 2. Project

### 2.1. Code
the Project is structured as follow:

 * __default package__: we have all the .xml, .xsd and .md files used for the project. In this package there are:
  * __build.xml__: The ant build script, in this file are defined all the instruction to perform by ant, such as compile, execute, download ivy and so forth.
  * __ivy.xml__: with this file we can use ivy to get the libraries we need, for instance jackson or jaxb.
  * __people.xml__: in this file are stored all the data we use.
  * __people.xsd__: this is the schema for people.xml.
  * __tinypeople.xml__: used for the execute.evaluating target.
 * __src.entity__: contains all the classes for creating the objects used in this project:

 * __src.jAxBTools__:cointains all the classes needed to perform the required instructions such as (UN)Marshall and JSON converting:
  * __JAXBMarshaller__:Get the person objects and marshall them on the newpeople.xml file. In the main we define the method to create 3 person objects for performing the execute.evaluating task.
  * __JAXBUnMarshaller__: This class defines the method to unmarshall the people.xml file, bases on the people.xsd schema, printing all the person objects found.
  * __JSONDoThings__: This class provides the method for creating a people.json file contains the json of the person objects. Even here in the main we define the method to create 3 person objects for performing the execute.evaluating task.

 * __src.xPathTools__:contains the class for performing the xpath queries:
  * __XPathDoThings__: with this class it is possible to run the xpath objects. In the main we get the arguments from command line in order to perform the 3 instructions of execute.evaluation.

### 2.2. Task
 * __clean__: remove all the built and generated file.
 * __compile__: compile the project
 * __download-ivy__: download ivy
 * __execute.JAXBMarshaller__: create a newpeople.xml file containg three people.
 * __execute.JAXBUnMarshaller__: unmarshall the people.xml and print the found results.
 * __execute.PeopleJson__: create a people.json file which contains 3 person objects in a json format.
 * __execute.XPATH__: performs different tasks depending on the arguments providing to it:
  * -Dtask=A:get all people in the people.xml file.
  * -Dtask=C -Darg0=<date> -Darg1=<condition>: accepts a date and an operator (=, > , <) as parameters and prints people which preferred activity start_date fulfill that condition.				    
  * -Dtask=B -Darg0=<personId>: print the activityprefence of a certain person
 * __generate__: automatically generate the classes in the generated package.
 * __init__: create the build dir.1
 * __install-ivy__: install ivy
 * __resolve__: downloads the dependencies to your lib folder

## 3. Execution
In order to run this launch this command:

  ```
  ant execute.evaluation
  ```

it would be better with the root permissions
