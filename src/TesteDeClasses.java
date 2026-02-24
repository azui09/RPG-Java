import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Arma {
    String nome;
    int dano;
    String tipo;

    Arma(String nome, int dano, String tipo) {
        this.nome = nome;
        this.dano = dano;
        this.tipo = tipo;

    }

    void exibirInfo() {
        System.out.println("Arma: " + nome + " | Dano: " + dano + " | Tipo: " + tipo);
    }
}

class Inventario {
    Arma[] armas;
    int capacidade;
    int totalArmas;

    Inventario(int capacidade) {
        this.capacidade = capacidade;
        this.armas = new Arma[capacidade];
        this.totalArmas = 0;
    }

    void adicionarArma(Arma arma) {
        if(totalArmas < capacidade) {
            armas[totalArmas] = arma;
            totalArmas++;
            System.out.println(arma.nome + " adicionada com sucesso");
        } else {
            System.out.println("Inventario cheio, nao foi possivel adicionar " + arma.nome);
        }
    }

    void listarArmas() {
        System.out.println("===Inventario===");
        for(int i = 0; i < totalArmas; i++) {
            armas[i].exibirInfo();
        }
    }
}

class Jogador {
    String nome;
    int vida;
    int nivel;
    Arma armaEquipada;
    Inventario inventario;
    boolean estaVivo;

    Jogador(String nome, int vida) {
        this.nome = nome;
        this.vida = vida;
        this.nivel = 1;
        this.inventario = new Inventario(5);
        this.armaEquipada = null;
        this.estaVivo = true;
    }

    void equiparArma(Arma arma) {
        this.armaEquipada = arma;
        System.out.println(nome + " equipou: " + arma.nome);
    }

    void atacar(Jogador alvo) {
        if(armaEquipada == null) {
            System.out.printf("\n%s nao tem uma arma equipada", nome);
            return;
        }
        alvo.vida -= armaEquipada.dano;
        if(nome.equals(alvo.nome)) {
            System.out.printf("\n%s atacou a si mesmo usando %s, causando %d de dano",
                    nome, armaEquipada.nome, armaEquipada.dano);
        }
        System.out.printf("\n%s atacou %s com %s, causando %d de dano",
                nome, alvo.nome, armaEquipada.nome, armaEquipada.dano);
        if(alvo.vida < 0) {
            alvo.vida = 0;
        }
        if(alvo.vida == 0) {
            System.out.printf("%s foi derrotado por %s", alvo.nome, nome);
            alvo.estaVivo = false;
        } else {
            System.out.printf("\nVida de %s: %d", alvo.nome, alvo.vida);
        }
    }

    void exibirStatus() {
        System.out.printf("\nJogador: %s", nome);
        System.out.printf("\nVida: %d | Nivel: %d", vida, nivel);
        if(armaEquipada != null) {
            System.out.printf("\nArma equipada: %s", armaEquipada.nome);
        } else {
            System.out.printf("\nNenhuma arma equipada");
        }
        inventario.listarArmas();
    }
}

public class TesteDeClasses {
    public static void main(String[] args) {
        Scanner sa = new Scanner(System.in);

        ArrayList<Jogador> jogadores = new ArrayList<>();
        while(true) {
            System.out.println("\n========== MENU ==========");
            System.out.println("1 - Criar jogador");
            System.out.println("2 - Criar arma e adicionar a um jogador");
            System.out.println("3 - Equipar arma");
            System.out.println("4 - Ver status dos jogadores");
            System.out.println("5 - Iniciar batalha");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            int opcao = sa.nextInt();
            sa.nextLine();

            switch(opcao) {
                case 1 -> criarJogador(sa, jogadores);
                case 2 -> criarArma(sa, jogadores);
                case 3 -> equiparArma(sa, jogadores);
                case 4 -> verStatus(jogadores);
                case 5 -> batalha(sa, jogadores);
                case 0 -> {
                    System.out.println("Saindo...");
                    sa.close();
                    return;
                }
                default -> System.out.println("Opcao invalida, tente novamente");
            }
        }
    }

        static void criarJogador(Scanner sa, ArrayList<Jogador> jogadores) {
            System.out.print("\nNome do jogador: ");
            String nome = sa.nextLine();

            System.out.print("\nVida do jogador: ");
            int vida = sa.nextInt();
            sa.nextLine();

            Jogador novo = new Jogador(nome, vida);
            jogadores.add(novo);

            System.out.printf("\nJogador %s foi criado com sucesso", nome);
        }

        static void listarJogadores(ArrayList<Jogador> jogadores) {
            System.out.printf("\n===Jogadores===");
            for(int i = 0; i < jogadores.size(); i++) {
                System.out.printf("\n%d - %s (vida: %d)",
                        i + 1, jogadores.get(i).nome, jogadores.get(i).vida);
            }
        }

        static void criarArma(Scanner sa, ArrayList<Jogador> jogadores) {
            if(jogadores.isEmpty()) {
                System.out.println("Nenhum jogador criado");
                return;
            }
            listarJogadores(jogadores);
            System.out.print("\nAdicionar arma para qual jogador (numero): ");
            int indice = sa.nextInt() - 1;
            sa.nextLine();

            if(indice < 0 || indice >= jogadores.size()) {
                System.out.println("Jogador invalido");
                return;
            }

            System.out.print("\nNome da arma: ");
            String nomeArma = sa.nextLine();

            System.out.print("\nDano da arma: ");
            int dano = sa.nextInt();
            sa.nextLine();

            System.out.print("\nTipo de arma (Espada, Arco, Cajado): ");
            String tipo = sa.nextLine().toUpperCase();

            Arma novaArma = new Arma(nomeArma, dano, tipo);
            jogadores.get(indice).inventario.adicionarArma(novaArma);
        }

        static void equiparArma(Scanner sa, ArrayList<Jogador> jogadores) {
            if(jogadores.isEmpty()) {
                System.out.println("Nenhum jogador criado");
                return;
            }

            listarJogadores(jogadores);
            System.out.print("Adicionar arma para qual jogador (numero): ");
            int indice = sa.nextInt() - 1;
            sa.nextLine();

            if(indice < 0 || indice >= jogadores.size()) {
                System.out.println("Jogador invalido");
                return;
            }

            Jogador jogador = jogadores.get(indice);

            if(jogador.inventario.totalArmas == 0) {
                System.out.println("O jogador nao possui nenhuma arma");
                return;
            }

            jogador.inventario.listarArmas();
            System.out.print("\nQual arma quer equipar (numero): ");
            int indiceArma = sa.nextInt() - 1;
            sa.nextLine();

            if(indiceArma < 0 || indiceArma >= jogador.inventario.totalArmas) {
                System.out.println("Arma invalida");
                return;
            }

            jogador.equiparArma(jogador.inventario.armas[indiceArma]);
        }

        static void verStatus(ArrayList<Jogador> jogadores) {
            if(jogadores.isEmpty()){
                System.out.println("Nenhum jogador criado");
                return;
            }
            for(Jogador j : jogadores) {
                j.exibirStatus();
            }
        }

        static void batalha(Scanner sa, ArrayList<Jogador> jogadores) {
            if(jogadores.size() < 2) {
                System.out.println("É necessario ao menos dois jogadores para batalhar");
                return;
            }

            ArrayList<Jogador> vivos = new ArrayList<>();
            for(Jogador j : jogadores) {
                if(j.estaVivo) vivos.add(j);
            }

            if(vivos.size() < 2) {
                System.out.println("É necessario ao menos dois jogadores vivos para batalhar");
                return;
            }

            System.out.println("=== INICIAR BATALHA ===");

            int turno = 1;
            Random random = new Random();

            while(vivos.size() > 1) {
                System.out.printf("\n=== Turno %d ===", turno);

                int indiceAtacante = random.nextInt(vivos.size());
                Jogador atacante = vivos.get(indiceAtacante);
                System.out.printf("\nÉ a vez de %s atacar", atacante.nome);

                System.out.println("Escolha o seu alvo: ");
                for(int i = 0; i < vivos.size(); i++) {
                    System.out.printf("\n%d - %s (vida: %d)", i + 1, vivos.get(i).nome, vivos.get(i).vida);
                }

                System.out.println("\nAlvo (numero): ");
                int escolha = sa.nextInt() - 1;
                sa.nextLine();

                if(escolha < 0 || escolha > vivos.size()) {
                    System.out.println("Escolha invalida, turno perdido");
                    turno++;
                    continue;
                }
                Jogador alvo = vivos.get(escolha);
                atacante.atacar(alvo);

                if(!alvo.estaVivo) {
                    vivos.remove(alvo);
                    System.out.printf("O jogador %s foi removido da batalha", alvo.nome);
                }
                turno++;
            }
            System.out.println("\n=== Fim de batalha ===");
            System.out.printf("\nVencedor: %s | vida: %d | arma equipada: %s",
                    vivos.getFirst().nome, vivos.getFirst().vida, vivos.getFirst().armaEquipada);
        }
}