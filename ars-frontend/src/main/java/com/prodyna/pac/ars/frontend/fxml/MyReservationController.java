/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.reservation.ReservationService;
import com.prodyna.pac.ars.reservation.model.Reservation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

/**
 *
 * @author aalbert
 */
public class MyReservationController extends AbstractCRUDController<Reservation> {
    
    private ReservationService reservationService;
    
    @FXML TableColumn<Reservation, String> aircraftColumn;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservationService = ServiceProxyFactory.createServiceProxy(ReservationService.class);
        entries.addAll(reservationService.readAllReservations());
        aircraftColumn.setCellValueFactory((TableColumn.CellDataFeatures<Reservation, String> data) -> {
            final Aircraft aircraft = data.getValue().getAircraft();
            return new ObservableValueBase<String>(){
                @Override
                public String getValue() {
                    return aircraft==null?"":aircraft.getName();
                }
            };  
        });
        entryTable.setItems(entries);

        initTableListener();
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
