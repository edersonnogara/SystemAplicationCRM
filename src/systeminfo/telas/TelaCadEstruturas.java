package systeminfo.telas;

import java.sql.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.Gerentedetelas;
import systeminfo.dal.ModuloConexao;
import static systeminfo.telas.TelaPrincipal.Desktop;

/**
 *
 * @author Nogara
 */
public class TelaCadEstruturas extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultListModel modelo;
    String[] Codig;
    int Enter = 0;
    
    Gerentedetelas gerentedetelas;

    private static TelaCadEstruturas telaCad;

    public static TelaCadEstruturas getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadEstruturas();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaMarcasProdutos
     */
    public TelaCadEstruturas() {
        initComponents();
        conexao = ModuloConexao.conector();
        modelo = new DefaultListModel();
        this.gerentedetelas = new Gerentedetelas(Desktop);
    }

    // public void ResultadoPesquisa() {
    //  txtCadEstruturaIdCidade.setText(null);
    //   try {
    //       rs.first();
    //      txtCadEstruturaIdCidade.setText(rs.getString(1));
    //       txtCadNomeCidade.setText(rs.getString(2));
    //txtCadEstruturaEstado.setText(rs.getString(7));
    //  } catch (Exception e) {
    // }
    // }
    // metodo de pesquisar
    private void pesquisar_marca_Prod() {
        String sql = "SELECT t.idest as Codigo,\n"
                + "t.estStatus as Status,\n"
                + "f.idfil as IdFilial,\n"
                + "f.filNome as Filial, \n"
                + "t.estNome as Estrutura, \n"
                + "t.estSigla as Sigla,\n"
                + "t.estEnd as Endereço, \n"
                + "c.idcidade as IdCid, \n"
                + "c.cidNome as Cidade, \n"
                + "c.cidEstado as Estado,\n"
                + "t.estObs as Obs\n"
                + "\n"
                + "FROM tbestruturas t \n"
                + "inner join tbcidade as c on c.idcidade = t.estIdCid\n"
                + "inner join tbfilial as f on f.idfil = t.estIdFilial \n"
                + "where t.estNome like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadPesq.getText() + "%%");
            rs = pst.executeQuery();
            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));

            //redimencionar colunas na tabela da tela após a pesquisa
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(250);
            tblPesqResult.getColumnModel().getColumn(9).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(10).setPreferredWidth(500);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para setar os campos dos formularios com o conteudo da tabela
    public void setar_campos() {
        txtCadPesq.setText(null);
        int setar = tblPesqResult.getSelectedRow();
        txtCadCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        txtCadCodigo.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        cboxAtivo.setSelectedItem(tblPesqResult.getModel().getValueAt(setar, 1).toString());
        txtCadEstruturaIdFilial.setText(tblPesqResult.getModel().getValueAt(setar, 2).toString());
        txtCadFilial.setText(tblPesqResult.getModel().getValueAt(setar, 3).toString());
        txtCadNomeEstrutura.setText(tblPesqResult.getModel().getValueAt(setar, 4).toString());
        txtCadEstruturaSigla.setText(tblPesqResult.getModel().getValueAt(setar, 5).toString());
        txtCadEnderecoEstrutura.setText(tblPesqResult.getModel().getValueAt(setar, 6).toString());
        txtCadEstruturaIdCidade.setText(tblPesqResult.getModel().getValueAt(setar, 7).toString());
        txtCadNomeCidade.setText(tblPesqResult.getModel().getValueAt(setar, 8).toString());
        txtCadEstruturaEstado.setText(tblPesqResult.getModel().getValueAt(setar, 9).toString());
        txtCadObs.setText(tblPesqResult.getModel().getValueAt(setar, 10).toString());

        tblPesqResult.setVisible(false);
        btnAdicionar.setEnabled(false);
        btnRemover.setEnabled(true);
        btnEditar.setEnabled(true);

        txtCadNomeEstrutura.setEnabled(true);
        txtCadNomeCidade.setEnabled(true);
        txtCadObs.setEnabled(true);
        txtCadFilial.setEnabled(true);
        txtCadEstruturaSigla.setEnabled(true);
        txtCadEstruturaEstado.setEnabled(true);
        txtCadEnderecoEstrutura.setEnabled(true);

        txtCadPesq.setFocusable(true);

    }

    //metodo para adiconar 
    private void gravar() {
        String sql = "insert into tbestruturas (estStatus, estIdFilial, estFilial, estNome, estSigla, estEnd, estIdCid, estCidade, estEstado, estObs) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareCall(sql);
            pst.setString(1, cboxAtivo.getSelectedItem().toString());
            pst.setString(2, txtCadEstruturaIdFilial.getText());
            pst.setString(3, txtCadFilial.getText());
            pst.setString(4, txtCadNomeEstrutura.getText());
            pst.setString(5, txtCadEstruturaSigla.getText());
            pst.setString(6, txtCadEnderecoEstrutura.getText());
            pst.setString(7, txtCadEstruturaIdCidade.getText());
            pst.setString(8, txtCadNomeCidade.getText());
            pst.setString(9, txtCadEstruturaEstado.getText());
            pst.setString(10, txtCadObs.getText());

            // validação dos campos obrigatorios
            if ((txtCadEstruturaIdFilial.getText().isEmpty())
                    || (txtCadFilial.getText().isEmpty())
                    || (txtCadNomeEstrutura.getText().isEmpty())
                    || (txtCadEstruturaSigla.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatorios para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Estrutura cadastrado com sucesso");

                    btnAdicionar.setEnabled(true);
                    btnRemover.setEnabled(false);
                    btnCancelar.setEnabled(false);
                    btnGravar.setEnabled(false);
                    btnEditar.setEnabled(false);

                    txtCadPesq.setEnabled(false);
                    txtCadNomeEstrutura.setEnabled(false);
                    txtCadEstruturaSigla.setEnabled(false);
                    txtCadNomeEstrutura.setText(null);
                    txtCadEstruturaSigla.setText(null);
                    txtCadCod.setText(null);
                    txtCadNomeCidade.setEnabled(false);
                    txtCadNomeCidade.setEditable(false);
                    txtCadNomeCidade.setText(null);
                    btnCadTorresPesqCidade.setEnabled(false);
                    btnPesqFilial.setEnabled(false);
                    txtCadEnderecoEstrutura.setEnabled(false);
                    txtCadEnderecoEstrutura.setText(null);
                    txtCadEstruturaIdCidade.setText(null);
                    txtCadFilial.setText(null);
                    txtCadEstruturaEstado.setText(null);
                    txtCadEstruturaEstado.setEnabled(false);
                    txtCadFilial.setEnabled(false);
                    txtCadEstruturaIdFilial.setEnabled(false);
                    txtCadEstruturaIdFilial.setText(null);
                    txtCadObs.setText(null);
                    txtCadObs.setEnabled(false);

                    tblPesqResult.setEnabled(false);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para excluir
    private void remover() {
        if (txtCadCod.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma Estrutura");
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Excluir a Estrutura?");
            if (confirma == JOptionPane.YES_OPTION) {
                String sql = "Delete from tbestruturas where idest = ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtCadCod.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Estrutura Excluido com sucesso");

                        btnAdicionar.setEnabled(true);
                        btnRemover.setEnabled(false);
                        btnCancelar.setEnabled(false);
                        btnGravar.setEnabled(false);
                        btnEditar.setEnabled(false);

                        txtCadPesq.setEnabled(false);
                        txtCadNomeEstrutura.setEnabled(false);
                        txtCadEstruturaSigla.setEnabled(false);
                        txtCadNomeEstrutura.setText(null);
                        txtCadEstruturaSigla.setText(null);
                        txtCadCod.setText(null);
                        txtCadNomeCidade.setEnabled(false);
                        txtCadNomeCidade.setEditable(false);
                        txtCadNomeCidade.setText(null);
                        btnCadTorresPesqCidade.setEnabled(false);
                        btnPesqFilial.setEnabled(false);
                        txtCadEnderecoEstrutura.setEnabled(false);
                        txtCadEnderecoEstrutura.setText(null);
                        txtCadEstruturaIdCidade.setText(null);
                        txtCadFilial.setText(null);
                        txtCadEstruturaEstado.setText(null);
                        txtCadEstruturaEstado.setEnabled(false);
                        txtCadFilial.setEnabled(false);
                        txtCadEstruturaIdFilial.setEnabled(false);
                        txtCadEstruturaIdFilial.setText(null);
                        txtCadObs.setText(null);
                        txtCadObs.setEnabled(false);

                        tblPesqResult.setEnabled(false);

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }

    private void alterar() {
        String sql = "update tbestruturas set estStatus = ?, estIdFilial = ?, estFilial = ?, estNome = ?, estSigla = ? , estEnd = ?, estIdCid = ?, estCidade = ?, estEstado = ?, estObs = ? where idest = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cboxAtivo.getSelectedItem().toString());
            pst.setString(2, txtCadEstruturaIdFilial.getText());
            pst.setString(3, txtCadFilial.getText());
            pst.setString(4, txtCadNomeEstrutura.getText());
            pst.setString(5, txtCadEstruturaSigla.getText());
            pst.setString(6, txtCadEnderecoEstrutura.getText());
            pst.setString(7, txtCadEstruturaIdCidade.getText());
            pst.setString(8, txtCadNomeCidade.getText());
            pst.setString(9, txtCadEstruturaEstado.getText());
            pst.setString(10, txtCadObs.getText());
            pst.setString(11, txtCadCod.getText());
            if ((txtCadEstruturaIdFilial.getText().isEmpty())
                    || (txtCadFilial.getText().isEmpty())
                    || (txtCadNomeEstrutura.getText().isEmpty())
                    || (txtCadEstruturaSigla.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatorios para Editar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Estrutura alterado com sucesso");

                    btnAdicionar.setEnabled(true);
                    btnRemover.setEnabled(false);
                    btnCancelar.setEnabled(false);
                    btnGravar.setEnabled(false);
                    btnEditar.setEnabled(false);

                    txtCadPesq.setEnabled(false);
                    txtCadNomeEstrutura.setEnabled(false);
                    txtCadEstruturaSigla.setEnabled(false);
                    txtCadNomeEstrutura.setText(null);
                    txtCadEstruturaSigla.setText(null);
                    txtCadCod.setText(null);
                    txtCadNomeCidade.setEnabled(false);
                    txtCadNomeCidade.setEditable(false);
                    txtCadNomeCidade.setText(null);
                    btnCadTorresPesqCidade.setEnabled(false);
                    btnPesqFilial.setEnabled(false);
                    txtCadEnderecoEstrutura.setEnabled(false);
                    txtCadEnderecoEstrutura.setText(null);
                    txtCadEstruturaIdCidade.setText(null);
                    txtCadFilial.setText(null);
                    txtCadEstruturaEstado.setText(null);
                    txtCadEstruturaEstado.setEnabled(false);
                    txtCadFilial.setEnabled(false);
                    txtCadEstruturaIdFilial.setEnabled(false);
                    txtCadEstruturaIdFilial.setText(null);
                    txtCadObs.setText(null);
                    txtCadObs.setEnabled(false);

                    tblPesqResult.setEnabled(false);

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
        txtCadPesq = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesqResult = new javax.swing.JTable();
        txtCadCod = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtCadEstruturaSigla = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCadEnderecoEstrutura = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCadFilial = new javax.swing.JTextField();
        txtCadNomeEstrutura = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCadEstruturaIdCidade = new javax.swing.JTextField();
        txtCadNomeCidade = new javax.swing.JTextField();
        txtCadEstruturaEstado = new javax.swing.JTextField();
        btnCadTorresPesqCidade = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboxAtivo = new javax.swing.JComboBox<>();
        txtCadCodigo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCadObs = new javax.swing.JTextArea();
        txtCadEstruturaIdFilial = new javax.swing.JTextField();
        btnPesqFilial = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnAdicionar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        setTitle("Cadastro de Estruturas");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquise os Estruturas:"));
        jPanel2.setOpaque(false);

        txtCadPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadPesqActionPerformed(evt);
            }
        });
        txtCadPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadPesqKeyReleased(evt);
            }
        });

        tblPesqResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Status", "IdFilial", "Filial", "Estrutura", "Sigla", "Endereço", "IdCid", "Cidade", "Estado", "Obs"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPesqResult.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPesqResult.setColumnSelectionAllowed(true);
        tblPesqResult.setEnabled(false);
        tblPesqResult.setName(""); // NOI18N
        tblPesqResult.getTableHeader().setReorderingAllowed(false);
        tblPesqResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesqResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesqResult);
        tblPesqResult.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblPesqResult.getColumnModel().getColumnCount() > 0) {
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(250);
            tblPesqResult.getColumnModel().getColumn(9).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(10).setPreferredWidth(500);
        }

        txtCadCod.setEnabled(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastre a Estruturas:"));
        jPanel3.setOpaque(false);

        txtCadEstruturaSigla.setEnabled(false);

        jLabel1.setText("*Nome:");

        jLabel2.setText("Sigla:");

        jLabel4.setText("Endereço:");

        txtCadEnderecoEstrutura.setEnabled(false);

        jLabel6.setText("Filial:");

        txtCadFilial.setEnabled(false);

        txtCadNomeEstrutura.setEnabled(false);

        jLabel3.setText("*Cidade:");

        txtCadEstruturaIdCidade.setEditable(false);
        txtCadEstruturaIdCidade.setEnabled(false);
        txtCadEstruturaIdCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadEstruturaIdCidadeActionPerformed(evt);
            }
        });

        txtCadNomeCidade.setEditable(false);
        txtCadNomeCidade.setEnabled(false);
        txtCadNomeCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCadNomeCidadeMouseClicked(evt);
            }
        });
        txtCadNomeCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadNomeCidadeActionPerformed(evt);
            }
        });
        txtCadNomeCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCadNomeCidadeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadNomeCidadeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCadNomeCidadeKeyTyped(evt);
            }
        });

        txtCadEstruturaEstado.setEditable(false);
        txtCadEstruturaEstado.setEnabled(false);
        txtCadEstruturaEstado.setOpaque(false);

        btnCadTorresPesqCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadTorresPesqCidade.setEnabled(false);
        btnCadTorresPesqCidade.setOpaque(false);
        btnCadTorresPesqCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCadTorresPesqCidadeMouseClicked(evt);
            }
        });
        btnCadTorresPesqCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadTorresPesqCidadeActionPerformed(evt);
            }
        });

        jLabel7.setText("Estado:");

        cboxAtivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));

        txtCadCodigo.setEnabled(false);

        jLabel8.setText("Código:");

        jLabel9.setText("Obs:");

        txtCadObs.setColumns(20);
        txtCadObs.setRows(5);
        txtCadObs.setEnabled(false);
        jScrollPane2.setViewportView(txtCadObs);

        txtCadEstruturaIdFilial.setEditable(false);
        txtCadEstruturaIdFilial.setEnabled(false);

        btnPesqFilial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnPesqFilial.setEnabled(false);
        btnPesqFilial.setOpaque(false);
        btnPesqFilial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqFilialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadEstruturaIdCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadNomeCidade))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCadCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtCadNomeEstrutura)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 19, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEstruturaIdFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadFilial)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPesqFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEstruturaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCadTorresPesqCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel4))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCadEnderecoEstrutura)
                            .addComponent(txtCadEstruturaSigla, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cboxAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txtCadCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtCadNomeEstrutura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtCadEstruturaIdCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCadEstruturaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadTorresPesqCidade)
                    .addComponent(jLabel4)
                    .addComponent(txtCadEnderecoEstrutura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtCadEstruturaIdFilial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadFilial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesqFilial)
                    .addComponent(jLabel2)
                    .addComponent(txtCadEstruturaSigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCadPesq)
                        .addGap(234, 234, 234)
                        .addComponent(txtCadCod, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5add.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.setPreferredSize(new java.awt.Dimension(133, 41));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5delete.png"))); // NOI18N
        btnRemover.setText("Excluir");
        btnRemover.setEnabled(false);
        btnRemover.setPreferredSize(new java.awt.Dimension(133, 41));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setPreferredSize(new java.awt.Dimension(133, 41));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/2sair.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.setPreferredSize(new java.awt.Dimension(133, 41));
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.setEnabled(false);
        btnGravar.setPreferredSize(new java.awt.Dimension(133, 41));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnEditar.setText("Salvar Edição");
        btnEditar.setToolTipText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnEditar))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        setBounds(2, 2, 1229, 784);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCadPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadPesqKeyReleased
        //chamando o metodo pesquisar
        pesquisar_marca_Prod();
        tblPesqResult.setVisible(true);
    }//GEN-LAST:event_txtCadPesqKeyReleased

    private void tblPesqResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesqResultMouseClicked
        // chamando o metodo setar campos
        setar_campos();
        txtCadPesq.setEnabled(false);
        txtCadNomeEstrutura.setEnabled(true);
        txtCadEstruturaSigla.setEnabled(true);
        txtCadEnderecoEstrutura.setEnabled(true);
        txtCadFilial.setEnabled(true);
        txtCadNomeCidade.setEnabled(true);

        txtCadEstruturaIdCidade.setEnabled(true);
        btnCadTorresPesqCidade.setEnabled(true);
        btnAdicionar.setEnabled(false);
        btnRemover.setEnabled(true);
        btnEditar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnGravar.setEnabled(false);

        tblPesqResult.setEnabled(false);
        tblPesqResult.setVisible(false);


    }//GEN-LAST:event_tblPesqResultMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // habilita os botoes
        btnAdicionar.setEnabled(true);
        btnRemover.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtCadPesq.setEnabled(true);
        txtCadPesq.requestFocus();
        txtCadNomeEstrutura.setEnabled(false);
        txtCadEstruturaSigla.setEnabled(false);
        

        btnGravar.setEnabled(false);
        txtCadNomeEstrutura.setText(null);
        txtCadEstruturaSigla.setText(null);
        txtCadCod.setText(null);
        txtCadNomeCidade.setEnabled(false);

        txtCadNomeCidade.setText(null);
        btnCadTorresPesqCidade.setEnabled(false);
        txtCadEnderecoEstrutura.setEnabled(false);
        txtCadEnderecoEstrutura.setText(null);
        txtCadEstruturaIdCidade.setText(null);
        txtCadEstruturaIdCidade.setEnabled(false);
        txtCadEstruturaEstado.setEnabled(false);
        txtCadFilial.setText(null);
        txtCadEstruturaEstado.setText(null);
        txtCadFilial.setEnabled(false);
        btnEditar.setEnabled(false);
        btnPesqFilial.setEnabled(false);
        tblPesqResult.setEnabled(true);
        tblPesqResult.setVisible(true);
        txtCadObs.setEnabled(false);
        txtCadObs.setText(null);
        txtCadEstruturaIdFilial.setEnabled(false);
        txtCadEstruturaIdFilial.setText(null);


    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // habilita os botoes

        btnAdicionar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnGravar.setEnabled(true);
        txtCadPesq.setEnabled(false);
        txtCadEstruturaSigla.setEnabled(true);
        txtCadNomeEstrutura.setEnabled(true);
        txtCadNomeEstrutura.requestFocus();
        txtCadFilial.setEnabled(true);
        txtCadFilial.setEditable(false);
        txtCadNomeEstrutura.setText(null);
        txtCadEstruturaSigla.setText(null);
        txtCadCod.setText(null);
        txtCadNomeCidade.setEnabled(true);
        txtCadNomeCidade.setText(null);
        txtCadEstruturaEstado.setEnabled(true);
        btnCadTorresPesqCidade.setEnabled(true);
        btnPesqFilial.setEnabled(true);
        txtCadEnderecoEstrutura.setEnabled(true);
        txtCadEnderecoEstrutura.setText(null);
        txtCadEstruturaIdCidade.setText(null);
        txtCadEstruturaIdCidade.setEnabled(true);
        txtCadEstruturaIdCidade.setEditable(false);
        txtCadEstruturaIdFilial.setEnabled(true);
        txtCadEstruturaIdFilial.setEditable(false);
        txtCadEstruturaIdFilial.setText(null);
        txtCadObs.setEnabled(true);
        txtCadObs.setText(null);


    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // metodo excluir marca
        remover();

    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        // grava a marca de produto no banco
        gravar();

    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // metodo alterar
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        btnAdicionar.setEnabled(true);
        btnRemover.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtCadPesq.setEnabled(true);
        txtCadPesq.requestFocus();
        txtCadNomeEstrutura.setEnabled(false);
        txtCadEstruturaSigla.setEnabled(false);
        

        btnGravar.setEnabled(false);
        txtCadNomeEstrutura.setText(null);
        txtCadEstruturaSigla.setText(null);
        txtCadCod.setText(null);
        txtCadNomeCidade.setEnabled(false);

        txtCadNomeCidade.setText(null);
        btnCadTorresPesqCidade.setEnabled(false);
        txtCadEnderecoEstrutura.setEnabled(false);
        txtCadEnderecoEstrutura.setText(null);
        txtCadEstruturaIdCidade.setText(null);
        txtCadEstruturaIdCidade.setEnabled(false);
        txtCadEstruturaEstado.setEnabled(false);
        txtCadFilial.setText(null);
        txtCadEstruturaEstado.setText(null);
        txtCadFilial.setEnabled(false);
        btnEditar.setEnabled(false);
        btnPesqFilial.setEnabled(false);
        tblPesqResult.setEnabled(true);
        tblPesqResult.setVisible(true);
        txtCadObs.setEnabled(false);
        txtCadObs.setText(null);
        txtCadEstruturaIdFilial.setEnabled(false);
        txtCadEstruturaIdFilial.setText(null);
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnCadTorresPesqCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadTorresPesqCidadeActionPerformed
        gerentedetelas.abrirjanelas(TelaPesquisaCidadeCadEstrutura.getInstancia());
        txtCadEstruturaIdCidade.setText(null);
        txtCadNomeCidade.setText(null);
        txtCadEstruturaEstado.setText(null);
        TelaPesquisaCidadeCadEstrutura.txtPesqCidade.setText(null);
        TelaPesquisaCidadeCadEstrutura.txtPesqCidCodigo.setText(null);
        TelaPesquisaCidadeCadEstrutura.txtPesqCidade.setFocusable(true);
        
    }//GEN-LAST:event_btnCadTorresPesqCidadeActionPerformed

    private void txtCadNomeCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadNomeCidadeActionPerformed


    }//GEN-LAST:event_txtCadNomeCidadeActionPerformed

    private void txtCadNomeCidadeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadNomeCidadeKeyReleased

    }//GEN-LAST:event_txtCadNomeCidadeKeyReleased

    private void txtCadPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadPesqActionPerformed

    }//GEN-LAST:event_txtCadPesqActionPerformed

    private void txtCadNomeCidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadNomeCidadeKeyTyped

    }//GEN-LAST:event_txtCadNomeCidadeKeyTyped

    private void txtCadNomeCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCadNomeCidadeMouseClicked

    }//GEN-LAST:event_txtCadNomeCidadeMouseClicked

    private void txtCadNomeCidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadNomeCidadeKeyPressed


    }//GEN-LAST:event_txtCadNomeCidadeKeyPressed

    private void btnPesqFilialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqFilialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesqFilialActionPerformed

    private void txtCadEstruturaIdCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadEstruturaIdCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadEstruturaIdCidadeActionPerformed

    private void btnCadTorresPesqCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCadTorresPesqCidadeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCadTorresPesqCidadeMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAdicionar;
    public static javax.swing.JButton btnCadTorresPesqCidade;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFechar;
    public static javax.swing.JButton btnGravar;
    public static javax.swing.JButton btnPesqFilial;
    public static javax.swing.JButton btnRemover;
    public static javax.swing.JComboBox<String> cboxAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable tblPesqResult;
    public static javax.swing.JTextField txtCadCod;
    public static javax.swing.JTextField txtCadCodigo;
    public static javax.swing.JTextField txtCadEnderecoEstrutura;
    public static javax.swing.JTextField txtCadEstruturaEstado;
    public static javax.swing.JTextField txtCadEstruturaIdCidade;
    public static javax.swing.JTextField txtCadEstruturaIdFilial;
    public static javax.swing.JTextField txtCadEstruturaSigla;
    public static javax.swing.JTextField txtCadFilial;
    public static javax.swing.JTextField txtCadNomeCidade;
    public static javax.swing.JTextField txtCadNomeEstrutura;
    private javax.swing.JTextArea txtCadObs;
    public static javax.swing.JTextField txtCadPesq;
    // End of variables declaration//GEN-END:variables
}
