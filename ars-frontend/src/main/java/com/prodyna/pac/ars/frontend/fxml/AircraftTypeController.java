/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.PermissionChecker;
import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import org.controlsfx.validation.ValidationSupport;

/**
 *
 * @author aalbert
 */
public class AircraftTypeController extends AbstractCRUDController<AircraftType> {
    
    private AircraftTypeService actService;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actService = ServiceProxyFactory.createServiceProxy(AircraftTypeService.class);
        reload(null);

        entryTable.setItems(entries);

        if (PermissionChecker.getInstance().isAdmin()) {
            initTableListener();
        }
    }

    @Override
    public void reload(ActionEvent event) {
        entries.clear();
        entries.addAll(actService.readAllAircraftTypes());
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
