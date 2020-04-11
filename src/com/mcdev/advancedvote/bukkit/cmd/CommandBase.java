package com.mcdev.advancedvote.bukkit.cmd;

import com.mcdev.advancedvote.bukkit.BukkitPlugin;
import com.mcdev.advancedvote.bukkit.util.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Framework de comandos de Meriland.es
 * @author MCDev
 */
public abstract class CommandBase {
    
    private final transient String name;
    private final transient String perm;
    private final transient List<String> aliases;
    protected static final transient BukkitPlugin plugin = BukkitPlugin.get();
    protected static final transient Util metodos = BukkitPlugin.get().getMetodos();
      
    protected CommandBase(final String name, final String perm, final List<String> aliases) {
        this.name = name;
        this.perm = perm;
        this.aliases = aliases;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPermission() {
        return perm;
    }

    public List<String> getAliases() {
        return aliases;
    }
    
    public void run(ConsoleCommandSender sender, String label, String[] args) {
        run((CommandSender) sender, label, args);
    }
    
    public void run(CommandSender sender, String label, String[] args) {
        
    }
    
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return new ArrayList<>();
    }
    
    protected static boolean perm(CommandSender p, String perm, Boolean message) {
        if (!(p instanceof Player)) {
            return true;
        }
        Player pl = (Player) p;
        boolean hasperm = perm(pl, perm);
        if (!hasperm && message) {
            plugin.sendMessage(plugin.getConfig().getString("nopermiso"), pl);
        }
        return hasperm;
    }
    
    private static boolean perm(Player p, String perm) {
        if (p.isOp()) {
            return true;
        }
        return p.hasPermission(perm);    
    }
    
    protected static boolean soloJugador(CommandSender p, Boolean message) {
        if (!(p instanceof Player)) {
            if (message) {
                plugin.sendMessage("&cNo puedes usar este comando si no eres un jugador", p); 
            }
            return false;
        }
        return true;
    }
}
