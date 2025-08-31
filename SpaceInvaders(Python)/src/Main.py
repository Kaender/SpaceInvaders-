# -*- coding: utf-8 -*-
import sys
from PySide6.QtWidgets import QApplication

from ControladorInicio import ControladorInicio
from ControladorJogo import ControladorJogo
from ControladorFinal import ControladorFinal


class GerenciadorDeJanelas:
    def __init__(self, app):
        self.app = app
        self.janela_inicio = None
        self.janela_jogo = None
        self.janela_final = None

    def mostrar_inicio(self):
        self.janela_inicio = ControladorInicio()
        self.janela_inicio.iniciar_jogo_signal.connect(self.iniciar_jogo)
        self.janela_inicio.show()

    def iniciar_jogo(self):
        if self.janela_inicio:
            self.janela_inicio.close()
            self.janela_inicio = None

        self.janela_jogo = ControladorJogo()
        self.janela_jogo.encerrar_jogo_signal.connect(self.mostrar_final)
        self.janela_jogo.show()
        self.janela_jogo.iniciar_jogo()

    def mostrar_final(self, venceu: bool):
        if self.janela_jogo:
            self.janela_jogo.close()
            self.janela_jogo = None

        self.janela_final = ControladorFinal(venceu)
        self.janela_final.iniciar_jogo.connect(self.iniciar_jogo)
        self.janela_final.encerrar_jogo.connect(self.app.quit)
        self.janela_final.show()


def main():
    app = QApplication(sys.argv)
    gerenciador = GerenciadorDeJanelas(app)
    gerenciador.mostrar_inicio()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()