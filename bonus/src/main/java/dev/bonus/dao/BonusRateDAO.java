package dev.bonus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.bonus.model.BonusRate;
import dev.bonus.util.DBUtil;

public class BonusRateDAO {
	Connection inputConnection;
	
	public BonusRateDAO(Connection inputConnection) {
		this.inputConnection = inputConnection;
	}

//	입력: year
	public List<BonusRate> findBonusRateByYear(String inputYear) throws SQLException {
		List<BonusRate> bonusRates = new ArrayList<>();
	    
	    Connection connection = inputConnection;
	    
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
	    		+ "SELECT r.emp_no, e.last_name, e.first_name, r.year, r.bonus_rate * s.salary * 0.01 AS bonus\n"
	    		+ "FROM RankedResults r JOIN salaries s ON r.emp_no = s.emp_no\n"
	    		+ "JOIN employees e ON r.emp_no = e.emp_no\n"
	    		+ "WHERE row_num = 1 AND year(s.from_date) = r.year\n"
	    		+ "ORDER BY r.emp_no, r.year;";

	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    	
	        statement.setString(1, inputYear);

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                int emp_no = rs.getInt("emp_no");
	                String year = rs.getString("year");
	                String last_name = rs.getString("last_name");
	                String first_name = rs.getString("first_name");
	                int bonus = rs.getInt("bonus");

	                bonusRates.add(new BonusRate(emp_no, year, last_name, first_name, bonus)); 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bonusRates;
	}
	
//	입력: emp_no
	public List<BonusRate> findBonusRateByEmpNo(int inputEmpNo) throws SQLException {
	    List<BonusRate> bonusRates = new ArrayList<>();
	    
	    Connection connection = inputConnection;
	    
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
	    		+ "SELECT r.emp_no, e.last_name, e.first_name, r.year, r.bonus_rate * s.salary * 0.01 AS bonus\n"
	    		+ "FROM RankedResults r JOIN salaries s ON r.emp_no = s.emp_no\n"
	    		+ "JOIN employees e ON r.emp_no = e.emp_no\n"
	    		+ "WHERE row_num = 1 AND year(s.from_date) = r.year\n"
	    		+ "ORDER BY r.emp_no, r.year;";

	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    	
	        statement.setInt(1, inputEmpNo);

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                int emp_no = rs.getInt("emp_no");
	                String year = rs.getString("year");
	                String last_name = rs.getString("last_name");
	                String first_name = rs.getString("first_name");
	                int bonus = rs.getInt("bonus");

	                bonusRates.add(new BonusRate(emp_no, year, last_name, first_name, bonus));
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
		
		Connection connection = inputConnection;

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
	    		+ "SELECT r.emp_no, e.last_name, e.first_name, r.year, r.bonus_rate * s.salary * 0.01 AS bonus\n"
	    		+ "FROM RankedResults r JOIN salaries s ON r.emp_no = s.emp_no\n"
	    		+ "JOIN employees e ON r.emp_no = e.emp_no\n"
	    		+ "WHERE row_num = 1 AND year(s.from_date) = r.year\n"
	    		+ "ORDER BY r.emp_no, r.year;";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setInt(1, inputEmpNo);
			statement.setString(2, inputYear);
			
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {					
					int emp_no = rs.getInt("emp_no");
					String year = rs.getString("year");
					String last_name = rs.getString("last_name");
	                String first_name = rs.getString("first_name");
					int bonus = rs.getInt("bonus");

					bonusRate = new BonusRate(emp_no, year, last_name, first_name, bonus);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return bonusRate;
	}
	
	public List<BonusRate> findBonusAvg() throws SQLException { // ex. findById(2); // id가 2번인 Todo 객체를 반환하는 메서드
		List<BonusRate> bonusRates = new ArrayList<>();
		
		Connection connection = inputConnection;

		String sql = "select year, bonus from year_bonus;";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                String year = rs.getString("year");
	                int bonus = rs.getInt("bonus");

	                bonusRates.add(new BonusRate(year, bonus));
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return bonusRates;
	}
	
	
}
