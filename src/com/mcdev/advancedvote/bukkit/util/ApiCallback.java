package com.mcdev.advancedvote.bukkit.util;

/**
 * Callback
 * @author MCDev
 */
@FunctionalInterface
public interface ApiCallback {
    
    /**
     * Código a ejecutar cuando se realice el callback
     * @param res {@link ApiResponse} de la API
     */
    void done(ApiResponse res);
} 