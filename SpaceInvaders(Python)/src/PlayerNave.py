# -*- coding: utf-8 -*-
from PySide6.QtCore import Qt
from PySide6.QtGui import QPixmap

from Nave import Nave
from Disparo import Disparo


class PlayerNave(Nave):
    """Nave controlada pelo jogador."""

    def __init__(self):
        super().__init__(hp=3)

        # Configura sprite
        pixmap = QPixmap("sprites/Player_Nave.png").scaledToWidth(60, Qt.SmoothTransformation)
        self.set_sprite(pixmap, scale_width=60)

        # Define posição inicial
        self.set_pos_x(600)
        self.set_pos_y(600)
        self._sprite_view.move(self.get_pos_x(), self.get_pos_y())
        self.set_hp(3)

    def mover(self, delta_x: int):
        """Move horizontalmente o player."""
        self.set_pos_x(self.get_pos_x() + delta_x)

    def atacar(self):
        """Cria um disparo partindo da nave."""
        disparo_x = self.get_pos_x() + self.get_sprite_view().width() // 2 - 5
        disparo_y = self.get_pos_y() - 20
        return Disparo(disparo_x, disparo_y, is_player=True)

    def destruir(self):
        """Remove a nave da tela."""
        self._sprite_view.hide()