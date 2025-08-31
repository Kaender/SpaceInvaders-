# -*- coding: utf-8 -*-
import os
import time
import random

from PySide6.QtWidgets import QMainWindow, QWidget, QLabel
from PySide6.QtUiTools import QUiLoader
from PySide6.QtCore import QFile, Qt, QTimer, Signal
from PySide6.QtGui import QPixmap

from PlayerNave import PlayerNave
from AlienNave import AlienNave
from Barreira import Barreira
from Disparo import Disparo


class JogoWindow(QMainWindow):
    encerrar_jogo_signal = Signal(bool)  # True se venceu, False se perdeu

    def __init__(self):
        super().__init__()

        # Carrega UI
        loader = QUiLoader()
        ui_path = os.path.join(os.path.dirname(__file__), "partida.ui")
        ui_file = QFile(ui_path)
        self.ui = loader.load(ui_file)
        ui_file.close()

        self.setCentralWidget(self.ui)
        self.setFixedSize(self.ui.size())

        self.game_pane = self.ui.findChild(QWidget, "gamePane")

        self.setFocusPolicy(Qt.StrongFocus)
        self.setFocus()

        # Variáveis do jogo
        self.player_nave = None
        self.alien_naves = []
        self.barreiras = []
        self.disparos_ativos = []
        self.teclas_pressionadas = set()
        self.vidas = []

        # Carrega pixmap da vida
        vida_path = os.path.join("sprites", "vidas.png")
        self.img_vida_pixmap = QPixmap(vida_path).scaledToWidth(30, Qt.SmoothTransformation) if os.path.exists(
            vida_path) else QPixmap()

        self.last_shot_time = 0
        self.cooldown_seconds = 0.5
        self.random = random.Random()
        self.alien_direita = True

        # Timer do jogo
        self.game_timer = QTimer(self)
        self.game_timer.timeout.connect(self.game_loop)

        self.setup_game()

    # --- Controle de teclas ---
    def keyPressEvent(self, event):
        self.teclas_pressionadas.add(event.key())

    def keyReleaseEvent(self, event):
        self.teclas_pressionadas.discard(event.key())

    # --- Loop principal ---
    def iniciar_jogo(self):
        self.setFocus()
        self.game_timer.start(16)  # deixa o jogo proximo a 60 FPS

    # --- Setup inicial ---
    def setup_game(self):
        # Limpa objetos anteriores para possivel restart
        for obj in (self.alien_naves + self.barreiras + self.disparos_ativos):
            try:
                obj.get_sprite_view().deleteLater()
            except Exception:
                pass
        self.alien_naves.clear()
        self.barreiras.clear()
        self.disparos_ativos.clear()
        for v in self.vidas:
            v.deleteLater()
        self.vidas.clear()

        # Player
        self.player_nave = PlayerNave()
        self.player_nave.set_pos_x(600)
        self.player_nave.set_pos_y(600)
        p_sprite = self.player_nave.get_sprite_view()
        p_sprite.setParent(self.game_pane)
        p_sprite.move(self.player_nave.get_pos_x(), self.player_nave.get_pos_y())
        p_sprite.setAttribute(Qt.WA_TranslucentBackground)
        p_sprite.show()

        # Aliens
        self.setup_aliens()

        # Barreiras
        for i in range(4):
            bx = 150 + i * 300
            by = 400
            barreira = Barreira(bx, by)
            self.barreiras.append(barreira)
            b_sprite = barreira.get_sprite_view()
            b_sprite.setParent(self.game_pane)
            b_sprite.setAttribute(Qt.WA_TranslucentBackground)
            b_sprite.move(barreira.get_pos_x(), barreira.get_pos_y())
            b_sprite.show()

        # Vidas HUD
        for i in range(self.player_nave.get_hp()):
            vida = QLabel(self.game_pane)
            vida.setPixmap(self.img_vida_pixmap)
            vida.setAttribute(Qt.WA_TranslucentBackground)
            vida.move(20 + i * 40, 680)
            vida.show()
            self.vidas.append(vida)

    # --- Setup aliens ---
    def setup_aliens(self):
        """Cria e posiciona as naves alienígenas."""
        alien_sprites = [
            os.path.join("sprites", "alien01.png"),
            os.path.join("sprites", "alien02.png"),
            os.path.join("sprites", "alien03.png"),
        ]
        aliens_per_row = 15
        start_x = 50
        spacing_x = 60
        start_y = 50
        spacing_y = 60
        self.alien_naves = []

        for row_index, sprite_path in enumerate(alien_sprites):
            y = start_y + row_index * spacing_y
            for i in range(aliens_per_row):
                x = start_x + i * spacing_x
                alien = AlienNave(sprite_path, x, y)
                self.alien_naves.append(alien)
                a_sprite = alien.get_sprite_view()
                a_sprite.setParent(self.game_pane)
                a_sprite.setAttribute(Qt.WA_TranslucentBackground)
                a_sprite.move(alien.get_pos_x(), alien.get_pos_y())
                a_sprite.show()

    # --- Loop do jogo ---
    def game_loop(self):
        self.movimentar_jogador()
        self.movimentar_disparos()
        self.movimentar_aliens()
        self.atirar_alien()
        self.verificar_colisoes()
        self.verificar_game_over()

    # --- Movimentação do jogador ---
    def movimentar_jogador(self):
        if not self.player_nave:
            return

        delta_x = 0
        if Qt.Key_Left in self.teclas_pressionadas:
            delta_x -= 5
        if Qt.Key_Right in self.teclas_pressionadas:
            delta_x += 5

        nova_x = self.player_nave.get_pos_x() + delta_x
        max_x = max(0, self.game_pane.width() - self.player_nave.get_sprite_view().width())
        if 0 <= nova_x <= max_x:
            self.player_nave.mover(delta_x)

        # Disparo
        if Qt.Key_Space in self.teclas_pressionadas:
            agora = time.time()
            if agora - self.last_shot_time > self.cooldown_seconds:
                self.last_shot_time = agora
                disparo = self.player_nave.atacar()
                self.disparos_ativos.append(disparo)
                d_sprite = disparo.get_sprite_view()
                d_sprite.setParent(self.game_pane)
                d_sprite.setAttribute(Qt.WA_TranslucentBackground)
                d_sprite.move(disparo.get_pos_x(), disparo.get_pos_y())
                d_sprite.show()
                d_sprite.raise_()

    # --- Movimentação dos disparos ---
    def movimentar_disparos(self):
        """Atualiza a posição dos disparos e os remove da tela se saírem."""
        removidos = []
        for d in list(self.disparos_ativos):
            d.mover()
            if d.get_pos_y() < 0 or d.get_pos_y() > self.game_pane.height():
                removidos.append(d)

        for d in removidos:
            if d in self.disparos_ativos:
                d.get_sprite_view().deleteLater()
                self.disparos_ativos.remove(d)

    # --- Movimentação dos aliens ---
    def movimentar_aliens(self):
        if not self.alien_naves:
            return
        velocidade = 1
        mover_baixo = False

        try:
            borda_direita = max([a.get_pos_x() + a.get_sprite_view().width() for a in self.alien_naves])
        except ValueError:
            borda_direita = 0
        try:
            borda_esquerda = min([a.get_pos_x() for a in self.alien_naves])
        except ValueError:
            borda_esquerda = 0

        if self.alien_direita and borda_direita >= self.game_pane.width():
            self.alien_direita = False
            mover_baixo = True
        elif not self.alien_direita and borda_esquerda <= 0:
            self.alien_direita = True
            mover_baixo = True

        for alien in self.alien_naves:
            alien.mover_horizontal(self.alien_direita, velocidade)
            if mover_baixo:
                alien.descer()

    # --- Disparo dos aliens ---
    def atirar_alien(self):
        """Faz um alien aleatório atirar."""
        if not self.alien_naves:
            return
        if self.random.randint(0, 50) == 0:
            atacante = self.random.choice(self.alien_naves)
            disparo = atacante.atacar()
            self.disparos_ativos.append(disparo)
            d_sprite = disparo.get_sprite_view()
            d_sprite.setParent(self.game_pane)
            d_sprite.setAttribute(Qt.WA_TranslucentBackground)
            d_sprite.move(disparo.get_pos_x(), disparo.get_pos_y())
            d_sprite.show()
            d_sprite.raise_()

    # --- Verificação de colisões ---
    def verificar_colisoes(self):
        disparos_remover = []
        aliens_remover = []

        # Colisão disparo do jogador com alien
        for d in list(self.disparos_ativos):
            if d.is_player_disparo():
                for alien in list(self.alien_naves):
                    if d.get_sprite_view().geometry().intersects(alien.get_sprite_view().geometry()):
                        aliens_remover.append(alien)
                        if d not in disparos_remover:
                            disparos_remover.append(d)
                        break

        # Colisão disparo com barreira
        for d in list(self.disparos_ativos):
            for b in list(self.barreiras):
                if b.get_sprite_view().isVisible() and d.get_sprite_view().geometry().intersects(
                        b.get_sprite_view().geometry()):
                    b.set_hp(b.get_hp() - 1)
                    if d not in disparos_remover:
                        disparos_remover.append(d)
                    if b.get_hp() <= 0:
                        b.destruir()
                    break

        # Colisão disparo do alien com jogador
        for d in list(self.disparos_ativos):
            if not d.is_player_disparo() and self.player_nave:
                if d.get_sprite_view().geometry().intersects(self.player_nave.get_sprite_view().geometry()):
                    self.player_nave.set_hp(self.player_nave.get_hp() - 1)
                    if d not in disparos_remover:
                        disparos_remover.append(d)
                    self.atualizar_vidas()
                    if self.player_nave.get_hp() <= 0:
                        self.player_nave.destruir()

        for alien in aliens_remover:
            if alien in self.alien_naves:
                self.alien_naves.remove(alien)
                alien.destruir()
                alien.get_sprite_view().deleteLater()

        for d in disparos_remover:
            if d in self.disparos_ativos:
                d.get_sprite_view().deleteLater()
                self.disparos_ativos.remove(d)

    # --- Atualiza vidas ---
    def atualizar_vidas(self):
        for i, vida in enumerate(self.vidas):
            vida.setVisible(i < self.player_nave.get_hp())

    # --- Verifica fim de jogo ---
    def verificar_game_over(self):
        """Checa as condições de vitória ou derrota para encerrar o jogo."""
        venceu = not self.alien_naves
        perdeu = self.player_nave is None or self.player_nave.get_hp() <= 0

        # Condição de derrota adicional: aliens alcançam o jogador
        for alien in self.alien_naves:
            if alien.get_pos_y() + alien.get_sprite_view().height() >= self.player_nave.get_pos_y():
                perdeu = True
                break

        if venceu or perdeu:
            self.game_timer.stop()
            self.encerrar_jogo_signal.emit(venceu)