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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import databaseControl.Entity;
import databaseControl.EntityRelationTableMapping;

public class Dao {
	
	public static String getEntityColName(Entity entity, Field field) {
		String colName = EntityRelationTableMapping.tableFieldNameMap.get(entity.getClass().getName()).get(field.getName());
		if (colName == null && field.getName().equalsIgnoreCase("id")) {
			return "id";
		}
		return colName;
	}
	
	public static void setFieldValueFromResultSet(Object targetObject, Field field, String colName, ResultSet rs) throws IllegalArgumentException, IllegalAccessException, SQLException {
		String classTypeName = field.getType().getName();
		switch(classTypeName) {
		case "java.lang.String": 
			field.set(targetObject, rs.getString(colName));
			break;
		case "java.lang.Boolean": 
			field.set(targetObject, rs.getBoolean(colName));
			break;
		case "java.math.BigDecimal": 
			field.set(targetObject, rs.getBigDecimal(colName));
			break;
		case "java.lang.Byte": 
			field.set(targetObject, rs.getByte(colName));
			break;
		case "java.lang.Short": 
			field.set(targetObject, rs.getShort(colName));
			break;
		case "java.lang.Integer": 
			field.set(targetObject, rs.getInt(colName));
			break;
		case "java.lang.Long": 
			field.set(targetObject, rs.getLong(colName));
			break;
		case "java.lang.Float": 
			field.set(targetObject, rs.getFloat(colName));
			break;
		case "java.lang.Double": 
			field.set(targetObject, rs.getDouble(colName));
			break;
		case "[Ljava.lang.Byte;": 
			field.set(targetObject, rs.getByte(colName));
			break;
		case "java.sql.Date": 
			field.set(targetObject, rs.getDate(colName));
			break;
		case "java.sql.Time": 
			field.set(targetObject, rs.getTime(colName));
			break;
		case "java.sql.Timestamp": 
			field.set(targetObject, rs.getTimestamp(colName));
			break;
		case "java.sql.Clob": 
			field.set(targetObject, rs.getClob(colName));
			break;
		case "java.sql.Blob": 
			field.set(targetObject, rs.getBlob(colName));
			break;
		case "java.sql.Array": 
			field.set(targetObject, rs.getArray(colName));
			break;
		case "java.sql.Ref": 
			field.set(targetObject, rs.getRef(colName));
			break;
		case "java.sql.Struct": 
			/**no supporting for setting struct*/
			break;
		}
	}
	
	public static List<Object> getListFromResultSet(Class<?> clazz, ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException{
		List<Object> reval = new ArrayList<Object>();
		while( rs.next()) {
			Object o = clazz.newInstance();
			for(int i = 0, size = o.getClass().getDeclaredFields().length; i< size; i++) {
				Field field = o.getClass().getDeclaredFields()[i];
				field.setAccessible(true);
				setFieldValueFromResultSet(o, field, field.getName(), rs);
				field.setAccessible(false);
			}
			reval.add(o);
		}
		return reval;
	}
	
	public static List<Entity> getListEntityFromResultSet(Class<?> entityClazz, ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException{
		List<Entity> reval = new ArrayList<Entity>();
		while( rs.next()) {
			Entity entity = (Entity) entityClazz.newInstance();
			for(int i = 0, size = entity.getClass().getDeclaredFields().length; i< size; i++) {
				Field field = entity.getClass().getDeclaredFields()[i];
				field.setAccessible(true);
				setFieldValueFromResultSet(entity, field, getEntityColName(entity, field), rs);
				field.setAccessible(false);
			}
			reval.add(entity);
		}
		return reval;
	}
	
	public static void setParamFromObject(PreparedStatement pre, String classTypeName, int index, Field field, Object originObject) throws IllegalArgumentException, IllegalAccessException, SQLException {
		switch(classTypeName) {
			case "java.lang.String": 
				pre.setString(index, (String)field.get(originObject));
				break;
			case "java.lang.Boolean": 
				pre.setBoolean(index, (Boolean)field.get(originObject));
				break;
			case "java.math.BigDecimal": 
				pre.setBigDecimal(index, new BigDecimal(field.get(originObject).toString()));
				break;
			case "java.lang.Byte": 
				pre.setByte(index, (Byte)field.get(originObject));
				break;
			case "java.lang.Short": 
				pre.setShort(index, (Short)field.get(originObject));
				break;
			case "java.lang.Integer": 
				pre.setInt(index, (Integer)field.get(originObject));
				break;
			case "java.lang.Long": 
				pre.setLong(index, (Long)field.get(originObject));
				break;
			case "java.lang.Float": 
				pre.setFloat(index, (Float)field.get(originObject));
				break;
			case "java.lang.Double": 
				pre.setDouble(index, (Double)field.get(originObject));
				break;
			case "[Ljava.lang.Byte;": 
				pre.setBytes(index, (byte[])field.get(originObject));
				break;
			case "java.sql.Date": 
				pre.setDate(index, (Date)field.get(originObject));
				break;
			case "java.sql.Time": 
				pre.setTime(index, (Time)field.get(originObject));
				break;
			case "java.sql.Timestamp": 
				pre.setTimestamp(index, (Timestamp)field.get(originObject));
				break;
			case "java.sql.Clob": 
				pre.setClob(index, (Clob)field.get(originObject));
				break;
			case "java.sql.Blob": 
				pre.setBlob(index, (Blob)field.get(originObject));
				break;
			case "java.sql.Array": 
				pre.setArray(index, (Array)field.get(originObject));
				break;
			case "java.sql.Ref": 
				pre.setRef(index, (Ref)field.get(originObject));
				break;
			case "java.sql.Struct": 
				/**no supporting for setting struct*/
				break;
		}
	}
	
	public static void setParamFromFilter(PreparedStatement pre, int index, Object filter) throws IllegalArgumentException, IllegalAccessException, SQLException {
		String classTypeName = filter.getClass().getTypeName();
		switch(classTypeName) {
			case "java.lang.String": 
				pre.setString(index, (String)(filter));
				break;
			case "java.lang.Boolean": 
				pre.setBoolean(index, (Boolean)(filter));
				break;
			case "java.math.BigDecimal": 
				pre.setBigDecimal(index, new BigDecimal((filter).toString()));
				break;
			case "java.lang.Byte": 
				pre.setByte(index, (Byte)(filter));
				break;
			case "java.lang.Short": 
				pre.setShort(index, (Short)(filter));
				break;
			case "java.lang.Integer": 
				pre.setInt(index, (Integer)(filter));
				break;
			case "java.lang.Long": 
				pre.setLong(index, (Long)(filter));
				break;
			case "java.lang.Float": 
				pre.setFloat(index, (Float)(filter));
				break;
			case "java.lang.Double": 
				pre.setDouble(index, (Double)(filter));
				break;
			case "[Ljava.lang.Byte;": 
				pre.setBytes(index, (byte[])(filter));
				break;
			case "java.sql.Date": 
				pre.setDate(index, (Date)(filter));
				break;
			case "java.sql.Time": 
				pre.setTime(index, (Time)(filter));
				break;
			case "java.sql.Timestamp": 
				pre.setTimestamp(index, (Timestamp)(filter));
				break;
			case "java.sql.Clob": 
				pre.setClob(index, (Clob)(filter));
				break;
			case "java.sql.Blob": 
				pre.setBlob(index, (Blob)(filter));
				break;
			case "java.sql.Array": 
				pre.setArray(index, (Array)(filter));
				break;
			case "java.sql.Ref": 
				pre.setRef(index, (Ref)(filter));
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
				tmp.setAccessible(true);
				if (tmp.getName().equalsIgnoreCase("id") || tmp.get(e) == null) {
					continue;
				}
				setParamFromObject(pre, tmp.getType().getName(), index, tmp, e);
				index++;
			}
			pre.executeUpdate();
			pre.close();
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static int update(Entity e, Connection conn) {
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
				setParamFromObject(pre, tmp.getType().getName(), index, tmp, e);
				index++;
			}
			pre.setLong(index, idValue); /** Set Id in Where Clause */
			int numUpdatedRows = pre.executeUpdate();
			pre.close();
			return numUpdatedRows;
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
	
	public static int delete(Entity e, Connection conn) {
		try {
			PreparedStatement pre = conn.prepareStatement(SqlString.deleteSql(e));
			int index = 1;
			Long idValue = -1l;
			for (int i = 0; i< e.getClass().getDeclaredFields().length; i++){
				Field tmp = e.getClass().getDeclaredFields()[i];
				tmp.setAccessible(true);
				if (tmp.getName().equalsIgnoreCase("id")) {
					if (tmp.get(e) != null) {
						idValue = (Long)tmp.get(e);
					}
					break;
				}
			}
			pre.setLong(index, idValue); /** Set Id in Where Clause */
			int numUpdatedRows =  pre.executeUpdate();
			pre.close();
			return numUpdatedRows;
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
	
	public static Entity findById(Class<?> clazz, Long id, Connection conn) {
		try {
			if (id != null) {
				Entity e = (Entity) clazz.newInstance();
				PreparedStatement pre = conn.prepareStatement(SqlString.findByIdSql(e));
				int index = 1;
				Long idValue = id;
				pre.setLong(index, idValue); /** Set Id in Where Clause */
				ResultSet rs = pre.executeQuery();
				List<?> lst = getListEntityFromResultSet(clazz, rs);
				rs.close();
				pre.close();
				if (lst.size() > 0) {
					return (Entity) lst.get(0);
				}
			}
		} catch (IllegalArgumentException | InstantiationException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static List select(Class<?> clazz, String sql, List<Object> filters, Connection conn) {
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			if (filters != null && filters.size() > 0) {
				int index = 1;
				for (int i=0, size = filters.size(); i< size; i++) {
					Object filter = filters.get(i);
					setParamFromFilter(pre, index, filter);
					index++;
				}
			}
			ResultSet rs = pre.executeQuery();
			List reval = null;
			if (EntityRelationTableMapping.tableNameMap.get(clazz.getName()) != null) {
				reval = (List)getListEntityFromResultSet(clazz, rs);
			} else {
				reval = (List)getListFromResultSet(clazz, rs);
			}
			rs.close();
			pre.close();
			return reval;
		} catch (IllegalArgumentException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Integer execute(String sql, List<Object> filters, Connection conn) {
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			if (filters != null && filters.size() > 0) {
				int index = 1;
				for (int i=0, size = filters.size(); i< size; i++) {
					Object filter = filters.get(i);
					setParamFromFilter(pre, index, filter);
					index++;
				}
			}
			int numUpdatedRows = pre.executeUpdate();
			pre.close();
			return numUpdatedRows;
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}