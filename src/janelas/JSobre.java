/*
 * Classe Janela Sobre
 * Monta a janela de informalções sobre o trabalho
 */

package janelas;

import javax.swing.*;

public class JSobre extends JFrame {

    private JLabel comp1;
    private JLabel comp2;
    private JLabel componentes;
    private JLabel data;
    private JSeparator separador_inferior;
    private JSeparator separador_superior;
    private JLabel titulo;

    public JSobre() {
        init();
    }

    private void init() {

        titulo = new JLabel();
        separador_superior = new JSeparator();
        componentes = new JLabel();
        comp1 = new JLabel();
        comp2 = new JLabel();
        separador_inferior = new JSeparator();
        data = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        titulo.setFont(new java.awt.Font("Tahoma", 1, 14));
        titulo.setText("SOGIL Sociedade de Ônibus Gigante Ltda.");

        componentes.setFont(new java.awt.Font("Tahoma", 1, 11));
        componentes.setText("Componentes:");

        comp1.setText("Vinicius Facco Rodrigues");

        comp2.setText("Analista de TI");

        data.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        data.setText("Desenvolvedor: Vinicius Facco Rodrigues. 2010");

        javax.swing.GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(data, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(separador_inferior, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(comp2, GroupLayout.Alignment.LEADING)
                    .addComponent(comp1, GroupLayout.Alignment.LEADING)
                    .addComponent(componentes, GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(titulo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(separador_superior, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(titulo)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separador_superior, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(componentes)
                .addGap(18, 18, 18)
                .addComponent(comp1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comp2)
                .addGap(52, 52, 52)
                .addComponent(separador_inferior, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(data)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }

}
