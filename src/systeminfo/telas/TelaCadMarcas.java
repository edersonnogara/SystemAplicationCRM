package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nutrizon
 */
public class TelaCadMarcas extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private String cbox1;

    private static TelaCadMarcas telaCad;

    public static TelaCadMarcas getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadMarcas();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaMarcasProdutos
     */
    public TelaCadMarcas() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // metodo de pesquisar material com filtro
    private void pesquisar_marca_Prod() {
        String sql = "select idMarca as Codigo, MarcaDescricao as Marca, marcaFuncao as Funcao from tbMarcas where MarcaDescricao like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadMarcasPesq.getText() + "%");
            rs = pst.executeQuery();
            tblCadMarcasPesq.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para setar os campos dos formularios com o conteudo da tabela
    public void setar_campos() {
        txtCadMarcasPesq.setText(null);

        int setar = tblCadMarcasPesq.getSelectedRow();
        txtCadMarcasCod.setText(tblCadMarcasPesq.getModel().getValueAt(setar, 0).toString());
        txtCadNomeMarca.setText(tblCadMarcasPesq.getModel().getValueAt(setar, 1).toString());

        String sql = "select idMarca as Codigo, MarcaDescricao as Marca, marcaFuncao as Funcao from tbMarcas where idMarca = ? group by MarcaDescricao";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadMarcasCod.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                String chkbox = rs.getString(3);
                if (chkbox.equals("MRouter")) {
                    cboCadFuncao.setSelectedItem(chkbox);
                    cbox1 = "MRouter";
                } else {
                    if (chkbox.equals("Acesso")) {
                        cboCadFuncao.setSelectedItem(chkbox);
                        cbox1 = "Acesso";
                    } else {
                        if (chkbox.equals("Borda")) {
                            cboCadFuncao.setSelectedItem(chkbox);
                            cbox1 = "Borda";
                        } else {
                            cboCadFuncao.setSelectedItem(chkbox);
                            cbox1 = "Service Router";
                        }
                    }
                }
            }

            tblCadMarcasPesq.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para adiconar marcas
    private void gravar() {
        String sql = "insert into tbMarcas (MarcaDescricao, marcaFuncao) values (?,?)";
        try {
            pst = conexao.prepareCall(sql);
            pst.setString(1, txtCadNomeMarca.getText());
            pst.setString(2, cboCadFuncao.getSelectedItem().toString());
            // validação dos campos obrigatorios
            if (txtCadNomeMarca.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Marca e Função cadastrada com sucesso");
                    btnCadMarcaAdicionar.setEnabled(true);
                    btnCadMarcaExcluir.setEnabled(false);
                    btnCadMarcaCancelar.setEnabled(false);
                    txtCadMarcasPesq.setEnabled(false);
                    txtCadNomeMarca.setEnabled(false);
                    cboCadFuncao.setEnabled(false);
                    btnCadMarcaPesq.setEnabled(true);
                    btnCadMarcaGravar.setEnabled(false);
                    txtCadNomeMarca.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para excluir a marca
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover a Marca?");
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete from tbMarcas where idMarca = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadMarcasCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Marca removida com sucesso");
                    btnCadMarcaAdicionar.setEnabled(true);
                    btnCadMarcaExcluir.setEnabled(false);
                    btnCadMarcaCancelar.setEnabled(false);
                    txtCadMarcasPesq.setEnabled(false);
                    txtCadNomeMarca.setEnabled(false);
                    btnCadMarcaPesq.setEnabled(true);
                    btnCadMarcaGravar.setEnabled(false);
                    txtCadNomeMarca.setText(null);
                    txtCadMarcasCod.setText(null);
                    cboCadFuncao.setEnabled(false);

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void alterar() {
        String sql = "update tbMarcas set MarcaDescricao = ?, marcaFuncao = ? where idMarca = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadNomeMarca.getText());
            pst.setString(2, cboCadFuncao.getSelectedItem().toString());
            pst.setString(3, txtCadMarcasCod.getText());
            if ((txtCadNomeMarca.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos para Editar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Marca e Função alterada com sucesso");
                    btnCadMarcaAdicionar.setEnabled(true);
                    btnCadMarcaExcluir.setEnabled(false);
                    btnCadMarcaCancelar.setEnabled(false);
                    btnCadMarcaPesq.setEnabled(true);
                    btnCadMarcaGravar.setEnabled(false);
                    txtCadNomeMarca.setText(null);
                    txtCadNomeMarca.setEnabled(false);
                    cboCadFuncao.setEnabled(false);
                }
            }
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

        jPanel2 = new javax.swing.JPanel();
        txtCadMarcasPesq = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCadMarcasPesq = new javax.swing.JTable();
        txtCadMarcasCod = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtCadNomeMarca = new javax.swing.JTextField();
        btnCadMarcaGravar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboCadFuncao = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        btnCadMarcaPesq = new javax.swing.JButton();
        btnCadMarcaAdicionar = new javax.swing.JButton();
        btnCadMarcaExcluir = new javax.swing.JButton();
        btnCadMarcaCancelar = new javax.swing.JButton();
        btnCadMarcaEditar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle("Cadastro de Marcas");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquise as Marcas:"));

        txtCadMarcasPesq.setEnabled(false);
        txtCadMarcasPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadMarcasPesqKeyReleased(evt);
            }
        });

        tblCadMarcasPesq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Descrição", "Função"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCadMarcasPesq.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tblCadMarcasPesq.setEnabled(false);
        tblCadMarcasPesq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCadMarcasPesqMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCadMarcasPesq);
        if (tblCadMarcasPesq.getColumnModel().getColumnCount() > 0) {
            tblCadMarcasPesq.getColumnModel().getColumn(0).setResizable(false);
            tblCadMarcasPesq.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblCadMarcasPesq.getColumnModel().getColumn(1).setResizable(false);
            tblCadMarcasPesq.getColumnModel().getColumn(1).setPreferredWidth(500);
            tblCadMarcasPesq.getColumnModel().getColumn(2).setResizable(false);
            tblCadMarcasPesq.getColumnModel().getColumn(2).setPreferredWidth(300);
        }

        txtCadMarcasCod.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCadMarcasPesq, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCadMarcasCod, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadMarcasPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadMarcasCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastre a Marca:"));

        txtCadNomeMarca.setEnabled(false);
        txtCadNomeMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadNomeMarcaActionPerformed(evt);
            }
        });

        btnCadMarcaGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnCadMarcaGravar.setText("Gravar");
        btnCadMarcaGravar.setEnabled(false);
        btnCadMarcaGravar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadMarcaGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadMarcaGravarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome:");

        jLabel2.setText("Função:");

        cboCadFuncao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Acesso", "Borda", "MRouter", "Service Router" }));
        cboCadFuncao.setToolTipText("");
        cboCadFuncao.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboCadFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCadNomeMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                        .addComponent(btnCadMarcaGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCadNomeMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboCadFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnCadMarcaGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCadMarcaPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1pesquisar.png"))); // NOI18N
        btnCadMarcaPesq.setText("Pesquisar");
        btnCadMarcaPesq.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCadMarcaPesq.setIconTextGap(1);
        btnCadMarcaPesq.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadMarcaPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadMarcaPesqActionPerformed(evt);
            }
        });

        btnCadMarcaAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1add.png"))); // NOI18N
        btnCadMarcaAdicionar.setText("Adicionar");
        btnCadMarcaAdicionar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadMarcaAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadMarcaAdicionarActionPerformed(evt);
            }
        });

        btnCadMarcaExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1delete.png"))); // NOI18N
        btnCadMarcaExcluir.setText("Excluir");
        btnCadMarcaExcluir.setEnabled(false);
        btnCadMarcaExcluir.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadMarcaExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadMarcaExcluirActionPerformed(evt);
            }
        });

        btnCadMarcaCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1cancelar.png"))); // NOI18N
        btnCadMarcaCancelar.setText("Cancelar");
        btnCadMarcaCancelar.setEnabled(false);
        btnCadMarcaCancelar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadMarcaCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadMarcaCancelarActionPerformed(evt);
            }
        });

        btnCadMarcaEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1editar.png"))); // NOI18N
        btnCadMarcaEditar.setText("Editar");
        btnCadMarcaEditar.setToolTipText("Editar");
        btnCadMarcaEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadMarcaEditar.setEnabled(false);
        btnCadMarcaEditar.setPreferredSize(new java.awt.Dimension(135, 57));
        btnCadMarcaEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadMarcaEditarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/2sair.png"))); // NOI18N
        jButton1.setText("Fechar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCadMarcaPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadMarcaAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadMarcaExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadMarcaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadMarcaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCadMarcaExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCadMarcaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCadMarcaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCadMarcaAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCadMarcaPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        setBounds(250, 75, 926, 458);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCadMarcasPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadMarcasPesqKeyReleased
        //chamando o metodo pesquisar
        pesquisar_marca_Prod();
    }//GEN-LAST:event_txtCadMarcasPesqKeyReleased

    private void tblCadMarcasPesqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCadMarcasPesqMouseClicked
        // chamando o metodo setar campos
        setar_campos();
        cboCadFuncao.setEnabled(true);
        txtCadNomeMarca.setEnabled(true);

    }//GEN-LAST:event_tblCadMarcasPesqMouseClicked

    private void btnCadMarcaPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadMarcaPesqActionPerformed
        // habilita campos para pesquisa
        txtCadMarcasPesq.setEnabled(true);
        btnCadMarcaAdicionar.setEnabled(false);
        btnCadMarcaExcluir.setEnabled(true);
        btnCadMarcaCancelar.setEnabled(true);
        btnCadMarcaGravar.setEnabled(false);
        btnCadMarcaEditar.setEnabled(true);
        txtCadNomeMarca.setText(null);
        txtCadMarcasCod.setText(null);
        tblCadMarcasPesq.setEnabled(true);

    }//GEN-LAST:event_btnCadMarcaPesqActionPerformed

    private void btnCadMarcaCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadMarcaCancelarActionPerformed
        // habilita os botoes
        btnCadMarcaAdicionar.setEnabled(true);
        btnCadMarcaExcluir.setEnabled(false);
        btnCadMarcaCancelar.setEnabled(false);
        txtCadMarcasPesq.setEnabled(false);
        txtCadNomeMarca.setEnabled(false);
        btnCadMarcaEditar.setEnabled(false);
        btnCadMarcaPesq.setEnabled(true);
        btnCadMarcaGravar.setEnabled(false);
        txtCadNomeMarca.setText(null);
        cboCadFuncao.setEnabled(false);
        txtCadMarcasCod.setText(null);

    }//GEN-LAST:event_btnCadMarcaCancelarActionPerformed

    private void btnCadMarcaAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadMarcaAdicionarActionPerformed
        // habilita os botoes
        btnCadMarcaPesq.setEnabled(false);
        btnCadMarcaAdicionar.setEnabled(false);
        btnCadMarcaExcluir.setEnabled(false);
        btnCadMarcaCancelar.setEnabled(true);
        txtCadMarcasPesq.setEnabled(false);
        cboCadFuncao.setEnabled(true);
        txtCadNomeMarca.setEnabled(true);
        btnCadMarcaGravar.setEnabled(true);
        txtCadNomeMarca.setText(null);
        tblCadMarcasPesq.setEnabled(false);
        txtCadNomeMarca.requestFocus();
        txtCadMarcasCod.setText(null);
    }//GEN-LAST:event_btnCadMarcaAdicionarActionPerformed

    private void btnCadMarcaExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadMarcaExcluirActionPerformed
        // metodo excluir marca
        remover();

    }//GEN-LAST:event_btnCadMarcaExcluirActionPerformed

    private void btnCadMarcaGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadMarcaGravarActionPerformed
        // grava a marca de produto no banco
        gravar();

    }//GEN-LAST:event_btnCadMarcaGravarActionPerformed

    private void txtCadNomeMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadNomeMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadNomeMarcaActionPerformed

    private void btnCadMarcaEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadMarcaEditarActionPerformed
        // metodo alterar
        alterar();
    }//GEN-LAST:event_btnCadMarcaEditarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadMarcaAdicionar;
    private javax.swing.JButton btnCadMarcaCancelar;
    public static javax.swing.JButton btnCadMarcaEditar;
    private javax.swing.JButton btnCadMarcaExcluir;
    private javax.swing.JButton btnCadMarcaGravar;
    private javax.swing.JButton btnCadMarcaPesq;
    public static javax.swing.JComboBox<String> cboCadFuncao;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCadMarcasPesq;
    private javax.swing.JTextField txtCadMarcasCod;
    private javax.swing.JTextField txtCadMarcasPesq;
    public static javax.swing.JTextField txtCadNomeMarca;
    // End of variables declaration//GEN-END:variables
}
