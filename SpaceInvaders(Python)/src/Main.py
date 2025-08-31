# -*- coding: utf-8 -*-
import sys
from PySide6.QtWidgets import QApplication

from InicioWindow import InicioWindow
from TelaJogo import JogoWindow
from FinalWindow import FinalWindow


class GerenciadorDeJanelas:
    """Gerencia a transição entre as diferentes janelas do jogo."""
    def __init__(self, app):
        self.app = app
        self.janela_inicio = None
        self.janela_jogo = None
        self.janela_final = None

    def mostrar_inicio(self):
        """Exibe a janela inicial do jogo."""
        self.janela_inicio = InicioWindow()
        self.janela_inicio.iniciar_jogo_signal.connect(self.iniciar_jogo)
        self.janela_inicio.show()

    def iniciar_jogo(self):
        """Inicia o jogo, fechando a tela inicial e abrindo a de jogo."""
        if self.janela_inicio:
            self.janela_inicio.close()
            self.janela_inicio = None

        self.janela_jogo = JogoWindow()
        self.janela_jogo.encerrar_jogo_signal.connect(self.mostrar_final)
        self.janela_jogo.show()
        self.janela_jogo.iniciar_jogo()

    def mostrar_final(self, venceu: bool):
        """Exibe a janela final, mostrando o resultado do jogo."""
        if self.janela_jogo:
            self.janela_jogo.close()
            self.janela_jogo = None

        self.janela_final = FinalWindow(venceu)
        self.janela_final.iniciar_jogo.connect(self.iniciar_jogo)
        self.janela_final.encerrar_jogo.connect(self.app.quit)
        self.janela_final.show()


def main():
    """Função principal que inicia a aplicação."""
    app = QApplication(sys.argv)
    gerenciador = GerenciadorDeJanelas(app)
    gerenciador.mostrar_inicio()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()