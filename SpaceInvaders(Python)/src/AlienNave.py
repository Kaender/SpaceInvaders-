# -*- coding: utf-8 -*-
from Nave import Nave
from Disparo import Disparo


class AlienNave(Nave):

    def __init__(self, sprite_path: str, x: int, y: int):
        super().__init__(sprite_path, x, y, hp=1)

    def mover_horizontal(self, direita: bool, velocidade: float):
        novo_x = self.get_pos_x() + (velocidade if direita else -velocidade)
        self.set_pos_x(novo_x)

    def descer(self, pixels: int = 20):
        self.set_pos_y(self.get_pos_y() + pixels)

    def atacar(self):
        disparo_x = self.get_pos_x() + self.get_sprite_view().width() // 2 - 5
        disparo_y = self.get_pos_y() + self.get_sprite_view().height()
        return Disparo(disparo_x, disparo_y, is_player=False)

    def destruir(self):

        self._sprite_view.hide()