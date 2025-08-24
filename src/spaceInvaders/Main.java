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
            Parent root = FXMLLoader.load(getClass().getResource("/Graficos.fxml"));
            Scene scene = new Scene(root);
            PlayerNave playerNave = new PlayerNave(); 
            GameMaster gameMaster = new GameMaster((AnchorPane) root, playerNave);
            ((AnchorPane) root).getChildren().add(gameMaster.getPlayerNave().getSpriteView());
            
            scene.setOnKeyPressed(event -> {
            	System.out.println("Tecla pressionada: " + event.getCode());
            	gameMaster.detectarTeclas(event.getCode(), true);});
            
            scene.setOnKeyReleased(event -> {
            	gameMaster.detectarTeclas(event.getCode(), false);});
          
            
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}


}   
