package spaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Disparo {
	
	private double posX;
	private double posY;
	private Image sprite;
	private ImageView spriteView;
	
	//Se auto destroi depois que danificar algo
	public void destruir() {
		
	}

	//remove 1hp ao entrar em contato com o sprite de uma nave ou barreira
	public void danificar() {
		
		
	}
	
	public Disparo(double posX) {
		
		setPosY(400);
		setSprite("/sprites/disparo.png");
		setSpriteView(new ImageView(this.getSprite()));
		setPosX(posX + 32);
		this.getSpriteView().setX(getPosX());
        this.getSpriteView().setY(getPosY());
	}
	
	
	public void mover() {
		
		this.posY -= 10;
		if (this.spriteView != null) {
			this.spriteView.setY(this.posY);
		}
	}
	
	
	//getters e setters
	public double getPosX() {
		return posX;
	}
	public ImageView getSpriteView() {
		return spriteView;
	}
	public void setSpriteView(ImageView spriteView) {
		this.spriteView = spriteView;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public Image getSprite() {
		return sprite;
	}
	public void setSprite(String sprite) {
		this.sprite = new Image(getClass().getResource(sprite).toExternalForm());
	}
	
	
}
