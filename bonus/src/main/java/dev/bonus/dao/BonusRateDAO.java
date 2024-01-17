package dev.bonus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.bonus.model.BonusRate;
import dev.bonus.util.DBUtil;

public class BonusRateDAO {
	
//	입력: year
	public BonusRate findBonusRateByYear(String inputYear) throws SQLException {
		BonusRate bonusRate = null;
	    
	    Connection connection = DBUtil.getConnection();
	    
	    String sql = "WITH RankedResults AS (\n"
				+ "  SELECT\n"
				+ "    e.emp_no,\n"
				+ "    a.year,\n"
				+ "    g.bonus_rate,\n"
				+ "    ROW_NUMBER() OVER (PARTITION BY e.emp_no, a.year ORDER BY e.from_date) AS row_num\n"
				+ "  FROM\n"
				+ "    (SELECT year, dept_no, grade_no FROM dept_achievement) a\n"
				+ "    JOIN (SELECT emp_no, dept_no, from_date, to_date FROM dept_emp) e ON a.year = ? AND YEAR(e.from_date) = a.year AND e.dept_no = a.dept_no\n"
				+ "    JOIN (SELECT grade_no, bonus_rate FROM dept_grade) g ON a.grade_no = g.grade_no\n"
				+ ")\n"
				+ "SELECT emp_no, year, bonus_rate\n"
				+ "FROM RankedResults\n"
				+ "WHERE row_num = 1\n"
				+ "ORDER BY emp_no, year;";

	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    	
	        statement.setString(1, inputYear);

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                int emp_no = rs.getInt("emp_no");
	                String year = rs.getString("year");
	                int bonus_rate = rs.getInt("bonus_rate");

	                bonusRate = new BonusRate(emp_no, year, bonus_rate);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bonusRate;
	}
	
//	입력: emp_no
	public List<BonusRate> findBonusRateByEmpNo(int inputEmpNo) throws SQLException {
	    List<BonusRate> bonusRates = new ArrayList<>();
	    
	    Connection connection = DBUtil.getConnection();
	    
	    String sql = "WITH RankedResults AS (\n"
				+ "  SELECT\n"
				+ "    e.emp_no,\n"
				+ "    a.year,\n"
				+ "    g.bonus_rate,\n"
				+ "    ROW_NUMBER() OVER (PARTITION BY e.emp_no, a.year ORDER BY e.from_date) AS row_num\n"
				+ "  FROM\n"
				+ "    (SELECT year, dept_no, grade_no FROM dept_achievement) a\n"
				+ "    JOIN (SELECT emp_no, dept_no, from_date, to_date FROM dept_emp) e ON e.emp_no = ? AND YEAR(e.from_date) = a.year AND e.dept_no = a.dept_no\n"
				+ "    JOIN (SELECT grade_no, bonus_rate FROM dept_grade) g ON a.grade_no = g.grade_no\n"
				+ ")\n"
				+ "SELECT emp_no, year, bonus_rate\n"
				+ "FROM RankedResults\n"
				+ "WHERE row_num = 1\n"
				+ "ORDER BY emp_no, year;";

	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    	
	        statement.setInt(1, inputEmpNo);

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                int emp_no = rs.getInt("emp_no");
	                String year = rs.getString("year");
	                int bonus_rate = rs.getInt("bonus_rate");

	                bonusRates.add(new BonusRate(emp_no, year, bonus_rate));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bonusRates;
	}
	
//	입력: emp_no, year
	public BonusRate findBonusRateByEmpNoAndYear(int inputEmpNo, String inputYear) throws SQLException { // ex. findById(2); // id가 2번인 Todo 객체를 반환하는 메서드
		BonusRate bonusRate = null;
		
		Connection connection = DBUtil.getConnection();

		String sql = "WITH RankedResults AS (\n"
				+ "  SELECT\n"
				+ "    e.emp_no,\n"
				+ "    a.year,\n"
				+ "    g.bonus_rate,\n"
				+ "    ROW_NUMBER() OVER (PARTITION BY e.emp_no, a.year ORDER BY e.from_date) AS row_num\n"
				+ "  FROM\n"
				+ "    (SELECT year, dept_no, grade_no FROM dept_achievement) a\n"
				+ "    JOIN (SELECT emp_no, dept_no, from_date, to_date FROM dept_emp) e ON e.emp_no = ? AND a.year = ? AND YEAR(e.from_date) = a.year AND e.dept_no = a.dept_no\n"
				+ "    JOIN (SELECT grade_no, bonus_rate FROM dept_grade) g ON a.grade_no = g.grade_no\n"
				+ ")\n"
				+ "SELECT emp_no, year, bonus_rate\n"
				+ "FROM RankedResults\n"
				+ "WHERE row_num = 1\n"
				+ "ORDER BY emp_no, year;";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setInt(1, inputEmpNo);
			statement.setString(2, inputYear);
			
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {					
					int emp_no = rs.getInt("emp_no");
					String year = rs.getString("year");
					int bonus_rate = rs.getInt("bonus_rate");

					bonusRate = new BonusRate(emp_no, year, bonus_rate);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return bonusRate;
	}
	
	
}

// 1. 부서별 등급 테이블(grade) 조회
// 2. 직원 테이블(dept_emp) 조회
// 3. 부서별 등급에 따른 성과급 계산
// 