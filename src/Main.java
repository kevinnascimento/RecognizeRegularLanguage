import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Kevin Nascimento 
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader arquivoFXML = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Parent fxmlParent = (Parent) arquivoFXML.load();
        primaryStage.setScene(new Scene(fxmlParent));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
