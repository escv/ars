/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author aalbert
 */
public class AircraftFormController extends AbstractCRUDFormController<Aircraft> {

    @FXML private TextField nameField;
    
    @FXML private ChoiceBox<AircraftType> actChoice;
    
    @FXML private TextField tailsignField;

    private AircraftService aircraftService;
    
    @Override
    public void init(Stage aDialog, AbstractCRUDController aController, Aircraft aircraft) {
        super.init(aDialog, aController, aircraft);
        this.nameField.setText(aircraft.getName());
        this.tailsignField.setText(aircraft.getTailsign());
        this.actChoice.getSelectionModel().select(aircraft.getType());
        this.deleteButton.setVisible(aircraft.getId()>0);
        
        // populate Choicebox
        aircraftService = ServiceProxyFactory.createServiceProxy(AircraftService.class);
        AircraftTypeService actypeService = ServiceProxyFactory.createServiceProxy(AircraftTypeService.class);
        ObservableList<AircraftType> actChoices = FXCollections.observableArrayList(actypeService.readAllAircraftTypes());
        actChoice.setConverter(new StringConverter<AircraftType>() {

            @Override
            public String toString(AircraftType t) {
                return t.getName();
            }

            @Override
            public AircraftType fromString(String string) {
                // we do not require this feature - it wont be called
                AircraftType at = new AircraftType();
                at.setName(string);
                return at;
            }
        });
        actChoice.setItems(actChoices);
    }
    
    @FXML protected void submitAircraft(ActionEvent event) {
        entry.setName(this.nameField.getText());
        entry.setTailsign(this.tailsignField.getText());
        entry.setType(this.actChoice.getSelectionModel().getSelectedItem());

        if (!(entry.getId()>0)) {
            Aircraft savedACT = aircraftService.addAircraft(entry);
            this.controller.addEntry(savedACT);
        } else {
            aircraftService.updateAircraft(entry);
            this.controller.refreshTable();
        }
        dialog.close();
    }
    
    @FXML protected void removeAircraft(ActionEvent event) {
        AircraftService actService = ServiceProxyFactory.createServiceProxy(AircraftService.class);
        actService.removeAircraft(entry.getId());
        this.controller.removeEntry(entry);
        dialog.close();
    }
}
