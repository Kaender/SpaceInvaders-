package spaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Barreira {

    private int hp;
    private double posX;
    private double posY;
    private Image sprite;
    private ImageView spriteView;

    // Construtor
    public Barreira(double x, double y) {
        setHp(30);
        setPosX(x);
        setPosY(y);
        setSprite("/sprites/barreira.png");

        spriteView = new ImageView(getSprite());
        spriteView.setX(getPosX());
        spriteView.setY(getPosY());
    }

    // Destrói a barreira visualmente
    public void destruir() {
        if (spriteView != null) {
            spriteView.setVisible(false);
        }
    }

    // Getters e Setters
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        if (spriteView != null) spriteView.setX(posX);
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
        if (spriteView != null) spriteView.setY(posY);
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(String spritePath) {
        this.sprite = new Image(getClass().getResource(spritePath).toExternalForm());
    }

    public ImageView getSpriteView() {
        return spriteView;
    }

    public void setSpriteView(ImageView spriteView) {
        this.spriteView = spriteView;
    }
}
