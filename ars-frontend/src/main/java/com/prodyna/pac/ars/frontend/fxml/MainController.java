package com.prodyna.pac.ars.frontend.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

@SuppressWarnings("restriction")
public class MainController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainController.class.getName());
    @FXML //  fx:id="lblHello"
    private Label lblHello; // Value injected by FXMLLoader


    // Handler for Button[id="btnHello"] onAction
    public void btnHelloOnAction(ActionEvent event) {
        lblHello.setText("Hello");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
