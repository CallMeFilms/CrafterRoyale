package us.projectrkade.CrafterRoyale;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{

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
				sndr.sendMessage(CRFront.prefix + ChatColor.RED + " You do not have the correct permission to use this command.");
				return true;
			}
			if(args.length < 1) {
//				Display substitute menu
				return true;
			}
			switch(args[0]) {
			case "admin":
				if(!sndr.hasPermission("cr.admin")) {
					sndr.sendMessage(CRFront.prefix + ChatColor.RED + " You do not have the correct permission to use this command.");
					return true;
				}
				if(args.length < 2) {
//					Display substitute menu
					return true;
				}
				switch(args[1]) {
				case "lobby":
					if(!sndr.hasPermission("cr.admin.lobby")) {
						sndr.sendMessage(CRFront.prefix + ChatColor.RED + " You do not have the correct permission to use this command.");
						return true;
					}
					if(args.length < 3) {
//						Display substitute menu
						return true;
					}
					switch(args[2]) {
					case "create":
						if(!sndr.hasPermission("cr.admin.lobby.create")) {
							sndr.sendMessage(CRFront.prefix + ChatColor.RED + " You do not have the correct permission to use this command.");
							return true;
						}
						if(args.length < 4) {
//							Display correct usage
							return true;
						}
//						/cr admin lobby create <name>
						String toCreate = args[3];
						if(setYAML.contains("lobbies." + toCreate)) {
							sndr.sendMessage(CRFront.prefix + ChatColor.DARK_RED + " Lobby " + ChatColor.RED + toCreate + ChatColor.DARK_RED + " already exists.");
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
						sndr.sendMessage(CRFront.prefix + ChatColor.DARK_RED + " Lobby " + ChatColor.RED + toCreate + ChatColor.DARK_RED + " created.");
						break;
					case "remove":
						if(!sndr.hasPermission("cr.admin.lobby.remove")) {
							sndr.sendMessage(CRFront.prefix + ChatColor.RED + " You do not have the correct permission to use this command.");
							return true;
						}
						if(args.length < 4) {
//							Display correct usage
							return true;
						}
						String toRemove = args[3];
						if(!setYAML.contains("lobbies." + toRemove)) {
							sndr.sendMessage(CRFront.prefix + ChatColor.DARK_RED + " Lobby " + ChatColor.RED + toRemove + ChatColor.DARK_RED + " does not exist.");
							return true;
						}
						setYAML.set("lobbies." + toRemove, null);
						try {
							setYAML.save(CRFront.settings);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
						sndr.sendMessage(CRFront.prefix + ChatColor.DARK_RED + " Lobby " + ChatColor.RED + toRemove + ChatColor.DARK_RED + " removed.");
						break;
					}
				}
			}
		}
		return true;
	}

}