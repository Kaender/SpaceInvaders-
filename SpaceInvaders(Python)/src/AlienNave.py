# -*- coding: utf-8 -*-
from Nave import Nave
from Disparo import Disparo


class AlienNave(Nave):
    """Nave inimiga."""

    def __init__(self, sprite_path: str, x: int, y: int):
        super().__init__(sprite_path, x, y, hp=1)

    def mover_horizontal(self, direita: bool, velocidade: float):
        """Move a nave alienígena horizontalmente."""
        novo_x = self.get_pos_x() + (velocidade if direita else -velocidade)
        self.set_pos_x(novo_x)

    def descer(self, pixels: int = 20):
        """Faz a nave alienígena descer na tela."""
        self.set_pos_y(self.get_pos_y() + pixels)

    def atacar(self):
        """Cria um disparo partindo da nave alienígena."""
        disparo_x = self.get_pos_x() + self.get_sprite_view().width() // 2 - 5
        disparo_y = self.get_pos_y() + self.get_sprite_view().height()
        return Disparo(disparo_x, disparo_y, is_player=False)

    def destruir(self):
        """Esconde a nave quando destruída."""
        self._sprite_view.hide()