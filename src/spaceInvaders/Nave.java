package spaceInvaders;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Nave {
	
	private int hp;
	private double posX;
	private double posY;
	private Image sprite;
	private static ImageView spriteView;
	
	public abstract void destruir();
	
	
	//getter e setters 
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
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
