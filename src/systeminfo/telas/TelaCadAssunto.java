package systeminfo.telas;

import java.sql.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nogara
 */
public class TelaCadAssunto extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultListModel modelo;
    String[] Codig;
    int Enter = 0;

    private static TelaCadAssunto telaCad;

    public static TelaCadAssunto getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadAssunto();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaCadAssunto
     */
    public TelaCadAssunto() {
        initComponents();
        conexao = ModuloConexao.conector();
        modelo = new DefaultListModel();
    }

    // metodo de pesquisar 
    private void pesquisar_Prod() {
        String sql = "select idassunto as Codigo, assNome as Descrição, assStatus as Status from tbassunto where assNome like ? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadPesq.getText() + "%%");
            rs = pst.executeQuery();
            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));
            //redimencionar colunas na tabela da tela após a pesquisa
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(350);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(60);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

// metodo para setar os campos dos formularios com o conteudo da tabela
    public void setar_campos() {
        txtCadPesq.setText(null);
        int setar = tblPesqResult.getSelectedRow();
        txtCadCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        txtCodigo.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        txtCadDescricao.setText(tblPesqResult.getModel().getValueAt(setar, 1).toString());
        cboxAtivo.setSelectedItem(tblPesqResult.getModel().getValueAt(setar, 2).toString());

        tblPesqResult.setVisible(false);
        btnAdicionar.setEnabled(false);
        btnRemover.setEnabled(true);
        btnEditar.setEnabled(true);

        txtCadDescricao.setEnabled(true);
        txtCadPesq.setFocusable(true);
        txtCadCod.setEnabled(false);
        txtCodigo.setEnabled(false);

    }
//metodo para adiconar 

    private void gravar() {

        String sql = "insert into tbassunto (assNome, assStatus) values (?,?);";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadDescricao.getText());
            pst.setString(2, cboxAtivo.getSelectedItem().toString());

// validação dos campos obrigatorios
            if ((txtCadDescricao.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatórios para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Assunto cadastrado com sucesso");

                    btnAdicionar.setEnabled(true);
                    btnSalvar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnCancela.setEnabled(false);
                    btnEditar.setEnabled(false);

                    txtCadDescricao.setText(null);

                    txtCadCod.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para excluir
    private void remover() {
        if (txtCadCod.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um Assunto");
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?");
            if (confirma == JOptionPane.YES_OPTION) {
                String sql = "Delete from tbassunto where idassunto = ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtCadCod.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Assunto Excluido com sucesso");

                        btnAdicionar.setEnabled(true);
                        btnSalvar.setEnabled(false);
                        btnRemover.setEnabled(false);
                        btnCancela.setEnabled(false);
                        btnEditar.setEnabled(false);

                        txtCadDescricao.setText(null);

                        txtCadCod.setText(null);

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }

    // metodo para alterar dados
    private void alterar() {

        String sql = "update tbassunto set assNome = ?, assStatus = ? where idassunto = ? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadDescricao.getText());
            pst.setString(2, cboxAtivo.getSelectedItem().toString());
            pst.setString(3, txtCodigo.getText());

            // validação dos campos obrigatorios
            if ((txtCadDescricao.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatório para Alterar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Assunto alterado com sucesso");

                    btnAdicionar.setEnabled(true);
                    btnSalvar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnCancela.setEnabled(false);
                    btnEditar.setEnabled(false);
                    txtCadDescricao.setText(null);
                    txtCodigo.setText(null);
                    txtCadCod.setText(null);
                    txtCadDescricao.setEnabled(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void cancela() {
        txtCadCod.setText(null);
        txtCadCod.setEnabled(false);

        txtCadPesq.setEnabled(true);
        tblPesqResult.setVisible(false);

        txtCadDescricao.setEnabled(false);
        txtCadDescricao.setText(null);

        txtCodigo.setEnabled(false);
        txtCodigo.setText(null);

        txtCadPesq.setFocusable(true);

        btnAdicionar.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancela.setEnabled(true);
        btnFechar.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCadPesq = new javax.swing.JTextField();
        txtCadCod = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesqResult = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCadDescricao = new javax.swing.JTextField();
        cboxAtivo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();

        setTitle("Cadastro de Assuntos");
        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquise:"));

        txtCadPesq.setNextFocusableComponent(txtCadPesq);
        txtCadPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadPesqKeyReleased(evt);
            }
        });

        txtCadCod.setEditable(false);
        txtCadCod.setEnabled(false);

        tblPesqResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Descrição", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPesqResult.setEnabled(false);
        tblPesqResult.getTableHeader().setResizingAllowed(false);
        tblPesqResult.getTableHeader().setReorderingAllowed(false);
        tblPesqResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesqResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesqResult);
        if (tblPesqResult.getColumnModel().getColumnCount() > 0) {
            tblPesqResult.getColumnModel().getColumn(0).setResizable(false);
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(25);
            tblPesqResult.getColumnModel().getColumn(1).setResizable(false);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblPesqResult.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel6.setText("* Codigo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtCadPesq)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadCod, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5add.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.setPreferredSize(new java.awt.Dimension(123, 41));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnEditar.setText("Salvar Edição");
        btnEditar.setToolTipText("Salvar Edição");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5delete.png"))); // NOI18N
        btnRemover.setText("Excluir");
        btnRemover.setToolTipText("Excluir");
        btnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemover.setEnabled(false);
        btnRemover.setPreferredSize(new java.awt.Dimension(123, 41));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnCancela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5cancelar.png"))); // NOI18N
        btnCancela.setText("Cancelar");
        btnCancela.setToolTipText("Cancelar");
        btnCancela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancela.setNextFocusableComponent(txtCadPesq);
        btnCancela.setPreferredSize(new java.awt.Dimension(123, 41));
        btnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelaActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setToolTipText("Salvar");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setEnabled(false);
        btnSalvar.setPreferredSize(new java.awt.Dimension(123, 41));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/2sair.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.setPreferredSize(new java.awt.Dimension(123, 41));
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("*Descrição do Assunto:");

        txtCadDescricao.setEnabled(false);

        cboxAtivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));

        jLabel1.setText("Código:");

        txtCodigo.setEditable(false);
        txtCodigo.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txtCodigo.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboxAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCadDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        setBounds(2, 2, 861, 525);
    }// </editor-fold>//GEN-END:initComponents

    private void tblPesqResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesqResultMouseClicked
        // chamando o metodo setar campos
        setar_campos();

    }//GEN-LAST:event_tblPesqResultMouseClicked

    private void txtCadPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadPesqKeyReleased
        // metos pesquisar
        pesquisar_Prod();
        tblPesqResult.setVisible(true);
    }//GEN-LAST:event_txtCadPesqKeyReleased

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        cancela();


    }//GEN-LAST:event_btnCancelaActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        btnAdicionar.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnRemover.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancela.setEnabled(true);
        btnFechar.setEnabled(true);

        tblPesqResult.setVisible(false);
        txtCadPesq.setEnabled(false);
        txtCadCod.setEnabled(false);

        txtCadDescricao.setEnabled(true);
        txtCodigo.setEnabled(false);


    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // metodo salvar
        gravar();

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        //metodo remover
        remover();

    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // metodo alterar
        alterar();

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed

        // limpar os campos
        //   txtusuBloqueado.setText(null);
        txtCadPesq.setEnabled(true);
        txtCadCod.setText(null);
        tblPesqResult.setVisible(false);
        //cboUsuPerfil.setSelectedItem(null);

        txtCadCod.setEnabled(false);
        txtCodigo.setEnabled(false);

        txtCodigo.setText(null);

        btnAdicionar.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancela.setEnabled(true);
        btnFechar.setEnabled(true);

        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    public static javax.swing.JComboBox<String> cboxAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPesqResult;
    private javax.swing.JTextField txtCadCod;
    private javax.swing.JTextField txtCadDescricao;
    public static javax.swing.JTextField txtCadPesq;
    public static javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
