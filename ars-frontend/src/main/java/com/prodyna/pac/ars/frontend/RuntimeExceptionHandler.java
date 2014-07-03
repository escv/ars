/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import org.controlsfx.control.Notifications;

/**
 *
 * @author aalbert
 */
public class RuntimeExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        final Throwable cause = e.getCause();
        if (cause instanceof InvocationTargetException && ((InvocationTargetException)cause).getTargetException() instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException)((InvocationTargetException)cause).getTargetException();
            if (wae instanceof BadRequestException) {
                Notifications.create().title("Fehler").text("Die eingegeben Daten können vom Server nicht akzeptiert werden").showError();
            } else {
                Notifications.create().title("Fehler").text("Der Server konnte die aktuelle Operation nicht erfolgreich durchführen").showError();
            }
        }
    }
}