package dev.bonus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BonusRate {
	private int emp_no;
	private String year;
	private String last_name;
	private String first_name;
	private int bouns;
	
	
	public BonusRate(String year, int bouns) {
		this.year = year;
		this.bouns = bouns;
	}
	
	
}
