package cn.edu.thu.platform.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Reports {
	public static Map<String, Report> reports = new HashMap<String, Report>();
	// 从这里找到特定名字的程序内的所有race
	public static Map<String, Report> compareReports = new HashMap<String, Report>();

	public static Map<String, Report> userReports = new HashMap<String, Report>();
	
	public static List<String> programNames = new ArrayList<String>();
	
	public static List<String> userNames = new ArrayList<String>();
	//wrongNames 存储脚本里的名字不在benchmark中的
	public static List<String> wrongNames = new ArrayList<String>();
	
	public static void removeAllBenchmakrs() {
		reports.clear();
		programNames.clear();
	}    
}  
