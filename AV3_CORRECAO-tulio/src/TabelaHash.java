import java.text.Normalizer;

public class TabelaHash {
    public ArvoreBinariaBusca[] tabela; // Array de ArvoreBinariaBusca (uma para cada letra do alfabeto)
    private int tamanho;

    public TabelaHash(int capacidade) {
        this.tabela = new ArvoreBinariaBusca[capacidade];
        this.tamanho = capacidade;
    }

    // Normaliza palavra: remove acento, minúscula. Singularização removida.
    public String normalizarPalavra(String palavra) {
        if (palavra == null || palavra.isEmpty()) return "";
        //Remove acentos e converte para minúsculas
        String normalizada = Normalizer.normalize(palavra, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();


        return normalizada;
    }
    // Determina a posição na tabela hash baseada na primeira letra
    private int calcularIndice(String chave) {
        if (chave == null || chave.isEmpty()) {
            return 0; // Ou um índice padrão, como 0 ou 25 para "outros"
        }

        char primeiraLetra = chave.charAt(0);
        if (primeiraLetra >= 'a' && primeiraLetra <= 'z') {
            return primeiraLetra - 'a';
        } else {
            return 0; // Caracteres não-alfabéticos (tratar como 'a' ou primeiro bucket)
        }
    }

    // Sobrecarga para inserir palavra-chave (sem linha inicial, apenas marcando como tal)
    public void inserir(String palavra) {
        inserir(palavra, -1, true); // Chama o metodo completo, linha -1, é palavra-chave
    }

    // Metodo para inserir palavra (do texto ou palavra-chave) com linha e flag
    public void inserir(String palavra, int linha, boolean ehPalavraChave) {
        String palavraNormalizada = normalizarPalavra(palavra);
        if (palavraNormalizada.isEmpty()) return;

        int indice = calcularIndice(palavraNormalizada);
        if (tabela[indice] == null) {
            tabela[indice] = new ArvoreBinariaBusca();
        }
        tabela[indice].inserir(palavraNormalizada, linha, ehPalavraChave);
    }

    // Busca pela palavra normalizada (retorna a ArvoreBinariaBusca)
    public ArvoreBinariaBusca buscar(String palavra) {
        String palavraNormalizada = normalizarPalavra(palavra);
        int indice = calcularIndice(palavraNormalizada);
        ArvoreBinariaBusca arvore = tabela[indice];
        return arvore;
    }
}