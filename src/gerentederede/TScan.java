/*
 * Classe Thread Scan
 * Thread para realizar o scaneamento dos dispositivos
 * Autor: Vinicius Facco Rodrigues
 * Data: 24/06/2010
 * Revisões:
 * V 1.0 _ 24/06/2010 _ Criação
*/
package gerentederede;

import janelas.Principal;
import java.util.Scanner;
import javax.swing.JOptionPane;

class TScan implements Runnable {

    private String ip;
    private final snmpcontrol snmpc;
    private Principal j;
    private controlador control;

    TScan(controlador jcontrol, Principal jconsole, String host) {
        ip = host;
        snmpc = new snmpcontrol();
        j = jconsole;
        control = jcontrol;
    }

    public void run() {
        String ip_host;
        String name;

        while (!ip.substring(ip.length()-1).equals(".")){//testa se ultimo caracter é "."
            ip = ip.substring(0, ip.length()-1);//remove último caracter da string ip
        }
        for (int i = 1; i < 256; i++){
            ip_host = ip+i;
            j.console.append("Consultando: " + ip_host + "\n");
            //System.out.println("Consultando: " + ip_host);
            if (!pinga(ip_host)){
                j.console.append("Dispositivo: " + ip_host + " não gerenciável... \n");
                j.console.setCaretPosition(j.console.getText().length());
                //System.out.println("Dispositivo: " + ip_host + " não gerenciável...");
            } else{
                j.console.append("Dispositivo: " + ip_host + " gerenciável... Adicionando dispositivo... \n");
                j.console.setCaretPosition(j.console.getText().length());
                //System.out.println("Dispositivo: " + ip_host + " gerenciável... Adicionando dispositivo...");
                name = snmpc.get_name(ip_host);
                control.add_disp(name, ip_host);
            }
        }
        JOptionPane.showMessageDialog(null,"Mapeamento finalizado. Foram encontrados "+control.get_disp_tam()+" dispositivos.");
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
                    for (int k = ip.length()+13;k<=fim;k++){
                        if (resposta.substring(k, k+5).equals("tempo")){
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