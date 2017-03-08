package cn.edu.thu.platform.match;

import java.util.HashSet;
import java.util.Set;

import cn.edu.thu.platform.entity.*;

public class MatchDATE {
	//读一个文件，然后匹配出每个项目的race，每队race是两个行号
	
	public MatchDATE(){
		String programName = "about 后面的名字";
		Race race = new Race("1","2");//1和2代表行号
		Set<Race> races = new HashSet<Race>();
		races.add(race);
		Report report = new Report(races);
		Reports.userReports.put(programName, report);//这行非常重要
	}
}
