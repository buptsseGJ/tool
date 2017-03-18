package cn.edu.thu.platform.comparison;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;
import cn.edu.thu.platform.entity.Result;

public class Comparison {

	public String compare(String programName,Report originalReport, Report newReport) {
//		if(newReport != null && originalReport !=null){
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
			String message = "中的race总数是" + originalLength + "个,"
					+ "该工具成功找到了" + rightMatch + "个,遗漏了"
					+ (originalLength - rightMatch) + "个,并且额外找出了" + falseMatch
					+ "个。因此正确率是" + rightMatch / originalLength * 100 + "%,漏报率是" + (originalLength - rightMatch)
					/ originalLength * 100 + "%,误报率是" + falseMatch / originalLength * 100
					+ "%。\n";
			Result result = new Result(programName, originalLength, rightMatch, falseMatch, originalLength - rightMatch, 0, 0);
			ComparisonResult.results.add(result);
			ComparisonResult.result += message;
			ComparisonResult.findRace.put(programName, findReport);
			ComparisonResult.missRace.put(programName, missReport);
			ComparisonResult.additianalRace.put(programName, additionalReport);
			ComparisonResult.summary.put(programName, message);
			Iterator<Race> itFind = newRaces.iterator();
			while(itFind.hasNext()){
				Race race = itFind.next();
//				message += "成功找到的data race信息如下：\n";
				message += getSuccessInformation(Reports.reports.get(programName).getRaces(),findRaces);
//				String temp = "\trace位置<" + race.getLine1() + "," +  race.getLine2() + ">";
				message += getMissInformation(Reports.reports.get(programName).getRaces(),missRaces);
				message += getAdditionInformation(additionalRaces);
			}
			return message ;
	}
//		else{
//			return "";
//		}
//	}
	
	public String getAdditionInformation(Set<Race> current){
		String info = "";
		if(current.isEmpty()){
			info += "\n误报的data race如下：无\n";
		}else{
			Iterator<Race> it = current.iterator();
			int count = 1;
			while(it.hasNext()){
				Race temp = it.next();
				if(count == 1){
					info += "误报的data race如下：\n";
				}
				info += "\t" + count + ")位置：<" + temp.getLine1() + "," + temp.getLine2() + ">\n";
				count++;
			}
		}
		return info;
	}
	
	public String getMissInformation(Set<Race> original, Set<Race> current){
		String info = "";
		if(current.isEmpty()){
			info += "\n漏报的dara race如下：无\n";
		}else{
			Iterator<Race> it = original.iterator();
			int count = 1;
			while(it.hasNext()){
				Race temp = it.next();
				if(current.contains(temp)){
					if(count == 1){
						info += "没找到的dara race如下：\n";
					}
					info += "\t" + count + ")位置：<" + temp.getLine1() + "," + temp.getLine2() + ">\n";
					info += "\t  变量：" + temp.getVariable() + "\n";
					info += "\t  类：" + temp.getPackageClass() + "\n";
					info += "\t  详细：" + temp.getDetail() + "\n";
					count++;
				}
			}		
		}
		return info;
	}
	
	public String getSuccessInformation(Set<Race> original, Set<Race> current){
		String info = "";
		if(current.isEmpty()){
			info += "成功找到的dara race如下：无\n";
		}else{
			Iterator<Race> it = original.iterator();
			int count = 1;
			while(it.hasNext()){
				Race temp = it.next();
				if(current.contains(temp)){
					if(count == 1){
						info += "成功找到的dara race如下：\n";
					}
					info += "\t" + count + ")位置：<" + temp.getLine1() + "," + temp.getLine2() + ">\n";
					info += "\t  变量：" + temp.getVariable() + "\n";
					info += "\t  类：" + temp.getPackageClass() + "\n";
					info += "\t  详细：" + temp.getDetail() + "\n";
					count++;
				}
			}
		}
		return info;
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
