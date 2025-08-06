import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o caminho do arquivo de palavras-chave: ");
        String caminhoPalavrasChave = scanner.nextLine();

        System.out.print("Digite o caminho do arquivo de texto: ");
        String caminhoTexto = scanner.nextLine();

        System.out.print("Digite o caminho do arquivo de saída: ");
        String caminhoSaida = scanner.nextLine();

        TabelaHash indiceRemissivo = new TabelaHash(26);

        // 1. Ler e marcar as palavras-chave na TabelaHash
        lerPalavrasChave(caminhoPalavrasChave, indiceRemissivo);
        // 2. Contar ocorrências de TODAS as palavras do texto, adicionando-as à hash
        contarOcorrenciasNoTexto(caminhoTexto, indiceRemissivo);
        // 3. Salvar o índice remissivo, filtrando APENAS as palavras-chave
        salvarIndiceRemissivo(caminhoSaida, indiceRemissivo);

        System.out.println("Índice remissivo gerado com sucesso!"); // Esta é a única mensagem que permanecerá
    }

    public static void lerPalavrasChave(String caminho, TabelaHash indiceRemissivo) {
        //Lê palavras-chave e insere na tabela hash (marcando como palavra-chave)
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] palavras = linha.split("\\s+");
                for (String palavra : palavras) {
                    palavra = palavra.replaceAll("[^\\p{L}\\d-]", "").toLowerCase();
                    if (!palavra.isEmpty()) {
                        // Insere a palavra-chave na TabelaHash, marcando-a como palavra-chave (true)
                        // Linha -1 é um placeholder para palavras-chave que não têm ocorrência inicial no texto
                        indiceRemissivo.inserir(palavra, -1, true);
                        // System.out.println("Palavra-chave marcada: " + palavra); // REMOVIDO DO CONSOLE
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de palavras-chave: " + e.getMessage());
        }
    }
    //Processa o texto, contando ocorrências de todas as palavras
    public static void contarOcorrenciasNoTexto(String caminhoTexto, TabelaHash indiceRemissivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoTexto))) {
            String linha;
            int numeroLinha = 1;

            while ((linha = br.readLine()) != null) {
                String[] palavras = linha.split("\\s+");

                for (String palavra : palavras) {
                    palavra = palavra.replaceAll("[^\\p{L}\\d-]", "").toLowerCase();
                    // System.out.println("Processando palavra do texto: " + palavra + " (Linha: " + numeroLinha + ")"); // REMOVIDO DO CONSOLE

                    if (palavra.isEmpty()) {
                        continue;
                    }

                    // Insere TODAS as palavras do texto na TabelaHash com suas linhas.
                    // O `false` indica que, por padrão, não é uma palavra-chave.
                    // Se a palavra já foi inserida como palavra-chave (pelo metodo lerPalavrasChave),
                    // o Nodo existente terá seu `ehPalavraChave` como true e as ocorrências serão adicionadas.
                    indiceRemissivo.inserir(palavra, numeroLinha, false);
                }
                numeroLinha++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de texto: " + e.getMessage());
        }
    }
    //Gera arquivo de saída apenas com palavras-chave e suas ocorrências
    public static void salvarIndiceRemissivo(String caminhoSaida, TabelaHash indiceRemissivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoSaida))) {
            for (ArvoreBinariaBusca arvore : indiceRemissivo.tabela) {
                if (arvore != null) {
                    arvore.listarOcorrenciasPalavrasChave(bw);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o índice remissivo: " + e.getMessage());
        }
    }
}