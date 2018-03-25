package us.projectrkade.CrafterRoyale;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CRFront extends JavaPlugin {
	
	public static File plugDir;
	public static File config;
	
//	onEnable() method invoke on plugin enable
	public void onEnable() {
//		Set static plugDir object to the "CrafterRoyale" plugin's data folder file
		plugDir = Bukkit.getServer().getPluginManager().getPlugin("CrafterRoyale").getDataFolder();
//		Check to see if a file or directory matching the plugDir File object path exists
		if(!plugDir.exists()) {
//			Create a directory out of the File object
			plugDir.mkdir();
		}
//		Set the config File object to a new file called "config.yml" inside of the plugin directory
		config = new File(plugDir.getPath() + File.separator + "config.yml");
//		Check to see if a file or directory matching the config File object path exists
		if(!config.exists()) {
//			Try to create a new file out of the new config File object
			try {
				config.createNewFile();
			} catch (IOException e) {
//				Print error message to console
				System.out.println(e.getMessage());
			}
		}
//		Create a new YamlConfiguration object by loading a configuration of the config file
		YamlConfiguration conYAML = YamlConfiguration.loadConfiguration(config);
//		Try to save the new conYAML configuration into the config file
		try {
			conYAML.save(config);
		} catch (IOException e) {
//			Print error message to console
			System.out.println(e.getMessage());
		}
	}
	
}
