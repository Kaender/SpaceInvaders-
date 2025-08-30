package spaceInvaders;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerInicio {

    public void iniciarPartida(ActionEvent event) throws IOException {
        // Carrega o FXML da partida
        FXMLLoader loaderJogo = new FXMLLoader(getClass().getResource("/partida.fxml"));
        Parent rootGame = loaderJogo.load();

        Scene sceneGame = new Scene(rootGame);
        ControllerJogo controllerJogo = loaderJogo.getController();

        // Configura captura de teclas
        sceneGame.setOnKeyPressed(keyEvent -> controllerJogo.detectarTeclas(keyEvent.getCode(), true));
        sceneGame.setOnKeyReleased(keyEvent -> controllerJogo.detectarTeclas(keyEvent.getCode(), false));

        // Obtém o stage e troca a cena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(sceneGame);
        stage.setResizable(false); // trava o redimensionamento
        stage.show();
    }

    public void fecharAplicacao(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
