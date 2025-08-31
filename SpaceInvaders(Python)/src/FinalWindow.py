# -*- coding: utf-8 -*-
import os
from PySide6.QtWidgets import QMainWindow, QMessageBox, QPushButton, QLabel
from PySide6.QtUiTools import QUiLoader
from PySide6.QtCore import QFile, QIODevice, Signal


class FinalWindow(QMainWindow):
    """Janela final do jogo, mostrando resultado e opções."""

    # Signals para trocar de tela
    iniciar_jogo = Signal()
    encerrar_jogo = Signal()

    def __init__(self, vitoria: bool):
        super().__init__()
        self.vitoria = vitoria
        self.ui = None

        # Carrega UI
        loader = QUiLoader()
        ui_path = os.path.join(os.path.dirname(__file__), "telaFinal.ui")
        ui_file = QFile(ui_path)
        if not ui_file.open(QIODevice.ReadOnly):
            QMessageBox.critical(self, "Erro", f"Não foi possível carregar '{ui_path}'")
            return

        self.ui = loader.load(ui_file, self)
        ui_file.close()

        if self.ui is None:
            QMessageBox.critical(self, "Erro", "Falha ao carregar interface final.")
            return

        self.setCentralWidget(self.ui)
        self.setFixedSize(self.ui.size())

        # Busca os widgets no UI
        self.playAgainButton = self.ui.findChild(QPushButton, "playAgainButton")
        self.exitButton = self.ui.findChild(QPushButton, "exitButton")
        self.labelVitoria = self.ui.findChild(QLabel, "labelVitoria")
        self.labelDerrota = self.ui.findChild(QLabel, "labelDerrota")

        # Conecta sinais
        if self.playAgainButton:
            self.playAgainButton.clicked.connect(self._emitir_jogar_novamente)
        if self.exitButton:
            self.exitButton.clicked.connect(self._emitir_encerrar)

        self.atualizar_texto()

    def atualizar_texto(self):
        """Atualiza o texto da janela de acordo com a vitória ou derrota."""
        if self.labelVitoria:
            self.labelVitoria.setVisible(self.vitoria)
        if self.labelDerrota:
            self.labelDerrota.setVisible(not self.vitoria)

    # --- Métodos que emitem signals ---
    def _emitir_jogar_novamente(self):
        self.iniciar_jogo.emit()
        self.close()

    def _emitir_encerrar(self):
        self.encerrar_jogo.emit()
        self.close()