/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.PermissionChecker;
import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import java.net.URL;
import java.security.Permission;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;

/**
 *
 * @author aalbert
 */
public class AircraftController extends AbstractCRUDController<Aircraft> {
    
    private AircraftService acService;
  
    @FXML private TableColumn<Aircraft, String> typeColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acService = ServiceProxyFactory.createServiceProxy(AircraftService.class);
        reload(null);
        
        typeColumn.setCellValueFactory((CellDataFeatures<Aircraft, String> data) -> {
            final AircraftType at = data.getValue().getType();
            return new ObservableValueBase<String>(){
                @Override
                public String getValue() {
                    return at==null?"":at.getName();
                }
            };  
        });  
        entryTable.setItems(entries);

        if (PermissionChecker.getInstance().isAdmin()) {
            initTableListener();
        }
    }

    @Override
    public void reload(ActionEvent event) {
        entries.clear();
        entries.addAll(acService.readAllAircrafts());
    }
    
    @FXML
    public void showCreateForm(ActionEvent event) {
        showForm(new Aircraft(), getFXMLForm());
    }

    @Override
    protected String getFXMLForm() {
        return "fxml/AircraftForm.fxml";
    }

}
