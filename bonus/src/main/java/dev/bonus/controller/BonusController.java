package dev.bonus.controller;

import java.sql.SQLException;
import java.util.List;

import dev.bonus.dao.BonusRateDAO;
import dev.bonus.model.BonusRate;

public class BonusController {
	public static void main(String[] args) throws SQLException {
		BonusRateDAO bonusRateDAO = new BonusRateDAO();
		
		
		BonusRate findBonusRateByEmpNoAndYear = bonusRateDAO.findBonusRateByEmpNoAndYear(10094, "1987");
		System.out.println(findBonusRateByEmpNoAndYear.getEmp_no() + " " + findBonusRateByEmpNoAndYear.getBouns_rate());
		
		BonusRate findBonusRateByYear = bonusRateDAO.findBonusRateByYear("1999");
		System.out.println(findBonusRateByYear.getEmp_no());
		
		List<BonusRate> findBonusRateByEmpNo = bonusRateDAO.findBonusRateByEmpNo(10099);
		System.out.println(findBonusRateByEmpNo);
	}
}
