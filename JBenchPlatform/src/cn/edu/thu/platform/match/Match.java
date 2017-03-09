package cn.edu.thu.platform.match;

public class Match {

	public static MatchInterface getMatch(String type) {
		MatchInterface match = null;
		switch (type) {
			case "CalFuzzer":
				match = new MatchCalfuzzer();
				break;
			case "DATE":
				match = new MatchDATE();
				break;
			case "Rv-Predict":
				match = new MatchRvPredict();
				break;
			case "other":
				match = new MatchDATE();
				break;
			default:
				break;
		}
		return match;
	}
}