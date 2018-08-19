package test;

public class UsingEntity {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		Entity E = new Entity("Test", 1);
		System.out.println(E.insertSql());
	}

}
