package dev.bonus;

import dev.bonus.controller.BonusController;

public class App {

	public static void main(String[] args) {
		BonusController bonusController = new BonusController("jdbc:mysql://localhost:3306/", "employees", "root", "1234");
		bonusController.empBonusByYear(1998);
//		bonusController.findBonusAvg();
	}

}
