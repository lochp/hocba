package databaseControl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class DaoSupper {
	
	protected DbConnection dbConnection = DbConnection.getSingleInstance();
	
	protected String insertSqlString(Entity e) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		StringBuilder _r = new StringBuilder();
		_r.append("Insert Into ");
		_r.append(EntityRelationTableMapping.tableNameMap.get(e.getClass().getName()));
		_r.append(" ( ");
		StringBuilder _fieldName = new StringBuilder();
		StringBuilder _fieldValue = new StringBuilder();
		for (int i = 0; i< e.getClass().getDeclaredFields().length; i++){
			Field tmp = e.getClass().getDeclaredFields()[i];
			tmp.setAccessible(true);
			_fieldName.append(EntityRelationTableMapping.tableFieldNameMap.get(e.getClass().getName()).get(tmp.getName()));
			_fieldValue.append(tmp.get(e).toString());
			if (i < e.getClass().getDeclaredFields().length - 1){
				_fieldName.append(", ");
				_fieldValue.append(", ");
			}
		}
		_r.append(_fieldName.toString());
		_r.append(")");
		_r.append(" Values( ");
		_r.append(_fieldValue.toString());
		_r.append(") ");
		return _r.toString();
	}
	
	protected String updateSqlString(Entity e) {
		StringBuilder _r = new StringBuilder();
		return _r.toString();
	}
	
	protected String deleteSqlString(Entity e) {
		StringBuilder _r = new StringBuilder();
		return _r.toString();
	}
	
	protected String findByIdString(Entity e) {
		StringBuilder _r = new StringBuilder();
		return _r.toString();
	}
	
	private void excuteUpdateSql(String sql, List<Object> filter) throws ClassNotFoundException, SQLException {
		Connection conn = dbConnection.getConnection();
		if (conn != null) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (filter != null) {
				for (int i = 0; i< filter.size(); i++) {
					Object _o = filter.get(i);
					switch (_o.getClass().getName()) {
					case Long.class.getName():
						pstmt.setLong(i, (long) _o);
						break;
					case Integer.class.getName():
						pstmt.setInt(i, (int) _o);
					case String.class.getName():
						break;
					}
				}
				
			}
			pstmt.executeUpdate(sql);
			if (pstmt != null) {
				pstmt.close();
				conn.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	private ResultSet executeQuerySql(String sql) throws ClassNotFoundException, SQLException {
		Connection conn = dbConnection.getConnection();
		if (conn != null) {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (stm != null) {
				stm.close();
				conn.close();
			}
			if (conn != null) {
				conn.close();
			}
			return rs;
		}
		return null;
	}
	
	protected Entity getEntityById(Class clazz, Long Id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Entity _e = null;
		String sql = findByIdString(_e);
		ResultSet rs = this.executeQuerySql(sql);
		if (rs.next()) {
			_e = (Entity) clazz.newInstance();
			for (int i = 0; i< _e.getClass().getDeclaredFields().length; i++) {
				Field field = _e.getClass().getDeclaredFields()[i];
				field.setAccessible(true);
				Object value = rs.getObject(EntityRelationTableMapping.tableFieldNameMap.get(_e.getClass().getName()).get(field.getName()));
				field.set(_e, value);
			}
		}
		return _e;
	}
	
	protected void createEntity(Entity e) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		String sql = insertSqlString(e);
		excuteUpdateSql(sql);
	}
	
	protected void updateEntity(Entity e) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		String sql = updateSqlString(e);
		excuteUpdateSql(sql);
	}
	
	protected void deleteEntity(Entity e) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		String sql = deleteSqlString(e);
		excuteUpdateSql(sql);
	}

}
