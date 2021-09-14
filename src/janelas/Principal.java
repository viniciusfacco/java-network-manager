/*
 * Classe Principal
 * Monta a janela e inicializa controlador
 */

package janelas;

import gerentederede.controlador;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Principal extends JFrame {

    private JMenu menu_arq;
    private JMenu menu_oper;
    private JMenu menu_ajuda;
    private JMenuItem arq_sair;
    private JMenuItem oper_listar;
    private JMenuItem oper_incluir;
    private JMenuItem oper_scan;
    private JMenuItem ajuda_sobre;
    private JMenuBar menubar;
    private controlador control;
    
    public JScrollPane consoleScrollPane;    
    public JTextArea console;
    
    public Principal() {
        init();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    private void init() {

        try {
            control = new controlador();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        menubar = new JMenuBar();
        menu_arq = new JMenu();
        menu_oper = new JMenu();
        menu_ajuda = new JMenu();
        arq_sair = new JMenuItem();
        oper_listar = new JMenuItem();
        oper_incluir = new JMenuItem();
        oper_scan = new JMenuItem();
        ajuda_sobre = new JMenuItem();
        consoleScrollPane = new JScrollPane();
        console = new JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerente de Rede");
        setSize(400, 300);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((tela.width - this.getSize().width) / 2,
             (tela.height - this.getSize().height) / 2);

        //inicio configuração dos menus
        menu_arq.setText("Arquivo");
            arq_sair.setText("Sair");
            menu_arq.add(arq_sair);
        menubar.add(menu_arq);

        menu_oper.setText("Operações");
            oper_listar.setText("Dispositivos");
            menu_oper.add(oper_listar);
            oper_incluir.setText("Adicionar Dispositivo");
            menu_oper.add(oper_incluir);
            oper_scan.setText("Auto Mapeamento");
            menu_oper.add(oper_scan);
        menubar.add(menu_oper);

        menu_ajuda.setText("Ajuda");
            ajuda_sobre.setText("Sobre...");
            menu_ajuda.add(ajuda_sobre);
        menubar.add(menu_ajuda);

        setJMenuBar(menubar);
        //fim configuração dos menus
        //inicio configuração console
        console.setBackground(new java.awt.Color(240, 240, 240));
        console.setColumns(20);
        console.setEditable(false);
        console.setFont(new java.awt.Font("Arial", 0, 10));
        console.setForeground(new java.awt.Color(150, 150, 150));
        console.setAutoscrolls(true);
        console.setRows(5);
        consoleScrollPane.setViewportView(console);
        //fim configuração console
        //inicio ações
        arq_sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                act_arq_sair(evt);
            }
        });

        oper_listar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                act_oper_listar(evt);
            }
        });

        oper_incluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                act_oper_incluir(evt);
            }
        });

        oper_scan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                act_oper_scan(evt);
            }
        });

        ajuda_sobre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                act_ajuda_sobre(evt);
            }
        });
        //fim ações

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
         layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            //.addGap(0, 400, Short.MAX_VALUE));
            .addComponent(consoleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
         layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            //.addGap(0, 298, Short.MAX_VALUE));
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(consoleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)));
         
        pack();
    }

    private void act_arq_sair(ActionEvent evt) {
        System.exit(0);
    }

    private void act_oper_listar(ActionEvent evt) {
        //chamar janela com lista de dispositivos
        JDispositivos jdisp = new JDispositivos(control);
    }

    private void act_oper_incluir(ActionEvent evt) {
        //chamar janela de inclusão
        String ip = null;
        ip = JOptionPane.showInputDialog("Insira o IP:");
        if (ip == null || ip.equals("")) {
            JOptionPane.showMessageDialog(null,"Você não inseriu o IP.");
        } else {
            control.man_add_disp(ip);
            JOptionPane.showMessageDialog(null,"Dispositivo adicionado com sucesso");
        }
    }

    private void act_oper_scan(ActionEvent evt) {
        //chamar janela de scaneamento
        //System.out.println("chamar janela de scaneamento");
        boolean opt = JOptionPane.showConfirmDialog(null, "Deseja iniciar o mapeamento automatico?",
                "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0;
        if (opt){
            control.auto_scan(this);
            //JOptionPane.showMessageDialog(null,"Mapeamento finalizado. Foram encontrados "+control.get_disp_tam()+" dispositivos.");
        }
    }

    private void act_ajuda_sobre(ActionEvent evt) {
        //chamar janela de informações
        JSobre sobre = new JSobre();
    }
}
