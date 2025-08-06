public class ListaDinamica {
    //Função: Armazena números de linha onde cada palavra aparece
    //Implementação: Lista encadeada ordenada
    private No primeiro;
    private No ultimo;
    private int count = 0;

    public void add(int elemento) {
        No novo = new No(elemento);
        if (primeiro == null) {
            primeiro = novo;
            ultimo = novo;
        } else if (novo.dado < primeiro.dado){
            novo.proximo = primeiro;
            primeiro = novo;
        } else if (novo.dado >= ultimo.dado) {
            ultimo.proximo = novo;
            ultimo = novo;
        } else {
            No aux = primeiro;
            while(novo.dado >= aux.proximo.dado){
                aux = aux.proximo;
            }
            novo.proximo = aux.proximo;
            aux.proximo = novo;
        }
        count++;
    }

    public boolean contains(int elemento) {
        No aux = primeiro;
        while (aux != null) {
            if (aux.dado == elemento) {
                return true;
            }
            aux = aux.proximo;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        No aux = primeiro;
        while (aux != null) {
            sb.append(aux.dado).append(" ");
            aux = aux.proximo;
        }
        return sb.toString().trim();
    }

    public int size() {
        return count;
    }
}