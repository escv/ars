package com.prodyna.pac.ars.frontend;

import com.prodyna.pac.ars.frontend.service.ServiceProxyFactory;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.User;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

@SuppressWarnings("restriction")
public class App extends Application {

    public static User PRINCIPAL = null;
    
    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new RuntimeExceptionHandler());

        Dialogs login = Dialogs.create().owner(stage).title("Login");
        login.showLogin(new Dialogs.UserInfo("",""), (Dialogs.UserInfo param) -> {
            UserService userService = ServiceProxyFactory.createServiceProxy(UserService.class, param.getUserName(), param.getPassword());
            App.PRINCIPAL = userService.readUserByName(param.getUserName());
            ServiceProxyFactory.USERNAME = param.getUserName();
            ServiceProxyFactory.PASSWORD = param.getPassword();
            return null;
        });

        Parent root = FXMLLoader.load(
                getClass().getResource("fxml/Main.fxml"), 
                ResourceBundle.getBundle("com.prodyna.pac.ars.frontend.bundles.Messages"));
        Scene scene = new Scene(root);        
        stage.setTitle("Aircraft Reservation Service by PAC");
        stage.setScene(scene);
        stage.setWidth(650);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
