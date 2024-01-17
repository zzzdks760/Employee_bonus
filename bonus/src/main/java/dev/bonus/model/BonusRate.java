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
	private int bouns_rate;
}
