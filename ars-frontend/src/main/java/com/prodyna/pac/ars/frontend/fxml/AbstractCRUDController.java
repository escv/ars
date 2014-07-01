/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prodyna.pac.ars.frontend.fxml;

import com.prodyna.pac.ars.frontend.App;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author aalbert
 * @param <T>
 */
public abstract class AbstractCRUDController <T> implements Initializable {
    
    protected final ObservableList<T> entries = FXCollections.observableArrayList();
    
    @FXML
    protected TableView<T> entryTable;
    
    public void addEntry(T at) {
        this.entries.add(at);
    }

    public void removeEntry(T at) {
        this.entries.remove(at);
    }
    
    public void refreshTable() {
        // pretty ugly, but did not find any metter solution
        this.entryTable.getColumns().get(0).setVisible(false);
        this.entryTable.getColumns().get(0).setVisible(true);
    }
    
    protected abstract String getFXMLForm();
    
    protected void initTableListener() {        
        entryTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount()>1) {
                    TableView src = (TableView) t.getSource();
                    T itm = (T) src.getSelectionModel().getSelectedItem();
                    showForm(itm, getFXMLForm());
                }
            }
        });
    }
    
    protected void showForm(T newVal, String fxmlForm) {
        try{
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlForm));
            GridPane page = (GridPane)loader.load();
            AbstractCRUDFormController controller =  loader.getController();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Entry");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            controller.init(dialogStage, this, newVal);
            dialogStage.showAndWait();
  
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
