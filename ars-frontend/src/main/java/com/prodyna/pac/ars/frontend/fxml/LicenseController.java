/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.PermissionChecker;
import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.reservation.UserAircraftTypeLicenseService;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;
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
public class LicenseController extends AbstractCRUDController<UserAircraftTypeLicense> {
    
    private UserAircraftTypeLicenseService actService;
    
    @FXML private TableColumn<UserAircraftTypeLicense, String> pilotColumn;
    @FXML private TableColumn<UserAircraftTypeLicense, String> aircraftTypeColumn;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!PermissionChecker.getInstance().isLoggedIn()) {
            return;
        }
        
        actService = ServiceProxyFactory.createServiceProxy(UserAircraftTypeLicenseService.class);
        reload(null);

        pilotColumn.setCellValueFactory((TableColumn.CellDataFeatures<UserAircraftTypeLicense, String> data) -> {
            final User user = data.getValue().getUser();
            return new ObservableValueBase<String>(){
                @Override
                public String getValue() {
                    return user==null?"":user.getName();
                }
            };  
        });
        
        aircraftTypeColumn.setCellValueFactory((TableColumn.CellDataFeatures<UserAircraftTypeLicense, String> data) -> {
            final AircraftType aType = data.getValue().getAircraftType();
            return new ObservableValueBase<String>(){
                @Override
                public String getValue() {
                    return aType==null?"":aType.getName();
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
        entries.addAll(actService.readAllLicenses());
    }
    
    @FXML
    public void showCreateForm(ActionEvent event) {
        showForm(new UserAircraftTypeLicense(), getFXMLForm());
    }

    @Override
    protected String getFXMLForm() {
        return "fxml/LicenseForm.fxml";
    }
 
    
}
