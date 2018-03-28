package us.projectrkade.CrafterRoyale;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sndr, Command cmd, String label, String[] args) {
		if(!(sndr instanceof Player)) {
			sndr.sendMessage("Command can not be executed by console personnel. Please try again in-game.");
			return true;
		}
		Player player = (Player) sndr;
		YamlConfiguration setYAML = YamlConfiguration.loadConfiguration(CRFront.settings);
		switch(cmd.getName().toLowerCase()) {
		case "cr":
			if(!sndr.hasPermission("cr.base")) {
				sndr.sendMessage(CRFront.prefix + ChatColor.YELLOW + " You do not have the correct permission to use this command.");
				return true;
			}
			if(args.length < 1) {
				sndr.sendMessage(ChatColor.YELLOW + "-------" + CRFront.prefix + ChatColor.YELLOW + "-------");
				sndr.sendMessage(ChatColor.GOLD + "/cr help: " + ChatColor.YELLOW + "Displays this menu.");
				sndr.sendMessage(ChatColor.GOLD + "/cr admin: " + ChatColor.YELLOW + "Used for all CrafterRoyale in-game administration.");
				return true;
			}
			switch(args[0]) {
			case "admin":
				if(!sndr.hasPermission("cr.admin")) {
					sndr.sendMessage(CRFront.prefix + ChatColor.YELLOW + " You do not have the correct permission to use this command.");
					return true;
				}
				if(args.length < 2) {
					sndr.sendMessage(ChatColor.YELLOW + "-------" + CRFront.prefix + ChatColor.YELLOW + "-------");
					sndr.sendMessage(ChatColor.GOLD + "/cr admin lobby: " + ChatColor.YELLOW + "Used for managing lobbies and their settings.");
					return true;
				}
				break;
			case "lobby":
				if(args.length < 2) {
					sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Correct usage: " + ChatColor.YELLOW + "/lobby <create:remove> <lobbyname>");
					return true;
				}
				switch(args[1]) {
				case "create":
					if(args.length < 3) {
						sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Correct usage: " + ChatColor.YELLOW + "/lobby create <name>");
						return true;
					}
					String toCreate = args[2];
					if(setYAML.contains("lobbies." + toCreate)) {
						sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Lobby " + ChatColor.YELLOW + toCreate + ChatColor.GOLD + " already exists.");
						return true;
					}
					setYAML.createSection("lobbies." + toCreate);
					setYAML.set("lobbies." + toCreate + ".world", player.getWorld().getName());
					setYAML.set("lobbies." + toCreate + ".location.x", player.getLocation().getX());
					setYAML.set("lobbies." + toCreate + ".location.y", player.getLocation().getY());
					setYAML.set("lobbies." + toCreate + ".location.z", player.getLocation().getZ());
					setYAML.set("lobbies." + toCreate + ".location.yaw", player.getLocation().getYaw());
					setYAML.set("lobbies." + toCreate + ".location.pitch", player.getLocation().getPitch());
					try {
						setYAML.save(CRFront.settings);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Lobby " + ChatColor.YELLOW + toCreate + ChatColor.GOLD + " created.");
					break;
				case "remove":
					if(args.length < 3) {
						sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Correct usage: " + ChatColor.YELLOW + "/lobby remove <name>");
						return true;
					}
					String toRemove = args[2];
					if(!setYAML.contains("lobbies." + toRemove)) {
						sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Lobby " + ChatColor.YELLOW + toRemove + ChatColor.GOLD + " does not exist.");
						return true;
					}
					setYAML.set("lobbies." + toRemove, null);
					try {
						setYAML.save(CRFront.settings);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Lobby " + ChatColor.YELLOW + toRemove + ChatColor.GOLD + " removed.");
					break;
				default:
					sndr.sendMessage(CRFront.prefix + ChatColor.GOLD + " Correct usage: " + ChatColor.YELLOW + "/lobby remove <name>");
					break;
				}
				break;
			default:
				sndr.sendMessage(ChatColor.YELLOW + "-------" + CRFront.prefix + ChatColor.YELLOW + "-------");
				sndr.sendMessage(ChatColor.GOLD + "/cr help: " + ChatColor.YELLOW + "Displays this menu.");
				sndr.sendMessage(ChatColor.GOLD + "/cr lobby <create:remove> <lobbyname>: " + ChatColor.YELLOW + "Create or remove a lobby.");
				break;
			}
			break;
		}
		return true;
	}

}