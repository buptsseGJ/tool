package cn.edu.thu.platform.parser;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
			Document document = builder.parse("file/benchmarks.xml");
			// Document document = builder.parse(fileAbsolutePath);
			System.out.println("ok,解析正确");
			return document;
		} catch (SAXException | IOException e) {
			// e.printStackTrace();
		}
		System.out.println("错误");
		return null;
	}
}
