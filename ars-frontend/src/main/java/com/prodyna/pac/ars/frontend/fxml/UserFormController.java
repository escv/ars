/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author aalbert
 */
public class UserFormController extends AbstractCRUDFormController<User> {

    @FXML
    private TextField nameField;

    @Override
    public void init(Stage aDialog, AbstractCRUDController aController, User at) {
        super.init(aDialog, aController, at);
        this.nameField.setText(at.getName());
        this.deleteButton.setVisible(at.getId()>0);
    }
    
    @FXML protected void submitUser(ActionEvent event) {
        UserService actService = ServiceProxyFactory.createServiceProxy(UserService.class);
        entry.setName(this.nameField.getText());

        if (!(entry.getId()>0)) {
            User savedACT = actService.addUser(entry);
            this.controller.addEntry(savedACT);
        } else {
            actService.updateUser(entry);
            this.controller.refreshTable();
        }
        dialog.close();
    }
    
    @FXML protected void removeUser(ActionEvent event) {
        UserService actService = ServiceProxyFactory.createServiceProxy(UserService.class);
        actService.removeUser(entry.getId());
        this.controller.removeEntry(entry);
        dialog.close();
    }
}
