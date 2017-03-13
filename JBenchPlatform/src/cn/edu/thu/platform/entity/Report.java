package cn.edu.thu.platform.entity;

import java.util.HashSet;
import java.util.Set;

public class Report {
	private Set<Race> races = new HashSet<Race>();
	private String name;
	private Set<Race> compareRaces = new HashSet<Race>();

	public Report(Set<Race> compareRaces) {
		this.compareRaces = compareRaces;
	}

	public Report(Set<Race> races, String name) {
		super();
		this.races = races;
		this.name = name;
	}

	public Set<Race> getRaces() {
		return races;
	}

	public void setRaces(Set<Race> races) {
		this.races = races;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Race> getCompareRaces() {
		return compareRaces;
	}

	public void setCompareRaces(Set<Race> compareRaces) {
		this.compareRaces = compareRaces;
	}
}
