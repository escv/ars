/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author aalbert
 */
public class AircraftTypeFormController extends AbstractCRUDFormController<AircraftType> {

    @FXML
    private TextField nameField;

    @Override
    public void init(Stage aDialog, AbstractCRUDController aController, AircraftType at) {
        super.init(aDialog, aController, at);
        this.nameField.setText(at.getName());
        this.deleteButton.setVisible(at.getId()>0);
    }
    
    @FXML protected void submitAircraftType(ActionEvent event) {
        AircraftTypeService actService = ServiceProxyFactory.createServiceProxy(AircraftTypeService.class);
        entry.setName(this.nameField.getText());

        if (!(entry.getId()>0)) {
            AircraftType savedACT = actService.addAircraftType(entry);
            this.controller.addEntry(savedACT);
        } else {
            actService.updateAircraftType(entry);
            this.controller.refreshTable();
        }
        dialog.close();
    }
    
    @FXML protected void removeAircraftType(ActionEvent event) {
        AircraftTypeService actService = ServiceProxyFactory.createServiceProxy(AircraftTypeService.class);
        actService.removeAircraftType(entry.getId());
        this.controller.removeEntry(entry);
        dialog.close();
    }
}
