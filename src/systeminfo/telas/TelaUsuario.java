package systeminfo.telas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nogara
 */
public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultListModel modelo;
    String[] Codig;
    int Enter = 0;

    private static TelaUsuario telaCad;

    public static TelaUsuario getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaUsuario();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector(); 
        // Lista.setVisible(false);
        modelo = new DefaultListModel(); 
        //  Lista.setModel(modelo);

    }

    // metodo de pesquisar material com filtro
    private void pesquisar() {
        String sql = "select idusu as 'Codigo', usuNome as 'Nome', usuLogin as 'Login', usuTipo as 'Tipo', usuSenha as 'Senha', usuBloqueado as 'Bloqueado' from tbusuarios where usuNome like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadPesq.getText() + "%");
            rs = pst.executeQuery();
            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));
            //redimencionar colunas na tabela da tela após a pesquisa
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(300);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(1);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(100);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para setar os campos dos formularios com o conteudo da tabela
    public void setar_campos() {
        txtCadPesq.setText(null);
        int setar = tblPesqResult.getSelectedRow();
        txtCadCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        //tratamento para pegar data da tabela e colocar no campo de data 
//        String data = tblPesqResult.getModel().getValueAt(setar, 9).toString();
//        String ano = data.substring(0, 4);
//        String mes = data.substring(5, 7);
//        String dia = data.substring(8, 10);
//        String formatData = dia + "/" + mes + "/" + ano;
//        txtCadCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());

        txtUsuCodigo.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        txtUsuNome.setText(tblPesqResult.getModel().getValueAt(setar, 1).toString());
        txtUsuLogin.setText(tblPesqResult.getModel().getValueAt(setar, 2).toString());
        CombUsuTipo.setSelectedItem(tblPesqResult.getModel().getValueAt(setar, 3).toString());

        txtUsuSenha1.setText(tblPesqResult.getModel().getValueAt(setar, 4).toString());
        cboxAtivo.setSelectedItem(tblPesqResult.getModel().getValueAt(setar, 5).toString());
        txtUsuCodigo.setEnabled(false);
        txtUsuNome.setEnabled(true);
        txtUsuLogin.setEnabled(true);
        txtUsuSenha1.setEnabled(true);

        txtCadPesq.setFocusable(true);

        tblPesqResult.setVisible(false);
        btnAdicionar.setEnabled(false);
        btnRemover.setEnabled(true);
        btnEditar.setEnabled(true);

    }

//metodo para adiconar usuarios
    private void adicionar() {

        String sql = "insert into tbusuarios (usuNome,usuLogin, usuSenha,usuBloqueado, usuTipo) values (?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, txtUsuCodigo.getText());
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuLogin.getText());
            pst.setString(3, txtUsuSenha1.getText());
            pst.setString(4, cboxAtivo.getSelectedItem().toString());

            pst.setString(5, CombUsuTipo.getSelectedItem().toString());
            // validação dos campos obrigatorios
            if ((txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha1.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos para Salvar");
            } else {
                // a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // a estrutura abaixo é usado para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento a logica 
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");

                    // limpar os campos
                    tblPesqResult.setVisible(false);
                    txtCadPesq.setEnabled(false);
                    txtCadCod.setEnabled(false);

                    txtUsuCodigo.setEnabled(false);
                    txtUsuNome.setEnabled(true);
                    txtUsuLogin.setEnabled(true);
                    txtUsuSenha1.setEnabled(true);

                    txtUsuCodigo.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha1.setText(null);

                    btnAdicionar.setEnabled(false);
                    btnSalvar.setEnabled(true);
                    btnRemover.setEnabled(false);
                    btnEditar.setEnabled(false);
                    btnCancela.setEnabled(true);
                    btnFechar.setEnabled(true);

                    // cboxBloqueado.setSelected(false);
                    //   txtusuBloqueado.setText(null);
                    //cboUsuPerfil.setSelectedItem(null);
                    //btnUsuCreate.setEnabled(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
// metodo para alterar dados do usuario

    private void alterar() {
        String sql = "update tbusuarios set usuNome = ?, usuLogin =?, usuSenha = ?, usuBloqueado = ?, usuTipo = ? where idusu = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuLogin.getText());
            pst.setString(3, txtUsuSenha1.getText());
            pst.setString(4, cboxAtivo.getSelectedItem().toString());

            //   pst.setString(4, txtusuBloqueado.getText());
            pst.setString(5, CombUsuTipo.getSelectedItem().toString());
            pst.setString(6, txtUsuCodigo.getText());
            // validação dos campos obrigatorios
            if ((txtUsuCodigo.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha1.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos para Salvar");
            } else {
                // a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // a estrutura abaixo é usado para confirmar a alteração dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento a logica 
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Usuário alterados com sucesso");
                    // limpar os campos
                    txtUsuCodigo.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha1.setText(null);
                    // txtusuBloqueado.setText(null);

                    //cboUsuPerfil.setSelectedItem(null);
                    btnAdicionar.setEnabled(true);
                    btnSalvar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnEditar.setEnabled(false);
                    btnCancela.setEnabled(true);
                    btnFechar.setEnabled(true);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // metodo responsavel pela remoção de usuarios

    private void remover() {
        // a estrutura abaixo confirma a remoção do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o usuário?");
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete from tbusuarios where idusu = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuCodigo.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso");
                    // limpar os campos
                    txtCadPesq.setEnabled(true);
                    txtCadCod.setText(null);
                    tblPesqResult.setVisible(false);
                    //cboUsuPerfil.setSelectedItem(null);

                    txtCadCod.setEnabled(false);
                    txtUsuCodigo.setEnabled(false);
                    txtUsuNome.setEnabled(false);
                    txtUsuLogin.setEnabled(false);
                    txtUsuSenha1.setEnabled(false);

                    txtUsuCodigo.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha1.setText(null);

                    btnAdicionar.setEnabled(true);
                    btnSalvar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnEditar.setEnabled(false);
                    btnCancela.setEnabled(true);
                    btnFechar.setEnabled(true);

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    // metodo responsavel pela remoção de usuarios

    private void cancela() {
        // a estrutura abaixo cancela os dados
        {
            // limpar os campos

            //   txtusuBloqueado.setText(null);
            txtCadPesq.setEnabled(true);
            txtCadCod.setText(null);
            tblPesqResult.setVisible(false);
            //cboUsuPerfil.setSelectedItem(null);

            txtCadCod.setEnabled(false);
            txtUsuCodigo.setEnabled(false);
            txtUsuNome.setEnabled(false);
            txtUsuLogin.setEnabled(false);
            txtUsuSenha1.setEnabled(false);

            txtUsuCodigo.setText(null);
            txtUsuNome.setText(null);
            txtUsuLogin.setText(null);
            txtUsuSenha1.setText(null);

            btnAdicionar.setEnabled(true);
            btnSalvar.setEnabled(false);
            btnRemover.setEnabled(false);
            btnEditar.setEnabled(false);
            btnCancela.setEnabled(true);
            btnFechar.setEnabled(true);
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
        btnSalvar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CombUsuTipo = new javax.swing.JComboBox<>();
        cboxAtivo = new javax.swing.JComboBox<>();
        txtUsuCodigo = new javax.swing.JTextField();
        txtUsuSenha1 = new javax.swing.JPasswordField();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuLogin = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesqResult = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtCadPesq = new javax.swing.JTextField();
        txtCadCod = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setTitle("Cadastro de Usuários");
        setNextFocusableComponent(txtCadPesq);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnSalvar.setText("Gravar");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setEnabled(false);
        btnSalvar.setPreferredSize(new java.awt.Dimension(60, 70));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5delete.png"))); // NOI18N
        btnRemover.setText("  Excluir");
        btnRemover.setEnabled(false);
        btnRemover.setPreferredSize(new java.awt.Dimension(73, 73));
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

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5add.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnEditar.setText("Salvar Edição");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
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
                .addContainerGap()
                .addComponent(btnAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnCancela)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFechar))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastre o Usuário"));

        jLabel1.setText("Código:");

        jLabel2.setText("Nome:");

        jLabel3.setText("Login:");

        jLabel4.setText("Senha:");

        CombUsuTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));

        cboxAtivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Bloqueado" }));

        txtUsuCodigo.setEditable(false);
        txtUsuCodigo.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txtUsuCodigo.setEnabled(false);

        txtUsuSenha1.setText("jPasswordField1");
        txtUsuSenha1.setEnabled(false);

        txtUsuNome.setEnabled(false);

        txtUsuLogin.setEnabled(false);

        jLabel5.setText("Grupo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txtUsuCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboxAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(CombUsuTipo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUsuSenha1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUsuSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CombUsuTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));

        tblPesqResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, "", null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, "", null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Login", "Tipo", "Senha", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPesqResult.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPesqResult.setCellSelectionEnabled(true);
        tblPesqResult.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblPesqResult.setEnabled(false);
        tblPesqResult.getTableHeader().setResizingAllowed(false);
        tblPesqResult.getTableHeader().setReorderingAllowed(false);
        tblPesqResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesqResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesqResult);
        tblPesqResult.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblPesqResult.getColumnModel().getColumnCount() > 0) {
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(300);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Digite o Nome do Usuario:"));

        txtCadPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadPesqKeyReleased(evt);
            }
        });

        txtCadCod.setEditable(false);

        jLabel6.setText("* Codigo:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtCadPesq)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadCod, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtCadPesq)
                .addComponent(txtCadCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        setBounds(2, 2, 757, 823);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // chama o metod adicionar
        adicionar();

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // chama o metod alterar
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // chama o metod remover
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        // chama o botao cancelar
        cancela();


    }//GEN-LAST:event_btnCancelaActionPerformed

    private void tblPesqResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesqResultMouseClicked
        // chamando o metodo setar campos
        setar_campos();
    }//GEN-LAST:event_tblPesqResultMouseClicked

    private void txtCadPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadPesqKeyReleased
        // chamando o metodo pesquisar
        pesquisar();
        tblPesqResult.setVisible(true);

    }//GEN-LAST:event_txtCadPesqKeyReleased

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed

        // limpar os campos
        //   txtusuBloqueado.setText(null);
        txtCadPesq.setEnabled(true);
        txtCadCod.setText(null);
        tblPesqResult.setVisible(false);
        //cboUsuPerfil.setSelectedItem(null);

        txtCadCod.setEnabled(false);
        txtUsuCodigo.setEnabled(false);
        txtUsuNome.setEnabled(false);
        txtUsuLogin.setEnabled(false);
        txtUsuSenha1.setEnabled(false);

        txtUsuCodigo.setText(null);
        txtUsuNome.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha1.setText(null);

        btnAdicionar.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancela.setEnabled(true);
        btnFechar.setEnabled(true);

        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        //habilita so campos para cadastro
        tblPesqResult.setVisible(false);
        txtCadPesq.setEnabled(false);
        txtCadCod.setEnabled(false);

        txtUsuCodigo.setEnabled(false);
        txtUsuNome.setEnabled(true);
        txtUsuLogin.setEnabled(true);
        txtUsuSenha1.setEnabled(true);

        txtUsuCodigo.setText(null);
        txtUsuNome.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha1.setText(null);

        btnAdicionar.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnRemover.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancela.setEnabled(true);
        btnFechar.setEnabled(true);

    }//GEN-LAST:event_btnAdicionarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> CombUsuTipo;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    public static javax.swing.JComboBox<String> cboxAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPesqResult;
    public static javax.swing.JTextField txtCadCod;
    public static javax.swing.JTextField txtCadPesq;
    public static javax.swing.JTextField txtUsuCodigo;
    public static javax.swing.JTextField txtUsuLogin;
    public static javax.swing.JTextField txtUsuNome;
    public static javax.swing.JPasswordField txtUsuSenha1;
    // End of variables declaration//GEN-END:variables
}
