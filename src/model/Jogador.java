package model;

public class Jogador {
    public String nome;
    public int vida;
    public int nivel;
    public Arma armaEquipada;
    public Inventario inventario;
    public boolean estaVivo;

    public Jogador(String nome, int vida) {
        this.nome = nome;
        this.vida = vida;
        this.nivel = 1;
        this.inventario = new Inventario(5);
        this.armaEquipada = null;
        this.estaVivo = true;
    }

    public void equiparArma(Arma arma) {
        this.armaEquipada = arma;
        System.out.println(nome + " equipou: " + arma.nome);
    }

    public void atacar(Jogador alvo) {
        if (armaEquipada == null) {
            System.out.printf("\n%s nao tem uma arma equipada", nome);
            return;
        }
        alvo.vida -= armaEquipada.dano;
        if (nome.equals(alvo.nome)) {
            System.out.printf("\n%s atacou a si mesmo usando %s, causando %d de dano",
                    nome, armaEquipada.nome, armaEquipada.dano);
        } else {
            System.out.printf("\n%s atacou %s com %s, causando %d de dano",
                    nome, alvo.nome, armaEquipada.nome, armaEquipada.dano);
        }
        if (alvo.vida < 0) {
            alvo.vida = 0;
        }
        if (alvo.vida == 0) {
            System.out.printf("\n%s foi derrotado por %s", alvo.nome, nome);
            alvo.estaVivo = false;
        } else {
            System.out.printf("\nVida de %s: %d", alvo.nome, alvo.vida);
        }
    }

    public void exibirStatus() {
        System.out.printf("\nJogador: %s", nome);
        System.out.printf("\nVida: %d | Nivel: %d", vida, nivel);
        if (armaEquipada != null) {
            System.out.printf("\nArma equipada: %s\n", armaEquipada.nome);
        } else {
            System.out.print("\nNenhuma arma equipada\n");
        }
        inventario.listarArmas();
    }
}
