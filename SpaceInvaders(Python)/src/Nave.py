# -*- coding: utf-8 -*-
from PySide6.QtGui import QPixmap
from PySide6.QtWidgets import QLabel
from abc import ABC, abstractmethod


class Nave(ABC):
    """Classe base para qualquer nave (jogador ou alien)."""

    def __init__(self, sprite_path: str = None, x: int = 0, y: int = 0, hp: int = 1):
        self._hp = hp
        self._pos_x = x
        self._pos_y = y
        self._sprite = None
        self._sprite_view = QLabel()

        if sprite_path:
            self.set_sprite(sprite_path)

        # Define posição inicial
        self._sprite_view.move(int(self._pos_x), int(self._pos_y))

    def set_sprite(self, sprite_path: str, scale_width: int = None):
        """Configura o sprite da nave."""
        pixmap = QPixmap(sprite_path)
        if scale_width:
            pixmap = pixmap.scaledToWidth(scale_width)
        self._sprite = pixmap
        self._sprite_view.setPixmap(self._sprite)
        self._sprite_view.setGeometry(
            self._pos_x,
            self._pos_y,
            self._sprite.width(),
            self._sprite.height(),
        )

    @abstractmethod
    def destruir(self):
        """Esconde a nave quando destruída."""
        pass

    # --- Getters / Setters ---
    def get_hp(self):
        return self._hp

    def set_hp(self, hp):
        self._hp = hp

    def get_pos_x(self):
        return self._pos_x

    def set_pos_x(self, x):
        self._pos_x = x
        self._sprite_view.move(int(x), int(self._pos_y))

    def get_pos_y(self):
        return self._pos_y

    def set_pos_y(self, y):
        self._pos_y = y
        self._sprite_view.move(int(self._pos_x), int(y))

    def get_sprite_view(self):
        return self._sprite_view