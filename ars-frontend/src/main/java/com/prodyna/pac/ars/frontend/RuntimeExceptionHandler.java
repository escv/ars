/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import org.controlsfx.control.Notifications;

/**
 *
 * @author aalbert
 */
public class RuntimeExceptionHandler implements Thread.UncaughtExceptionHandler {

    private ResourceBundle messages = ResourceBundle.getBundle("com.prodyna.pac.ars.frontend.bundles.Messages");
    
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        final Throwable cause = e.getCause();
        if (cause instanceof InvocationTargetException && ((InvocationTargetException)cause).getTargetException() instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException)((InvocationTargetException)cause).getTargetException();
            if (wae instanceof BadRequestException) {
                Notifications.create().title("Fehler").text(messages.getString("exception_badrequest")).showError();
            } else {
                Notifications.create().title("Fehler").text(messages.getString("exception_servererror")).showError();
            }
        }
    }
}