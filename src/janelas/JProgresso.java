/*
 * Classe Janela Progresso
 * Monta a janela que exibe barra de progresso
 */

package janelas;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

public class JProgresso extends JDialog {

    public JProgresso(){
        this.setTitle("Aguarde...");
        setModal(false);
        setVisible(true);
        JProgressBar progress = new JProgressBar();
        progress.setIndeterminate(true);
        progress.setSize(200,20);
        getContentPane().add(progress);
        pack();
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((tela.width - this.getSize().width) / 2,
             (tela.height - this.getSize().height) / 2);
    }

}

