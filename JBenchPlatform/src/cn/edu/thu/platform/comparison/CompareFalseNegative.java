package cn.edu.thu.platform.comparison;

import java.util.Iterator;
import java.util.Set;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;

public class CompareFalseNegative {

	public void compare(Report originalReport, Report newReport) {
		Set<Race> originalRaces = originalReport.getRaces();
		Set<Race> newRaces = newReport.getRaces();
		int originalLength = originalRaces.size();
		int newlength = newRaces.size();
		int rightMatch = 0;// 匹配个数
		int falseMatch = 0;// 误报个数
		Iterator it = newRaces.iterator();
		while (it.hasNext()) {
			Race race1 = (Race) it.next();
			if (originalRaces.contains(race1)) {
				rightMatch++;
			} else {
				falseMatch++;
			}
		}
		String programName = newReport.getName();
		String message = programName + "中的race总数是" + originalLength + "个,"
				+ "该工具成功找到了" + rightMatch + "个,遗漏了"
				+ (originalLength - rightMatch) + "个,并且额外找出了" + falseMatch
				+ "个,暂且怀疑是误报情况。因此漏报率是" + (originalLength - rightMatch)
				/ originalLength + ",误报率是" + falseMatch / originalLength
				+ "\n\n";
		ComparisonResult.result += message;
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
