# -*- coding: utf-8 -*-
from PySide6.QtGui import QPixmap, Qt
from PySide6.QtWidgets import QLabel
from PySide6.QtCore import QSize


class Disparo:
    SPRITE_PATH = "sprites/disparo.png"

    def __init__(self, x: int, y: int, is_player: bool):
        self._pos_x = x
        self._pos_y = y
        self._is_player = is_player
        self._velocidade = -5 if is_player else 3

        sprite = QPixmap(self.SPRITE_PATH).scaled(QSize(10, 20))
        self._sprite_view = QLabel()
        self._sprite_view.setAttribute(Qt.WA_TranslucentBackground)
        self._sprite_view.setPixmap(sprite)
        self._sprite_view.setGeometry(x, y, 10, 20)

    def mover(self):
        self._pos_y += self._velocidade
        self._sprite_view.move(int(self._pos_x), int(self._pos_y))

    def get_sprite_view(self):
        return self._sprite_view

    def get_pos_x(self):
        return self._pos_x

    def get_pos_y(self):
        return self._pos_y

    def is_player_disparo(self):
        return self._is_player