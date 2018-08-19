package databaseControl;

import java.util.HashMap;
import java.util.Map;

import entity.TestTable;

public class EntityRelationTableMapping {

	public static Map<String, String> tableNameMap;
	
	public static Map<String, Map<String, String>> tableFieldNameMap;
	
	static {
		tableNameMap = new HashMap<String, String>();
		tableFieldNameMap = new HashMap<String, Map<String, String>>();
	}
	
	/** Table Name Mapping */
	static {
		tableNameMap.put(Entity.class.getName(), "entity");
		tableNameMap.put(TestTable.class.getName(), "testTable");
	}
	
	/** Entity Field Mapping */
	static {
		Map<String, String> entityMap = new HashMap<String, String>();
		tableFieldNameMap.put(Entity.class.getName(), entityMap);
	}
	
	static {
		Map<String, String> testTableMap = new HashMap<String, String>();
		testTableMap.put("harbor", "Cang");
		testTableMap.put("vase", "LoHoa");
		testTableMap.put("paddle", "MaiCheo");
		tableFieldNameMap.put(TestTable.class.getName(), testTableMap);
	}
	
}
