package cn.edu.thu.platform.parser;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.edu.thu.platform.entity.Race;

import java.io.*;

public class ParseXml {

	public Document validateXml(String fileAbsolutePath) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			factory.setValidating(true);
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		builder.setErrorHandler(new MyErrorHandler());
		try {
			Document document = builder.parse("file/bench1.xml");
//			Document document = builder.parse(fileAbsolutePath);
			System.out.println("ok,解析正确");
			return document;
		} catch (SAXException | IOException e) {
//			e.printStackTrace();
		}
		System.out.println("错误");
		return null;
	}
	
	public Document buildDocument(String fileAbsolutePath) throws SAXException, IOException{
		String filePath = "file/bench1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
	        doc.getDocumentElement().normalize();
	        return doc;
		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
		}
        return null;
	}
	
	public Document emptyDocument(String fileAbsolutePath) throws SAXException, IOException{
		String filePath = "file/bench1.xml";

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = dBuilder.parse(filePath);
		doc.getDocumentElement().normalize();
		return doc;
	}
    public void addElement(Document doc, String id, Race race) {
    	deleteElement(doc, id, race);
    	Element report = doc.getElementById(id);    	
    	Element raceElement = doc.createElement("race");
    	Element line1Element = doc.createElement("line1");
    	line1Element.appendChild(doc.createTextNode(race.getLine1()));
    	Element line2Element = doc.createElement("line2");
    	line2Element.appendChild(doc.createTextNode(race.getLine2()));
    	Element variableElement = doc.createElement("variable");
    	variableElement.appendChild(doc.createTextNode(race.getVariable()));
    	Element packageClassElement = doc.createElement("packageClass");
    	packageClassElement.appendChild(doc.createTextNode(race.getPackageClass()));
    	Element detailElement = doc.createElement("detail");
    	detailElement.appendChild(doc.createCDATASection(race.getDetail()));
    	raceElement.appendChild(line1Element);
    	raceElement.appendChild(line2Element);
    	raceElement.appendChild(variableElement);
    	raceElement.appendChild(packageClassElement);
    	raceElement.appendChild(detailElement);
    	report.appendChild(raceElement);
    }
    
    public void deleteElement(Document doc, String id, Race race) {
    	String currentLine1 = race.getLine1();
    	String currentLine2 = race.getLine2();
    	String temp = currentLine1;
    	if(Integer.parseInt(currentLine1) > Integer.parseInt(currentLine2)){
    		currentLine1 = currentLine2;
    		currentLine2 = temp;
    	}
        Element report = doc.getElementById(id);
        NodeList elements = report.getElementsByTagName("race");
        Element element = null;
        //loop for each element
        for(int i=0; i<elements.getLength();i++){
            element = (Element) elements.item(i);//a race
            Node line1Node = element.getElementsByTagName("line1").item(0);
            Node line2Node = element.getElementsByTagName("line2").item(0);
            String line1 = line1Node.getFirstChild().getNodeValue();
            String line2 = line2Node.getFirstChild().getNodeValue();
            temp = line1;
            if(Integer.parseInt(line1) > Integer.parseInt(line2)){
            	line1 = line2;
            	line2 = temp;
            }
            if(line1.equals(currentLine1)&&line2.equals(currentLine2)){
            	report.removeChild(element);
            }            
        }
    }
    
    public void writeDomToXml(Document doc) throws TransformerException{
    	doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //注意：如果想让xml中的Doctype标签不丢失，必须在转换类对象进行输出属性设置。
        //下面这句由于对xml的Dctype设置的dtd声明为System的情况
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_SYSTEM, doc.getDoctype().getSystemId());
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("file/bench1.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("XML file updated successfully");
    }
}
