package us.projectrkade.CrafterRoyale;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CRFront extends JavaPlugin {
	
	public static String prefix;
	public static File plugDir;
	public static File config;
	public static File settings;
	
//	onEnable() method invoke on plugin enable
	public void onEnable() {
//		Set prefix String to correct plugin prefix
		prefix = "" + ChatColor.GRAY + "[" + ChatColor.YELLOW + "Crafter" + ChatColor.GREEN + "Royale" + ChatColor.GRAY + "]" + ChatColor.RESET + "";
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
//			Create a new YamlConfiguration object by loading a configuration of the config file
			YamlConfiguration conYAML = YamlConfiguration.loadConfiguration(config);
//			Try to save the new conYAML configuration into the config file
			try {
				conYAML.save(config);
			} catch (IOException e) {
//				Print error message to console
				System.out.println(e.getMessage());
			}
		}
//		Set the settingsFile object to a new file called "settings.yml" inside of the plugin directory
		settings = new File(plugDir.getPath() + File.separator + "settings.yml");
//		Check to see if a file or directory matching the settings File object path exists
		if(!settings.exists()) {
//			Try to create a new file out of the new settings File object
			try {
				settings.createNewFile();
			} catch (IOException e) {
//				Print error message to console
				System.out.println(e.getMessage());
			}
//			Create a new YamlConfiguration object by loading a configuration of the settings file
			YamlConfiguration setYAML = YamlConfiguration.loadConfiguration(config);
//			Check to see if conYAML contains a "lobbies" section
			if(!setYAML.contains("lobbies")) {
//				Create a "lobbies" section inside of conYAML
				setYAML.createSection("lobbies");
			}
//			Try to save the new setYAML configuration into the config file
			try {
				setYAML.save(settings);
			} catch (IOException e) {
//				Print error message to console
				System.out.println(e.getMessage());
			}
		}
		Bukkit.getServer().getPluginCommand("cr").setExecutor(new Commands());
	}
	
}