package spaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Disparo {

    private static final String SPRITE_PATH = "/sprites/disparo.png"; 
    private final ImageView spriteView;
    private double posX;
    private double posY;
    private final double velocidade; 
    private final boolean isPlayerDisparo;

    public Disparo(double startX, double startY, boolean isPlayer) {
        this.posX = startX;
        this.posY = startY;
        this.isPlayerDisparo = isPlayer;
        this.velocidade = isPlayer ? -5 : 3; // player dispara pra cima, alien pra baixo

        this.spriteView = new ImageView(new Image(SPRITE_PATH));
        this.spriteView.setFitWidth(10);
        this.spriteView.setFitHeight(20);
        this.spriteView.setX(posX);
        this.spriteView.setY(posY);
    }

    public void mover() {
        posY += velocidade;
        spriteView.setY(posY);
    }

    // Getters
    public ImageView getSpriteView() {
        return spriteView;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public boolean isPlayerDisparo() {
        return isPlayerDisparo;
    }
}
