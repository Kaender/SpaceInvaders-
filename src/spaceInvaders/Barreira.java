package spaceInvaders;

public class Barreira {

	private int hp;
	
	//Construtor
	
	public Barreira() {
		
		this.setHp(30);
	}

	// Quando hp = 0, desaparece
	public void Destruir() {
		
		if(this.getHp() == 0) {
			
			//destroi
			
		}
		return;
	}
	
	
	
	//Setter e Getter
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
