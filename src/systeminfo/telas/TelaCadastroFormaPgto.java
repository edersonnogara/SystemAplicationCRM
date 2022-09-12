package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nutrizon
 */
public class TelaCadastroFormaPgto extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String cbox;
    private String cbox1;
    private String cbox2;

    /**
     * Creates new form TelaCadastroProdutos
     */
    public TelaCadastroFormaPgto() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    
    
    
    
    // metodo de pesquisar material com filtro
    private void pesquisar_Prod() {
        String sql = "select idFormaPgto as Codigo, pgtoDescricao as Descrição from tbFormaPgto where pgtoDescricao like ? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadPesqPgto.getText() + "%");
            rs = pst.executeQuery();
            tblCadProdPesq.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

// metodo para setar os campos dos formularios com o conteudo da tabela
    public void setar_campos() {
        btnCadProdPesq.setEnabled(false);
        btnCadProdAdic.setEnabled(false);
        btnCadProdCanc.setEnabled(true);
        btnCadProdSalvar.setEnabled(false);
        btnCadProdEdit.setEnabled(true);
//        txtCadProdBarra.setEnabled(true);
        txtCadPgtoDescricao.setEnabled(true);
  //      txtCadProdLiquido.setEnabled(true);
    //    txtCadProdBruto.setEnabled(true);
    //    txtCadProdCusto.setEnabled(true);
      //  txtCadProdAvista.setEnabled(true);
        //txtCadProdLucro.setEnabled(true);
        //txtCadProdEstoque.setEnabled(true);
 //       txtCadProdGrupo.setEnabled(true);
   //     txtCadProdMarca.setEnabled(true);
        txtCadCodPgto.setEnabled(false);
        txtCadPesqPgto.setText(null);
        chkboxCadPgtoAVista.setEnabled(true);
        chkboxCadPgtoPrazo.setEnabled(true);
        int setar = tblCadProdPesq.getSelectedRow();
        txtCadCodPgto.setText(tblCadProdPesq.getModel().getValueAt(setar, 0).toString());
        txtCadPgtoDescricao.setText(tblCadProdPesq.getModel().getValueAt(setar, 1).toString());
        
        String sql = "select * from tbFormaPgto where idFormaPgto = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadCodPgto.getText());
            rs = pst.executeQuery();
            
            if (rs.next()) {
                String chkbox = rs.getString(3);
                if (chkbox.equals("A")) {
                    chkboxCadPgtoAtivo.setSelected(true);
                    cbox = "A";
                } else {
                    chkboxCadPgtoAtivo.setSelected(false);
                    cbox = "I";
                }
            
            txtCadPgtoDescricao.setText(rs.getString(2));
            String chkbox1 = rs.getString(4);
            if (chkbox1.equals("T")) {
                    chkboxCadPgtoAVista.setSelected(true);
                    cbox1 = "T";
                } else {
                    chkboxCadPgtoAVista.setSelected(false);
                    cbox1 = "F";
                }
            String chkbox2 = rs.getString(5);
            if (chkbox2.equals("T")) {
                    chkboxCadPgtoPrazo.setSelected(true);
                    cbox2 = "T";
                } else {
                    chkboxCadPgtoPrazo.setSelected(false);
                    cbox2 = "F";
                }
            txtCadPgtoDiasPrazo.setText(rs.getString(6));
             
            }

            tblCadProdPesq.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//metodo para adiconar 

    private void gravar() {

        String sql = "insert into tbFormaPgto (pgtoDescricao,pgtoAtivo,pgtoAvista,pgtoPrazo,pgtoDiasPrazo)values(?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadPgtoDescricao.getText());
            if (chkboxCadPgtoAtivo.isSelected()) {
                //System.out.print("Está selecionado");
                pst.setString(2, "A");
            } else {
                // System.out.print("Não está selecionado");
                pst.setString(2, "I");
            }
            if (chkboxCadPgtoAVista.isSelected()) {
                pst.setString(3, "T");
                
            } else {
                pst.setString(3, "F");
            }
            if (chkboxCadPgtoPrazo.isSelected()) {
                pst.setString(4, "T");
                
            } else {
                pst.setString(4, "F");
            }
            pst.setString(5, txtCadPgtoDiasPrazo.getText());
            
//            pst.setString(4, txtCadProdLiquido.getText().replace(",", "."));
//            pst.setString(5, txtCadProdBruto.getText().replace(",", "."));
//            pst.setString(6, txtCadProdCusto.getText().replace(",", "."));
//            pst.setString(7, txtCadProdAvista.getText().replace(",", "."));
//            pst.setString(8, txtCadProdLucro.getText().replace(",", "."));
//            pst.setString(9, txtCadProdEstoque.getText().replace(",", "."));
//            pst.setString(10, txtCadProdGrupo.getText());
//            pst.setString(11, txtCadProdMarca.getText());

// validação dos campos obrigatorios
            if ((txtCadPgtoDescricao.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatórios para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Forma de Pagamento Cadastrada");
                    btnCadProdPesq.setEnabled(true);
                    btnCadProdAdic.setEnabled(true);
                    btnCadProdSalvar.setEnabled(false);
                    btnCadProdExcl.setEnabled(false);
                    btnCadProdCanc.setEnabled(false);
                    btnCadProdEdit.setEnabled(false);
                   // txtCadProdBarra.setText(null);
                    txtCadPgtoDescricao.setText(null);
                  //  txtCadProdLiquido.setText(null);
                //    txtCadProdBruto.setText(null);
                //    txtCadProdCusto.setText(null);
                  //  txtCadProdAvista.setText(null);
                 //   txtCadProdLucro.setText(null);
//                    txtCadProdEstoque.setText(null);
//                    txtCadProdGrupo.setText(null);
//                    txtCadProdMarca.setText(null);
                    txtCadCodPgto.setText(null);
                    txtCadPgtoDiasPrazo.setText(null);
                    chkboxCadPgtoAVista.setEnabled(false);
                    chkboxCadPgtoAVista.setSelected(false);
                    chkboxCadPgtoPrazo.setEnabled(false);
                    chkboxCadPgtoPrazo.setSelected(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para excluir
    private void remover() {
        if (txtCadCodPgto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma Forma de Pagamento!");
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?");
            if (confirma == JOptionPane.YES_NO_OPTION) {
                String sql = "Delete from tbFormaPgto where idFormaPgto = ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtCadCodPgto.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Forma de Pagamento excluida com sucesso");
                        btnCadProdPesq.setEnabled(true);
                        btnCadProdAdic.setEnabled(true);
                        btnCadProdSalvar.setEnabled(false);
                        btnCadProdExcl.setEnabled(false);
                        btnCadProdCanc.setEnabled(false);
                        btnCadProdEdit.setEnabled(false);
//                        txtCadProdBarra.setText(null);
                        txtCadPgtoDescricao.setText(null);
//                        txtCadProdLiquido.setText(null);
//                        txtCadProdBruto.setText(null);
//                        txtCadProdCusto.setText(null);
//                        txtCadProdAvista.setText(null);
//                        txtCadProdLucro.setText(null);
//                        txtCadProdEstoque.setText(null);
//                        txtCadProdGrupo.setText(null);
//                        txtCadProdMarca.setText(null);
                        txtCadCodPgto.setText(null);
                        txtCadPgtoDiasPrazo.setText(null);
                        chkboxCadPgtoAVista.setEnabled(false);
                        chkboxCadPgtoAVista.setSelected(false);
                        chkboxCadPgtoPrazo.setEnabled(false);
                        chkboxCadPgtoPrazo.setSelected(false);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }

    // metodo para alterar dados
    private void alterar() {

        String sql = "update tbFormaPgto set pgtoDescricao = ?, pgtoAtivo = ?,pgtoAvista = ?,pgtoPrazo = ?,pgtoDiasPrazo = ?where idFormaPgto = ? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadPgtoDescricao.getText());
            if (chkboxCadPgtoAtivo.isSelected()) {
                //System.out.print("Está selecionado");
                pst.setString(2, "A");
            } else {
                // System.out.print("Não está selecionado");
                pst.setString(2, "I");
            }
            if (chkboxCadPgtoAVista.isSelected()) {
                pst.setString(3, "T");
                
            } else {
                pst.setString(3, "F");
            }
            if (chkboxCadPgtoPrazo.isSelected()) {
                pst.setString(4, "T");
                
            } else {
                pst.setString(4, "F");
            }
            pst.setString(5, txtCadPgtoDiasPrazo.getText());
//            pst.setString(2, txtCadProdBarra.getText());
//            
//            pst.setString(4, txtCadProdLiquido.getText().replace(",", "."));
//            pst.setString(5, txtCadProdBruto.getText().replace(",", "."));
//            pst.setString(6, txtCadProdCusto.getText().replace(",", "."));
//            pst.setString(7, txtCadProdAvista.getText().replace(",", "."));
//            pst.setString(8, txtCadProdLucro.getText().replace(",", "."));
//            pst.setString(9, txtCadProdEstoque.getText().replace(",", "."));
//            pst.setString(10, txtCadProdGrupo.getText());
//            pst.setString(11, txtCadProdMarca.getText());
           pst.setString(6, txtCadCodPgto.getText());

            // validação dos campos obrigatorios
            if ((txtCadPgtoDescricao.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatório para Alterar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Forma de Pagamento alterada com sucesso");
                    btnCadProdPesq.setEnabled(true);
                    btnCadProdAdic.setEnabled(true);
                    btnCadProdSalvar.setEnabled(false);
                    btnCadProdExcl.setEnabled(false);
                    btnCadProdCanc.setEnabled(false);
                    btnCadProdEdit.setEnabled(false);
//                    txtCadProdBarra.setText(null);
                    txtCadPgtoDescricao.setText(null);
//                    txtCadProdLiquido.setText(null);
//                    txtCadProdBruto.setText(null);
//                    txtCadProdCusto.setText(null);
//                    txtCadProdAvista.setText(null);
//                    txtCadProdLucro.setText(null);
//                    txtCadProdEstoque.setText(null);
//                    txtCadProdGrupo.setText(null);
//                    txtCadProdMarca.setText(null);
                    txtCadCodPgto.setText(null);
                    txtCadPgtoDiasPrazo.setText(null);
                    chkboxCadPgtoAVista.setEnabled(false);
                    chkboxCadPgtoAVista.setSelected(false);
                    chkboxCadPgtoPrazo.setEnabled(false);
                    chkboxCadPgtoPrazo.setSelected(false);
                    
                    
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

        jPanel1 = new javax.swing.JPanel();
        txtCadPesqPgto = new javax.swing.JTextField();
        txtCadCodPgto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCadProdPesq = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnCadProdPesq = new javax.swing.JButton();
        btnCadProdAdic = new javax.swing.JButton();
        btnCadProdEdit = new javax.swing.JButton();
        btnCadProdExcl = new javax.swing.JButton();
        btnCadProdCanc = new javax.swing.JButton();
        btnCadProdSalvar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCadPgtoDescricao = new javax.swing.JTextField();
        chkboxCadPgtoAtivo = new javax.swing.JCheckBox();
        chkboxCadPgtoAVista = new javax.swing.JCheckBox();
        chkboxCadPgtoPrazo = new javax.swing.JCheckBox();
        txtCadPgtoDiasPrazo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Cadastro de Forma de Pagamento");
        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquise a Forma de Pagamento"));

        txtCadPesqPgto.setEnabled(false);
        txtCadPesqPgto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadPesqPgtoKeyReleased(evt);
            }
        });

        txtCadCodPgto.setEditable(false);
        txtCadCodPgto.setEnabled(false);

        tblCadProdPesq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Codigo", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCadProdPesq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCadProdPesqMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCadProdPesq);
        if (tblCadProdPesq.getColumnModel().getColumnCount() > 0) {
            tblCadProdPesq.getColumnModel().getColumn(0).setResizable(false);
            tblCadProdPesq.getColumnModel().getColumn(0).setPreferredWidth(25);
            tblCadProdPesq.getColumnModel().getColumn(1).setResizable(false);
            tblCadProdPesq.getColumnModel().getColumn(1).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtCadPesqPgto, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCadCodPgto))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadPesqPgto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadCodPgto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCadProdPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5pesquisar.png"))); // NOI18N
        btnCadProdPesq.setText("Pesquisar");
        btnCadProdPesq.setToolTipText("Pesquisar");
        btnCadProdPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdPesqActionPerformed(evt);
            }
        });

        btnCadProdAdic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5add.png"))); // NOI18N
        btnCadProdAdic.setText("Adicionar");
        btnCadProdAdic.setToolTipText("Adicionar");
        btnCadProdAdic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdAdic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdAdicActionPerformed(evt);
            }
        });

        btnCadProdEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnCadProdEdit.setText("Editar");
        btnCadProdEdit.setToolTipText("Editar");
        btnCadProdEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdEdit.setEnabled(false);
        btnCadProdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdEditActionPerformed(evt);
            }
        });

        btnCadProdExcl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5delete.png"))); // NOI18N
        btnCadProdExcl.setText("Excluir");
        btnCadProdExcl.setToolTipText("Excluir");
        btnCadProdExcl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdExcl.setEnabled(false);
        btnCadProdExcl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdExclActionPerformed(evt);
            }
        });

        btnCadProdCanc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5cancelar.png"))); // NOI18N
        btnCadProdCanc.setText("Cancelar");
        btnCadProdCanc.setToolTipText("Cancelar");
        btnCadProdCanc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdCanc.setEnabled(false);
        btnCadProdCanc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdCancActionPerformed(evt);
            }
        });

        btnCadProdSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnCadProdSalvar.setText("Salvar");
        btnCadProdSalvar.setToolTipText("Salvar");
        btnCadProdSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdSalvar.setEnabled(false);
        btnCadProdSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnCadProdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdAdic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdExcl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdCanc)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnCadProdSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCadProdEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCadProdExcl)
                .addComponent(btnCadProdCanc)
                .addComponent(btnCadProdAdic))
            .addComponent(btnCadProdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Descrição da Forma de Pgto:");

        txtCadPgtoDescricao.setEnabled(false);

        chkboxCadPgtoAtivo.setSelected(true);
        chkboxCadPgtoAtivo.setText("Ativo");

        chkboxCadPgtoAVista.setText("À Vista");
        chkboxCadPgtoAVista.setEnabled(false);
        chkboxCadPgtoAVista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkboxCadPgtoAVistaActionPerformed(evt);
            }
        });

        chkboxCadPgtoPrazo.setText("À Prazo");
        chkboxCadPgtoPrazo.setEnabled(false);
        chkboxCadPgtoPrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkboxCadPgtoPrazoActionPerformed(evt);
            }
        });

        txtCadPgtoDiasPrazo.setEnabled(false);

        jLabel1.setText("Quantidade de Dias:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkboxCadPgtoAtivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadPgtoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(chkboxCadPgtoPrazo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadPgtoDiasPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chkboxCadPgtoAVista))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtCadPgtoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkboxCadPgtoAtivo))
                .addGap(17, 17, 17)
                .addComponent(chkboxCadPgtoAVista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkboxCadPgtoPrazo)
                    .addComponent(txtCadPgtoDiasPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(43, Short.MAX_VALUE))
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
                .addContainerGap(81, Short.MAX_VALUE))
        );

        setBounds(10, 10, 731, 525);
    }// </editor-fold>//GEN-END:initComponents

    private void tblCadProdPesqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCadProdPesqMouseClicked
        // chamando o metodo setar campos
        
        setar_campos();
        pesquisar_Prod();
       
    }//GEN-LAST:event_tblCadProdPesqMouseClicked

    private void txtCadPesqPgtoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadPesqPgtoKeyReleased
        // metos pesquisar
        pesquisar_Prod();
        
    }//GEN-LAST:event_txtCadPesqPgtoKeyReleased

    private void btnCadProdPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdPesqActionPerformed
        // habilita campos para pesquisa
//        tblCadProdPesq.setEnabled(true);
        txtCadPesqPgto.setEnabled(true);
        btnCadProdAdic.setEnabled(false);
        btnCadProdExcl.setEnabled(true);
        btnCadProdCanc.setEnabled(true);
        btnCadProdEdit.setEnabled(true);
//        txtCadProdBarra.setText(null);
        txtCadPgtoDescricao.setText(null);
//        txtCadProdLiquido.setText(null);
//        txtCadProdBruto.setText(null);
//        txtCadProdCusto.setText(null);
//        txtCadProdAvista.setText(null);
//        txtCadProdLucro.setText(null);
//        txtCadProdEstoque.setText(null);
//        txtCadProdGrupo.setText(null);
//        txtCadProdMarca.setText(null);
        txtCadCodPgto.setText(null);
        chkboxCadPgtoAVista.setEnabled(false);
        chkboxCadPgtoAVista.setSelected(false);
        chkboxCadPgtoPrazo.setEnabled(false);
        chkboxCadPgtoPrazo.setSelected(false);
        txtCadPgtoDiasPrazo.setText(null);
        pesquisar_Prod();
    }//GEN-LAST:event_btnCadProdPesqActionPerformed

    private void btnCadProdCancActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdCancActionPerformed
        // habilita campos para pesquisa]
        
        tblCadProdPesq.setRowSelectionAllowed(false);
        txtCadPesqPgto.setEnabled(false);
        btnCadProdPesq.setEnabled(true);
        btnCadProdSalvar.setEnabled(false);
        btnCadProdAdic.setEnabled(true);
        btnCadProdExcl.setEnabled(false);
        btnCadProdCanc.setEnabled(false);
        btnCadProdEdit.setEnabled(false);
//        txtCadProdBarra.setText(null);
        txtCadPgtoDescricao.setText(null);
//        txtCadProdLiquido.setText(null);
//        txtCadProdBruto.setText(null);
//        txtCadProdCusto.setText(null);
//        txtCadProdAvista.setText(null);
//        txtCadProdLucro.setText(null);
//        txtCadProdEstoque.setText(null);
//        txtCadProdGrupo.setText(null);
//        txtCadProdMarca.setText(null);
        txtCadCodPgto.setText(null);

//        txtCadProdBarra.setEnabled(false);
        txtCadPgtoDescricao.setEnabled(false);
//        txtCadProdLiquido.setEnabled(false);
//        txtCadProdBruto.setEnabled(false);
//        txtCadProdCusto.setEnabled(false);
//        txtCadProdAvista.setEnabled(false);
//        txtCadProdLucro.setEnabled(false);
//        txtCadProdEstoque.setEnabled(false);
//        txtCadProdGrupo.setEnabled(false);
//        txtCadProdMarca.setEnabled(false);
        txtCadCodPgto.setEnabled(false);
        chkboxCadPgtoAVista.setEnabled(false);
        chkboxCadPgtoAVista.setSelected(false);
        chkboxCadPgtoPrazo.setEnabled(false);
        chkboxCadPgtoPrazo.setSelected(false);
        txtCadPgtoDiasPrazo.setText(null);
        pesquisar_Prod();
    }//GEN-LAST:event_btnCadProdCancActionPerformed

    private void btnCadProdAdicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdAdicActionPerformed
//        tblCadProdPesq.setEnabled(false);
        btnCadProdPesq.setEnabled(false);
        btnCadProdAdic.setEnabled(false);
        btnCadProdCanc.setEnabled(true);
        btnCadProdSalvar.setEnabled(true);
//        txtCadProdBarra.setEnabled(true);
        txtCadPgtoDescricao.setEnabled(true);
//        txtCadProdLiquido.setEnabled(true);
//        txtCadProdBruto.setEnabled(true);
//        txtCadProdCusto.setEnabled(true);
//        txtCadProdAvista.setEnabled(true);
//        txtCadProdLucro.setEnabled(true);
//        txtCadProdEstoque.setEnabled(true);
//        txtCadProdGrupo.setEnabled(true);
//        txtCadProdMarca.setEnabled(true);
        txtCadCodPgto.setEnabled(false);
        chkboxCadPgtoAVista.setEnabled(true);
        chkboxCadPgtoAVista.setSelected(false);
        chkboxCadPgtoPrazo.setEnabled(true);
        chkboxCadPgtoPrazo.setSelected(false);
        txtCadPgtoDiasPrazo.setText(null);
        pesquisar_Prod();
    }//GEN-LAST:event_btnCadProdAdicActionPerformed

    private void btnCadProdSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdSalvarActionPerformed
        // metodo salvar
        gravar();

    }//GEN-LAST:event_btnCadProdSalvarActionPerformed

    private void btnCadProdExclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdExclActionPerformed
        //metodo remover
        remover();
//        tblCadProdPesq.setEnabled(false);
    }//GEN-LAST:event_btnCadProdExclActionPerformed

    private void btnCadProdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdEditActionPerformed
        // metodo alterar
        alterar();
//        tblCadProdPesq.setEnabled(false);
    }//GEN-LAST:event_btnCadProdEditActionPerformed

    private void chkboxCadPgtoPrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkboxCadPgtoPrazoActionPerformed
        // ao selecionar habilita campo para descrever
        if (chkboxCadPgtoPrazo.isSelected()){
            txtCadPgtoDiasPrazo.setEnabled(true);
            txtCadPgtoDiasPrazo.setText(null);
            chkboxCadPgtoAVista.setSelected(false);
        }else{
            txtCadPgtoDiasPrazo.setEnabled(false);
            txtCadPgtoDiasPrazo.setText(null);
        }
    }//GEN-LAST:event_chkboxCadPgtoPrazoActionPerformed

    private void chkboxCadPgtoAVistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkboxCadPgtoAVistaActionPerformed
        // TODO add your handling code here:
        if (chkboxCadPgtoAVista.isSelected()){
            chkboxCadPgtoPrazo.setSelected(false);
            txtCadPgtoDiasPrazo.setText(null);
            txtCadPgtoDiasPrazo.setEnabled(false);
        }else{
            txtCadPgtoDiasPrazo.setEnabled(false);
            txtCadPgtoDiasPrazo.setText(null);
        }
    }//GEN-LAST:event_chkboxCadPgtoAVistaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadProdAdic;
    private javax.swing.JButton btnCadProdCanc;
    private javax.swing.JButton btnCadProdEdit;
    private javax.swing.JButton btnCadProdExcl;
    private javax.swing.JButton btnCadProdPesq;
    private javax.swing.JButton btnCadProdSalvar;
    private javax.swing.JCheckBox chkboxCadPgtoAVista;
    private javax.swing.JCheckBox chkboxCadPgtoAtivo;
    public static javax.swing.JCheckBox chkboxCadPgtoPrazo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCadProdPesq;
    private javax.swing.JTextField txtCadCodPgto;
    private javax.swing.JTextField txtCadPesqPgto;
    private javax.swing.JTextField txtCadPgtoDescricao;
    public static javax.swing.JTextField txtCadPgtoDiasPrazo;
    // End of variables declaration//GEN-END:variables
}
