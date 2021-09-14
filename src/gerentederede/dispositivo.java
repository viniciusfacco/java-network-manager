/*
 * Classe Dispositivo
 * Configura cada Dispositivo
*/

package gerentederede;

public class dispositivo {

    private String nome;
    private String ip;
    private float taxa;
    private String status;

    public dispositivo(String n, String i, String st){
        nome = n;
        ip = i;
        status = st;
    }

    public String get_on(){
        return status;
    }

    public float get_taxa(){
       return taxa;
    }

    public String get_nome(){
        return nome;
    }

    public String get_ip(){
        return ip;
    }

    public void set_nome(String n){
        nome = n;
    }

    public void set_ip(String i){
        ip = i;
    }

    public void set_on(String o){
        status = o;
    }

    public void set_taxa(float t){
        taxa = t;
    }
    
}
