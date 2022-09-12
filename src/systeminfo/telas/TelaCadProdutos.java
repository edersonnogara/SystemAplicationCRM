package systeminfo.telas;

import java.sql.*;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nogara
 */
public class TelaCadProdutos extends javax.swing.JInternalFrame { 
   
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultListModel modelo;
    private String cbox;

    private static TelaCadProdutos telaProd;
    
    public static  TelaCadProdutos getInstancia(){
        if (telaProd == null){
            telaProd = new TelaCadProdutos();
        }
        return telaProd;
    }
    
    
    /**
     * Creates new form TelaCadastroProdutos
     */
    public TelaCadProdutos() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

//metodo para adiconar 
    private void gravar() {

        String sql = "insert into tbProdutos (prodDescricao,prodAtivo,prodCodigo,prodMAC,prodMarca,prodTorre,prodAlmox)values(?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadProdDescricao.getText());
            pst.setString(2, chkboxCadProdAtivo.getSelectedItem().toString());
//            if (chkboxCadProdAtivo.isSelected()) {
//                //System.out.print("Está selecionado");
//                pst.setString(2, "A");
//            } else {
//                // System.out.print("Não está selecionado");
//                pst.setString(2, "I");
//            }

            pst.setString(3, txtCadProdBarra.getText());
            pst.setString(4, txtCadProdMAC.getText());
            pst.setString(5, txtCadProdMarca.getText());
            pst.setString(6, txtCadProdTorre.getText());
            pst.setString(7, txtCadProdAlmox.getText());

// validação dos campos obrigatorios
            if ((txtCadProdBarra.getText().isEmpty()) || (txtCadProdDescricao.getText().isEmpty())
                    || (txtCadProdMAC.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os Campos para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
                    btnCadProdPesq.setEnabled(true);
                    btnCadProdAdic.setEnabled(true);
                    btnCadProdSalvar.setEnabled(false);
                    btnCadProdExcl.setEnabled(false);
                    btnCadProdCanc.setEnabled(false);
                    btnCadProdEdit.setEnabled(false);

                    txtCadProdBarra.setEnabled(false);
                    txtCadProdDescricao.setEnabled(false);
                    txtCadProdMarca.setEnabled(false);
                    btnCadProdPesqMarca.setEnabled(false);

                    txtCadProdMAC.setEnabled(false);
                    txtCadProdTorre.setEnabled(false);
                    btnCadProdPesqTorre.setEnabled(false);
                    txtCadProdAlmox.setEnabled(false);
                    btnCadProdPesqAlmox.setEnabled(false);

                    txtCadProdBarra.setText(null);
                    txtCadProdDescricao.setText(null);
                    txtCadProdMarca.setText(null);
                    txtCadProdMAC.setText(null);
                    txtCadProdTorre.setText(null);
                    txtCadProdAlmox.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para excluir
    private void remover() {
        if (txtCadCodProd.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um produto!");
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o Produto?");
            if (confirma == JOptionPane.YES_NO_OPTION) {
                String sql = "Delete from tbProdutos where idprod = ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtCadCodProd.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Produto excluido com sucesso");
                        btnCadProdPesq.setEnabled(true);
                        btnCadProdAdic.setEnabled(true);
                        btnCadProdSalvar.setEnabled(false);
                        btnCadProdExcl.setEnabled(false);
                        btnCadProdCanc.setEnabled(false);
                        btnCadProdEdit.setEnabled(false);

                        txtCadProdBarra.setEnabled(false);
                        txtCadProdDescricao.setEnabled(false);
                        txtCadProdMarca.setEnabled(false);
                        btnCadProdPesqMarca.setEnabled(false);
                        txtCadCodProd.setEnabled(false);
                        txtCadProdMAC.setEnabled(false);
                        txtCadProdTorre.setEnabled(false);
                        btnCadProdPesqTorre.setEnabled(false);
                        txtCadProdAlmox.setEnabled(false);
                        btnCadProdPesqAlmox.setEnabled(false);

                        txtCadProdBarra.setText(null);
                        txtCadProdDescricao.setText(null);
                        txtCadProdMarca.setText(null);
                        txtCadCodProd.setText(null);
                        txtCadProdMAC.setText(null);
                        txtCadProdTorre.setText(null);
                        txtCadProdAlmox.setText(null);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }

    // metodo para alterar dados
    private void alterar() {

        String sql = "update tbprodutos set prodDescricao = ?, prodAtivo = ?, prodCodigo = ?, prodMAC = ?, prodMarca = ?, prodTorre = ?, prodAlmox = ? where idprod = ? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadProdDescricao.getText());
            pst.setString(2, chkboxCadProdAtivo.getSelectedItem().toString());
//            if (chkboxCadProdAtivo.isSelected()) {
//                //System.out.print("Está selecionado");
//                pst.setString(2, "A");
//            } else {
//                // System.out.print("Não está selecionado");
//                pst.setString(2, "I");
//            }

            pst.setString(3, txtCadProdBarra.getText());
            pst.setString(4, txtCadProdMAC.getText());
            pst.setString(5, txtCadProdMarca.getText());
            pst.setString(6, txtCadProdTorre.getText());
            pst.setString(7, txtCadProdAlmox.getText());
            pst.setString(8, txtCadCodProd.getText());
            // validação dos campos obrigatorios
            if ((txtCadProdBarra.getText().isEmpty()) || (txtCadProdDescricao.getText().isEmpty())
                    || (txtCadProdMAC.getText().isEmpty())
                    || (txtCadProdAlmox.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os Campos para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto alterado com sucesso");
                    btnCadProdPesq.setEnabled(true);
                    btnCadProdAdic.setEnabled(true);
                    btnCadProdSalvar.setEnabled(false);
                    btnCadProdExcl.setEnabled(false);
                    btnCadProdCanc.setEnabled(false);
                    btnCadProdEdit.setEnabled(false);

                    txtCadProdBarra.setEnabled(false);
                    txtCadProdDescricao.setEnabled(false);
                    txtCadProdMarca.setEnabled(false);
                    btnCadProdPesqMarca.setEnabled(false);
                    txtCadCodProd.setEnabled(false);
                    txtCadProdMAC.setEnabled(false);
                    txtCadProdTorre.setEnabled(false);
                    btnCadProdPesqTorre.setEnabled(false);
                    txtCadProdAlmox.setEnabled(false);
                    btnCadProdPesqAlmox.setEnabled(false);

                    txtCadProdBarra.setText(null);
                    txtCadProdDescricao.setText(null);
                    txtCadProdMarca.setText(null);
                    txtCadCodProd.setText(null);
                    txtCadProdMAC.setText(null);
                    txtCadProdTorre.setText(null);
                    txtCadProdAlmox.setText(null);
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
        btnCadProdPesq = new javax.swing.JButton();
        btnCadProdAdic = new javax.swing.JButton();
        btnCadProdEdit = new javax.swing.JButton();
        btnCadProdExcl = new javax.swing.JButton();
        btnCadProdCanc = new javax.swing.JButton();
        btnCadProdSalvar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCadProdDescricao = new javax.swing.JTextField();
        txtCadProdMarca = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCadProdBarra = new javax.swing.JTextField();
        btnCadProdPesqMarca = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtCadProdMAC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCadProdAlmox = new javax.swing.JTextField();
        txtCadProdTorre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnCadProdPesqTorre = new javax.swing.JButton();
        btnCadProdPesqAlmox = new javax.swing.JButton();
        txtCadCodProd = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        chkboxCadProdAtivo = new javax.swing.JComboBox<>();

        setClosable(true);
        setTitle("Cadastro de Itens");
        setToolTipText("");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCadProdPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5pesquisar.png"))); // NOI18N
        btnCadProdPesq.setText("Pesquisar");
        btnCadProdPesq.setToolTipText("Pesquisar");
        btnCadProdPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdPesq.setPreferredSize(new java.awt.Dimension(113, 21));
        btnCadProdPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdPesqActionPerformed(evt);
            }
        });

        btnCadProdAdic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5add.png"))); // NOI18N
        btnCadProdAdic.setText("Adicionar");
        btnCadProdAdic.setToolTipText("Adicionar");
        btnCadProdAdic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdAdic.setPreferredSize(new java.awt.Dimension(113, 21));
        btnCadProdAdic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdAdicActionPerformed(evt);
            }
        });

        btnCadProdEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnCadProdEdit.setText("Salvar Edição");
        btnCadProdEdit.setToolTipText("Salvar Edição");
        btnCadProdEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdEdit.setEnabled(false);
        btnCadProdEdit.setPreferredSize(new java.awt.Dimension(113, 21));
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
        btnCadProdExcl.setPreferredSize(new java.awt.Dimension(113, 21));
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
        btnCadProdCanc.setPreferredSize(new java.awt.Dimension(113, 21));
        btnCadProdCanc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdCancActionPerformed(evt);
            }
        });

        btnCadProdSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnCadProdSalvar.setText("Gravar");
        btnCadProdSalvar.setToolTipText("Gravar");
        btnCadProdSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadProdSalvar.setEnabled(false);
        btnCadProdSalvar.setPreferredSize(new java.awt.Dimension(113, 21));
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
                .addGap(16, 16, 16)
                .addComponent(btnCadProdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdAdic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadProdEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadProdExcl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadProdCanc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadProdCanc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnCadProdExcl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                        .addComponent(btnCadProdEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadProdSalvar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadProdPesq, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadProdAdic, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Descrição do Item:");

        jLabel3.setText("Marca:");

        txtCadProdDescricao.setEnabled(false);

        txtCadProdMarca.setEnabled(false);

        jLabel10.setText("Cod. Barras:");

        txtCadProdBarra.setEnabled(false);

        btnCadProdPesqMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadProdPesqMarca.setEnabled(false);
        btnCadProdPesqMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdPesqMarcaActionPerformed(evt);
            }
        });

        jLabel11.setText("Cod. MAC:");

        txtCadProdMAC.setEnabled(false);

        jLabel4.setText("Almoxarifado:");

        txtCadProdAlmox.setEnabled(false);
        txtCadProdAlmox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadProdAlmoxActionPerformed(evt);
            }
        });

        txtCadProdTorre.setEnabled(false);

        jLabel5.setText("Torre:");

        btnCadProdPesqTorre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadProdPesqTorre.setEnabled(false);
        btnCadProdPesqTorre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdPesqTorreActionPerformed(evt);
            }
        });

        btnCadProdPesqAlmox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadProdPesqAlmox.setEnabled(false);
        btnCadProdPesqAlmox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdPesqAlmoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadProdDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel11))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCadProdMarca)
                                    .addComponent(txtCadProdTorre)
                                    .addComponent(txtCadProdAlmox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCadProdPesqTorre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCadProdPesqAlmox, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCadProdPesqMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCadProdBarra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(txtCadProdMAC, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCadProdDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCadProdBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadProdMAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCadProdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(btnCadProdPesqMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCadProdTorre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnCadProdPesqTorre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtCadProdAlmox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCadProdPesqAlmox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
        );

        txtCadCodProd.setEnabled(false);

        jLabel1.setText("Código:");

        jLabel6.setText("Status:");

        chkboxCadProdAtivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Funcionando", "Danificado" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkboxCadProdAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCadCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(chkboxCadProdAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBounds(2, 2, 836, 472);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadProdPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdPesqActionPerformed
        // habilita campos para pesquisa

        btnCadProdAdic.setEnabled(false);
        btnCadProdExcl.setEnabled(true);
        btnCadProdCanc.setEnabled(true);
        btnCadProdEdit.setEnabled(true);
        btnCadProdSalvar.setEnabled(false);

        txtCadProdBarra.setText(null);
        txtCadProdDescricao.setText(null);
        txtCadProdMarca.setText(null);
        txtCadCodProd.setText(null);
        txtCadProdMAC.setText(null);
        txtCadProdTorre.setText(null);
        txtCadProdAlmox.setText(null);

        txtCadProdBarra.setEnabled(true);
        txtCadProdDescricao.setEnabled(true);
        txtCadProdTorre.setEnabled(true);
        txtCadProdTorre.setEditable(false);
        btnCadProdPesqTorre.setEnabled(true);
        txtCadProdAlmox.setEnabled(true);
        txtCadProdAlmox.setEditable(false);
        btnCadProdPesqAlmox.setEnabled(true);
        txtCadProdMAC.setEnabled(true);
        txtCadProdMarca.setEnabled(true);
        txtCadProdMarca.setEditable(false);
        btnCadProdPesqMarca.setEnabled(true);
        txtCadCodProd.setEnabled(false);

        TelaPesqAtivos marca = new TelaPesqAtivos();
        TelaPrincipal.Desktop.add(marca);
        marca.setVisible(true);

    }//GEN-LAST:event_btnCadProdPesqActionPerformed

    private void btnCadProdCancActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdCancActionPerformed
        // habilita campos para pesquisa

        btnCadProdPesq.setEnabled(true);
        btnCadProdSalvar.setEnabled(false);
        btnCadProdAdic.setEnabled(true);
        btnCadProdExcl.setEnabled(false);
        btnCadProdCanc.setEnabled(false);
        btnCadProdEdit.setEnabled(false);

        txtCadProdBarra.setText(null);
        txtCadProdDescricao.setText(null);
        txtCadProdMarca.setText(null);
        txtCadCodProd.setText(null);
        txtCadProdMAC.setText(null);
        txtCadProdTorre.setText(null);
        txtCadProdAlmox.setText(null);

        txtCadProdBarra.setEnabled(false);
        txtCadProdDescricao.setEnabled(false);
        txtCadProdMarca.setEnabled(false);
        btnCadProdPesqMarca.setEnabled(false);
        txtCadCodProd.setEnabled(false);
        txtCadProdMAC.setEnabled(false);
        txtCadProdTorre.setEnabled(false);
        btnCadProdPesqTorre.setEnabled(false);
        txtCadProdAlmox.setEnabled(false);
        btnCadProdPesqAlmox.setEnabled(false);

    }//GEN-LAST:event_btnCadProdCancActionPerformed

    private void btnCadProdAdicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdAdicActionPerformed

        btnCadProdPesq.setEnabled(false);
        btnCadProdAdic.setEnabled(false);
        btnCadProdCanc.setEnabled(true);
        btnCadProdSalvar.setEnabled(true);

        txtCadProdBarra.setEnabled(true);
        txtCadProdDescricao.setEnabled(true);
        txtCadProdTorre.setEnabled(true);
        txtCadProdTorre.setEditable(false);
        btnCadProdPesqTorre.setEnabled(true);
        txtCadProdAlmox.setEnabled(true);
        txtCadProdAlmox.setEditable(false);
        btnCadProdPesqAlmox.setEnabled(true);
        txtCadProdMAC.setEnabled(true);
        txtCadProdMarca.setEnabled(true);
        txtCadProdMarca.setEditable(false);
        btnCadProdPesqMarca.setEnabled(true);
        txtCadCodProd.setEnabled(false);

    }//GEN-LAST:event_btnCadProdAdicActionPerformed

    private void btnCadProdSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdSalvarActionPerformed
        // metodo salvar
        gravar();

    }//GEN-LAST:event_btnCadProdSalvarActionPerformed

    private void btnCadProdExclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdExclActionPerformed
        //metodo remover
        remover();
    }//GEN-LAST:event_btnCadProdExclActionPerformed

    private void btnCadProdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdEditActionPerformed
        // metodo alterar
        alterar();
    }//GEN-LAST:event_btnCadProdEditActionPerformed

    private void btnCadProdPesqMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdPesqMarcaActionPerformed
        TelaPesquisaMarca marca = new TelaPesquisaMarca();
        TelaPrincipal.Desktop.add(marca);
        marca.setVisible(true);
    }//GEN-LAST:event_btnCadProdPesqMarcaActionPerformed

    private void txtCadProdAlmoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadProdAlmoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadProdAlmoxActionPerformed

    private void btnCadProdPesqTorreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdPesqTorreActionPerformed
        TelaPesquisaTorre marca = new TelaPesquisaTorre();
        TelaPrincipal.Desktop.add(marca);
        marca.setVisible(true);
    }//GEN-LAST:event_btnCadProdPesqTorreActionPerformed

    private void btnCadProdPesqAlmoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdPesqAlmoxActionPerformed
        TelaPesquisaAlmox marca = new TelaPesquisaAlmox();
        TelaPrincipal.Desktop.add(marca);
        marca.setVisible(true);
    }//GEN-LAST:event_btnCadProdPesqAlmoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadProdAdic;
    private javax.swing.JButton btnCadProdCanc;
    private javax.swing.JButton btnCadProdEdit;
    private javax.swing.JButton btnCadProdExcl;
    private javax.swing.JButton btnCadProdPesq;
    private javax.swing.JButton btnCadProdPesqAlmox;
    public static javax.swing.JButton btnCadProdPesqMarca;
    private javax.swing.JButton btnCadProdPesqTorre;
    private javax.swing.JButton btnCadProdSalvar;
    public static javax.swing.JComboBox<String> chkboxCadProdAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JTextField txtCadCodProd;
    public static javax.swing.JTextField txtCadProdAlmox;
    public static javax.swing.JTextField txtCadProdBarra;
    public static javax.swing.JTextField txtCadProdDescricao;
    public static javax.swing.JTextField txtCadProdMAC;
    public static javax.swing.JTextField txtCadProdMarca;
    public static javax.swing.JTextField txtCadProdTorre;
    // End of variables declaration//GEN-END:variables
}
