import java.io.BufferedWriter;
import java.io.IOException;

public class ArvoreBinariaBusca {
    //Função: Armazena palavras em ordem alfabética e suas ocorrências

    //Estrutura: Árvore binária de busca com nós contendo:
    class Nodo {
        public String valor;
        public ListaDinamica ocorrencias;
        public Nodo esquerdo;
        public Nodo direito;
        public boolean ehPalavraChave; // NOVO CAMPO

        public Nodo(String valor) {
            this.valor = valor;
            this.ocorrencias = new ListaDinamica();
            this.esquerdo = null;
            this.direito = null;
            this.ehPalavraChave = false; // Inicialmente false
        }

        public void adicionarOcorrencia(int linha) {
            if (!ocorrencias.contains(linha)) {
                ocorrencias.add(linha);
            }
        }
    }

    public Nodo raiz;
    public int nElementos;

    public ArvoreBinariaBusca() {
        this.raiz = null;
        this.nElementos = 0;
    }

    public int tamanho() {
        return this.nElementos;
    }

    public boolean estaVazia() {
        return this.raiz == null;
    }

    //Metodo público para inserir adaptado para receber a linha e se é palavra-chave
    public void inserir(String valor, int linha, boolean ehPalavraChave) {
        this.raiz = this.inserir(valor, linha, ehPalavraChave, this.raiz);
    }

    private Nodo inserir(String valor, int linha, boolean ehPalavraChave, Nodo nodo) { // PARÂMETROS NOVOS
        if (nodo == null) {
            Nodo novo = new Nodo(valor);
            novo.ehPalavraChave = ehPalavraChave; // Define se é palavra-chave
            if (linha != -1) { // Apenas adiciona a linha se for uma ocorrência real
                novo.adicionarOcorrencia(linha);
            }
            this.nElementos++;
            return novo;
        }

        int comparacao = valor.compareTo(nodo.valor);

        if (comparacao < 0) {
            nodo.esquerdo = this.inserir(valor, linha, ehPalavraChave, nodo.esquerdo);
        } else if (comparacao > 0) {
            nodo.direito = this.inserir(valor, linha, ehPalavraChave, nodo.direito);
        } else {
            // Palavra já existe
            if (ehPalavraChave) { // Se a inserção atual é de uma palavra-chave, marca o nodo como palavra-chave
                nodo.ehPalavraChave = true;
            }
            if (linha != -1) { // Adiciona a ocorrência apenas se for uma linha válida
                nodo.adicionarOcorrencia(linha);
            }
        }

        return nodo;
    }

    public boolean contem(String valor) {
        return this.busca(valor, this.raiz);
    }

    private boolean busca(String valor, Nodo nodo) {
        if (nodo == null) {
            return false;
        }

        int comparacao = valor.compareTo(nodo.valor);

        if (comparacao < 0) {
            return this.busca(valor, nodo.esquerdo);
        } else if (comparacao > 0) {
            return this.busca(valor, nodo.direito);
        } else {
            return true;
        }
    }

    // Metodo original, talvez não seja mais usado se listarOcorrenciasPalavrasChave for o principal
    public void listarOcorrencias(BufferedWriter bw) throws IOException {
        listarOcorrenciasRec(raiz, bw);
    }

    private void listarOcorrenciasRec(Nodo nodo, BufferedWriter bw) throws IOException {
        if (nodo != null) {
            listarOcorrenciasRec(nodo.esquerdo, bw); // Esquerda
            String ocorrencias = nodo.ocorrencias.toString().replaceAll("[\\[\\],]", "").replace("-1", "").trim();
            if (!ocorrencias.isEmpty()) {
                bw.write(nodo.valor + " " + ocorrencias + "\n");
            }
            listarOcorrenciasRec(nodo.direito, bw); // Direita
        }
    }

    // NOVO METODO: Listar apenas as ocorrências de palavras-chave
    public void listarOcorrenciasPalavrasChave(BufferedWriter bw) throws IOException {
        listarOcorrenciasPalavrasChaveRec(raiz, bw);
    }

    private void listarOcorrenciasPalavrasChaveRec(Nodo nodo, BufferedWriter bw) throws IOException {
        if (nodo != null) {
            listarOcorrenciasPalavrasChaveRec(nodo.esquerdo, bw); // Esquerda
            if (nodo.ehPalavraChave) { // APENAS SE FOR UMA PALAVRA-CHAVE
                String ocorrencias = nodo.ocorrencias.toString().replaceAll("[\\[\\],]", "").replace("-1", "").trim();
                // A ocorrência -1 (da inserção inicial de palavra-chave) não deve aparecer
                if (!ocorrencias.isEmpty()) {
                    bw.write(nodo.valor + " " + ocorrencias + "\n");
                }
            }
            listarOcorrenciasPalavrasChaveRec(nodo.direito, bw); // Direita
        }
    }

    public void imprimirArvore() {
        imprimirArvoreRec(raiz, 0);
    }

    private void imprimirArvoreRec(Nodo nodo, int nivel) {
        if (nodo == null) return;

        imprimirArvoreRec(nodo.direito, nivel + 1);

        for (int i = 0; i < nivel; i++) {
            System.out.print("    ");
        }
        System.out.println(nodo.valor + (nodo.ehPalavraChave ? " (PC)" : "")); // Indica se é palavra-chave

        imprimirArvoreRec(nodo.esquerdo, nivel + 1);
    }

    // Métodos originais da sua BST adaptados para String
    public void imprimeEmOrdem() {
        this.emOrdem(this.raiz);
        System.out.println();
    }

    private void emOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        this.emOrdem(nodo.esquerdo);
        System.out.print(nodo.valor + " ");
        this.emOrdem(nodo.direito);
    }

    public void imprimePreOrdem() {
        this.preOrdem(this.raiz);
        System.out.println();
    }

    private void preOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        System.out.print(nodo.valor + " ");
        this.preOrdem(nodo.esquerdo);
        this.preOrdem(nodo.direito);
    }

    public void imprimePosOrdem() {
        this.posOrdem(this.raiz);
        System.out.println();
    }

    private void posOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        this.posOrdem(nodo.esquerdo);
        this.posOrdem(nodo.direito);
        System.out.print(nodo.valor + " ");
    }

    private int altura(Nodo nodo) {
        if (nodo == null) {
            return -1;
        }

        int alturaEsquerda = this.altura(nodo.esquerdo) + 1;
        int alturaDireita = this.altura(nodo.direito) + 1;

        return alturaEsquerda > alturaDireita ? alturaEsquerda : alturaDireita;
    }

    public int altura() {
        return this.altura(this.raiz);
    }
}