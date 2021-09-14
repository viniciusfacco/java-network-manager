/*
 * Classe Controlador
 * Configura o fluxo básico do sistema
 * Autor: Vinicius Facco Rodrigues
 * Data: 24/06/2010
 * Revisões:
 * V 1.0 _ 24/06/2010 _ Readequação
*/


package gerentederede;

import janelas.JGrafico;
import janelas.Principal;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class controlador {

    private InetAddress Host; //ip da máquina onde o gerente está rodando
    private ArrayList<dispositivo> disp; //vetor para guardar os dispositivos
    private snmpcontrol snmpc; //controlador para requisições snmp
    private Timer monitor; //timer a ser configurado para agendamento de verificação
    private TimerTask verificar; //tarefa a ser agendada para ser executada de tempos em tempos ---> agenda o ntmonitor
    private NetworkMonitor ntmonitor; //objeto responsável por gerar os alertas

    public controlador() throws Exception{
        Host = null;
        disp = new ArrayList<dispositivo>();
        snmpc = new snmpcontrol();
        set_Host();
        ntmonitor = new NetworkMonitor();
        verificar = new TimerTask(){
                public void run(){
                    //System.out.println("Verificando dispositivos...");
                    ntmonitor.varredura(disp);
                }
            };
        agenda_ntmonitor(30);
    }

    public int get_disp_tam(){
        return disp.size();
    }

    public String get_disp(int pos){
        return disp.get(pos).get_nome();
    }

    public int get_disp_index(String ip){
        int i = -1;
        for (int j = 0; j < disp.size(); j++){
            if (disp.get(j).get_ip().equals(ip)){
                i = j;
                break;
            }
        }
        return i;
    }

    public String[][] gera_tabela(){
        String[][] tb = new String[disp.size()][3];
        for (int i = 0; i < disp.size(); i++){
            tb[i][0] = disp.get(i).get_nome();
            tb[i][1] = disp.get(i).get_ip();
            tb[i][2] = disp.get(i).get_on();
        }
        return tb;
    }

    public void auto_scan(Principal j){

        TScan scan = new TScan(this, j, Host.getHostAddress());
        Thread newThrd = new Thread(scan);
        newThrd.start();
        /*
        String ip = Host.getHostAddress();//obtem ip do Host
        String ip_host;
        String name;

        while (!ip.substring(ip.length()-1).equals(".")){//testa se ultimo caracter é "."
            ip = ip.substring(0, ip.length()-1);//remove último caracter da string ip
        }

        for (int i = 1; i < 256; i++){
            ip_host = ip+i;
            System.out.println("Consultando: " + ip_host);
            name = snmpc.get_name(ip_host);
            if (name.equals("")){
                j.console.append("Dispositivo: " + ip + " não gerenciável...");
                System.out.println("Dispositivo: " + ip + " não gerenciável...");
            } else{
                j.console.append("Dispositivo: " + ip + " gerenciável... Adicionando dispositivo...");
                System.out.println("Dispositivo: " + ip + " gerenciável... Adicionando dispositivo...");
                add_disp(name, ip_host);
            }
        }
        */
    }

    public void set_Host(){
        InetAddress ia = null;  
        try {ia = InetAddress.getLocalHost();}
        catch (UnknownHostException e) {}
        Host = ia;
    }

    public void add_disp(String name, String ip){
            disp.add(new dispositivo(name, ip, "on"));
    }

    public void man_add_disp(String ip){
        String name;
        String status;
        name = snmpc.get_name(ip);
        if (!name.equals("")){
            status = "on";
        } else {
            name = "-";
            status = "off";
        }
        disp.add(new dispositivo(name, ip, status));
    }

    public void rem_disp(String ip){
        int index = get_disp_index(ip);
        if (index > -1){
            disp.remove(index);
        }else{
            System.out.println("Dispositivo não encontrado...");
        }
    }

    public dispositivo get_disp(String ip){
        int i = 0;
        boolean achou = false;
        dispositivo d = null;
        while ((i < disp.size()) && (!achou)){
            if (disp.get(i).get_ip().equals(ip)){
                achou = true;
                d = disp.get(i);
            }
            i++;
        }
        return d;
    }


    public void agenda_ntmonitor(int seconds) {
        monitor = new Timer();
        //monitor.schedule(new RemindTask(), seconds * 1000);
        monitor.scheduleAtFixedRate(verificar, new Date(), seconds * 1000);
  }

   public void grafico(String ip){
        final JGrafico grafico = new JGrafico();
        final TGrafico mt = new TGrafico(grafico, ip);
        Thread newThrd = new Thread(mt);
        newThrd.start();
        grafico.addMouseListener(new MouseListener(){

            public void mouseClicked(MouseEvent e) {
                //System.out.println("mouseClicked");
                grafico.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                mt.gera = false;
            }

            public void mousePressed(MouseEvent e) {
                //System.out.println("mousePressed");
            }

            public void mouseReleased(MouseEvent e) {
                //System.out.println("mouseReleased");
            }

            public void mouseEntered(MouseEvent e) {
                //System.out.println("mouseEntered");
            }

            public void mouseExited(MouseEvent e) {
                //System.out.println("mouseExited");
            }

        });
    }

    /* faz um ping no ip - não é mais utilizada
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
    */
}