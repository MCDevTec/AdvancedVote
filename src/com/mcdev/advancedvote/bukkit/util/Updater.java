package com.mcdev.advancedvote.bukkit.util;

import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

import com.mcdev.advancedvote.bukkit.BukkitPlugin;

import java.util.Optional;
 
/**
 * Clase para comprobar las actualizaciones a través de Github
 * @author MCDev
 */
public class Updater {

    private static String versionInstalada, versionMinecraft;
    private static BukkitPlugin plugin;
    private final String readurl = "https://raw.githubusercontent.com/MCDevTec/Vote40MC/master/etc/v.json"; //TODO Mantener ruta actualizada

    public Updater(BukkitPlugin instance, String vInstalada, String vMinecraft) {
        plugin = instance;
        versionInstalada = vInstalada;
        versionMinecraft = vMinecraft;
    }
    
    private final String ERROR = "&cError obteniendo la versi�n.";
    private final String DISABLED = "&cEl Updater ha sido remotamente desactivado debido a un mantenimiento";
    private final String UPDATED = "&aVersi�n actualizada";
    private final String NOVERSION = "&cNo se ha encontrado plugin para la versi�n de servidor que estas ejecutando";
    private final String NEWVERSION = "&cVersi�n desactualizada. &aNueva versi�n: %s. &6Changelog: %s. Descarga en: %s";

    /**
     * Comprobar si hay nueva versi�n
     * @param send Opcional. Jugador al que se avisar�
     * @param boot Si es al iniciar o no. Avisar� de que est� actualizado depende de eso
     */
    public void checkearVersion(CommandSender send, boolean boot) {
        plugin.debugLog("Buscando nueva versi�n...");
        Optional<CommandSender> sender = Optional.ofNullable(send);
        Util.readUrl(readurl, (ApiResponse response) -> {
            if (response.getException().isPresent()) {
                plugin.log(ERROR);
                sender.ifPresent(s -> plugin.sendMessage(ERROR, s));
                plugin.debugLog("Causa: " + response.getException().get().getMessage());
            }
            
            JSONObject jsonData = response.getResult();
            boolean online = (boolean) jsonData.get("online");
            if (!online) {
                if (boot) plugin.log(DISABLED);
                sender.ifPresent(s -> plugin.sendMessage(DISABLED, s));
            } else {
                if (jsonData.containsKey(versionMinecraft)) {
                    JSONObject array = (JSONObject) jsonData.get(versionMinecraft);
                    String ultimaVersion = "" + array.get("lastVersion");
                    if (versionInstalada.matches(ultimaVersion)) {
                        if (boot) plugin.log(UPDATED);
                        sender.ifPresent(s -> plugin.sendMessage(UPDATED, s));
                    } else {
                        String urlDescarga = (String) array.get("lastDownload");
                        String changelog = (String) array.get("cambiosBreves");
                        String versionParsed = String.format(NEWVERSION, ultimaVersion, changelog, urlDescarga);
                        if (boot) plugin.log(versionParsed);
                        sender.ifPresent(s -> plugin.sendMessage(versionParsed, s));
                    }
                } else {
                    if (boot) plugin.log(NOVERSION);
                    sender.ifPresent(s -> plugin.sendMessage(NOVERSION, s));
                }
            }
        });
    }

}
