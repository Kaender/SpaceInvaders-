# -*- coding: utf-8 -*-
import os
from PySide6.QtWidgets import QMainWindow, QPushButton
from PySide6.QtUiTools import QUiLoader
from PySide6.QtCore import QFile, Signal


class InicioWindow(QMainWindow):
    iniciar_jogo_signal = Signal()

    def __init__(self):
        super().__init__()
        loader = QUiLoader()
        ui_path = os.path.join(os.path.dirname(__file__), "TelaInicial.ui")
        ui_file = QFile(ui_path)
        if not ui_file.open(QFile.ReadOnly):
            raise RuntimeError(f"Não foi possível abrir '{ui_path}'")
        self.ui = loader.load(ui_file)
        ui_file.close()

        self.setCentralWidget(self.ui)
        self.startButton = self.ui.findChild(QPushButton, "startButton")
        self.exitButton = self.ui.findChild(QPushButton, "exitButton")
        self.startButton.clicked.connect(self.iniciar_jogo_signal.emit)
        self.exitButton.clicked.connect(self.close)
        self.setFixedSize(self.ui.size())