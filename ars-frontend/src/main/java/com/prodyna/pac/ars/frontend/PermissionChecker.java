/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend;

import com.prodyna.pac.ars.masterdata.model.UserRole;

/**
 *
 * @author aalbert
 */
public class PermissionChecker {
    
    private static PermissionChecker instance;
    
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }
    
    public boolean isGuest() {
        return true;
    }
    
    public boolean isPilot() {
        return hasRole("PILOT");
    }
    
    public boolean isLoggedIn() {
        return App.PRINCIPAL!=null;
    }
    
    private boolean hasRole(String roleName) {
        if (App.PRINCIPAL==null) {
            return false;
        }
        for (UserRole role : App.PRINCIPAL.getRoles()) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
    
    public static synchronized PermissionChecker getInstance() {
        if (instance==null) {
            instance = new PermissionChecker();
        }
        return instance;
    }
}
