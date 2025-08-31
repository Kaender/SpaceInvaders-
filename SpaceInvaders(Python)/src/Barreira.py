# -*- coding: utf-8 -*-
from PySide6.QtGui import QPixmap, Qt
from PySide6.QtWidgets import QLabel


class Barreira:
    """Barreira que protege o jogador."""

    def __init__(self, x: int, y: int):
        self._hp = 30
        self._pos_x = x
        self._pos_y = y
        self._sprite = QPixmap("sprites/barreira.png")
        self._sprite_view = QLabel()
        self._sprite_view.setPixmap(self._sprite)
        self._sprite_view.setAttribute(Qt.WA_TranslucentBackground)
        self._sprite_view.setGeometry(x, y, self._sprite.width(), self._sprite.height())

    def destruir(self):
        """Esconde a barreira quando destruída."""
        self._sprite_view.hide()

    def get_hp(self):
        return self._hp

    def set_hp(self, hp):
        self._hp = hp

    def get_sprite_view(self):
        return self._sprite_view

    def get_pos_x(self):
        return self._pos_x

    def get_pos_y(self):
        return self._pos_y