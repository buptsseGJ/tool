package cn.edu.thu.platform.parser;

import java.awt.TextArea;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextArea;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.thu.platform.comparison.Comparison;
import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;

public class DomToEntity {

	String textAreaInfo ="";
	TextArea textArea = null;
	
	public void startDom(Node node) {
		if (node == null) {
			return;
		}
		Node root = ((Document) node).getDocumentElement();
		loopDom(root);
	}

	public String startDom(Document validationResult, String textAreaInfo,TextArea textArea2) {
		this.textAreaInfo = textAreaInfo;
		this.textArea = textArea2;
		if (validationResult == null) {
			return "";
		}
		Node root = validationResult.getDocumentElement();
		loopDom(root);
		this.textArea = null;
		return this.textAreaInfo;
	}
	// Note:each element node may contain test node,
	// so we need to eliminate it.
	public void loopDom(Node node) {
		if (node.hasChildNodes()) {
			// get all the report element
			Node textNode = node.getFirstChild();// textNode stands for text node
			NodeList reportList = node.getChildNodes();
			for (int i = 0; i < reportList.getLength(); i++) {
				if (reportList.item(i).getNodeType() != (Node.TEXT_NODE)) {
					// get all the race element for each report
					if(textArea!=null) {
						textAreaInfo=textAreaInfo+"reportList Length:"+reportList.getLength()+"\n";
						textArea.setText(textAreaInfo);
					}
					System.out.println("reportList Length:"	+ reportList.getLength());
					NodeList raceList = reportList.item(i).getChildNodes();
					Set<Race> races = new HashSet<Race>();
					Set<Race> compareRaces = new HashSet<Race>();
					for (int j = 0; j < raceList.getLength(); j++) {
						// deal with each race
						if(textArea!=null) {
							textAreaInfo=textAreaInfo+"raceList Length:"+raceList.getLength()+"\n";
							textArea.setText(textAreaInfo);
						}
						System.out.println("raceList length:"+ raceList.getLength());
						if (raceList.item(j).getNodeType() != Node.TEXT_NODE) {
							if (raceList.item(j).hasChildNodes()) {
								Node tempNode = raceList.item(j).getFirstChild();
								Node tempLine1 = getNonTextNode(tempNode);
								tempNode = tempLine1.getNextSibling();
								Node tempLine2 = getNonTextNode(tempNode);
								tempNode = tempLine2.getNextSibling();
								Node tempVariable = getNonTextNode(tempNode);
								tempNode = tempVariable.getNextSibling();
								Node tempPackageClass = getNonTextNode(tempNode);
								tempNode = tempPackageClass.getNextSibling();
								Node tempDetail = getNonTextNode(tempNode);
								tempNode = tempDetail.getNextSibling();
								if(textArea!=null) {
									textAreaInfo=textAreaInfo+"line1:"+tempLine1.getFirstChild().getNodeValue().toString()+"\n";
									textArea.setText(textAreaInfo);
								}
								System.out.println("line1:"	+ tempLine1.getFirstChild().getNodeValue().toString());
								String line1 = tempLine1.getFirstChild().getNodeValue() != null ? (tempLine1.getFirstChild().getNodeValue().toString()).trim() : null;
								String line2 = tempLine2.getFirstChild().getNodeValue() != null ? (tempLine2.getFirstChild().getNodeValue().toString()).trim() : null;
								String variable = "";
								if(tempVariable.getFirstChild()!=null) {
									variable = tempVariable.getFirstChild().getNodeValue() != null ? tempVariable.getFirstChild().getNodeValue().toString().trim() : null;
								}else {
									variable="";
								}
								//String variable = tempVariable.getFirstChild().getNodeValue() != null ? tempVariable.getFirstChild().getNodeValue().toString().trim() : null;
								String packageClass = tempPackageClass.getFirstChild().getNodeValue() != null ? tempPackageClass.getFirstChild().getNodeValue().toString().trim(): null;
								String detail ="";
								if(tempDetail.getFirstChild()!=null) {
								if(tempDetail.getFirstChild().getNodeType() == node.TEXT_NODE){
									detail = tempDetail.getFirstChild().getNextSibling().getNodeValue() != null ? tempDetail.getFirstChild().getNextSibling().getNodeValue().toString(): null;
									System.out.println("detail:" + tempDetail.getFirstChild().getNextSibling().getNodeValue().toString());
//									detail.replace("\t", "");
									if(textArea!=null) {
										textAreaInfo=textAreaInfo+"detail:"+"      "+tempDetail.getFirstChild().getNextSibling().getNodeValue().toString().replace("\t", "")+"\n";
										textArea.setText(textAreaInfo);
									}
								}else{
									detail = tempDetail.getFirstChild().getNodeValue() != null ? tempDetail.getFirstChild().getNodeValue().toString(): null;
									System.out.println("detail:" + tempDetail.getFirstChild().getNodeValue().toString());
									if(textArea!=null) {
										textAreaInfo=textAreaInfo+"detail:"+"      "+tempDetail.getFirstChild().getNodeValue().toString().replace("\t", "")+"\n";
										textArea.setText(textAreaInfo);
									}
								}
								}
								Race tempRace, compareRace;
								if (Integer.parseInt(line1) < Integer.parseInt(line2)) {
									tempRace = new Race(line1, line2, variable,packageClass, detail);
									compareRace = new Race(line1, line2);
								} else {
									tempRace = new Race(line2, line1, variable,packageClass, detail);
									compareRace = new Race(line2, line1);
								}
								races.add(tempRace);
								compareRaces.add(compareRace);
							}
						}
					}
//					Comparison aaa = new Comparison();
//					aaa.getUniqueRace(compareRaces);
					NamedNodeMap reportAttributes = reportList.item(i).getAttributes();
					String programName = reportAttributes.getNamedItem("name").getNodeValue().toString();
					Reports.programNames.add(programName);
					Report report = new Report(races, programName);
					Report compareReport = new Report(compareRaces);// is a set all the races for each program
					if (!races.isEmpty()) {
						Reports.reports.put(programName, report);
						Reports.compareReports.put(programName, compareReport);
					}
				}
			}
		}
	}

	public Node getNonTextNode(Node node) {
		while (node.getNodeType() == Node.TEXT_NODE) {
			node = node.getNextSibling();
		}
		return node;
	}

}
