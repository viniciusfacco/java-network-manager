/*
 * Classe Janela Gráfico
 * Monta a janela que exibe gráfico
 */

package janelas;

import java.awt.Dimension;
import java.awt.Toolkit;

public class JGrafico extends javax.swing.JFrame {

   public JGrafico(){
       init();
    }

    private void init() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setTitle("Grafico");
        setSize(800, 650);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((tela.width - this.getSize().width) / 2,
             (tela.height - this.getSize().height) / 2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        pack();
    }

}
