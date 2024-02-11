import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class loadControlller {

    @FXML
    private Label test;
    @FXML
    void Login_button(ActionEvent event) throws IOException{
        
        //switch to sign in page
        Parent root = FXMLLoader.load(getClass().getResource("signIn_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void signUp_button(ActionEvent event) throws IOException {
        
         //switch to sign up page
        Parent root = FXMLLoader.load(getClass().getResource("Sign_up_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
           
    }
}
