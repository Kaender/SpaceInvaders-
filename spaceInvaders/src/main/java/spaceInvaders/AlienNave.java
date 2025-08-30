package spaceInvaders;

import javafx.scene.image.ImageView;
import java.util.Random;

public class AlienNave extends Nave {
    private final Random random = new Random();

    public AlienNave(String spritePath, double x, double y) {
        setPosX(x);
        setPosY(y);
        setSprite(spritePath);
        setSpriteView(new ImageView(getSprite()));

        // Posiciona o sprite na cena
        getSpriteView().setX(getPosX());
        getSpriteView().setY(getPosY());

        setVelocidade(1);
        setDescida(20);
        setMovimentoParaDireita(true);
    }

    public void moverHorizontal(boolean paraDireita, double velocidade) {
        double x = getSpriteView().getX();
        if (paraDireita) {
            x += velocidade;
        } else {
            x -= velocidade;
        }
        getSpriteView().setX(x);
        setPosX(x);
    }

    public void descer() {
        double y = getSpriteView().getY();
        double novaY = y + getDescida();
        getSpriteView().setY(novaY);
        setPosY(novaY);
    }

    public Disparo atacar() {
        // Centraliza o disparo horizontalmente em relação à nave
        double disparoX = getPosX() + getSpriteView().getFitWidth() / 2 - 5;
        double disparoY = getPosY() + getSpriteView().getFitHeight();

        return new Disparo(disparoX, disparoY, false); //false = o disparo não veio do player
    }

    @Override
    public void destruir() {
        getSpriteView().setVisible(false);
    }
}
