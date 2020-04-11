package com.mcdev.advancedvote.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.mcdev.advancedvote.bukkit.util.Cooldown;
import com.mcdev.advancedvote.bukkit.util.ApiResponse;
import com.mcdev.advancedvote.bukkit.util.Updater;
import com.mcdev.advancedvote.bukkit.util.Util;
import java.io.File;
import java.util.List;
import java.util.logging.Level;

/**
 * Implementación para Bukkit, Spigot y Glowstone
 * @author MCDev
 */
public class BukkitPlugin extends JavaPlugin {
    private final Util met = new Util(this);
    private Updater updater;
    public String prefix;
    public String comando;
    public String mensaje;
    public String tag;
    public String espera;
    public String noexiste;
    public String nopermiso;
    public String obteniendo;
    public String novotaste;
    public String broadcast;
    public String error;
    private int configVer = 0;
    private final int configActual = 3;
    public List<String> listaComandos;
    private static BukkitPlugin instance;
    public static BukkitPlugin get() {
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;
        debugLog("Modo Debug activado en el plugin");
        
        /*
         * Generar y cargar Config.yml
         */
        debugLog("Cargando configuración...");
        cargarConfig();
        /*
         * Cargar mensajes custom
         */
        debugLog("Cargando mensajes custom...");
        cargarMensajes();
        updater = new Updater(this, getPluginVersion(), getServer().getBukkitVersion().split("-")[0]);
        debugLog("Checkeando nuevas versiones...");
        updater.checkearVersion(null, true);
        log("Plugin 40ServidoresMC v" + getPluginVersion() + " cargado completamente");
    }
    private void cargarConfig() {
        File file = new File(getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            try {
                getConfig().options().copyDefaults(true);
                saveConfig();
                log("Generando archivo config.yml correctamente");
            } catch (Exception e) {
                this.getLogger().info("Fallo al generar el config.yml!");
                debugLog("Causa: " + e.toString());
            }
        }
        configVer = this.getConfig().getInt("configVer", configVer);
        if (configVer < configActual) {
            log(Level.SEVERE, "Tu configuración es de una versión más antigua a la de este plugin!"
                + "Corrígelo o podrás tener errores..." );
        }
         cargarMensajes();      
    }
    /**
     * Cargar mensajes custom
     */
    public void cargarMensajes() {
    	tag = getConfig().getString("prefix");
    	mensaje = getConfig().getString("mensajePremio");
    	comando = getConfig().getString("comando");
    	espera = getConfig().getString("espera");
    	noexiste = getConfig().getString("noexiste");
    	nopermiso = getConfig().getString("nopermiso");
    	obteniendo = getConfig().getString("obteniendo");
    	novotaste = getConfig().getString("novotaste");
    	broadcast = getConfig().getString("broadcast.mensajeBroadcast");
    	error = getConfig().getString("error");
    }
    
    

    
    public boolean isDebug() {
        return this.getConfig().getBoolean("debug");
    }
    
    public void debugLog(String s) {
        if (isDebug()){
            getLogger().log(Level.INFO, "[Debug] {0}", s);
        }
    }
    
    public void log(String s) {
        getLogger().log(Level.INFO, s);
    }
    
    public void log(Level l, String s){
       getLogger().log(l, s);
    }
    
    public void sendMessage(String str, CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', tag + " " + str));
    }
    
    public Util getMetodos() {
        return this.met;
    }
    
    public String getPluginVersion(){
        return this.getDescription().getVersion();
    }
    
    public String getTag() {
        return this.tag;     
    }
    
    public Updater getUpdater() {
        return this.updater;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("advancedvote")) {
			Player p = (Player)sender;
			if(p.hasPermission("adv.use")) {
			if (args.length == 0) {
			        	p.sendMessage("");
			        	p.sendMessage("");
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lAdvancedVote &fAyuda de comandos"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVersion: &fv2.5"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDesarrollador: &fMCDevTec"));
			            p.sendMessage("");
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote recarga"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Recarga el plugin AdvancedVote"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote estadistica"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Estadisticas de tu servidor en 40ServidoresMC"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote prueba"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Prueba el premio previamente configurado en config.yml"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote actualizacion"));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Ve si existe una nueva version del plugin"));
			            p.sendMessage("");
			            p.sendMessage("");
			            return true;
			}
			else {
				if (args[0].equalsIgnoreCase("help")) {
		        	p.sendMessage("");
		        	p.sendMessage("");
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lAdvancedVote &fAyuda de comandos"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVersion: &fv2.5"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDesarrollador: &fMCDevTec"));
		            p.sendMessage("");
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote recarga"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Recarga el plugin AdvancedVote"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote estadistica"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Estadisticas de tu servidor en 40ServidoresMC"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote prueba"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Prueba el premio previamente configurado en config.yml"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote actualizacion"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Ve si existe una nueva version del plugin"));
		            p.sendMessage("");
		            p.sendMessage("");
		            return true;
				}
				if (args[0].equalsIgnoreCase("ayuda")) {
		        	p.sendMessage("");
		        	p.sendMessage("");
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lAdvancedVote &fAyuda de comandos"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVersion: &fv2.5"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDesarrollador: &fMCDevTec"));
		            p.sendMessage("");
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote recarga"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Recarga el plugin AdvancedVote"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote estadistica"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Estadisticas de tu servidor en 40ServidoresMC"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote prueba"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Prueba el premio previamente configurado en config.yml"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/advancedvote actualizacion"));
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &7Ve si existe una nueva version del plugin"));
		            p.sendMessage("");
		            p.sendMessage("");
		            return true;
				}
				if (args[0].equalsIgnoreCase("recarga")) {
					BukkitPlugin.get().reloadConfig();
					BukkitPlugin.get().cargarMensajes();
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" &aConfiguracion recargada correctamente."));
					return true;
				}
				if (args[0].equalsIgnoreCase("prueba")) {
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" &bPrueba de premio y/o mensaje a la hora de obtener premio."));
					String mensajecmd = mensaje.replace("{0}", sender.getName());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+mensajecmd));
					
			        String comandocmd = comando.replace("{0}", sender.getName());
			        Bukkit.dispatchCommand(console, comandocmd);
					return true;
				}
				if (args[0].equalsIgnoreCase("estadistica")) {
			        Util.readUrl("https://40servidoresmc.es/api2.php?estadisticas=1&clave=" + getConfig().getString("clave"), (ApiResponse response) -> {
			            if (response.getException().isPresent()) {
			                p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" &cHa ocurrido una excepción. Revisa la consola o avisa a un administrador"));
			                log(Level.SEVERE, "Excepción obteniendo estadisticas: " + response.getException().get().getMessage());
			                return;
			            }
			            
			            JSONObject jsonData = response.getResult();
			            if (jsonData.get("nombre") == null) { //clave mal configurada
			                p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" &cClave incorrecta. Entra en &bhttps://40servidoresmc.es/miservidor.php &cy cambia esta en config.yml."));
			                return;
			            }

			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9==> &7" + jsonData.get("nombre") + " &festá en el TOP &a" + jsonData.get("puesto")));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bVotos hoy: &6" + jsonData.get("votoshoy")));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bVotos premiados hoy: &6" + jsonData.get("votoshoypremiados")));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bVotos semanales: &6" + jsonData.get("votossemanales")));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bVotos premiados semanales: &6" + jsonData.get("votossemanalespremiados")));
			            JSONArray array = (JSONArray) jsonData.get("ultimos20votos");
			            StringBuilder usuarios = new StringBuilder();
			            for (Object obj : array) {
			                JSONObject object = (JSONObject) obj;
			                String strellita = (Integer.parseInt((String) object.get("recompensado")) == 1) ? "&a" : "&c";
			                usuarios.append(strellita).append(object.get("usuario")).append("&6, ");
			            }
			            usuarios = new StringBuilder(usuarios.substring(0, usuarios.length() - 2) + ".");
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bÚltimos 20 votos: " + usuarios));
			        });
			        return true;
				}
				if (args[0].equalsIgnoreCase("actualizacion")) {
					getUpdater().checkearVersion(sender, false);
					return true;
				}
				if (args[0].equalsIgnoreCase("votar")) {
					final Cooldown cooldown = new Cooldown(15);
					if (cooldown.isCoolingDown(sender)) {
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', espera));
			        return true;
			        }
					cooldown.setOnCooldown(sender);
			        p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+obteniendo));
			        Util.readUrl("https://40servidoresmc.es/api2.php?nombre=" + sender.getName() + "&clave=" + getConfig().getString("clave"), (ApiResponse response) -> {
			            if (response.getException().isPresent()) {
			                p.sendMessage(ChatColor.translateAlternateColorCodes('&',tag+" &cHa ocurrido una excepción. Avisa a un administrador"));
			                log(Level.SEVERE, "Excepción intentando votar: " + response.getException().get());
			                return;
			            }

			            JSONObject jsonData = response.getResult();
			            String web = (String) jsonData.get("web");
			            int status = (int) ((long) jsonData.get("status"));
			            
			            switch (status) {
			                case 0:
			                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',tag+" "+novotaste+web));
			                    break;
			                case 1:
			                	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
								String mensajecmd = mensaje.replace("{0}", sender.getName());
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+mensajecmd));
								
						        String comandocmd = comando.replace("{0}", sender.getName());
						        Bukkit.dispatchCommand(console, comandocmd);

			                    if (getConfig().getBoolean("broadcast.activado")) {
			                        Bukkit.getServer().broadcastMessage(tag+" "+broadcast.replace("{0}", sender.getName()));
			                    }
			                    break;
			                case 2:
			                    p.sendMessage(getConfig().getString("yapremiado"));
			                    break;
			                case 3:
			                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" &cClave incorrecta. Entra en &bhttps://40servidoresmc.es/miservidor.php &cy cambia esta en config.yml."));
			                    break;
			                default:
			                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+error));
			                    break;
			            }
			        });
			        return true;
				}
				else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+noexiste));
				}

			}
		}
			else {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+nopermiso));
				return true;
			}
			
    }
    	if(cmd.getName().equalsIgnoreCase("vote40")) {
    		Player p = (Player)sender;
    		if(p.hasPermission("adv.vote")) {
			final Cooldown cooldown = new Cooldown(15);
			if (cooldown.isCoolingDown(sender)) {
	            p.sendMessage(ChatColor.translateAlternateColorCodes('&', espera));
	        return true;
	        }
			cooldown.setOnCooldown(sender);
	        p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+obteniendo));
	        Util.readUrl("https://40servidoresmc.es/api2.php?nombre=" + sender.getName() + "&clave=" + getConfig().getString("clave"), (ApiResponse response) -> {
	            if (response.getException().isPresent()) {
	                p.sendMessage(ChatColor.translateAlternateColorCodes('&',tag+" &cHa ocurrido una excepción. Avisa a un administrador"));
	                log(Level.SEVERE, "Excepción intentando votar: " + response.getException().get());
	                return;
	            }

	            JSONObject jsonData = response.getResult();
	            String web = (String) jsonData.get("web");
	            int status = (int) ((long) jsonData.get("status"));
	            
	            switch (status) {
	                case 0:
	                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',tag+" "+novotaste+web));
	                    break;
	                case 1:
	                	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						String mensajecmd = mensaje.replace("{0}", sender.getName());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+mensajecmd));
						
				        String comandocmd = comando.replace("{0}", sender.getName());
				        Bukkit.dispatchCommand(console, comandocmd);

	                    if (getConfig().getBoolean("broadcast.activado")) {
	                        Bukkit.getServer().broadcastMessage(tag+" "+broadcast.replace("{0}", sender.getName()));
	                    }
	                    break;
	                case 2:
	                    p.sendMessage(getConfig().getString("yapremiado"));
	                    break;
	                case 3:
	                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" &cClave incorrecta. Entra en &bhttps://40servidoresmc.es/miservidor.php &cy cambia esta en config.yml."));
	                    break;
	                default:
	                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', tag+" "+error));
	                    break;
	            }
	        });
	        return true;
    		}
    	}
    	return false;
    }
}
