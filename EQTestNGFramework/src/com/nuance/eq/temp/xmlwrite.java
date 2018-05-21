package com.nuance.eq.temp;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class xmlwrite {

   public static void main(String argv[]) {

      try {
    	  String workingDir = System.getProperty("user.dir");
    	  final String parellelTestngXML = workingDir + "\\config\\TestNGExecution\\test.xml";
         DocumentBuilderFactory dbFactory =
         DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();
         
         // root element
         Element rootElement = doc.createElement("suite");
         Attr rootattr = doc.createAttribute("name");
         rootattr.setValue("Automation Test");
         rootElement.setAttributeNode(rootattr);
         
         rootattr = doc.createAttribute("allow-return-values");
         rootattr.setValue("false");
         rootElement.setAttributeNode(rootattr);
         
         rootattr = doc.createAttribute("configfailurepolicy");
         rootattr.setValue("skip");
         rootElement.setAttributeNode(rootattr);
         
         rootattr = doc.createAttribute("data-provider-thread-count");
         rootattr.setValue("10");
         rootElement.setAttributeNode(rootattr);
         doc.appendChild(rootElement);
         //Listeners
         Element listeners = doc.createElement("listeners");
         rootElement.appendChild(listeners);
         
         Element listener = doc.createElement("listener");
         listeners.appendChild(listener);
         
         rootattr = doc.createAttribute("class-name");
         rootattr.setValue("atu.testng.reports.listeners.ATUReportsListener");
         listener.setAttributeNode(rootattr);
         
         listener = doc.createElement("listener");
         listeners.appendChild(listener);
         
         rootattr = doc.createAttribute("class-name");
         rootattr.setValue("atu.testng.reports.listeners.ConfigurationListener");
         listener.setAttributeNode(rootattr);
         
         listener = doc.createElement("listener");
         listeners.appendChild(listener);
         
         rootattr = doc.createAttribute("class-name");
         rootattr.setValue("atu.testng.reports.listeners.MethodListener");
         listener.setAttributeNode(rootattr);
         
         // parameter element
         Element parameter = doc.createElement("parameter");
         rootElement.appendChild(parameter);

         // setting attribute to parameter element
         Attr attr = doc.createAttribute("name");
         attr.setValue("test_param");
         parameter.setAttributeNode(attr);
         
         attr = doc.createAttribute("value");
         attr.setValue("test");
         parameter.setAttributeNode(attr);

         // test tag element
         Element test = doc.createElement("test");
         rootElement.appendChild(test);

         // setting attribute to test tag element
         attr = doc.createAttribute("allow-return-values");
         attr.setValue("false");
         test.setAttributeNode(attr);
         
         attr = doc.createAttribute("group-by-instances");
         attr.setValue("false");
         test.setAttributeNode(attr);
         
         attr = doc.createAttribute("junit");
         attr.setValue("false");
         test.setAttributeNode(attr);
         
         attr = doc.createAttribute("name");
         attr.setValue("tests");
         test.setAttributeNode(attr);
         
         attr = doc.createAttribute("preserve-order");
         attr.setValue("true");
         test.setAttributeNode(attr);
         
         attr = doc.createAttribute("skipfailedinvocationcounts");
         attr.setValue("false");
         test.setAttributeNode(attr);
         
         // test classes element
         Element classes = doc.createElement("classes");
         test.appendChild(classes);

      // test class element
         Element classss = doc.createElement("class");
         classes.appendChild(classss);
         
         attr = doc.createAttribute("name");
         attr.setValue("com.adp.bi.tests.TestWorkFrontJobs");
         classss.setAttributeNode(attr);
       

      // test method element
         Element methods = doc.createElement("methods");
         classes.appendChild(methods);
         
         // test include element
         Element include = doc.createElement("include");
         methods.appendChild(include);
         
         attr = doc.createAttribute("name");
         attr.setValue("testCaseName");
         include.setAttributeNode(attr);
         
         
         // carname element
         /*Element carname = doc.createElement("carname");
         Attr attrType = doc.createAttribute("type");
         attrType.setValue("formula one");
         carname.setAttributeNode(attrType);
         carname.appendChild(doc.createTextNode("Ferrari 101"));
         supercar.appendChild(carname);

         Element carname1 = doc.createElement("carname");
         Attr attrType1 = doc.createAttribute("type");
         attrType1.setValue("sports");
         carname1.setAttributeNode(attrType1);
         carname1.appendChild(doc.createTextNode("Ferrari 202"));
         supercar.appendChild(carname1);
*/
         // write the content into xml file
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(new File(parellelTestngXML));
         transformer.transform(source, result);
         
         // Output to console for testing
         StreamResult consoleResult = new StreamResult(System.out);
         transformer.transform(source, consoleResult);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}


