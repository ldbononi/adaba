package ufu.facom.lia.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemConfigs {

	private Properties properties;

	private static SystemConfigs configs;
	
	public static void main(String[] args) { 
		System.out.println(SystemConfigs.getInstance().getConfig("master"));	
	}

	private SystemConfigs() {

		properties = new Properties();

		try {
			InputStream is = new FileInputStream(new File("E:\\UFU\\RespositorioAdaba\\executaveis\\configs.properties"));
			//InputStream is = new FileInputStream(new File("D:\\Doutorado\\execution-tests\\configs.properties"));
			//InputStream is = new FileInputStream(new File("D:\\Doutorado\\execution-tests\\matches\\adaba-x-aphid\\adaba\\configs.properties"));
			//InputStream is = ClassLoader.getSystemResourceAsStream("configs.properties");
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SystemConfigs getInstance() {
		if (configs == null) {
			configs = new SystemConfigs();
		}

		return configs;
	}

	public String getConfig(String key) {

		if (properties == null) {
			new SystemConfigs();
		}

		return properties.getProperty(key);
	}

}
