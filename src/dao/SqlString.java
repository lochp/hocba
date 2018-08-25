package dao;

import java.lang.reflect.Field;

import databaseControl.Entity;
import databaseControl.EntityRelationTableMapping;

public class SqlString {

	public static String insertSql(Entity e) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder reval= new StringBuilder();
		reval.append("INSERT INTO ");
		reval.append(EntityRelationTableMapping.tableNameMap.get(e.getClass().getName()));
		reval.append(" ( ");
		StringBuilder _fieldName = new StringBuilder();
		for (int i = 0; i< e.getClass().getDeclaredFields().length; i++){
			Field tmp = e.getClass().getDeclaredFields()[i];
			if (tmp.getName().equalsIgnoreCase("id")) {
				continue;
			}
			tmp.setAccessible(true);
			_fieldName.append(EntityRelationTableMapping.tableFieldNameMap.get(e.getClass().getName()).get(tmp.getName()));
			if (i < e.getClass().getDeclaredFields().length - 1){
				_fieldName.append(", ");
			}
			tmp.setAccessible(false);
		}
		reval.append(_fieldName.toString());
		reval.append(" ) VALUES ( ");
		for(int i=0, size = e.getClass().getDeclaredFields().length; i< size; i++) {
			Field tmp = e.getClass().getDeclaredFields()[i];
			if (tmp.getName().equalsIgnoreCase("id")) {
				continue;
			}
			reval.append(i == size - 1? "? ": "?, ");
		}
		reval.append(")");
		return reval.toString();
	}
	
	public static String updateSql(Entity e) {
		StringBuilder reval= new StringBuilder();
		reval.append("UPDATE ");
		reval.append(EntityRelationTableMapping.tableNameMap.get(e.getClass().getName()));
		reval.append(" SET ");
		StringBuilder _fieldName = new StringBuilder();
		for (int i = 0; i< e.getClass().getDeclaredFields().length; i++){
			Field tmp = e.getClass().getDeclaredFields()[i];
			if (tmp.getName().equalsIgnoreCase("id")) {
				continue;
			}
			tmp.setAccessible(true);
			_fieldName.append(EntityRelationTableMapping.tableFieldNameMap.get(e.getClass().getName()).get(tmp.getName()));
			_fieldName.append(" = ?");
			if (i < e.getClass().getDeclaredFields().length - 1){
				_fieldName.append(", ");
			}
			tmp.setAccessible(false);
		}
		reval.append(_fieldName.toString());
		reval.append(" WHERE id = ?");
		return reval.toString();
	}
	
	public static String deleteSql(Entity e) {
		StringBuilder reval = new StringBuilder();
		reval.append("DELETE FROM ");
		reval.append(EntityRelationTableMapping.tableNameMap.get(e.getClass().getName()));
		reval.append(" WHERE id = ? ");
		return reval.toString();
	}
	
	public static String findByIdSql(Entity e) {
		StringBuilder reval = new StringBuilder();
		reval.append("SELECT * ");
		reval.append("FROM ");
		reval.append(EntityRelationTableMapping.tableNameMap.get(e.getClass().getName()));
		reval.append(" WHERE id = ? ");
		return reval.toString();
	}
	
}
