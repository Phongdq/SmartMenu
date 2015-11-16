/**
 * 
 */
package com.nnm.emenu.server.dataengine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nnm.emenu.shared.Bill;
import com.nnm.emenu.shared.SumMoney;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author bizluvsakura
 *
 */
public class BillDAO extends SimpleJdbcDaoSupport {
	public BillDAO(JdbcConnectionPool pool) {
		super(pool);
	}

	private class BillMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			Bill bill = new Bill();
			bill.setId(rs.getLong("id"));
			bill.setOrder_id(rs.getLong("order_id"));
			bill.setUser_id(rs.getInt("user_id"));
			bill.setTable(rs.getInt("table"));
			bill.setMoney_total(rs.getDouble("money_total"));
			bill.setMoney_discount(rs.getDouble("money_discount"));
			bill.setVat(rs.getFloat("vat"));
			bill.setMoney_payment(rs.getDouble("money_payment"));
			bill.setMoney_customer(rs.getDouble("money_customer"));
			bill.setMoney_return(rs.getDouble("money_return"));
			bill.setGendate(rs.getString("gendate"));
			return bill;
		}
	}

	public void insertNewBill(long order_id, int user_id, int table,
			double money_total, double money_discount, float vat,
			double money_payment, double money_customer, double money_return,
			String gendate) {
		String query = "insert into `bill`(`order_id`,`user_id`,`table`,`money_total`,`money_discount`,`vat`,`money_payment`,`money_customer`,`money_return`,`gendate`) values('"
				+ order_id
				+ "','"
				+ user_id
				+ "','"
				+ table
				+ "','"
				+ money_total
				+ "','"
				+ money_discount
				+ "','"
				+ vat
				+ "','"
				+ money_payment
				+ "','"
				+ money_customer
				+ "','"
				+ money_return
				+ "','" + gendate + "')";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public List<SumMoney> getBill(int typeFilter, String time_start,
			String time_end) {
		// typeFilter == 0 : query theo ngay
		// typeFilter == 1 : query theo tuan
		// typeFilter == 2 : query theo thang
		// typeFilter == 3 : query theo nam
		// typeFilter == 4 : query link bang user_info
		String query = "";
		if (typeFilter == 0) {
			query = "select convert(gendate,DATE),sum(money_payment) "
					+ "from emenudb.bill where convert(gendate,DATE) >= '"
					+ time_start + "' and convert(gendate,DATE) <= '"
					+ time_end + "' group by convert(gendate,DATE);";
		} else if (typeFilter == 1) {
			query = "SELECT weekofyear(gendate),sum(money_payment) from bill  where gendate >= '"
					+ time_start
					+ "' and gendate <= '"
					+ time_end
					+ "'"
					+ "group by weekofyear(gendate)";
		} else if (typeFilter == 2) {
			query = "SELECT month(gendate),sum(money_payment) from bill  where gendate >= '"
					+ time_start
					+ "' and gendate <= '"
					+ time_end
					+ "'"
					+ "group by month(gendate)";
		} else if (typeFilter == 3) {
			query = "SELECT year(gendate),sum(money_payment) from bill  where gendate >= '"
					+ time_start
					+ "' and gendate <= '"
					+ time_end
					+ "'"
					+ "group by year(gendate)";
		} else if (typeFilter == 4) {
			query = "SELECT user_info.username,sum(bill.money_payment) from bill,user_info  where bill.user_id = user_info.id and gendate >= '"
					+ time_start
					+ "' and gendate <= '"
					+ time_end
					+ "'"
					+ "group by bill.user_id";
		}
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query,
				new SumMoneyMapper(typeFilter));
	}

	public Object getTotalMoney(String time_start, String time_end) {
		String query = "select count(id),sum(money_payment),sum(money_discount) from `bill` where gendate >= '"
				+ time_start + "' and gendate <= '" + time_end + " 23:59:59'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new TotalMoneyMapper())
				.get(0);
	}

	class TotalMoneyMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			Map<String, Long> result = new HashMap<String, Long>();
			result.put("money_payment", rs.getLong("sum(money_payment)"));
			result.put("money_discount", rs.getLong("sum(money_discount)"));
			result.put("count_bill", rs.getLong("count(id)"));
			return result;
		}
	}

	class SumMoneyMapper implements RowMapper {
		int typeFilter = -1;

		public SumMoneyMapper(int typeFilter) {
			this.typeFilter = typeFilter;
		}

		@Override
		public SumMoney mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			SumMoney sumMoney = new SumMoney();
			if (typeFilter != 4) {
				sumMoney.setSumMoney(rs.getLong("sum(money_payment)"));
			} else {
				sumMoney.setSumMoney(rs.getLong("sum(bill.money_payment)"));
			}
			if (typeFilter == 0) {
				sumMoney.setTime(rs.getString("convert(gendate,DATE)"));
			} else if (typeFilter == 1) {
				sumMoney.setTime(rs.getString("weekofyear(gendate)"));
			} else if (typeFilter == 2) {
				sumMoney.setTime(rs.getString("month(gendate)"));
			} else if (typeFilter == 3) {
				sumMoney.setTime(rs.getString("year(gendate)"));
			} else if (typeFilter == 4) {
				sumMoney.setTime(rs.getString("user_info.username"));
			}

			return sumMoney;
		}

	}
}
