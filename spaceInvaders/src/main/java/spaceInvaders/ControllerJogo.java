package spaceInvaders;

import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class ControllerJogo {

	private PlayerNave playerNave;
	private ArrayList<Nave> Alien_Nave; 
	private ArrayList<Barreira> Barreiras;
	private HashSet<KeyCode> teclasPressionadas;
	private ArrayList<Disparo> disparosAtivos;
	private long lastShotTime = 0;
	private final long COOLDOWN_NANOSECONDS = 500_000_000;     
        @FXML
	private AnchorPane gamePane;
      
	public ControllerJogo() {}
	
        @FXML 
        public void iniciarGame(AnchorPane pane) {
            
        this.playerNave = new PlayerNave();
        this.Alien_Nave = new ArrayList<>();
        this.Barreiras = new ArrayList<>();
        this.teclasPressionadas = new HashSet<>();
        this.disparosAtivos = new ArrayList<>();
        this.gamePane.getChildren().add(this.playerNave.getSpriteView());
        GameLoop();
        }
        
	public void detectarTeclas(KeyCode code, boolean isPressed) {
            if (isPressed) {
                teclasPressionadas.add(code);
            } else {
                teclasPressionadas.remove(code);
            }
        }
	public void GameLoop() {
		
		new AnimationTimer() {
			
		@Override
		public void handle(long now) {
			
			if (teclasPressionadas.contains(KeyCode.LEFT) && (playerNave.getPosX() != 0)) {
				playerNave.mover(-10);			
			}
			if (teclasPressionadas.contains(KeyCode.RIGHT) && (playerNave.getPosX() != 670)) {
				playerNave.mover(10);			
				}
			if (teclasPressionadas.contains(KeyCode.SPACE) && (now - lastShotTime >= COOLDOWN_NANOSECONDS)) {
				Disparo disparo = playerNave.atacar();
				gamePane.getChildren().add(disparo.getSpriteView());
				disparosAtivos.add(disparo);
				lastShotTime = now;
				}
			
			 Iterator<Disparo> iterator = disparosAtivos.iterator();
             while (iterator.hasNext()) {
                 Disparo disparo = iterator.next();
                 disparo.mover(); 
                 
             
                 if (disparo.getPosY() < 0) { 
                     gamePane.getChildren().remove(disparo.getSpriteView());
                     iterator.remove();
                    
                   }
             	}
			} 
		}.start();
	}
	
	//getters e setters
	public PlayerNave getPlayerNave() {
		return playerNave;
	}

	public void setPlayerNave(PlayerNave playerNave) {
		this.playerNave = playerNave;
	}

	public ArrayList<Nave> getAlien_Nave() {
		return Alien_Nave;
	}

	public void setAlien_Nave(ArrayList<Nave> alien_Nave) {
		Alien_Nave = alien_Nave;
	}

	public ArrayList<Barreira> getBarreiras() {
		return Barreiras;
	}

	public void setBarreiras(ArrayList<Barreira> barreiras) {
		Barreiras = barreiras;
	}

	public HashSet<KeyCode> getTeclasPressionadas() {
		return teclasPressionadas;
	}

	public void setTeclasPressionadas(HashSet<KeyCode> teclasPressionadas) {
		this.teclasPressionadas = teclasPressionadas;
	}
	
}
