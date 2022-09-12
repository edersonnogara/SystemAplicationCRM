/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import systeminfo.dal.Gerentedetelas;

import systeminfo.dal.ModuloConexao;
import static systeminfo.telas.TelaPrincipal.Desktop;

/**
 *
 * @author Nogara
 */
public class TelaCadCidade extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    Gerentedetelas gerentedetelas;
    
    private static TelaCadCidade telaCad;

    public static TelaCadCidade getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadCidade();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaUF
     */
    public TelaCadCidade() {
        initComponents();
        conexao = ModuloConexao.conector();
        btnSalvar.setEnabled(true);
        this.gerentedetelas = new Gerentedetelas(Desktop);
    }

//adicionar cidade
    private void adicionar() {
        String sql = "insert into tbcidade(cidNome, cidSigla, cidIBGE, cidCEP, cidBairro, cidEstado, cidPais) values(?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadCid.getText());
            pst.setString(2, txtCadCidSigla.getText());
            pst.setString(3, txtCadCidIBGE.getText());
            pst.setString(4, txtCadCidCEP.getText());
            pst.setString(5, txtCadCidBairro.getText());
            pst.setString(6, cboCadEstado.getSelectedItem().toString());
            pst.setString(7, cboCadPais.getSelectedItem().toString());
            //valida campos obrigatorios 
            if ((txtCadCid.getText().isEmpty()) || (txtCadCidSigla.getText().isEmpty()) || (txtCadCidIBGE.getText().isEmpty()) || (txtCadCidCEP.getText().isEmpty()) || (txtCadCidBairro.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos para Salvar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cidade cadastrada com sucesso");
                    txtCadCid.setText(null);
                    txtCadCidCEP.setText(null);
                    txtCadCidBairro.setText(null);
                    txtCadCidIBGE.setText(null);
                    txtCadCidSigla.setText(null);
                    
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para alterar
    private void alterar() {
        String sql = "update tbcidade set cidNome = ?, cidSigla = ?, cidIBGE = ?, cidCEP = ?, cidBairro = ?, cidEstado = ?, cidPais = ? where idcidade = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadCid.getText());
            pst.setString(2, txtCadCidSigla.getText());
            pst.setString(3, txtCadCidIBGE.getText());
            pst.setString(4, txtCadCidCEP.getText());
            pst.setString(5, txtCadCidBairro.getText());
            pst.setString(6, cboCadEstado.getSelectedItem().toString());
            pst.setString(7, cboCadPais.getSelectedItem().toString());
            pst.setString(8, txtCadCidCodigo.getText());
            if ((txtCadCid.getText().isEmpty()) || (txtCadCidSigla.getText().isEmpty()) || (txtCadCidIBGE.getText().isEmpty()) || (txtCadCidCEP.getText().isEmpty()) || (txtCadCidBairro.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos para Salvar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados da Cidade alterados com sucesso");
                    txtCadCidCodigo.setText(null);
                    txtCadCid.setText(null);
                    txtCadCidCEP.setText(null);
                    txtCadCidBairro.setText(null);
                    txtCadCidIBGE.setText(null);
                    btnEditar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnSalvar.setEnabled(true);
                    txtCadCidSigla.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void cancela() {
        // a estrutura abaixo cancela os dados
        {
            // limpar os campos

            txtCadCidCodigo.setText(null);
            txtCadCid.setText(null);
            txtCadCidSigla.setText(null);
            txtCadCidIBGE.setText(null);
            txtCadCidCEP.setText(null);
            txtCadCidBairro.setText(null);

            btnSalvar.setEnabled(true);
            btnRemover.setEnabled(false);
            btnEditar.setEnabled(false);
            btnCancela.setEnabled(true);
            btnFechar.setEnabled(true);

        }

    }

    //remover cidade
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover a cidade?");
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete from tbcidade where idcidade = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadCidCodigo.getText());
                if ((txtCadCid.getText().isEmpty()) || (txtCadCidCodigo.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Selecione uma Cidade para remover");
                } else {
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cidade removida com sucesso");
                    txtCadCidCodigo.setText(null);
                    txtCadCid.setText(null);
                    txtCadCidCEP.setText(null);
                    txtCadCidBairro.setText(null);
                    txtCadCidIBGE.setText(null);
                    txtCadCidSigla.setText(null);
                    btnEditar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnSalvar.setEnabled(true);
                }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCadCid = new javax.swing.JTextField();
        cboCadEstado = new javax.swing.JComboBox<>();
        cboCadPais = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        btnCadPesq = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCadCidCodigo = new javax.swing.JTextField();
        txtCadCidIBGE = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCadCidCEP = new javax.swing.JTextField();
        txtCadCidBairro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCadCidSigla = new javax.swing.JTextField();

        setTitle("Cadastro de Cidades");

        jLabel1.setText("Cidade:");

        jLabel2.setText("Estado:");

        jLabel3.setText("País:");

        cboCadEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "EX" }));
        cboCadEstado.setToolTipText("");

        cboCadPais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brasil" }));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnEditar.setText("Salvar Edição");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnSalvar.setText("Gravar");
        btnSalvar.setToolTipText("Gravar");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5delete.png"))); // NOI18N
        btnRemover.setText("Excluir");
        btnRemover.setEnabled(false);
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnCancela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5cancelar.png"))); // NOI18N
        btnCancela.setText("Cancelar");
        btnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelaActionPerformed(evt);
            }
        });

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/2sair.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar)
                .addGap(139, 139, 139))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancela)
                    .addComponent(btnFechar))
                .addContainerGap())
        );

        btnCadPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesqActionPerformed(evt);
            }
        });

        jLabel4.setText("Codigo:");

        txtCadCidCodigo.setEditable(false);
        txtCadCidCodigo.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txtCadCidCodigo.setEnabled(false);
        txtCadCidCodigo.setFocusable(false);
        txtCadCidCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadCidCodigoActionPerformed(evt);
            }
        });

        jLabel5.setText("Cod IBGE:");

        jLabel6.setText("CEP:");

        jLabel7.setText("Bairro:");

        jLabel8.setText("Sigla:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCadCidCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadCid, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadCidSigla, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCadPais, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadCidIBGE, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadCidCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadCidBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCadEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(143, 143, 143)))
                .addComponent(btnCadPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCidCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtCadCid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadPesq))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCidSigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCidIBGE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCadCidCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCadCidBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboCadEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboCadPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        setBounds(2, 2, 700, 459);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesqActionPerformed
        // CHAMA A TELA DE PESQUISA
        gerentedetelas.abrirjanelas(TelaPesquisaCidade.getInstancia());
        btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true);
        btnRemover.setEnabled(true);
    }//GEN-LAST:event_btnCadPesqActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // metodo adicionar
        adicionar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtCadCidCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadCidCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadCidCodigoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // chama metodo editar
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // chama metodo remover
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        // chama o botao cancelar
        cancela();

    }//GEN-LAST:event_btnCancelaActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed

        // limpar os campos
        //   txtusuBloqueado.setText(null);
        //cboUsuPerfil.setSelectedItem(null);

        txtCadCidCodigo.setText(null);
        txtCadCid.setText(null);
        txtCadCidSigla.setText(null);
        txtCadCidIBGE.setText(null);
        txtCadCidCEP.setText(null);
        txtCadCidBairro.setText(null);

        btnSalvar.setEnabled(true);
        btnRemover.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancela.setEnabled(true);
        btnFechar.setEnabled(true);

        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadPesq;
    private javax.swing.JButton btnCancela;
    public static javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFechar;
    public static javax.swing.JButton btnRemover;
    public static javax.swing.JButton btnSalvar;
    public static javax.swing.JComboBox<String> cboCadEstado;
    public static javax.swing.JComboBox<String> cboCadPais;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JTextField txtCadCid;
    public static javax.swing.JTextField txtCadCidBairro;
    public static javax.swing.JTextField txtCadCidCEP;
    public static javax.swing.JTextField txtCadCidCodigo;
    public static javax.swing.JTextField txtCadCidIBGE;
    public static javax.swing.JTextField txtCadCidSigla;
    // End of variables declaration//GEN-END:variables

    private void tELA() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void TelaPesquisaCidade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
