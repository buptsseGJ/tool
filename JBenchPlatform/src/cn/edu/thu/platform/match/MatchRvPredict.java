package cn.edu.thu.platform.match;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;

public class MatchRvPredict implements MatchInterface {

	public MatchRvPredict() {
		System.out.println(" RvPredict 文件匹配  start...");
	}

	@Override
	public void matchFile() {
		File file = new File("../result.txt");
		BufferedReader reader = null;
		String info = "";
		// state 表示目前的匹配进度
		// 0初始状态；1匹配到>>>start<<<;2匹配到>>>end<<<;
		int state = 0;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String tempString = "";
			while((tempString = reader.readLine())!= null){

				switch(state) {
					case 0://若状态为0,说明刚开始读取文件,需要寻找>>>start
						if(tempString.indexOf(">>>start")!=-1) {//说明找到了当前测试用例的结束位置
							state=1;
						}
						//防止输出信息位于两行中，将一个测试用例中的输出文本拼接成一行
						info +=tempString;
						break;
					case 1://若状态为1,说明已经匹配到>>>start<<<,正在寻找>>>end<<<
						if(tempString.indexOf(">>>>>end<<<<<")!=-1) {//说明找到了当前测试用例的结束位置
							state=2;
						}
						//防止输出信息位于两行中，将一个测试用例中的输出文本拼接成一行
						info +=tempString;
						break;
					case 2://若状态为2,说明已经找到测试用例名,正在寻找测试用例的行号对
						System.out.println("\n\n\n"+info+"\n");
						String caseName = "";
						//防止 为发现race时匹配处理时可能引发的异常
						String pattern = "((>>>>>start.*?about\\s+)(.*?)(on\\s+))(.*)";
						Pattern namePattern = Pattern.compile(pattern);
						Matcher nameMatch = namePattern.matcher(info);
						if (nameMatch.find()) {
							caseName = nameMatch.group(3);
							Set<Race> races = new HashSet<Race>();
							
							String pair = "(.*?\\-+?>\\s+?at.*?\\(\\s*?)(.*?\\.java)(\\s*?:\\s*?)(\\d+?)(\\s*?\\).*?)(.*?\\-+?>\\s+?at.*?\\(\\s*?)(.*?\\.java)(\\s*?:\\s*?)(\\d+?)(\\s*?\\).*?)(.*)";
							Pattern pairPattern = Pattern.compile(pair);
							info = nameMatch.group(5);
							Matcher pairMatch = pairPattern.matcher(info);
							while(pairMatch.find()) {
								String str1 = pairMatch.group(2);
								String num1 = pairMatch.group(4);
								String str2 = pairMatch.group(7);
								String num2 = pairMatch.group(9);
								
								//保证 行号对中行较小的数字永远在前面
								int x1 = Integer.parseInt(num1.trim());
								int x2 = Integer.parseInt(num1.trim());
								if(x1<x2) {
									//匹配到一对
									Race race = new Race(num1,num2);
									races.add(race);
									System.out.println(str1+"	" + num1+"	"+str2+"	"+num2);
								}else {
									//匹配到一对
									Race race = new Race(num2.trim(),num1.trim());
									races.add(race);			
									System.out.println(str2+"	" + num2+"	"+str1+"	"+num1);						
								}
		
								pairMatch = pairPattern.matcher(pairMatch.group(11));
							}
							Report report = new Report(races);
							Reports.userReports.put(caseName, report);
						}
						//当前测试用例输出匹配完毕，开始寻找下一对
						tempString = "";
						state = 0;
						break;
						
					default:break;
				}
			}
		}catch (IOException e) {
				e.printStackTrace();
		}finally{
			if (reader != null) {
				try {
						reader.close();
					} catch (IOException e1) {
				}
			}
		}
	}
}
