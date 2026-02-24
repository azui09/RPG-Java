package model;

public class Inventario {
    public Arma[] armas;
    public int capacidade;
    public int totalArmas;

    public Inventario(int capacidade) {
        this.capacidade = capacidade;
        this.armas = new Arma[capacidade];
        this.totalArmas = 0;
    }

    public void adicionarArma(Arma arma) {
        if (totalArmas < capacidade) {
            armas[totalArmas] = arma;
            totalArmas++;
            System.out.println(arma.nome + " adicionada com sucesso");
        } else {
            System.out.println("Inventario cheio, nao foi possivel adicionar " + arma.nome);
        }
    }

    public void listarArmas() {
        System.out.println("=== Inventario ===");
        for (int i = 0; i < totalArmas; i++) {
            System.out.printf("%d - %s | Dano: %d | tipo: %s\n",
                    i + 1, armas[i].nome, armas[i].dano, armas[i].tipo);
        }
    }
}
