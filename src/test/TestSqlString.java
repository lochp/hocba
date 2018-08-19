package test;

import dao.SqlString;
import entity.TestTable;

public class TestSqlString {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		TestTable e = new TestTable();
		System.out.println(SqlString.insertSql(e));
		System.out.println(SqlString.updateSql(e));
		System.out.println(SqlString.deleteSql(e));
	}

}
