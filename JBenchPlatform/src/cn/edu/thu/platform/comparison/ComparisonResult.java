package cn.edu.thu.platform.comparison;

import java.util.HashMap;
import java.util.Map;
import cn.edu.thu.platform.entity.Report;

public class ComparisonResult {
	public static String tool = "CalFuzzer";
	public static String result = "";
	public static Map<String,Report> findRace = new HashMap<String,Report>();
	public static Map<String,Report> missRace = new HashMap<String,Report>();
	public static Map<String,Report> additianalRace = new HashMap<String,Report>();
	public static Map<String,String> summary = new HashMap<String,String>(); 
}
