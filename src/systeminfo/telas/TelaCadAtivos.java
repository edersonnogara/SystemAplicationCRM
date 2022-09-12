package systeminfo.telas;

import groovy.swing.factory.FormattedTextFactory;
import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nutrizon
 */
public class TelaCadAtivos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;

//metodo que abre somente uma tela junto com a classe Gerencia de Tela    
    private static TelaCadAtivos telaCad;

    public static TelaCadAtivos getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadAtivos();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaMarcasProdutos
     */
    public TelaCadAtivos() {
        initComponents();
        conexao = ModuloConexao.conector();

    }

    //metodo para adiconar marcas
    private void gravar() {
        String sql = "insert into tbativos (atStatus, atDescricao, atIPLegado, atSiteID, atTerm, atIP, atVLAN, atMarcaID, atNome, atOBS) values (?,?,?,?,?,?,?,?,?,?)";
        String desc = null;
        String term = null;
        try {
            pst = conexao.prepareCall(sql);
            desc = txtCadAtivoDescricao.getText();
            term = txtCadAtivoTerm.getText();
            if (cboCadAtivoStatus.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            pst.setString(2, txtCadAtivoDescricao.getText());
            pst.setString(3, txtCadAtivoIPLegado.getText());
            pst.setString(4, txtCadAtivoIdSite.getText());
            pst.setString(5, txtCadAtivoTerm.getText());
            pst.setString(6, txtCadAtivoIP.getText());
            pst.setString(7, txtCadAtivoVLAN.getText());
            pst.setString(8, txtCadAtivoIdMarca.getText());
            pst.setString(9, desc + "-" + term);
            pst.setString(10, txtCadAtivoObserv.getText());
            // validação dos campos obrigatorios
            if ((txtCadAtivoDescricao.getText().isEmpty()) || (txtCadAtivoIP.getText().isEmpty()) || (txtCadAtivoTerm.getText().isEmpty()) || (txtCadAtivoIdMarca.getText().isEmpty()) || (txtCadAtivoIPLegado.getText().isEmpty()) || (txtCadAtivoIdSite.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos com * para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Site cadastrado com sucesso");

                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    btnCadAtivoPesSite.setEnabled(false);
                    btnCadAtivoPesqMarca.setEnabled(false);

                    txtCadAtivoDescricao.setEnabled(false);
                    txtCadAtivoIPLegado.setEnabled(false);
                    btnCadPesquisar.setEnabled(true);
                    btnCadGravar.setEnabled(false);
                    txtCadAtivoDescricao.setText(null);
                    txtCadAtivoIPLegado.setText(null);
                    txtCadAtivoCod.setText(null);
                    txtCadAtivoSiteSigla.setEnabled(false);
                    txtCadAtivoSiteSigla.setEditable(false);
                    txtCadAtivoSiteSigla.setText(null);
                    btnCadAtivoPesSite.setEnabled(false);
                    txtCadAtivoIP.setEnabled(false);
                    txtCadAtivoIP.setText(null);
                    txtCadAtivoIdSite.setText(null);
                    txtCadAtivoVLAN.setText(null);
                    txtCadAtivoTerm.setText(null);
                    txtCadAtivoTerm.setEnabled(false);
                    txtCadAtivoVLAN.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadAtivoFuncao.setText(null);
                    txtCadAtivoFuncao.setEnabled(false);
                    txtCadAtivoMarca.setText(null);
                    txtCadAtivoMarca.setEnabled(false);
                    txtCadAtivoObserv.setText(null);
                    txtCadAtivoObserv.setEnabled(false);
                    txtCadAtivoIdMarca.setText(null);
                    txtCadAtivoNome.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para excluir a marca
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Excluir o Ativo?");
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete from tbativos where idativos = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadAtivoCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ativo Excluido com sucesso");

                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    btnCadAtivoPesSite.setEnabled(false);
                    btnCadAtivoPesqMarca.setEnabled(false);

                    txtCadAtivoDescricao.setEnabled(false);
                    txtCadAtivoIPLegado.setEnabled(false);
                    btnCadPesquisar.setEnabled(true);
                    btnCadGravar.setEnabled(false);
                    txtCadAtivoDescricao.setText(null);
                    txtCadAtivoIPLegado.setText(null);
                    txtCadAtivoCod.setText(null);
                    txtCadAtivoSiteSigla.setEnabled(false);
                    txtCadAtivoSiteSigla.setEditable(false);
                    txtCadAtivoSiteSigla.setText(null);
                    btnCadAtivoPesSite.setEnabled(false);
                    txtCadAtivoIP.setEnabled(false);
                    txtCadAtivoIP.setText(null);
                    txtCadAtivoIdSite.setText(null);
                    txtCadAtivoVLAN.setText(null);
                    txtCadAtivoTerm.setText(null);
                    txtCadAtivoTerm.setEnabled(false);
                    txtCadAtivoVLAN.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadAtivoFuncao.setText(null);
                    txtCadAtivoFuncao.setEnabled(false);
                    txtCadAtivoMarca.setText(null);
                    txtCadAtivoMarca.setEnabled(false);
                    txtCadAtivoObserv.setText(null);
                    txtCadAtivoObserv.setEnabled(false);
                    txtCadAtivoIdMarca.setText(null);
                    txtCadAtivoNome.setText(null);

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void alterar() {
        String sql = "update tbativos set atStatus = ?, atDescricao = ?, atIPLegado = ?, atSiteID = ?, atTerm = ? , atIP = ?, atVLAN = ?, atMarcaID = ?, atNome = ?, atOBS = ? where idativos = ?";
        String desc = null;
        String term = null;
        try {
            pst = conexao.prepareCall(sql);
            desc = txtCadAtivoDescricao.getText();
            term = txtCadAtivoTerm.getText();
            if (cboCadAtivoStatus.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            pst.setString(2, txtCadAtivoDescricao.getText());
            pst.setString(3, txtCadAtivoIPLegado.getText());
            pst.setString(4, txtCadAtivoIdSite.getText());
            pst.setString(5, txtCadAtivoTerm.getText());
            pst.setString(6, txtCadAtivoIP.getText());
            pst.setString(7, txtCadAtivoVLAN.getText());
            pst.setString(8, txtCadAtivoIdMarca.getText());
            pst.setString(9, desc + "-" + term);
            pst.setString(10, txtCadAtivoObserv.getText());
            pst.setString(11, txtCadAtivoCod.getText());
            if ((txtCadAtivoDescricao.getText().isEmpty()) || (txtCadAtivoIP.getText().isEmpty()) || (txtCadAtivoTerm.getText().isEmpty()) || (txtCadAtivoIdMarca.getText().isEmpty()) || (txtCadAtivoIPLegado.getText().isEmpty()) || (txtCadAtivoIdSite.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos com * para Editar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ativo alterado com sucesso");
                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    btnCadAtivoPesSite.setEnabled(false);
                    btnCadAtivoPesqMarca.setEnabled(false);

                    txtCadAtivoDescricao.setEnabled(false);
                    txtCadAtivoIPLegado.setEnabled(false);
                    btnCadPesquisar.setEnabled(true);
                    btnCadGravar.setEnabled(false);
                    txtCadAtivoDescricao.setText(null);
                    txtCadAtivoIPLegado.setText(null);
                    txtCadAtivoCod.setText(null);
                    txtCadAtivoSiteSigla.setEnabled(false);
                    txtCadAtivoSiteSigla.setEditable(false);
                    txtCadAtivoSiteSigla.setText(null);
                    btnCadAtivoPesSite.setEnabled(false);
                    txtCadAtivoIP.setEnabled(false);
                    txtCadAtivoIP.setText(null);
                    txtCadAtivoIdSite.setText(null);
                    txtCadAtivoVLAN.setText(null);
                    txtCadAtivoTerm.setText(null);
                    txtCadAtivoTerm.setEnabled(false);
                    txtCadAtivoVLAN.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadAtivoFuncao.setText(null);
                    txtCadAtivoFuncao.setEnabled(false);
                    txtCadAtivoMarca.setText(null);
                    txtCadAtivoMarca.setEnabled(false);
                    txtCadAtivoObserv.setText(null);
                    txtCadAtivoObserv.setEnabled(false);
                    txtCadAtivoIdMarca.setText(null);
                    txtCadAtivoNome.setText(null);

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

        jPanel3 = new javax.swing.JPanel();
        txtCadAtivoDescricao = new javax.swing.JTextField();
        txtCadAtivoIPLegado = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCadAtivoSiteSigla = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnCadAtivoPesSite = new javax.swing.JButton();
        txtCadAtivoIP = new javax.swing.JTextField();
        txtCadAtivoIdSite = new javax.swing.JTextField();
        btnCadGravar = new javax.swing.JButton();
        cboCadAtivoStatus = new javax.swing.JCheckBox();
        txtCadAtivoTerm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCadAtivoVLAN = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCadAtivoIdMarca = new javax.swing.JTextField();
        txtCadAtivoMarca = new javax.swing.JTextField();
        btnCadAtivoPesqMarca = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtCadAtivoNome = new javax.swing.JTextField();
        txtCadAtivoObserv = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCadAtivoCod = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCadAtivoFuncao = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCadPesquisar = new javax.swing.JButton();
        btnCadAdicionar = new javax.swing.JButton();
        btnCadExcluir = new javax.swing.JButton();
        btnCadCancelar = new javax.swing.JButton();
        btnCadEditar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle("Cadastro de Ativos");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setNextFocusableComponent(btnCadPesquisar);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastre o Ativo:"));

        txtCadAtivoDescricao.setEnabled(false);
        txtCadAtivoDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadAtivoDescricaoActionPerformed(evt);
            }
        });

        txtCadAtivoIPLegado.setEnabled(false);

        jLabel1.setText("* Descrição:");

        jLabel2.setText("* IP Legado:");

        jLabel3.setText("* Site:");

        txtCadAtivoSiteSigla.setEditable(false);
        txtCadAtivoSiteSigla.setEnabled(false);

        jLabel4.setText("* IP:");

        btnCadAtivoPesSite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadAtivoPesSite.setEnabled(false);
        btnCadAtivoPesSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadAtivoPesSiteActionPerformed(evt);
            }
        });

        txtCadAtivoIP.setEnabled(false);
        txtCadAtivoIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadAtivoIPActionPerformed(evt);
            }
        });

        txtCadAtivoIdSite.setEnabled(false);
        txtCadAtivoIdSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadAtivoIdSiteActionPerformed(evt);
            }
        });

        btnCadGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1salvar.png"))); // NOI18N
        btnCadGravar.setText("Gravar");
        btnCadGravar.setEnabled(false);
        btnCadGravar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadGravarActionPerformed(evt);
            }
        });

        cboCadAtivoStatus.setSelected(true);
        cboCadAtivoStatus.setText("A");

        txtCadAtivoTerm.setEnabled(false);

        jLabel5.setText("Ativo?:");

        jLabel6.setText("VLAN:");

        txtCadAtivoVLAN.setEnabled(false);

        jLabel7.setText("* Term.:");

        jLabel8.setText("* Marca:");

        txtCadAtivoIdMarca.setEnabled(false);
        txtCadAtivoIdMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadAtivoIdMarcaActionPerformed(evt);
            }
        });

        txtCadAtivoMarca.setEditable(false);
        txtCadAtivoMarca.setEnabled(false);

        btnCadAtivoPesqMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadAtivoPesqMarca.setEnabled(false);
        btnCadAtivoPesqMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadAtivoPesqMarcaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Nome do Ativo:");

        txtCadAtivoNome.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCadAtivoNome.setEnabled(false);
        txtCadAtivoNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadAtivoNomeActionPerformed(evt);
            }
        });

        txtCadAtivoObserv.setEnabled(false);
        txtCadAtivoObserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadAtivoObservActionPerformed(evt);
            }
        });

        jLabel10.setText("Observação:");

        txtCadAtivoCod.setEnabled(false);

        jLabel11.setText("Código:");

        txtCadAtivoFuncao.setEditable(false);
        txtCadAtivoFuncao.setEnabled(false);

        jLabel12.setText("* Func.:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCadAtivoStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadAtivoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(3, 3, 3))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel8)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCadAtivoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCadAtivoCod, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCadAtivoIPLegado, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(txtCadAtivoIdSite, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtCadAtivoSiteSigla, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(txtCadAtivoIP))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCadAtivoPesSite, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(txtCadAtivoIdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCadAtivoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCadAtivoPesqMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCadAtivoFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel7))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtCadAtivoVLAN, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtCadAtivoTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(txtCadAtivoObserv, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(btnCadGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(86, 86, 86))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(cboCadAtivoStatus)
                    .addComponent(txtCadAtivoCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCadAtivoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCadAtivoIPLegado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCadAtivoTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btnCadAtivoPesSite, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadAtivoSiteSigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadAtivoIdSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadAtivoIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCadAtivoVLAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCadAtivoIdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadAtivoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btnCadAtivoPesqMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadAtivoFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtCadAtivoObserv, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadGravar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCadAtivoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addGap(21, 21, 21))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCadPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1pesquisar.png"))); // NOI18N
        btnCadPesquisar.setText("Pesquisar");
        btnCadPesquisar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCadPesquisar.setIconTextGap(1);
        btnCadPesquisar.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesquisarActionPerformed(evt);
            }
        });

        btnCadAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5add.png"))); // NOI18N
        btnCadAdicionar.setText("Adicionar");
        btnCadAdicionar.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadAdicionarActionPerformed(evt);
            }
        });

        btnCadExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5delete.png"))); // NOI18N
        btnCadExcluir.setText("Excluir");
        btnCadExcluir.setEnabled(false);
        btnCadExcluir.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadExcluirActionPerformed(evt);
            }
        });

        btnCadCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5cancelar.png"))); // NOI18N
        btnCadCancelar.setText("Cancelar");
        btnCadCancelar.setEnabled(false);
        btnCadCancelar.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCancelarActionPerformed(evt);
            }
        });

        btnCadEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnCadEditar.setText("Salvar Edição");
        btnCadEditar.setToolTipText("");
        btnCadEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadEditar.setEnabled(false);
        btnCadEditar.setPreferredSize(new java.awt.Dimension(133, 41));
        btnCadEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadEditarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/2sair.png"))); // NOI18N
        jButton1.setText("Fechar");
        jButton1.setPreferredSize(new java.awt.Dimension(113, 41));
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
                .addComponent(btnCadPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        setBounds(20, 20, 1008, 570);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesquisarActionPerformed
        // habilita campos para pesquisa
        TelaPesqAtivos tela = new TelaPesqAtivos();
        TelaPrincipal.Desktop.add(tela);
        tela.setVisible(true);
        btnCadAtivoPesSite.setEnabled(false);
        btnCadAdicionar.setEnabled(false);
        btnCadExcluir.setEnabled(true);
        btnCadEditar.setEnabled(true);
        btnCadCancelar.setEnabled(true);
        btnCadGravar.setEnabled(false);
        btnCadPesquisar.setEnabled(false);

        txtCadAtivoDescricao.setText(null);
        txtCadAtivoDescricao.setEnabled(true);
        txtCadAtivoIPLegado.setText(null);
        txtCadAtivoIPLegado.setEnabled(true);
        txtCadAtivoCod.setText(null);
        txtCadAtivoVLAN.setText(null);
        txtCadAtivoVLAN.setEnabled(true);
        txtCadAtivoTerm.setText(null);
        txtCadAtivoTerm.setEnabled(true);
        txtCadAtivoSiteSigla.setText(null);
        txtCadAtivoSiteSigla.setEnabled(true);
        txtCadAtivoIdSite.setText(null);
        txtCadAtivoIP.setText(null);
        txtCadAtivoIP.setEnabled(true);
        txtCadAtivoIdMarca.setText(null);
        txtCadAtivoMarca.setText(null);
        txtCadAtivoMarca.setEnabled(true);
        txtCadAtivoObserv.setText(null);
        txtCadAtivoObserv.setEnabled(true);
        txtCadAtivoNome.setText(null);
        txtCadAtivoFuncao.setText(null);
        txtCadAtivoFuncao.setEnabled(true);

        btnCadAtivoPesSite.setEnabled(true);
        btnCadAtivoPesqMarca.setEnabled(true);
    }//GEN-LAST:event_btnCadPesquisarActionPerformed

    private void btnCadCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCancelarActionPerformed
        // habilita os botoes
        btnCadAdicionar.setEnabled(true);
        btnCadExcluir.setEnabled(false);
        btnCadCancelar.setEnabled(false);
        btnCadAtivoPesSite.setEnabled(false);
        btnCadAtivoPesqMarca.setEnabled(false);

        txtCadAtivoDescricao.setEnabled(false);
        txtCadAtivoIPLegado.setEnabled(false);
        btnCadPesquisar.setEnabled(true);
        btnCadGravar.setEnabled(false);
        txtCadAtivoDescricao.setText(null);
        txtCadAtivoIPLegado.setText(null);
        txtCadAtivoCod.setText(null);
        txtCadAtivoSiteSigla.setEnabled(false);
        txtCadAtivoSiteSigla.setEditable(false);
        txtCadAtivoSiteSigla.setText(null);
        btnCadAtivoPesSite.setEnabled(false);
        txtCadAtivoIP.setEnabled(false);
        txtCadAtivoIP.setText(null);
        txtCadAtivoIdSite.setText(null);
        txtCadAtivoVLAN.setText(null);
        txtCadAtivoTerm.setText(null);
        txtCadAtivoTerm.setEnabled(false);
        txtCadAtivoVLAN.setEnabled(false);
        btnCadEditar.setEnabled(false);
        txtCadAtivoFuncao.setText(null);
        txtCadAtivoFuncao.setEnabled(false);
        txtCadAtivoMarca.setText(null);
        txtCadAtivoMarca.setEnabled(false);
        txtCadAtivoObserv.setText(null);
        txtCadAtivoObserv.setEnabled(false);
        txtCadAtivoIdMarca.setText(null);
        txtCadAtivoNome.setText(null);

    }//GEN-LAST:event_btnCadCancelarActionPerformed

    private void btnCadAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadAdicionarActionPerformed
        // habilita os botoes
        btnCadAtivoPesSite.setEnabled(false);
        btnCadAdicionar.setEnabled(false);
        btnCadExcluir.setEnabled(false);
        btnCadEditar.setEnabled(false);
        btnCadCancelar.setEnabled(true);
        btnCadGravar.setEnabled(true);
        btnCadPesquisar.setEnabled(false);

        txtCadAtivoDescricao.setText(null);
        txtCadAtivoDescricao.setEnabled(true);
        txtCadAtivoDescricao.requestFocus();
        txtCadAtivoIPLegado.setText(null);
        txtCadAtivoIPLegado.setEnabled(true);
        txtCadAtivoCod.setText(null);
        txtCadAtivoVLAN.setText(null);
        txtCadAtivoVLAN.setEnabled(true);
        txtCadAtivoTerm.setText(null);
        txtCadAtivoTerm.setEnabled(true);
        txtCadAtivoSiteSigla.setText(null);
        txtCadAtivoSiteSigla.setEnabled(true);
        txtCadAtivoSiteSigla.setEditable(false);
        txtCadAtivoIdSite.setText(null);
        txtCadAtivoIP.setText(null);
        txtCadAtivoIP.setEnabled(true);
        txtCadAtivoIdMarca.setText(null);
        txtCadAtivoMarca.setText(null);
        txtCadAtivoMarca.setEditable(false);
        txtCadAtivoMarca.setEnabled(true);
        txtCadAtivoObserv.setText(null);
        txtCadAtivoObserv.setEnabled(true);
        txtCadAtivoNome.setText(null);
        txtCadAtivoFuncao.setText(null);
        txtCadAtivoFuncao.setEnabled(true);
        txtCadAtivoFuncao.setEditable(false);

        btnCadAtivoPesSite.setEnabled(true);
        btnCadAtivoPesqMarca.setEnabled(true);
    }//GEN-LAST:event_btnCadAdicionarActionPerformed

    private void btnCadExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadExcluirActionPerformed
        // metodo excluir marca
        remover();
    }//GEN-LAST:event_btnCadExcluirActionPerformed

    private void btnCadEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadEditarActionPerformed
        // metodo alterar
        alterar();
    }//GEN-LAST:event_btnCadEditarActionPerformed

    private void btnCadGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadGravarActionPerformed
        // grava a marca de produto no banco
        gravar();
    }//GEN-LAST:event_btnCadGravarActionPerformed

    private void txtCadAtivoIdSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadAtivoIdSiteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadAtivoIdSiteActionPerformed

    private void btnCadAtivoPesSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadAtivoPesSiteActionPerformed
        TelaPesqSites tela = new TelaPesqSites();
        TelaPrincipal.Desktop.add(tela);
        tela.setVisible(true);
    }//GEN-LAST:event_btnCadAtivoPesSiteActionPerformed

    private void txtCadAtivoDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadAtivoDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadAtivoDescricaoActionPerformed

    private void txtCadAtivoIdMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadAtivoIdMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadAtivoIdMarcaActionPerformed

    private void btnCadAtivoPesqMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadAtivoPesqMarcaActionPerformed
        // TODO add your handling code here:
        TelaPesqMarca tela = new TelaPesqMarca();
        TelaPrincipal.Desktop.add(tela);
        tela.setVisible(true);
    }//GEN-LAST:event_btnCadAtivoPesqMarcaActionPerformed

    private void txtCadAtivoNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadAtivoNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadAtivoNomeActionPerformed

    private void txtCadAtivoObservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadAtivoObservActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadAtivoObservActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        //TelaPrincipal.menCadMarcas.setEnabled(true);
        //TelaPrincipal.menCadAtivos.setEnabled(true);
        //TelaPrincipal.menCadSites.setEnabled(true);
        //TelaPrincipal.menCadEnlace.setEnabled(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCadAtivoIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadAtivoIPActionPerformed
    
    }//GEN-LAST:event_txtCadAtivoIPActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnCadAdicionar;
    public static javax.swing.JButton btnCadAtivoPesSite;
    public static javax.swing.JButton btnCadAtivoPesqMarca;
    public static javax.swing.JButton btnCadCancelar;
    public static javax.swing.JButton btnCadEditar;
    public static javax.swing.JButton btnCadExcluir;
    public static javax.swing.JButton btnCadGravar;
    public static javax.swing.JButton btnCadPesquisar;
    public static javax.swing.JCheckBox cboCadAtivoStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JTextField txtCadAtivoCod;
    public static javax.swing.JTextField txtCadAtivoDescricao;
    public static javax.swing.JTextField txtCadAtivoFuncao;
    public static javax.swing.JTextField txtCadAtivoIP;
    public static javax.swing.JTextField txtCadAtivoIPLegado;
    public static javax.swing.JTextField txtCadAtivoIdMarca;
    public static javax.swing.JTextField txtCadAtivoIdSite;
    public static javax.swing.JTextField txtCadAtivoMarca;
    public static javax.swing.JTextField txtCadAtivoNome;
    public static javax.swing.JTextField txtCadAtivoObserv;
    public static javax.swing.JTextField txtCadAtivoSiteSigla;
    public static javax.swing.JTextField txtCadAtivoTerm;
    public static javax.swing.JTextField txtCadAtivoVLAN;
    // End of variables declaration//GEN-END:variables

    private void setExtendedState(String IS_MAXIMUM_PROPERTY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
