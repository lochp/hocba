package test;

import java.lang.reflect.Field;

public class E {
	
	public E() {
		
	}
	
	protected String insertSql() throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		StringBuilder _r = new StringBuilder();
		_r.append("Insert Into ");
		_r.append(Property.tableNameMap.get(this.getClass().getName()));
		_r.append(" ( ");
		StringBuilder _fieldName = new StringBuilder();
		StringBuilder _fieldValue = new StringBuilder();
		for (int i = 0; i< this.getClass().getDeclaredFields().length; i++){
			Field tmp = this.getClass().getDeclaredFields()[i];
			tmp.setAccessible(true);
			_fieldName.append(Property.tableFieldNameMap.get(this.getClass().getName()).get(tmp.getName()));
			_fieldValue.append(tmp.get(this).toString());
			if (i < this.getClass().getDeclaredFields().length - 1){
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

}
