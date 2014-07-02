/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;

/**
 *
 * @author aalbert
 * @param <T>
 */
public abstract class AbstractCRUDFormController <T> implements Initializable {
    
    protected Stage dialog;
    protected AbstractCRUDController controller;
    protected T entry;
    protected ValidationSupport validation = new ValidationSupport();
    
    @FXML
    protected Button deleteButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init(Stage dialog, AbstractCRUDController controller, T entry) {
        this.dialog = dialog;
        this.controller = controller;
        this.entry = entry;
    }
}
