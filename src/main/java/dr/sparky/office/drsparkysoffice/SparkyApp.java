package dr.sparky.office.drsparkysoffice;

import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.UserAccount;
import dr.sparky.office.drsparkysoffice.model.UserType;
import dr.sparky.office.drsparkysoffice.util.FXUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SparkyApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SparkyApp.class.getResource(FXUtil.START_VIEW));
        Scene scene = new Scene(fxmlLoader.load(),600 , 640);
        stage.setTitle("Dr Sparky's Office");
        stage.setScene(scene);
        stage.show();

        // Add 2 Hardcoded doctors and Nurse, User will not add if already exist
        UserManager manager = new UserManager();
        manager.addUser(new UserAccount("nurse1@g.com","nurse1", UserType.NURSE));
        manager.addUser(new UserAccount("nurse2@g.com","nurse2", UserType.NURSE));
        manager.addUser(new UserAccount("n@n.n","n", UserType.NURSE));
        manager.addUser(new UserAccount("doctor1@g.com","doctor1", UserType.DOCTOR));
        manager.addUser(new UserAccount("doctor2@g.com","doctor2", UserType.DOCTOR));
        manager.addUser(new UserAccount("d@d.d","d", UserType.DOCTOR));
    }

    public static void main(String[] args) {
        launch();
    }
}