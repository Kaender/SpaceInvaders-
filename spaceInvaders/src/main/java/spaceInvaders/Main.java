package spaceInvaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent rootInicio = FXMLLoader.load(getClass().getResource("/telaInicial.fxml"));
            Scene sceneInicio = new Scene(rootInicio);

            stage.setScene(sceneInicio);
            stage.setTitle("Space Invaders");   // título da janela
            stage.setResizable(false);          // bloqueia redimensionamento
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
