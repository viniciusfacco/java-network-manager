/*
 * Classe Mail System
 * Utilizada para envio de email's de alerta
*/


package gerentederede;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

class mailsystem {

    void envia(String msg) {
        SimpleEmail email = new SimpleEmail();

        try {
        email.setDebug(true);
        email.setHostName("192.1.1.200");
        email.setAuthentication("informatica","infotisogil");
        email.setSSL(false);
        email.addTo("vinicius.rodrigues@sogil.com.br"); //email destino
        email.setFrom("informatica@sogil.com.br"); //aqui necessita ser o email que fara a autenticacao
        email.setSubject("Alerta!");
        email.setMsg(msg);
        email.send();

        } catch (EmailException e) {

        System.out.println(e.getMessage());

        }
    }

}
