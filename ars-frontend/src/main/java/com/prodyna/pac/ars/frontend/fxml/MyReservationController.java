/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.App;
import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.reservation.ReservationService;
import com.prodyna.pac.ars.reservation.model.Reservation;

import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;

/**
 *
 * @author aalbert
 */
public class MyReservationController extends AbstractCRUDController<Reservation> {
    
    private ReservationService reservationService;
    
    @FXML private Agenda calendar;
    
    @FXML TableColumn<Reservation, String> aircraftColumn;
  
    private Agenda.AppointmentGroup appGroup;
    
    private Map<String, Reservation> localCache = new HashMap<String, Reservation>();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        reservationService = ServiceProxyFactory.createServiceProxy(ReservationService.class);
        this.appGroup = new Agenda.AppointmentGroupImpl().withStyleClass("group0");
        reload(null);

        calendar.setCreateAppointmentCallback((Callback<Agenda.CalendarRange, Agenda.Appointment>) (Agenda.CalendarRange param) -> {
            Reservation r = new Reservation();
            
            r.setBegin(param.getStartCalendar().getTime());
            r.setEnd(param.getEndCalendar().getTime());
            showForm(r, getFXMLForm());
            return null;
        });

        calendar.setEditAppointmentCallback((Agenda.Appointment param) -> {
            final Reservation resv = localCache.get(param.getDescription());
            showForm(resv, getFXMLForm());
            return null;
        });
    }

    @Override
    public void refreshTable() {
        this.reload(null);
    }

    @Override
    public void removeEntry(Reservation at) {
        Agenda.Appointment toRemove = null;
        for (Agenda.Appointment appmt : calendar.appointments()) {
            if (String.valueOf(at.getId()).equals(appmt.getDescription())) {
                toRemove = appmt;
                break;
            }
        }
        calendar.appointments().remove(toRemove);
    }

    @Override
    public void reload(ActionEvent event) {
        calendar.appointments().clear();
        localCache.clear();
        for (Reservation resv : reservationService.readAllReservationsForUser(App.PRINCIPAL.getName())) {
            localCache.put(String.valueOf(resv.getId()), resv);
            Calendar begin = Calendar.getInstance();
            begin.setTime(resv.getBegin());
            Calendar end = Calendar.getInstance();
            end.setTime(resv.getEnd());
            calendar.appointments().add(new Agenda.AppointmentImpl()
                    .withStartTime(begin)
                    .withEndTime(end)
                    .withAppointmentGroup(appGroup)
                    .withLocation("FRA")
                    .withSummary(resv.getAircraft().getName())
                    .withDescription(String.valueOf(resv.getId()))
            );
        }
        entries.addAll(reservationService.readAllReservations());
    }

    @Override
    public void addEntry(Reservation r) {
        Calendar begin = Calendar.getInstance();
            begin.setTime(r.getBegin());
            Calendar end = Calendar.getInstance();
            end.setTime(r.getEnd());
        final Agenda.AppointmentImpl appointment = new Agenda.AppointmentImpl()
                .withStartTime(begin)
                .withEndTime(end)
                .withAppointmentGroup(appGroup)
                .withLocation("FRA")
                .withSummary(r.getAircraft().getName())
                .withDescription(String.valueOf(r.getId()));
        calendar.appointments().add(appointment);
        localCache.put(String.valueOf(r.getId()), r);
    }
    
    @FXML
    public void showCreateForm(ActionEvent event) {
        showForm(new Reservation(), getFXMLForm());
    }

    @Override
    protected String getFXMLForm() {
        return "fxml/MyReservationForm.fxml";
    }
 
}
