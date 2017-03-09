package cn.edu.thu.platform.entity;

import java.util.HashMap;
import java.util.Map;

public class Reports {
	public static Map<String, Report> reports = new HashMap<String, Report>();
	// 从这里找到特定名字的程序内的所有race
	public static Map<String, Report> compareReports = new HashMap<String, Report>();

	public static Map<String, Report> userReports = new HashMap<String, Report>();
}
