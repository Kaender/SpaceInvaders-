/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaceInvaders;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.AnchorPane;

public class ControllerInicio {
    
    public void iniciarPartida(ActionEvent event) throws IOException {
 
        FXMLLoader loaderJogo = new FXMLLoader(getClass().getResource("/partida.fxml"));
        Parent root_game = loaderJogo.load();
        ControllerJogo controllerJogo = loaderJogo.getController();
        Scene scene_game = new Scene(root_game);
        
        controllerJogo.iniciarGame((AnchorPane) root_game);
        scene_game.setOnKeyPressed(keyEvent -> {
            System.out.println("Tecla pressionada: " + keyEvent.getCode());
            controllerJogo.detectarTeclas(keyEvent.getCode(), true);
        });
        
        scene_game.setOnKeyReleased(keyEvent -> {
            controllerJogo.detectarTeclas(keyEvent.getCode(), false);
        });
        
        // Get the stage and change the scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene_game);
        stage.show();
    }
}