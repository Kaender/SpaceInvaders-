package spaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Nave {

    private int hp;
    private double posX;
    private double posY;
    private Image sprite;
    private ImageView spriteView;
    private double velocidade;
    private double descida;
    private boolean movimentoParaDireita;

    // Método abstrato para destruir a nave
    public abstract void destruir();

    // Getters e setters
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
        if (spriteView != null) spriteView.setX(posX); // mantém a posição do sprite sincronizada
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
        if (spriteView != null) spriteView.setY(posY); // mantém a posição do sprite sincronizada
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(String spritePath) {
        this.sprite = new Image(getClass().getResource(spritePath).toExternalForm());
        if (spriteView != null) spriteView.setImage(this.sprite);
    }

    public ImageView getSpriteView() {
        return spriteView;
    }

    public void setSpriteView(ImageView spriteView) {
        this.spriteView = spriteView;
        this.spriteView.setX(posX);
        this.spriteView.setY(posY);
        if (sprite != null) this.spriteView.setImage(sprite);
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public double getDescida() {
        return descida;
    }

    public void setDescida(double descida) {
        this.descida = descida;
    }

    public boolean isMovimentoParaDireita() {
        return movimentoParaDireita;
    }

    public void setMovimentoParaDireita(boolean movimentoParaDireita) {
        this.movimentoParaDireita = movimentoParaDireita;
    }
}
