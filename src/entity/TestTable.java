package entity;

import databaseControl.Entity;
import lombok.Data;

@Data
public class TestTable extends Entity {

	private Long id;
	
	private String harbor;
	
	private String vase;
	
	private String paddle;
	
}
