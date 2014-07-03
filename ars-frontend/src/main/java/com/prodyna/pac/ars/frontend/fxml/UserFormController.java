/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.UserRoleService;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.masterdata.model.UserRole;
import java.security.MessageDigest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.SelectableCheckListView;
import org.controlsfx.validation.Validator;

/**
 *
 * @author aalbert
 */
public class UserFormController extends AbstractCRUDFormController<User> {

    @FXML private TextField nameField;
    @FXML private TextField mailField;
    @FXML private PasswordField passwordField;
    @FXML private SelectableCheckListView<UserRole> rolesChoice;
    
    @Override
    public void init(Stage aDialog, AbstractCRUDController aController, User user) {
        super.init(aDialog, aController, user);
        this.nameField.setText(user.getName());
        this.mailField.setText(user.getEmail());
        this.passwordField.setPromptText("EMPTY TO NOT CHANGE IT");
        this.deleteButton.setVisible(user.getId()>0);
        
        UserRoleService roleService = ServiceProxyFactory.createServiceProxy(UserRoleService.class);
        ObservableList<UserRole> roles = FXCollections.observableArrayList(roleService.readAllUserRoles());
        this.rolesChoice.setItems(roles);
        
        for (UserRole role : user.getRoles()) {
            this.rolesChoice.getCheckModel().select(role);
        }
        
        // hook in validators
        validation.registerValidator(nameField, Validator.createEmptyValidator("Name is required"));
        validation.registerValidator(mailField, Validator.createEmptyValidator("Mail is required"));
        validation.registerValidator(passwordField, Validator.createEmptyValidator("Password is required"));
    }
    
    @FXML protected void submitUser(ActionEvent event) {
        if (!validation.isInvalid()) {
        UserService actService = ServiceProxyFactory.createServiceProxy(UserService.class);
        entry.setName(this.nameField.getText());
        entry.setEmail(this.mailField.getText());
        entry.getRoles().clear();
        entry.getRoles().addAll(this.rolesChoice.getCheckModel().getSelectedItems());
            try {
                if (!passwordField.getText().isEmpty()) {
                    entry.setPasswordDigest(digestPassword(passwordField.getText()));
                }
                if (!(entry.getId()>0)) {
                    User savedACT = actService.addUser(entry);
                    this.controller.addEntry(savedACT);
                } else {
                    actService.updateUser(entry);
                    this.controller.refreshTable();
                }
                dialog.close();
            } catch (Exception e) {
                // TODO Error handling
            }
        }
    }
    
    @FXML protected void removeUser(ActionEvent event) {
        UserService actService = ServiceProxyFactory.createServiceProxy(UserService.class);
        actService.removeUser(entry.getId());
        this.controller.removeEntry(entry);
        dialog.close();
    }
    
    private String digestPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(password.getBytes());
        String result = "";
        for (int i=0; i<digest.length; i++) {
            result += Integer.toString( ( digest[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}
