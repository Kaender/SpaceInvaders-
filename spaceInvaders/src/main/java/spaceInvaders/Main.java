package spaceInvaders;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
            Parent root_iniciar = FXMLLoader.load(getClass().getResource("/telaInicial.fxml"));
            Scene scene_inicio = new Scene(root_iniciar);
            scene_inicio.getStylesheets().addAll(this.getClass().getResource("/application.css").toExternalForm());
            

          
            
            stage.setScene(scene_inicio);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}


}   
