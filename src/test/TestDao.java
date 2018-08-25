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
import java.util.Arrays;
import java.util.List;

import dao.Dao;
import databaseControl.DbConnection;
import entity.TestTable;

public class TestDao {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, SQLException {
		TestTable e = new TestTable();
		e.setHarbor("Harbor");
		e.setVase("vase");
		e.setPaddle("C�i m�i ch�o!");
		e.setId(1l);
		Connection conn = DbConnection.getSingleInstance().getConnection(true);
		TestTable t = (TestTable) Dao.findById(TestTable.class, 4l, conn);
		System.out.println(t);
		t.setPaddle("New Paddle");
		Dao.update(t, conn);
		t = (TestTable) Dao.findById(TestTable.class, 4l, conn);
		System.out.println(t);
		@SuppressWarnings("unchecked")
		List<TestTable> lst = Dao.select(TestTable.class, "select id id, cang harbor, lohoa vase, maicheo paddle from testTable where id >= ?", Arrays.asList(3l), conn);
		for (int i=0; i< lst.size(); i++) {
			System.out.println(lst.get(i).getId());
		}
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
