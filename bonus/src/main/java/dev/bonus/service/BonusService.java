package dev.bonus.service;

import java.sql.SQLException;
import java.util.List;

import dev.bonus.dao.BonusRateDAO;
import dev.bonus.model.BonusRate;

public class BonusService {
	
	BonusRateDAO bonusRateDAO = new BonusRateDAO();
	
	public List<BonusRate> findBonusRateByYear(int inputYear) {
		if (inputYear >= 1985 && inputYear <= 2001) {
			String year = Integer.toString(inputYear);
			try {
				List<BonusRate> findBonusRateByYear = bonusRateDAO.findBonusRateByYear(year);
				
				return findBonusRateByYear;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			System.out.println("1985년 부터 2001년 사이의 값을 입력해 주세요.");
			return null;
		}
	}
	
	public List<BonusRate> findBonusRateByEmpNo(int inputEmpNo) {
		if (inputEmpNo >= 10001 && inputEmpNo <= 499999) {
			try {
				List<BonusRate> findBonusRateByEmpNo = bonusRateDAO.findBonusRateByEmpNo(inputEmpNo);
				return findBonusRateByEmpNo;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			System.out.println("10001부터 499999사이의 직원 번호를 입력해 주세요.");
			return null;
		}
	}
	
	public BonusRate findBonusRateByEmpNoAndYear(int inputEmpNo, int inputYear) {
		if (inputYear >= 1985 && inputYear <= 2001 && inputEmpNo >= 10001 && inputEmpNo <= 499999) {
			String year = Integer.toString(inputYear);
			try {
				BonusRate findBonusRateByEmpNoAndYear = bonusRateDAO.findBonusRateByEmpNoAndYear(inputEmpNo, year);
				return findBonusRateByEmpNoAndYear;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			System.out.println("년도는 1985년 부터 2001년 사이의 값 직원 번호는 10001부터 499999사이를 입력해 주세요. ");
			return null;
		}
	}
	
	public List<BonusRate> findBonusAvg() {
		try {
			return bonusRateDAO.findBonusAvg();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
