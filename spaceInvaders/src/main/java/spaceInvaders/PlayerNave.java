package spaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerNave extends Nave{

	 
	public PlayerNave() {
		
		setHp(3);
		setSprite("/sprites/Player_Nave.png");
		setPosX(300);
		setPosY(450);
		setSpriteView(new ImageView(this.getSprite()));
		this.getSpriteView().setX(getPosX());
        this.getSpriteView().setY(getPosY());
	}	
	
	
	
	 public void mover(int deltaX) {
	        // Atualiza a posicao interna
	        this.setPosX(this.getPosX() + deltaX);
	        
	        // ATUALIZA A POSICAO VISUAL
	        this.getSpriteView().setX(getPosX());
	 }
	
	public Disparo atacar() {
		Disparo disparo = new Disparo(this.getPosX());	
		return disparo;
	}

	@Override
	public void destruir() {

		
	}


	
	
}
