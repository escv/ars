/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.App;
import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.reservation.ReservationService;
import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.ReservationState;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 *
 * @author aalbert
 */
public class MyReservationFormController extends AbstractCRUDFormController<Reservation> {

    @FXML private ChoiceBox<Aircraft> aircraftChoice;
    @FXML private DatePicker beginPicker;
    @FXML private DatePicker endPicker;

    @Override
    public void init(Stage aDialog, AbstractCRUDController aController, Reservation reservation) {
        super.init(aDialog, aController, reservation);
        AircraftService acService = ServiceProxyFactory.createServiceProxy(AircraftService.class);
        ObservableList<Aircraft> acChoices = FXCollections.observableArrayList(acService.readAllAircrafts());
        this.aircraftChoice.setItems(acChoices);
        
        this.aircraftChoice.getSelectionModel().select(reservation.getAircraft());
        if (reservation.getBegin()!=null) {
            Instant instant = reservation.getBegin().toInstant();
            this.beginPicker.setValue(instant.atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (reservation.getEnd()!=null) {
            Instant instant = reservation.getEnd().toInstant();
            this.endPicker.setValue(instant.atZone(ZoneId.systemDefault()).toLocalDate());
        }
        this.deleteButton.setVisible(reservation.getId()>0);
    }
    
    @FXML protected void submitReservation(ActionEvent event) {
        ReservationService reservationService = ServiceProxyFactory.createServiceProxy(ReservationService.class);
        entry.setAircraft(this.aircraftChoice.getSelectionModel().getSelectedItem());
        
        Instant instant = Instant.from(this.beginPicker.getValue().atStartOfDay(ZoneId.systemDefault()));
        entry.setBegin(Date.from(instant));
        
        instant = Instant.from(this.endPicker.getValue().atStartOfDay(ZoneId.systemDefault()));
        entry.setEnd(Date.from(instant));
        
        entry.setState(ReservationState.RESERVED);
        entry.setUser(App.PRINCIPAL);
        
        if (!(entry.getId()>0)) {
            Reservation savedReservation = reservationService.createReservation(entry);
            this.controller.addEntry(savedReservation);
        } else {
            reservationService.updateReservation(entry);
            this.controller.refreshTable();
        }
        dialog.close();
    }
    
    @FXML protected void removeReservation(ActionEvent event) {
        ReservationService actService = ServiceProxyFactory.createServiceProxy(ReservationService.class);
        actService.cancelReservation(entry.getId());
        this.controller.removeEntry(entry);
        dialog.close();
    }
}
