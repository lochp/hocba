package dao;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import databaseControl.Entity;

public class Dao {
	
	private static void setParam(PreparedStatement pre, String classTypeName, int index, Field tmp, Object e) throws IllegalArgumentException, IllegalAccessException, SQLException {
		switch(classTypeName) {
		case "java.lang.String": 
			pre.setString(index, (String)tmp.get(e));
			break;
		case "java.lang.Boolean": 
			pre.setBoolean(index, (Boolean)tmp.get(e));
			break;
		case "java.math.BigDecimal": 
			pre.setBigDecimal(index, new BigDecimal(tmp.get(e).toString()));
			break;
		case "java.lang.Byte": 
			pre.setByte(index, (Byte)tmp.get(e));
			break;
		case "java.lang.Short": 
			pre.setShort(index, (Short)tmp.get(e));
			break;
		case "java.lang.Integer": 
			pre.setInt(index, (Integer)tmp.get(e));
			break;
		case "java.lang.Long": 
			pre.setLong(index, (Long)tmp.get(e));
			break;
		case "java.lang.Float": 
			pre.setFloat(index, (Float)tmp.get(e));
			break;
		case "java.lang.Double": 
			pre.setDouble(index, (Double)tmp.get(e));
			break;
		case "[Ljava.lang.Byte;": 
			pre.setBytes(index, (byte[])tmp.get(e));
			break;
		case "java.sql.Date": 
			pre.setDate(index, (Date)tmp.get(e));
			break;
		case "java.sql.Time": 
			pre.setTime(index, (Time)tmp.get(e));
			break;
		case "java.sql.Timestamp": 
			pre.setTimestamp(index, (Timestamp)tmp.get(e));
			break;
		case "java.sql.Clob": 
			pre.setClob(index, (Clob)tmp.get(e));
			break;
		case "java.sql.Blob": 
			pre.setBlob(index, (Blob)tmp.get(e));
			break;
		case "java.sql.Array": 
			pre.setArray(index, (Array)tmp.get(e));
			break;
		case "java.sql.Ref": 
			pre.setRef(index, (Ref)tmp.get(e));
			break;
		case "java.sql.Struct": 
			/**no supporting for setting struct*/
			break;
	}
	}
	
	public static void insert(Entity e, Connection conn) {
		try {
			PreparedStatement pre = conn.prepareStatement(SqlString.insertSql(e));
			int index = 1;
			for (int i = 0; i< e.getClass().getDeclaredFields().length; i++){
				Field tmp = e.getClass().getDeclaredFields()[i];
				if (tmp.getName().equalsIgnoreCase("id")) {
					continue;
				}
				tmp.setAccessible(true);
				setParam(pre, tmp.getType().getName(), index, tmp, e);
				index++;
			}
			pre.executeUpdate();
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void update(Entity e, Connection conn) {
		try {
			PreparedStatement pre = conn.prepareStatement(SqlString.updateSql(e));
			int index = 1;
			Long idValue = -1l;
			for (int i = 0; i< e.getClass().getDeclaredFields().length; i++){
				Field tmp = e.getClass().getDeclaredFields()[i];
				tmp.setAccessible(true);
				if (tmp.getName().equalsIgnoreCase("id")) {
					idValue = (Long)tmp.get(e);
					continue;
				}
				setParam(pre, tmp.getType().getName(), index, tmp, e);
				index++;
			}
			pre.setLong(index, idValue); /** Set Id in Where Clause */
			pre.executeUpdate();
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void delete(Entity e, Connection conn) {
		try {
			PreparedStatement pre = conn.prepareStatement(SqlString.deleteSql(e));
			int index = 1;
			Long idValue = -1l;
			for (int i = 0; i< e.getClass().getDeclaredFields().length; i++){
				Field tmp = e.getClass().getDeclaredFields()[i];
				tmp.setAccessible(true);
				if (tmp.getName().equalsIgnoreCase("id")) {
					idValue = (Long)tmp.get(e);
					break;
				}
			}
			pre.setLong(index, idValue); /** Set Id in Where Clause */
			pre.executeUpdate();
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		}
	}
	
}