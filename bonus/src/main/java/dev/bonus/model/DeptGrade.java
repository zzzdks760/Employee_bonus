package dev.bonus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class DeptGrade {
	
	private int grade_no;
	private int bonus_rate;
	private int percentage;
	private Grade grade;
	
}
