module spaceInvaders {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens spaceInvaders to javafx.graphics, javafx.fxml;
}
