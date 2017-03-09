package cn.edu.thu.platform.match;

import java.util.HashSet;
import java.util.Set;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;

public class MatchDATE implements MatchInterface {
	// 读一个文件，然后匹配出每个项目的race，每队race是两个行号

	public MatchDATE() {
		System.out.println(" DATA 文件匹配  start...");
	}

	@Override
	public void matchFile() {

		String programName = "about 后面的名字";
		Race race = new Race("1", "2");// 1和2代表行号

		Set<Race> races = new HashSet<Race>();
		races.add(race);
		Report report = new Report(races);
		Reports.userReports.put(programName, report);// 这行非常重要
	}
}
