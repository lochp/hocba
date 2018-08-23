package properties;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import utils.StringUtils;

public class Properties {
	
	private static Properties instance;
	
	private Properties() {
		pushFile();
		parseProperties();
	}
	
	public static Properties getInstance() {
		if (instance == null) {
			instance = new Properties();
		}
		return instance;
	}
	
	private Map<String, String> properties = new HashMap<String, String>();
	
	private List<String> fileList = new ArrayList<String>();
	
	private void pushFile() {
		fileList.add("DbConfigInfo.properties");
	}
	
	private void parseProperties() {
		Scanner scanner;
		for(String fileName : fileList) {
			String line = null;
			InputStream  is = Properties.class.getResourceAsStream(fileName);
			scanner = new Scanner(is);
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
                String[] arr = line.split("=");
                if (!StringUtils.isNullOrEmpty(arr[0])) {
                	properties.put(arr[0], (StringUtils.isNullOrEmpty(arr[1]) ? "" : arr[1]));
                }
            }
		}
    }
	
	public String getProperty(String propertyName) {
		return (String) properties.get(propertyName);
	}

}
