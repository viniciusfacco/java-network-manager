/*
 * Classe Principal
 * Monta a janela de gerenciamento SNMP do dispositivo
 */

package janelas;

import gerentederede.controlador;
import gerentederede.snmpcontrol;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JGerenciar extends javax.swing.JFrame {

    private javax.swing.JButton btalterar;
    private javax.swing.JButton btconsultar;
    private javax.swing.JButton btgrafico;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_valor;
    private javax.swing.ButtonGroup opcoes;
    private javax.swing.JPanel painel_consultas;
    private javax.swing.JRadioButton radio_ifnumber;
    private javax.swing.JRadioButton radio_processos;
    private javax.swing.JRadioButton radio_proprietario;
    private javax.swing.JRadioButton radio_so;
    private javax.swing.JTextArea texto;
    private javax.swing.JTextField valor_pro_name;
    private String ip;
    private snmpcontrol snmp;
    private controlador ctrl;

    public JGerenciar(controlador control, String host) {
        ip = host;
        snmp = new snmpcontrol();
        ctrl = control;
        init();
    }

    private void init() {

        opcoes = new javax.swing.ButtonGroup();
        painel_consultas = new javax.swing.JPanel();
        radio_ifnumber = new javax.swing.JRadioButton();
        radio_processos = new javax.swing.JRadioButton();
        radio_so = new javax.swing.JRadioButton();
        radio_proprietario = new javax.swing.JRadioButton();
        btconsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        texto = new javax.swing.JTextArea();
        valor_pro_name = new javax.swing.JTextField();
        label_valor = new javax.swing.JLabel();
        btalterar = new javax.swing.JButton();
        btgrafico = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento SNMP");
        setSize(400, 300);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((tela.width - this.getSize().width) / 2,
             (tela.height - this.getSize().height) / 2);

        setVisible(true);

        painel_consultas.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultas SNMP"));

        opcoes.add(radio_ifnumber);
        radio_ifnumber.setText("Numero de Interfaces");
        radio_ifnumber.setMnemonic(1);
        radio_ifnumber.setSelected(true);

        opcoes.add(radio_processos);
        radio_processos.setText("Processos");
        radio_processos.setMnemonic(2);

        opcoes.add(radio_so);
        radio_so.setText("Sistema Operacional");
        radio_so.setMnemonic(3);

        opcoes.add(radio_proprietario);
        radio_proprietario.setText("Proprietario");
        radio_proprietario.setMnemonic(4);

        javax.swing.GroupLayout painel_consultasLayout = new javax.swing.GroupLayout(painel_consultas);
        painel_consultas.setLayout(painel_consultasLayout);
        painel_consultasLayout.setHorizontalGroup(
            painel_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_consultasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radio_ifnumber)
                    .addComponent(radio_processos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                .addGroup(painel_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radio_so)
                    .addComponent(radio_proprietario))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        painel_consultasLayout.setVerticalGroup(
            painel_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_consultasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_ifnumber)
                    .addComponent(radio_so))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painel_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_processos)
                    .addComponent(radio_proprietario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btconsultar.setText("Consultar");
        btconsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                act_btconsultar(evt);
            }
        });

        texto.setColumns(20);
        texto.setEditable(false);
        texto.setRows(5);
        texto.append("Gerenciando host ip: " + ip);
        jScrollPane1.setViewportView(texto);

        label_valor.setText("Valor");

        btalterar.setText("Alterar");
        btalterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btalterarActionPerformed(evt);
            }
        });

        btgrafico.setText("Gráfico");
        btgrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btgraficoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(painel_consultas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(label_valor)
                        .addGap(18, 18, 18)
                        .addComponent(valor_pro_name, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(btconsultar)
                        .addGap(18, 18, 18)
                        .addComponent(btalterar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btgrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painel_consultas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valor_pro_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_valor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btconsultar)
                    .addComponent(btalterar)
                    .addComponent(btgrafico))
                .addContainerGap())
        );

        pack();
    }

    private void act_btconsultar(java.awt.event.ActionEvent evt) {
        switch (opcoes.getSelection().getMnemonic()){ //qual tipo de requisição para saber qual será a consulta
            case 1 :
                texto.append("\n Iniciando consulta ao número de Interfaces");
                texto.append("\n Resposta: " + snmp.getnext_ifnumber(ip));
                break;
            case 2 :
                texto.append("\n Iniciando consulta a Processos");
                texto.append("\n Resposta: " + snmp.get_cont_process(ip));
                break;
            case 3 :
                texto.append("\n Iniciando consulta a Sistema Operacional");
                texto.append("\n Resposta: " + snmp.get_os_name(ip));
                break;
            case 4 :
                texto.append("\n Iniciando consulta a Proprietario");
                texto.append("\n Resposta: " + snmp.get_pro_name(ip));
                break;
        }        
    }

    private void btalterarActionPerformed(java.awt.event.ActionEvent evt) {
        switch (opcoes.getSelection().getMnemonic()){ //qual tipo de requisição para saber qual será a consulta
            case 1 :
                texto.append("\n Este valor não pode ser alterado.");
                break;
            case 2 :
                texto.append("\n Este valor não pode ser alterado.");
                break;
            case 3 :
                texto.append("\n Este valor não pode ser alterado.");
                break;
            case 4 :
                texto.append("\n Iniciando alteração de: Proprietario");
                snmp.set_pro_name(ip, valor_pro_name.getText());
                texto.append("\n Resposta: " + snmp.get_pro_name(ip));
                break;
        }
    }

    private void btgraficoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ctrl.grafico(ip);
            //JInterfaces option_interface = new JInterfaces(ctrl, ip);
        } catch (Exception ex) {
            Logger.getLogger(JGerenciar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
