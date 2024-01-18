package dev.bonus.controller;

import java.sql.Connection;
import java.util.List;

import dev.bonus.model.BonusRate;
import dev.bonus.service.BonusService;
import dev.bonus.util.DBUtil;

public class BonusController {
	
	BonusService bonusService;
	Connection connection;
	
	public BonusController(String url, String database, String user, String password) {
		this.connection = DBUtil.getConnection(url, database, user, password);
		this.bonusService = new BonusService(this.connection);
	}

//	특정 년도를 입력받아 직원들의 성과급 조회
	public void empBonusByYear(int year) {
		List<BonusRate> findBonusRateByYear = bonusService.findBonusRateByYear(year);
		
		for (BonusRate bonusRate : findBonusRateByYear) {
			System.out.println(year + "년도 " + bonusRate.getLast_name()+ " " + bonusRate.getFirst_name() + "의 성과급은: " + bonusRate.getBouns());
		}
	}
	
//	특정 회원번호를 입력받아 특정 직원의 년도별 성과급 조회
	public void empBonusByEmpNo(int empNo) {
		List<BonusRate> findBonusRateByEmpNo = bonusService.findBonusRateByEmpNo(empNo);
		
		for (BonusRate bonusRate : findBonusRateByEmpNo) {
			System.out.println(bonusRate.getYear() + "년도 " + bonusRate.getLast_name()+ " " + bonusRate.getFirst_name() + "의 성과급은: " + bonusRate.getBouns());
		}
	}
	
//	특정회원의 특정 년도 성과급 조회
	public void empBonusByEmpNoAndYear(int empNo, int year) {
		BonusRate findBonusRateByEmpNoAndYear = bonusService.findBonusRateByEmpNoAndYear(empNo, year);
		
		System.out.println(findBonusRateByEmpNoAndYear.getYear() + "년도 " + findBonusRateByEmpNoAndYear.getLast_name()+ " " + findBonusRateByEmpNoAndYear.getFirst_name() + "의 성과급은: " + findBonusRateByEmpNoAndYear.getBouns());
	}

//	년도별 평균 성과급 조회하는
	public void findBonusAvg() {
		List<BonusRate> findBonusAvg = bonusService.findBonusAvg();
		
		for (BonusRate bonusRate : findBonusAvg) {
			System.out.println(bonusRate.getYear() + "년도의 평균 성과급: " + bonusRate.getBouns());
		}
	}
	
	
}
