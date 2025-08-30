package spaceInvaders;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.io.IOException;

public class ControllerJogo {

    @FXML
    private AnchorPane gamePane;

    private PlayerNave playerNave;
    private ArrayList<AlienNave> alienNaves;
    private ArrayList<Barreira> barreiras;
    private HashSet<KeyCode> teclasPressionadas;
    private ArrayList<Disparo> disparosAtivos;

    // HUD de vidas
    private final ArrayList<ImageView> vidas = new ArrayList<>();
    private Image imgVida; // cache da imagem de vida

    private long lastShotTime = 0;
    private static final long COOLDOWN_NANOSECONDS = 500_000_000;
    private boolean alienMovimentoDireita;

    private AnimationTimer gameTimer;
    private final Random random = new Random();

    public ControllerJogo() {}

    @FXML
    public void initialize() {
        playerNave = new PlayerNave();
        alienNaves = new ArrayList<>();
        barreiras = new ArrayList<>();
        teclasPressionadas = new HashSet<>();
        disparosAtivos = new ArrayList<>();

        // carrega sprite de vida uma vez
        imgVida = new Image(getClass().getResourceAsStream("/sprites/vidas.png"));

        inicializarAliens();
        inicializarBarreiras();

        gamePane.getChildren().add(playerNave.getSpriteView());
        mostrarVidas(); // desenha vidas iniciais

        gameLoop();
    }

    /* ---------------- HUD - Vidas ---------------- */

    public void mostrarVidas() {
        // cria e posiciona cada coração
        for (int i = 0; i < playerNave.getHp(); i++) {
            ImageView vida = new ImageView(imgVida);
            vida.setFitWidth(30);
            vida.setFitHeight(30);
            vida.setX(10 + (i * 35));
            vida.setY(10);

            vidas.add(vida);
            gamePane.getChildren().add(vida);
        }
    }

    public void atualizarVidas(int hpAtual) {
        // remove do Pane e limpa a lista
        for (ImageView vida : vidas) {
            gamePane.getChildren().remove(vida);
        }
        vidas.clear();

        // recria de acordo com o HP atual
        for (int i = 0; i < hpAtual; i++) {
            ImageView vida = new ImageView(imgVida);
            vida.setFitWidth(30);
            vida.setFitHeight(30);
            vida.setX(10 + (i * 35));
            vida.setY(10);

            vidas.add(vida);
            gamePane.getChildren().add(vida);
        }
    }

    /* ---------------- Setups ---------------- */

    private void inicializarAliens() {
        for (int i = 0; i <= 15; i++) {
            AlienNave alienAzul = new AlienNave("/sprites/alien01.png", i * 70, 100);
            alienNaves.add(alienAzul);
            gamePane.getChildren().add(alienAzul.getSpriteView());

            AlienNave alienVerde = new AlienNave("/sprites/alien02.png", i * 70, 50);
            alienNaves.add(alienVerde);
            gamePane.getChildren().add(alienVerde.getSpriteView());

            AlienNave alienVermelho = new AlienNave("/sprites/alien03.png", i * 70, 0);
            alienNaves.add(alienVermelho);
            gamePane.getChildren().add(alienVermelho.getSpriteView());
        }
    }

    private void inicializarBarreiras() {
        for (int i = 0; i <= 3; i++) {
            double x = i * 320 + 100;
            double y = 450;
            Barreira barreira = new Barreira(x, y);
            barreiras.add(barreira);
            gamePane.getChildren().add(barreira.getSpriteView());
        }
    }

    /* ---------------- Input ---------------- */

    public void detectarTeclas(KeyCode code, boolean isPressed) {
        if (isPressed) {
            teclasPressionadas.add(code);
        } else {
            teclasPressionadas.remove(code);
        }
    }

    /* ---------------- Game Loop ---------------- */

    private void gameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moverTodosAliens();
                moverPlayer();
                disparar(now);
                atualizarDisparos();
                aliensAtacam();
                verificarGameOver();
            }
        };
        gameTimer.start();
    }

    private void moverPlayer() {
        if (teclasPressionadas.contains(KeyCode.LEFT) && playerNave.getPosX() > 0) {
            playerNave.mover(-5);
        }
        if (teclasPressionadas.contains(KeyCode.RIGHT) && playerNave.getPosX() < 1210) {
            playerNave.mover(5);
        }
    }

    private void disparar(long now) {
        if (teclasPressionadas.contains(KeyCode.SPACE) && (now - lastShotTime >= COOLDOWN_NANOSECONDS)) {
            Disparo disparo = playerNave.atacar(); 
            disparosAtivos.add(disparo);
            gamePane.getChildren().add(disparo.getSpriteView());
            lastShotTime = now;
        }
    }

    private void atualizarDisparos() {
        Iterator<Disparo> disparoIterator = disparosAtivos.iterator();

        while (disparoIterator.hasNext()) {
            Disparo disparo = disparoIterator.next();
            disparo.mover();

            // Colisão com aliens (apenas tiros do player)
            if (disparo.isPlayerDisparo()) {
                Iterator<AlienNave> alienIterator = alienNaves.iterator();
                while (alienIterator.hasNext()) {
                    AlienNave alien = alienIterator.next();
                    if (alien.getSpriteView().getBoundsInParent()
                            .intersects(disparo.getSpriteView().getBoundsInParent())) {
                        alien.destruir();
                        alienIterator.remove();
                        gamePane.getChildren().remove(disparo.getSpriteView());
                        disparoIterator.remove();
                        break;
                    }
                }
            }

            // Colisão com barreiras (qualquer tiro)
            Iterator<Barreira> barreiraIterator = barreiras.iterator();
            while (barreiraIterator.hasNext()) {
                Barreira barreira = barreiraIterator.next();
                if (barreira.getSpriteView().getBoundsInParent()
                        .intersects(disparo.getSpriteView().getBoundsInParent())) {
                    barreira.setHp(barreira.getHp() - 1);
                    if (barreira.getHp() <= 0) {
                        barreira.destruir();
                        barreiraIterator.remove();
                    }
                    gamePane.getChildren().remove(disparo.getSpriteView());
                    disparoIterator.remove();
                    break;
                }
            }

            // Colisão com player (apenas tiros dos aliens)
            if (!disparo.isPlayerDisparo()
                    && playerNave.getSpriteView().getBoundsInParent()
                                  .intersects(disparo.getSpriteView().getBoundsInParent())) {

                playerNave.setHp(playerNave.getHp() - 1);
                if (playerNave.getHp() <= 0) {
                    gamePane.getChildren().remove(playerNave.getSpriteView());
                }
                atualizarVidas(playerNave.getHp());

                gamePane.getChildren().remove(disparo.getSpriteView());
                disparoIterator.remove();
                continue;
            }

            // Fora da tela: remove
            if (disparo.isPlayerDisparo() && disparo.getPosY() < 0) {
                gamePane.getChildren().remove(disparo.getSpriteView());
                disparoIterator.remove();
            } else if (!disparo.isPlayerDisparo() && disparo.getPosY() > gamePane.getHeight()) {
                gamePane.getChildren().remove(disparo.getSpriteView());
                disparoIterator.remove();
            }
        }
    }

    private void moverTodosAliens() {
        boolean chegouNaBorda = false;

        for (AlienNave alien : alienNaves) {
            double x = alien.getSpriteView().getX();
            double larguraAlien = alien.getSpriteView().getFitWidth();

            if (alienMovimentoDireita && x + larguraAlien >= 1210) {
                chegouNaBorda = true;
                break;
            }
            if (!alienMovimentoDireita && x <= 0) {
                chegouNaBorda = true;
                break;
            }
        }

        if (chegouNaBorda) {
            alienMovimentoDireita = !alienMovimentoDireita;
            for (AlienNave alien : alienNaves) {
                alien.descer();
            }
        } else {
            for (AlienNave alien : alienNaves) {
                alien.moverHorizontal(alienMovimentoDireita, alien.getVelocidade());
            }
        }
    }

    private void aliensAtacam() {
        for (AlienNave alien : alienNaves) {
            // 1 chance em 5000 por frame, valor que testei e achei justo
            if (random.nextInt(5000) == 0) {
                Disparo disparoAlien = alien.atacar();
                gamePane.getChildren().add(disparoAlien.getSpriteView());
                disparosAtivos.add(disparoAlien);
            }
        }
    }

    private void verificarGameOver() {
        boolean venceu = alienNaves.isEmpty();
        boolean perdeu = playerNave.getHp() == 0;

        for (AlienNave alien : alienNaves) {
            if (alien.getSpriteView().getY() + alien.getSpriteView().getFitHeight()
                    >= playerNave.getPosY()) {
                perdeu = true;
                break;
            }
        }

        if (venceu || perdeu) {
            gameTimer.stop();
            boolean vitoria = venceu && !perdeu;

            Platform.runLater(() -> { //Precisei desse runLater por que estava dando um erro de exceção
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/telaFinal.fxml"));
                    Parent finalPane = loader.load();

                    ControllerFinal controllerFinal = loader.getController();
                    controllerFinal.setVitoria(vitoria);

                    Stage stage = (Stage) gamePane.getScene().getWindow();
                    if (stage != null) {
                        stage.setScene(new Scene(finalPane));
                        stage.setResizable(false);
                        stage.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
