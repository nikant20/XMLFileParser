package tech.nikant.service;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tech.nikant.model.JohnData;
import tech.nikant.model.MichelData;
import tech.nikant.repo.JohnRepo;
import tech.nikant.repo.MichelRepo;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired
	JohnRepo johnRepo;
	
	@Autowired
	MichelRepo michelRepo;

	@Override
	public Object saveData(String fileName) {
		Object response = null;
		try {
			//Find Customer Name and Based on customer Names, save their data
			//If customer is John, execute below code
			if (getCustomerName(fileName).equals("John")) {
				
				//Loading File
				File file = new File("src/main/resources/"+fileName.toLowerCase()+".xml");
				//Building Document
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document xmlDocument = builder.parse(file);
				
				//Using XPATH to get the Nodes
				XPath xPath = XPathFactory.newInstance().newXPath();
				XPathExpression columnNodes = xPath.compile("/Document/record/tr");
				Object resultOfJohn = columnNodes.evaluate(xmlDocument, XPathConstants.NODESET);
				NodeList nodesOfJohn = (NodeList) resultOfJohn;
				
				//Iterating over Nodes
				for (int i = 0; i < nodesOfJohn.getLength(); i++) {
					Node nNode = nodesOfJohn.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						//Getting data from XML and setting it to Model Class
						JohnData jd = new JohnData(eElement.getElementsByTagName("td").item(0).getTextContent(),
								Double.parseDouble(eElement.getElementsByTagName("td").item(1).getTextContent()),
								eElement.getElementsByTagName("td").item(2).getTextContent());
						//Saving Data to DB
						response = johnRepo.save(jd);
					}
				}
			}
			
			//If customer is Michel, execute below code
			if (getCustomerName(fileName).equals("Michel")) {
				
				//Loading File
				File file = new File("src/main/resources/"+fileName.toLowerCase()+".xml");
				//Building Document
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document xmlDocument = builder.parse(file);
				
				//Using XPATH to get the Nodes
				XPath xPath = XPathFactory.newInstance().newXPath();
				XPathExpression columnNodes = xPath.compile("/Document/record/tr");
				Object resultOfMichel = columnNodes.evaluate(xmlDocument, XPathConstants.NODESET);
				NodeList nodesOfMichel = (NodeList) resultOfMichel;
				
				//Iterating over Nodes
				for (int i = 0; i < nodesOfMichel.getLength(); i++) {
					Node nNode = nodesOfMichel.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						//Getting data from XML and setting it to Model Class
						MichelData md = new MichelData(eElement.getElementsByTagName("td").item(0).getTextContent(),
								eElement.getElementsByTagName("td").item(1).getTextContent(),
								eElement.getElementsByTagName("td").item(2).getTextContent());
						//Saving Data to DB
						response = michelRepo.save(md);
					}
				}
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Object fetchData(String fileName) {
		String customerName = null;
		//Get Customer Name based on filename
		try {
			customerName = getCustomerName(fileName);
		} catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		Object response = null;
		//If customer is John, find all data
		if(customerName.contentEquals("John")) {
			response =  johnRepo.findAll();
		}
		//If customer is Michel, find all data
		if(customerName.contentEquals("Michel")) {
			response = michelRepo.findAll();
		}
		
		return response;
	}
	
	private String getCustomerName(String fileName) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		
		//Loading File
		File file = new File("src/main/resources/"+fileName.toLowerCase()+".xml");
		
		String customerName = null;
		//Building Document
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlDocument = builder.parse(file);
		
		//Using XPATH to get the Nodes
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression expression = xPath.compile("/Document");
		Object result = expression.evaluate(xmlDocument, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node nNode = nodes.item(i);
			//Getting data from XML
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				customerName = eElement.getAttribute("CustomerName").toString();
			}
		}
		
		return customerName;
	}
	
	
	
}
