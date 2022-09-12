package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nutrizon
 */
public class TelaPesqExcluirFicha extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String sql1 = null;
    private String cbox;
    private String cbox1;
    private String cbox2;
    private String cbox3;

    /**
     * Creates new form TelaPesquisaCidade
     */
    public TelaPesqExcluirFicha() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // metodo de pesquisar  com filtro
    private void pesquisa() {
        String sql = "select fx.NumFicha as Ficha\n" +
", p.prodDescricao as Serviço\n" +
", pes.pesNome as Paciente\n" +
", fx.dataCad as DataLançamento\n" +
", emp.pesNome as Empresa  from tbfichamedica fx\n" +
"inner join tbprodutos p on p.idprod = fx.idprod\n" +
"inner join tbpessoas pes on pes.idpes = fx.idpes\n" +
"inner join tbempresa emp on emp.idpes = fx.idemp\n" +
"where fx.NumFicha like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesqCidade.getText() + "%");
            rs = pst.executeQuery();
            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
// metodo para setar os campos dos formularios com o conteudo da tabela

    public void setar_campos() {
        txtPesqCidade.setText(null);
        int setar = tblPesqResult.getSelectedRow();
        txtPesqCidCodigo.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        txtPesqCidade.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());

        //manipula os objetos da tela de cadastro de pessoas que foram declarados publicos
        
        String sql = "select * from tbfichamedica where NumFicha = ? ";
        try {
            pst1 = conexao.prepareStatement(sql);
            pst1.setString(1, txtPesqCidCodigo.getText());
            rs1 = pst1.executeQuery();
//            if (rs1.next()) {
//                String chkbox = rs1.getString(2);
//                String chkbox1 = rs1.getString(4);
                //String chkbox2 = rs1.getString(5);
               // String chkbox3 = rs1.getString(20);
                //chekbox Casado ou Solteiro
//                if (chkbox3.equals("Casado")) {
//                    TelaCadastroPessoas.cboCadPesEstadoCivil.setSelectedItem(chkbox3);
//                    cbox3 = "Casado";
//                } else {
//                    TelaCadastroPessoas.cboCadPesEstadoCivil.setSelectedItem(chkbox3);
//                    cbox3 = "Solteiro";
//                }
                //chekbox Masculino ou Feminino
//                if (chkbox2.equals("M")) {
//                    TelaCadastroPessoas.cboCadPesSexo.setSelectedItem(chkbox2);
//                    cbox2 = "M";
//                } else {
//                    TelaCadastroPessoas.cboCadPesSexo.setSelectedItem(chkbox2);
//                    cbox2 = "F";
//                }
                //chekbox juridica ou fisica
//                if (chkbox1.equals("Juridica")) {
//                    TelaCadastroPessoas.cboCadPesTipo.setSelectedItem(chkbox1);
//                    cbox1 = "Juridica";
//                } else {
//                    TelaCadastroPessoas.cboCadPesTipo.setSelectedItem(chkbox1);
//                    cbox1 = "Fisica";
//                }
//chekbox ativo ou inativo
//                if (chkbox.equals("A")) {
//                    TelaCadastroPessoas.cxCadPesAtivo.setSelected(true);
//                    cbox = "A";
//                } else {
//                    TelaCadastroPessoas.cxCadPesAtivo.setSelected(false);
//                    cbox = "I";
//                }
//            }
//            TelaCadastroPessoas.txtCadPesDataCadastro.setText(rs1.getString(3));
//            //TelaCadastroPessoas.txtCadPesDataNascimento.setText(rs1.getString(7));
//            TelaCadastroPessoas.txtCadPesIE.setText(rs1.getString(7));
//            TelaCadastroPessoas.txtCadPesEmail.setText(rs1.getString(9));
//            TelaCadastroPessoas.txtCadPesEndComp.setText(rs1.getString(14));
//            TelaCadastroPessoas.idcidade.setText(rs1.getString(15));
//            TelaCadastroPessoas.txtCadPesBairro.setText(rs1.getString(16));
//            //TelaCadastroPessoas.txtCadPesNaturalidade.setText(rs1.getString(19));
//            TelaCadastroPessoas.txtCadPesObservacao.setText(rs1.getString(17));
//            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        txtPesqCidade = new javax.swing.JTextField();
        txtPesqCidCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesqResult = new javax.swing.JTable();
        btnPesqCancelar = new javax.swing.JButton();
        btnPesqConfirmar = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("Pesquisa de Empresa");
        setToolTipText("");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Digite o Numero da Ficha"));

        txtPesqCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqCidadeKeyReleased(evt);
            }
        });

        txtPesqCidCodigo.setEditable(false);

        jLabel1.setText("* Codigo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtPesqCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesqCidCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtPesqCidade)
                .addComponent(txtPesqCidCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));

        tblPesqResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Ficha", "Serviço", "Paciente", "Data Lançamento", "Empresa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPesqResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesqResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesqResult);
        if (tblPesqResult.getColumnModel().getColumnCount() > 0) {
            tblPesqResult.getColumnModel().getColumn(0).setResizable(false);
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblPesqResult.getColumnModel().getColumn(1).setResizable(false);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblPesqResult.getColumnModel().getColumn(2).setResizable(false);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblPesqResult.getColumnModel().getColumn(3).setResizable(false);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblPesqResult.getColumnModel().getColumn(4).setResizable(false);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
        );

        btnPesqCancelar.setText("Cancelar");
        btnPesqCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqCancelarActionPerformed(evt);
            }
        });

        btnPesqConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/plugin_delete.png"))); // NOI18N
        btnPesqConfirmar.setText("Excluir");
        btnPesqConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnPesqConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnPesqCancelar)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPesqConfirmar)
                    .addComponent(btnPesqCancelar))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        setBounds(50, 50, 814, 467);
    }// </editor-fold>//GEN-END:initComponents


    private void tblPesqResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesqResultMouseClicked
        // chamando o metodo setar campos
        setar_campos();
    }//GEN-LAST:event_tblPesqResultMouseClicked

    private void txtPesqCidadeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqCidadeKeyReleased
        // chamando o metodo pesquisar
        pesquisa();
    }//GEN-LAST:event_txtPesqCidadeKeyReleased

    private void btnPesqConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqConfirmarActionPerformed
        // confirma a seleção
        //validar campos obrigatorios
        if (txtPesqCidCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione a Ficha para Excluir!");
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a Ficha?");
            if (confirma == JOptionPane.YES_NO_OPTION) {
                String sql = "Delete from tbFichaMedica where NumFicha = ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtPesqCidCodigo.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Ficha excluida com sucesso");
                    }
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            dispose();
        }
    }//GEN-LAST:event_btnPesqConfirmarActionPerformed

    private void btnPesqCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqCancelarActionPerformed
        // cancelar a tela
        //TelaLancFichaExameMedico.txtExaCodEmpresa.setText(null);
        //TelaLancFichaExameMedico.txtExaDescEmpresa.setText(null);
        dispose();
//        TelaUF.btnCadAdicionar.setEnabled(true);
//        TelaUF.btnCadEditar.setEnabled(false);
//        TelaUF.btnCadExcluir.setEnabled(false);
    }//GEN-LAST:event_btnPesqCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesqCancelar;
    private javax.swing.JButton btnPesqConfirmar;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPesqResult;
    public static javax.swing.JTextField txtPesqCidCodigo;
    private javax.swing.JTextField txtPesqCidade;
    // End of variables declaration//GEN-END:variables
}
