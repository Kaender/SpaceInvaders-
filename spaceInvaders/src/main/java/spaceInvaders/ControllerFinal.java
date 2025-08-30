package spaceInvaders;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ControllerFinal extends ControllerInicio {

    private boolean vitoria;

    @FXML
    private AnchorPane finalPane;

    @FXML
    private Text labelVitoria;

    @FXML
    private Text labelDerrota;

    public void setVitoria(boolean n){
        this.vitoria = n;
        atualizarTexto();
    }

    public boolean getVitoria(){
        return this.vitoria;
    }

    private void atualizarTexto(){
        if (vitoria){
            labelVitoria.setVisible(true);
            labelDerrota.setVisible(false);
        } else{
            labelVitoria.setVisible(false);
            labelDerrota.setVisible(true);
        }
    }
}
