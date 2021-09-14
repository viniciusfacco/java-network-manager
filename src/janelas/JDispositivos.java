/*
 * Classe Janela Dispositivos
 * Monta a janela que exibe os dispositivos
 */

package janelas;

import gerentederede.controlador;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

public class JDispositivos extends JFrame {

    private JButton btadd; //botão adicionar
    private JButton btrem; //botão remover
    private JButton btger; //botão gerenciar
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable table_disp;
    private controlador ctrl;

    public JDispositivos(controlador control) {
        ctrl = control;
        init();
    }

    private void init() {

        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        table_disp = new JTable();
        btadd = new JButton();
        btrem = new JButton();
        btger = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dispositivos");
        setSize(400, 300);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((tela.width - this.getSize().width) / 2,
             (tela.height - this.getSize().height) / 2);

        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dispositivos"));

        table_disp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                
            },
            new String [] {
                "Nome", "IP", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_disp);

        initable();

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
        );

        btadd.setText("Adicionar");
        btadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                act_btadd(evt);
            }
        });

        btrem.setText("Excluir");
        btrem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                act_btrem(evt);
            }
        });

        btger.setText("Gerenciar");
        btger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                act_btger(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btadd, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btrem, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btger, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btadd)
                    .addComponent(btrem)
                    .addComponent(btger))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void act_btadd(java.awt.event.ActionEvent evt) {
        javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)table_disp.getModel();
        String ip = null;
        ip = JOptionPane.showInputDialog("Insira o IP:");
        if (ip == null || ip.equals("")) {
            JOptionPane.showMessageDialog(null,"Você não inseriu o IP.");
        } else {
            ctrl.man_add_disp(ip);
            String nome = ctrl.get_disp(ip).get_nome();
            String on = ctrl.get_disp(ip).get_on();
            dtm.addRow(new Object[]{nome,ip,on});
            JOptionPane.showMessageDialog(null,"Dispositivo adicionado com sucesso");
        }
        
    }

    private void act_btrem(java.awt.event.ActionEvent evt) {
        int[] l = table_disp.getSelectedRows();
        javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)table_disp.getModel();
        
        for(int i = (l.length-1); i >= 0; --i){
            ctrl.rem_disp(String.valueOf(table_disp.getModel()
                    .getValueAt(table_disp.getSelectedRow(), 1)));
            dtm.removeRow(l[i]);
            JOptionPane.showMessageDialog(null,"Dispositivo removido com sucesso");
        }
    }

    private void act_btger(java.awt.event.ActionEvent evt) {
        int[] l = table_disp.getSelectedRows();
        if (l.length == 1){            
            String ipselecionado = String.valueOf(table_disp.getModel()
                    .getValueAt(table_disp.getSelectedRow(), 1));
            JGerenciar gerenciador = new JGerenciar(ctrl, ipselecionado);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Você deve selecionar apenas 1 dispositivo.");
        }
    }

    public void initable(){
        javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)table_disp.getModel();
        String nome;
        String ip;
        String on;
        String[][] tabela = ctrl.gera_tabela();
        for (int i = 0; i < ctrl.get_disp_tam(); i++){
            nome = tabela[i][0];
            ip = tabela[i][1];
            on = tabela[i][2];
            dtm.addRow(new Object[]{nome,ip,on});
        }
    }

}

