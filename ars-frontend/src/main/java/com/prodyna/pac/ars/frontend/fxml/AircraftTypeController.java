/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

/**
 *
 * @author aalbert
 */
public class AircraftTypeController extends AbstractCRUDController<AircraftType> {
    
    private AircraftTypeService actService;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actService = ServiceProxyFactory.createServiceProxy(AircraftTypeService.class);
        entries.addAll(actService.readAllAircraftTypes());

        entryTable.setItems(entries);

        initTableListener();
        
    }
    
    @FXML
    public void showCreateForm(ActionEvent event) {
        showForm(new AircraftType(), getFXMLForm());
    }

    @Override
    protected String getFXMLForm() {
        return "fxml/AircraftTypeForm.fxml";
    }
 
    
}
