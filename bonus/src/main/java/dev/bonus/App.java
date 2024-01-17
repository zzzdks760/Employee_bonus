package dev.bonus;

import dev.bonus.controller.BonusController;

public class App {

	public static void main(String[] args) {
		BonusController bonusController = new BonusController();
		bonusController.empBonusByYear(1999);
//		bonusController.findBonusAvg();
	}

}
