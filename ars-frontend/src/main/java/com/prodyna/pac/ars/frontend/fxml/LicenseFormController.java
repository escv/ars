/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.reservation.UserAircraftTypeLicenseService;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;
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
public class LicenseFormController extends AbstractCRUDFormController<UserAircraftTypeLicense> {

    @FXML private ChoiceBox<User> userChoice;
    @FXML private ChoiceBox<AircraftType> aircraftTypeChoice;
    @FXML private DatePicker validFromPicker;
    @FXML private DatePicker validUntilPicker;

    @Override
    public void init(Stage aDialog, AbstractCRUDController aController, UserAircraftTypeLicense at) {
        super.init(aDialog, aController, at);
        
        AircraftTypeService actypeService = ServiceProxyFactory.createServiceProxy(AircraftTypeService.class);
        ObservableList<AircraftType> actChoices = FXCollections.observableArrayList(actypeService.readAllAircraftTypes());
        this.aircraftTypeChoice.setItems(actChoices);
        
        UserService userService = ServiceProxyFactory.createServiceProxy(UserService.class);
        ObservableList<User> userChoices = FXCollections.observableArrayList(userService.readAllUsers());
        this.userChoice.setItems(userChoices);
        
        this.deleteButton.setVisible(at.getId()>0);
    }
    
    @FXML protected void submitUserAircraftTypeLicense(ActionEvent event) {
        UserAircraftTypeLicenseService licenseServie = ServiceProxyFactory.createServiceProxy(UserAircraftTypeLicenseService.class);
        entry.setAircraftType(this.aircraftTypeChoice.getSelectionModel().getSelectedItem());
        entry.setUser(this.userChoice.getSelectionModel().getSelectedItem());
        Instant instant = Instant.from(this.validFromPicker.getValue().atStartOfDay(ZoneId.systemDefault()));
        entry.setValidFrom(Date.from(instant));
        instant = Instant.from(this.validUntilPicker.getValue().atStartOfDay(ZoneId.systemDefault()));
        entry.setValidUntil(Date.from(instant));

        if (!(entry.getId()>0)) {
            UserAircraftTypeLicense savedLicense = licenseServie.addUserAircraftTypeLicense(entry);
            this.controller.addEntry(savedLicense);
        } else {
            licenseServie.updateUserAircraftTypeLicense(entry);
            this.controller.refreshTable();
        }
        dialog.close();
    }
    
    @FXML protected void removeUserAircraftTypeLicense(ActionEvent event) {
        UserAircraftTypeLicenseService actService = ServiceProxyFactory.createServiceProxy(UserAircraftTypeLicenseService.class);
        actService.removeUserAircraftTypeLicense(entry.getId());
        this.controller.removeEntry(entry);
        dialog.close();
    }
}
