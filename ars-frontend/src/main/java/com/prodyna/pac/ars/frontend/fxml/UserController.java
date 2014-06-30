/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

/**
 *
 * @author aalbert
 */
public class UserController extends AbstractCRUDController<User> {
    
    private UserService userService;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService = ServiceProxyFactory.createServiceProxy(UserService.class);
        entries.addAll(userService.readAllUsers());

        entryTable.setItems(entries);

        initTableListener();
        
    }
    
    @FXML
    public void showCreateForm(ActionEvent event) {
        showForm(new User(), getFXMLForm());
    }

    @Override
    protected String getFXMLForm() {
        return "fxml/UserForm.fxml";
    }
 
    
}
