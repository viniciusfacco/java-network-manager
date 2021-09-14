/*
 * Classe Modelo Grafico Item
 * Modelo dos itens para o gerador gráfico
*/


package gerentederede;

import java.io.Serializable;


public class ModeloGraficoItem implements Serializable {

    private String time;
    private String taxa;
    private long valor;

    public ModeloGraficoItem() {

    }

    public ModeloGraficoItem(String time, String taxa, long valor) {
        this.time = time;
        this.taxa = taxa;
        this.valor = valor;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }
}