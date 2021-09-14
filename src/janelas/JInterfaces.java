/*
 * Classe Janela Dispositivos
 * Monta a janela que exibe os dispositivos
 */

package janelas;

import gerentederede.controlador;
import gerentederede.snmpcontrol;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

public class JInterfaces extends JFrame {

    private JButton bt_grafico; //botão gerenciar
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable table_disp;
    private controlador ctrl;
    private snmpcontrol snmpc;
    private String ip;

    public JInterfaces(controlador control, String selected_ip) {
        snmpc = new snmpcontrol();
        ctrl = control;
        selected_ip = ip;
        init();
    }

    private void init() {

        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        table_disp = new JTable();
        bt_grafico = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Interfaces");
        setSize(400, 300);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((tela.width - this.getSize().width) / 2,
             (tela.height - this.getSize().height) / 2);

        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Escolha a interface a ser monitorada"));

        table_disp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                
            },
            new String [] {
                "Número", "Descrição"
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

        bt_grafico.setText("Visualizar");
        bt_grafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                act_bt_grafico(evt);
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
                        .addGap(18, 18, 18)
                        .addComponent(bt_grafico, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_grafico))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void act_bt_grafico(java.awt.event.ActionEvent evt) {
        int[] l = table_disp.getSelectedRows();
        if (l.length == 1){            
            String ifselecionada = String.valueOf(table_disp.getModel()
                    .getValueAt(table_disp.getSelectedRow(), 0));
            snmpc.set_number(ifselecionada);
            ctrl.grafico(ip);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Você deve selecionar apenas 1 dispositivo.");
        }
    }

    private void initable(){
        javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)table_disp.getModel();
        String num;
        String desc;
        snmpc.set_number("0");
        num = snmpc.getnext_ifnumber(ip);
        snmpc.set_number(num);
        desc = snmpc.get_ifdesc(ip);
        dtm.addRow(new Object[]{num,desc});
        for (int i = 1; i < snmpc.get_nint(ip); i++){
            num = snmpc.getnext_ifindex(ip);
            snmpc.set_number(num);
            desc = snmpc.get_ifdesc(ip);
            dtm.addRow(new Object[]{num,desc});
        }
    }

}

