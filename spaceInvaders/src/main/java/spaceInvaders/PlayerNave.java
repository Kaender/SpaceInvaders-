package spaceInvaders;

import javafx.scene.image.ImageView;

public class PlayerNave extends Nave {

    public PlayerNave() {
        setHp(3);
        setSprite("/sprites/Player_Nave.png");
        setPosX(620);
        setPosY(600);
        setSpriteView(new ImageView(getSprite()));
    }

    public void mover(int deltaX) {
        setPosX(getPosX() + deltaX);
    }

    public Disparo atacar() {
        double disparoX = getPosX() + 22;
        double disparoY = getPosY() - 20; // dispara para cima
        return new Disparo(disparoX, disparoY, true); //true = o disparo veio do player
    }

    @Override
    public void destruir() {
        if (getSpriteView() != null) {
            getSpriteView().setVisible(false);
        }
    }
}
