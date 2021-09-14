/*
 * Classe Itens Grafico
 * Gera os itens para serem incluidos no grafico
*/



package gerentederede;

import java.util.ArrayList;

public class ItensGrafico {

    protected ArrayList array = new ArrayList();

    public ItensGrafico() {
    }

    public void add_element(String tempo, String tipo, long valor){
        array.add(new ModeloGraficoItem(tempo, tipo, valor));
    }

    public void del_element(){
        array.remove(0);
    }

    public int get_size(){
        return array.size();
    }

    public ArrayList getArray() {
        return array;
    }

    public void setArray(ArrayList array) {
        this.array = array;
    }
}