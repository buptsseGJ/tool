package cn.edu.thu.platform.comparison;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;

public class Comparison {

	public String compare(Report originalReport, Report newReport) {
		if(newReport != null && originalReport !=null){
			Set<Race> originalRaces = originalReport.getCompareRaces();
			Set<Race> newRaces = newReport.getCompareRaces();
			int originalLength = originalRaces.size();
			int newlength = newRaces.size();
			int rightMatch = 0;// 匹配个数
			int falseMatch = 0;// 误报个数
			Iterator<Race> it = newRaces.iterator();
			Set<Race> findRaces = new HashSet<Race>();
			Set<Race> missRaces = new HashSet<Race>();
			Set<Race> additionalRaces = new HashSet<Race>();
			Report findReport = null;
			Report missReport = null;
			Report additionalReport = null;
			while (it.hasNext()) {
				Race race1 = (Race) it.next();
				if (originalRaces.contains(race1)) {
					rightMatch++;
					findRaces.add(race1);
				} else {
					falseMatch++;
					additionalRaces.add(race1);
				}
			}
			missRaces.addAll(originalRaces);
			missRaces.removeAll(newRaces);
			findReport = new Report(findRaces);
			missReport = new Report(missRaces);
			additionalReport = new Report(additionalRaces);
			String programName = newReport.getName();
			String message = "中的race总数是" + originalLength + "个,"
					+ "该工具成功找到了" + rightMatch + "个,遗漏了"
					+ (originalLength - rightMatch) + "个,并且额外找出了" + falseMatch
					+ "个,暂且怀疑是误报情况。因此漏报率是" + (originalLength - rightMatch)
					/ originalLength + ",误报率是" + falseMatch / originalLength
					+ "\n\n";
			ComparisonResult.result += message;
			ComparisonResult.findRace.put(programName, findReport);
			ComparisonResult.missRace.put(programName, missReport);
			ComparisonResult.additianalRace.put(programName, additionalReport);
			ComparisonResult.summary.put(programName, message);
			return message;
		}else{
			return "";
		}
	}

	public Set<Race> getUniqueRace(Set<Race> races) {
		Iterator it = races.iterator();
		while (it.hasNext()) {
			Race race1 = (Race) it.next();
			races.remove(race1);
			if (!races.contains(race1)) {
				races.add(race1);
			}
		}
		return races;
	}
}
