import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application{

    @Override
    public void start(Stage stage){
    
         try {
            
            Parent root = FXMLLoader.load(getClass().getResource("AppUI.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Eaze Bank");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
         } catch (Exception e) {
             
            e.printStackTrace();
         }
    }

    public static void main(String[] args) {        
        launch(args);
    }
} 