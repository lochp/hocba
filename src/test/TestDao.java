package test;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;

import dao.Dao;
import databaseControl.DbConnection;
import entity.TestTable;

public class TestDao {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, SQLException {
		TestTable e = new TestTable();
		e.setHarbor("Harbor");
		e.setVase("vase");
		e.setPaddle("Cái mái chèo!");
		e.setId(1l);
		Connection conn = DbConnection.getSingleInstance().getConnection(true);
		TestTable t = (TestTable) Dao.findById(TestTable.class, 3l, conn);
		System.out.println(t);
	}
	
	private static void test_string() {
		System.out.println(String.class.getName());
		System.out.println(Boolean.class.getName());
		System.out.println(BigDecimal.class.getName());
		System.out.println(Byte.class.getName());
		System.out.println(Short.class.getName());
		System.out.println(Integer.class.getName());
		System.out.println(Long.class.getName()); 
		System.out.println(Float.class.getName()); 
		System.out.println(Double.class.getName());
		System.out.println(Byte[].class.getName());
		System.out.println(Date.class.getName());
		System.out.println(Time.class.getName());
		System.out.println(Timestamp.class.getName());
		System.out.println(Clob.class.getName());
		System.out.println(Blob.class.getName());
		System.out.println(Array.class.getName());
		System.out.println(Ref.class.getName());
		System.out.println(Struct.class.getName());
	}
	
	private void test_1() throws ClassNotFoundException, SQLException {
		TestTable e = new TestTable();
		Connection conn = DbConnection.getSingleInstance().getConnection(true);
		Dao.insert(e, conn);
	}

}
