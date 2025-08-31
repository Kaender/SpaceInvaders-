# SpaceInvaders-
Criação de jogo inspirado no Classico Space Invaders utilizando java na biblioteca JavaFX e python na biblioteca Qt.
projeto criado por: Kaender Teixeira Lages
link para o repositorio: https://github.com/Kaender/SpaceInvaders-

Professor me desculpe a falta dos sinais, estou usando um teclado US, que nao possui os sinais do portugues.

## Instruções:

Para executar o projeto em java apenas abra o terminal na pasta do programa, e utilize: mvn javafx:run
Para executar o projeto em python abra o terminal na pasta do programa, e utilize: python main.py
Caso você não tenha a biblioteca necessária para o python, use pip install PySide6 no terminal antes.

## Discussão sobre o Tema

O tema proposto foi criar uma cópia do jogo Space Invaders, um jogo simples, possui poucas mecanicas e objetos, portanto o grande desafio desse tema foi apenas o uso das bibliotecas graficas, pois ate mesmo os metodos que 
utilizavam apenas java, ou python, ficaram bem simples. Minha maior dificuldade foi com certeza utilizar direito as duas bibliotecas graficas, criar os controlers e fazer eles alterarem os elementos graficos.

## O que eu consegui desenvolver

Ambas as linguagens tem a mesma estrutura nas classes dos objetos, e difereciam nas classes controladoras da biblioteca. Essas classes possuem praticamente a mesma logica nas duas:

Classe Nave:
Essa classe e uma classe abstrata que serviu de molde para a PlayerNave e AlienNave (eu sei que os aliens nao eram naves, apenas serviu para simplificar) 
Ela implementeu atributos de posicao e sprites, alem do metodo abstrato destruir(). Eu tambem queria implementar o disparo() e movimento() mas a playerNave e alienNave tem logicas muito diferentes, o que afetaria no codigo.

Classe PlayerNave:

Esta implementa seu construtor, que coloca seu sprite, e alguns atributos como velocidade e hp. Ela implementa tambem seu metodo mover() que move apenas horizontalmente, e o metodo disparar() que cria um objeto da classe disparo.

Classe Disparo:

Essa classe e o disparo das naves, ela implementa um metodo mover(), que caso seja chamado pelo PlayerNave, o mover sobe o disparo, e se for chamado pelo AlienNave, ela desce o disparo. A classe controladorJogo controla a colisao e destruicao das naves

Classe Barreira:

Essa classe serve apenas para criar um objeto de barreira, com 30 hp, que fica estatico, como no space invaders. Ela defende tiros dos Aliens, mas tambem do Player. se destroi quando o hp = 0

Classe AlienNave:

Essa classe cria os aliens, ela define os metodos de movimento horizontal, descer e atacar que sao chamados pela controladorJogo

Classe Main:

A classe main possui um Gerenciador de Janelas que implementa signals, cada classe controlador ao ativarem algum botao, envia um sinal, que ativa os metodos da classe main para mostrar cada janela, ela inicia mostrando o ControladorInicio. 

Classe ControladorInicio:

Essa classe conecta os botoes start com o metodo iniciarJogo() da classe main com signal, que por sua vez puxa e exibe a controladoraJogo, o metodo sair() apenas fecha a janela.

Classe ControladorJogo:

Essa classe e o foco principal de todo esse projeto, ela que implementa todo o jogo
Ela tem listas para cada objeto do jogo, com excecao da playerNave, que e um objeto unico, ela inicializa cada um deles em suas posicoes e com suas quantidades certas, alem disso tambem implementa coracoes para representar a vida do player.
Ela detecta as teclas pressionadas (<- esquerda, -> direita, space atirar) e ativa o gameLoop()  que ativa a cada frame, os metodos que fazem todo o jogo funcionar:
movimentar_jogador() movimento horizontal dependendo da tecla
movimentar_disparos() disparos subirem ou descerem
movimentar_aliens() aliens moverem ate a borda da janela, e depois descerem um pouco
atirar_aliens() alien tem uma chande de atirarem a cada frame
verificar_colisoes() colisoes dos disparos com as naves e barreiras
verificar_gameOver() verificar as condicoes de vitoria e derrota

Esse gameOver() ira detectar a vitoria ou derrota, caso o player.hp <= 0 ou os aliens alcancem o player, vai emitir um sinal com o bool vitoria = false, para o controladorFinal. Caso nao exista mais aliens, vitoria = true.
independente do resultado, a classe controladorFinal, vai ser chamada pelo main.

Classe ControladorFinal: 

Essa classe e a tela final do jogo, nela se vitoria = true, o texto sera "Vitoria!" verde, se for false, "Derrota!" vermelho.
Ela possui 2 botes, sair() que fecha, e o de reiniciar, que emite um sinal para puxar o controladorJogo denovo, recomecando o jogo.

## Orientacao a Objeto em Python

A principal diferença da (POO) em Python, comparada com Java, é que Python é mais flexível e direta.
Em vez de regras rígidas, Python confia em convencoes, Por exemplo, atributos como _hp e _pos_x na classe Nave são considerados privados e não devem ser acessados diretamente. Em Java, você precisaria usar a palavra private para bloquear o acesso.
Python também usa "duck typing". Isso significa que a linguagem se preocupa com o que um objeto pode fazer, e não com qual classe ele pertence. Na classe ControladorJogo, as colisões verificam a geometria de qualquer objeto que possa ter um get_sprite_view(), sem se importar se é uma nave do jogador ou um alien. Em Java, isso exigiu sistema mais complexo.
Essa abordagem torna a herança em Python mais versátil e o código mais conciso, permitindo um desenvolvimento mais rápido e dinâmico.
