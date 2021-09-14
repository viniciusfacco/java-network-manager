/*
 * Classe Network Monitor
 * Monitora se as maquinas ficaram offline ou se alguma ultrapassou o limite
 * de 80% de utilização da rede
*/

package gerentederede;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkMonitor {

    private snmpcontrol snmpc;
    private mailsystem mail;
    private final float limite_taxa = 80;

    public void NetwokMonitor(){
    }

    public void varredura(ArrayList<dispositivo> dispositivos){
        snmpc = new snmpcontrol();
        mail = new mailsystem();
        String status;
        for (int i = 0; i < dispositivos.size(); i++){
            status = dispositivos.get(i).get_on();            
            set_status(dispositivos.get(i));
            if (dispositivos.get(i).get_on().equals("on")){
                set_taxa(dispositivos.get(i));
                if (dispositivos.get(i).get_taxa() > limite_taxa){
                    alerta_taxa(dispositivos.get(i).get_ip());
                }
            }
            if (dispositivos.get(i).get_on().equals("off") && status.equals("on")){
                alerta_status(dispositivos.get(i).get_ip());
            }            
        }
    }

    private void set_taxa(dispositivo d) {
        try {
            float tx = get_taxa(d.get_ip());
            d.set_taxa(tx);
        } catch (InterruptedException ex) {
            Logger.getLogger(NetworkMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void set_status(dispositivo d) {
       if (pinga(d.get_ip())){
           d.set_on("on");
       } else {
           d.set_on("off");
       }
    }

    private void alerta_status(String ip) {
        String msg = "O dispositivo ip: " + ip + " estava online e ficou offline.";
        mail.envia(msg);
    }

    private void alerta_taxa(String ip) {
        String msg = "A taxa de utilizacao da interface ip: " + ip + " e superior a 80 %";
        mail.envia(msg);
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

    public boolean pinga(String ip){
        String resposta = "";
        int fim = 0;
        boolean online = false;
        String comando = "ping -n 1 -w 600 " + ip;
            try {
                Scanner s = new Scanner( Runtime.getRuntime().exec("cmd /c " + comando).getInputStream());
                while(s.hasNextLine()) {
                    resposta = s.nextLine()+"\n";
                    fim = resposta.length()-5;
                    for (int j=ip.length()+13;j<=fim;j++){
                        if (resposta.substring(j, j+5).equals("tempo")){
                            online = true;
                            break;
                        }
                    }
                    if (online) break;
                 }
            } catch (Exception e) {}
        return online;
    }
}
