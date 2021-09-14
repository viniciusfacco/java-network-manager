/*
 * Classe Thread Grafico
 * Thread para ficar desenhando o gráfico
*/

package gerentederede;

import janelas.JGrafico;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

class TGrafico implements Runnable {

    public boolean gera;
    private final GeradorGrafico gergra;
    private Calendar cal;
    private String hora;
    private ItensGrafico dados;
    private BufferedImage Imagem;
    private String ip;
    private Graphics g;
    private snmpcontrol snmpc;

  TGrafico(JGrafico graf, String host) {
    snmpc = new snmpcontrol();
    ip = host;
    gera = true;
    hora = "";
    cal = new GregorianCalendar();
    gergra = new GeradorGrafico();
    dados = new ItensGrafico();
    g = graf.getGraphics();
  }

  public void run() {
    try {
      do {
        //Thread.sleep(1000);
        cal.setTime(new Date());
        hora =  Integer.toString(cal.get(Calendar.SECOND));
                try {
                    dados.add_element(hora, "Taxa", 0);
                    Imagem = gergra.gerarGraficoLinha("Taxa de Utilização da Interface de Rede: " + ip, "Tempo", "Taxa", dados.getArray());
                    g.drawImage(Imagem, 5, 27, 800, 650, null);
                } catch (Exception ex) {
                    Logger.getLogger(TGrafico.class.getName()).log(Level.SEVERE, null, ex);
                }
        dados.add_element(hora, "Taxa", get_taxa(ip));
        if (dados.get_size() > 15){dados.del_element();}
                try {
                    Imagem = gergra.gerarGraficoLinha("Taxa de Utilização da Interface de Rede: " + ip, "Tempo", "Taxa", dados.getArray());
                } catch (Exception ex) {
                    Logger.getLogger(TGrafico.class.getName()).log(Level.SEVERE, null, ex);
                }
        g.drawImage(Imagem, 5, 27, 800, 650, null);
      } while (gera);
    } catch (InterruptedException exc) {
      System.out.println("TGrafico interrupted.");
    }
  }

  private long get_taxa(String ip) throws InterruptedException{ //frequencia em segundos
        long t1_time = System.currentTimeMillis()/1000;
        long t1_iio = snmpc.get_iio(ip);
        long t1_ioo = snmpc.get_ioo(ip);
        Thread.sleep(10000);
        long t2_time = System.currentTimeMillis()/1000;
        long t2_iio = snmpc.get_iio(ip);
        long t2_ioo = snmpc.get_ioo(ip);
        long is = snmpc.get_is(ip);
        long tx = 0;
        if ((((t2_iio - t1_iio)+(t2_ioo - t1_ioo)) > 0) && (is > 0)) {
            tx = calcula_taxa(t2_iio, t1_iio, t2_ioo, t1_ioo, is, t2_time, t1_time );
        }
        return tx;
    }

    private long calcula_taxa(long iio_t2, long iio_t1, long ioo_t2, long ioo_t1, long is, long t2, long t1){
        float tot_bytes = (iio_t2 - iio_t1)+(ioo_t2 - ioo_t1);
        float tot_bits = tot_bytes * 8;
        float tot_speed = is * (t2 - t1);
        float taxa = tot_bits / tot_speed;
        taxa = taxa * 100;
        return (long) taxa;
        //iio - ifInOctets
        //ioo - ifOutOctets
        //is - ifSpeed
    }
}