package systeminfo.telas;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import systeminfo.dal.Gerentedetelas;
import systeminfo.dal.ModuloConexao;
import static systeminfo.telas.TelaPrincipal.Desktop;

public class TelaCadClientes extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    private static TelaCadClientes telaCad;
    
    public static TelaCadClientes getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadClientes();
        }
        return telaCad;
    }
    Gerentedetelas gerentedetelas;
    /**
     * Creates new form TelaCadastroPessoas
     */
    public TelaCadClientes() {
        initComponents();
        conexao = ModuloConexao.conector();
        this.gerentedetelas = new Gerentedetelas(Desktop);
    }
// medodo adicionar pessoa

    private void adicionar() {
        
        String sql = "insert into tbclientes (pesAtivo,pesTipo,pesSexo,"
                + "pesNome,pesDataNasc,pesCPF_CNPJ,pesRG_IE,pesFantasia,pesEMAIL,"
                + "pesTelefone,pesCelular,pesEndereco,pesEndNum,pesEndComp,pesBairro,"
                + "pesNaturalidade,pesEstadoCivil,pesObservacao,idcidade,pesSenha) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            if (cxCadPesAtivo.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            
            
//                Date data = new Date();
//            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
//            lblData.setText(formatador.format(data));
            
            pst.setString(2, cboCadPesTipo.getSelectedItem().toString());
            pst.setString(3, cboCadPesSex.getSelectedItem().toString());
            pst.setString(4, txtCadPesNome.getText());
//            pst.setDate(5, jDateNacimento.getDate());
            //pst.setString(5, jDateNacimento.getDate().toString());
            pst.setString(6, txtCadPesCNPJ.getText());
            pst.setString(7, txtCadPesIE.getText());
            pst.setString(8, txtCadPesFantasia.getText());
            pst.setString(9, txtCadPesEmail.getText());
            pst.setString(10, txtCadPesTelefone.getText());
            pst.setString(11, txtCadPesCelular.getText());
            pst.setString(12, txtCadPesEndereco.getText());
            pst.setString(13, txtCadPesNumEndereco.getText());
            pst.setString(14, txtCadPesEndComp.getText());
            pst.setString(15, txtCadPesBairro.getText());
            pst.setString(16, txtCadPesNaturalidade.getText());
            pst.setString(17, cboCadPesEstCivil.getSelectedItem().toString());
            pst.setString(18, txtCadPesObservacao.getText());
            pst.setString(19, idcidade.getText());
           // pst.setString(20, idconvenio.getText());
            pst.setString(21, "");
            // validação dos campos obrigatorios
            if ((txtCadPesNome.getText().isEmpty()) || (txtCadPesCNPJ.getText().isEmpty())
                    || (txtCadPesEndereco.getText().isEmpty()) || (txtCadPesNumEndereco.getText().isEmpty())
                    || (txtCadPesCelular.getText().isEmpty())
                    || (idcidade.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatórios: Nome, CPF/CNPJ, Endereço, Num. Endereço,"
                        + " Celular e cidade, para Salvar");
            } else {
                int adicionado = pst.executeUpdate();
                System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Empresa Cadastrada com sucesso");

                    // limpar os campos
                    //txtCadPesNaturalidade.setEnabled(false);
                    txtCadPesNome.setEnabled(false);
                  //  jDateNacimento.setEnabled(false);
                    txtCadPesCNPJ.setEnabled(false);
                    txtCadPesIE.setEnabled(false);
                    txtCadPesFantasia.setEnabled(false);
                    txtCadPesEmail.setEnabled(false);
                    txtCadPesEndereco.setEnabled(false);
                    txtCadPesNumEndereco.setEnabled(false);
                    txtCadPesCidade.setEnabled(false);
                    txtCadPesTelefone.setEnabled(false);
                    txtCadPesEndComp.setEnabled(false);
                    txtCadPesCelular.setEnabled(false);
                    txtCadPesObservacao.setEnabled(false);
                    txtCadPesBairro.setEnabled(false);
                    btnCadPesAdicionar.setEnabled(true);
                    btnCadPesSalvar.setEnabled(false);
                    btnCadPesEditar.setEnabled(false);
                    btnCadPesExcluir.setEnabled(false);
                    btnCadPesCancelar.setEnabled(false);
                    btnCadPessPesquisar.setEnabled(true);
                    btnCadPesPesqCidade.setEnabled(false);
//limpa campos do cadastro apos salvar
                    //txtCadPesNaturalidade.setText(null);
                    txtCadPesNome.setText(null);
//                    jDateNacimento.setCalendar(null);
                    txtCadPesCNPJ.setText(null);
                    txtCadPesIE.setText(null);
                    txtCadPesFantasia.setText(null);
                    txtCadPesEmail.setText(null);
                    txtCadPesEndereco.setText(null);
                    txtCadPesNumEndereco.setText(null);
                    txtCadPesCidade.setText(null);
                    idcidade.setText(null);
                    txtCadPesTelefone.setText(null);
                    txtCadPesEndComp.setText(null);
                    txtCadPesCelular.setText(null);
                    txtCadPesObservacao.setText(null);
                    txtCadPesBairro.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
// metodo para excluir

    private void remover() {
        if (txtCadPesCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma Empresa!");
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a Empresa?");
            if (confirma == JOptionPane.YES_NO_OPTION) {
                String sql = "Delete from tbempresa where idpes = ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtCadPesCodigo.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Empresa excluída com sucesso");
                        // limpar os campos
                        //txtCadPesNaturalidade.setEnabled(false);
                        txtCadPesNome.setEnabled(false);
//                        jDateNacimento.setEnabled(false);
                        txtCadPesCNPJ.setEnabled(false);
                        txtCadPesIE.setEnabled(false);
                        txtCadPesFantasia.setEnabled(false);
                        txtCadPesEmail.setEnabled(false);
                        txtCadPesEndereco.setEnabled(false);
                        txtCadPesNumEndereco.setEnabled(false);
                        txtCadPesCidade.setEnabled(false);
                        txtCadPesTelefone.setEnabled(false);
                        txtCadPesEndComp.setEnabled(false);
                        txtCadPesCelular.setEnabled(false);
                        txtCadPesObservacao.setEnabled(false);
                        txtCadPesBairro.setEnabled(false);
                        btnCadPesAdicionar.setEnabled(true);
                        btnCadPesSalvar.setEnabled(false);
                        btnCadPesEditar.setEnabled(false);
                        btnCadPesExcluir.setEnabled(false);
                        btnCadPesCancelar.setEnabled(false);
                        btnCadPessPesquisar.setEnabled(true);
                        btnCadPesPesqCidade.setEnabled(false);
//limpa campos do cadastro apos salvar
                        //txtCadPesNaturalidade.setText(null);
                        txtCadPesNome.setText(null);
//                        jDateNacimento.setCalendar(null);
                        txtCadPesCNPJ.setText(null);
                        txtCadPesIE.setText(null);
                        txtCadPesFantasia.setText(null);
                        txtCadPesEmail.setText(null);
                        txtCadPesEndereco.setText(null);
                        txtCadPesNumEndereco.setText(null);
                        txtCadPesCidade.setText(null);
                        idcidade.setText(null);
                        txtCadPesTelefone.setText(null);
                        txtCadPesEndComp.setText(null);
                        txtCadPesCelular.setText(null);
                        txtCadPesObservacao.setText(null);
                        txtCadPesBairro.setText(null);
                        txtCadPesCodigo.setText(null);
                        txtCadPesDataCadastro.setText(null);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }
// metodo para alterar dados

    private void alterar() {
        String sql = "update tbempresa set pesAtivo = ?,pesTipo = ?,pesNome = ?,pesCPF_CNPJ = ?,pesRG_IE = ?,pesFantasia = ?,pesEMAIL = ?,pesTelefone = ?,pesCelular = ?,pesEndereco = ? ,pesEndNum = ?,pesEndComp = ?,idcidade = ?,pesBairro = ?,pesObservacao = ? where idpes = ? ";
        try {
            pst = conexao.prepareStatement(sql);
            if (cxCadPesAtivo.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            pst.setString(2, cboCadPesTipo.getSelectedItem().toString());
            pst.setString(3, txtCadPesNome.getText());
            pst.setString(4, txtCadPesCNPJ.getText());
            pst.setString(5, txtCadPesIE.getText());
            pst.setString(6, txtCadPesFantasia.getText());
            pst.setString(7, txtCadPesEmail.getText());
            pst.setString(8, txtCadPesTelefone.getText());
            pst.setString(9, txtCadPesCelular.getText());
            pst.setString(10, txtCadPesEndereco.getText());
            pst.setString(11, txtCadPesNumEndereco.getText());
            pst.setString(12, txtCadPesEndComp.getText());
            pst.setString(13, idcidade.getText());
            pst.setString(14, txtCadPesBairro.getText());
            // pst.setString(15, txtCadPesNaturalidade.getText());
            pst.setString(15, txtCadPesObservacao.getText());
            // validação dos campos obrigatorios
            if ((txtCadPesNome.getText().isEmpty()) || (txtCadPesCNPJ.getText().isEmpty()) || (txtCadPesEndereco.getText().isEmpty()) || (txtCadPesNumEndereco.getText().isEmpty()) || (idcidade.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatórios para Salvar");
            } else {
                int adicionado = pst.executeUpdate();
                System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Empresa Alterada com sucesso");

                    // limpar os campos
                    //txtCadPesNaturalidade.setEnabled(false);
                    txtCadPesNome.setEnabled(false);
                    
                    txtCadPesCNPJ.setEnabled(false);
                    txtCadPesIE.setEnabled(false);
                    txtCadPesFantasia.setEnabled(false);
                    txtCadPesEmail.setEnabled(false);
                    txtCadPesEndereco.setEnabled(false);
                    txtCadPesNumEndereco.setEnabled(false);
                    txtCadPesCidade.setEnabled(false);
                    txtCadPesTelefone.setEnabled(false);
                    txtCadPesEndComp.setEnabled(false);
                    txtCadPesCelular.setEnabled(false);
                    txtCadPesObservacao.setEnabled(false);
                    txtCadPesBairro.setEnabled(false);
                    btnCadPesAdicionar.setEnabled(true);
                    btnCadPesSalvar.setEnabled(false);
                    btnCadPesEditar.setEnabled(false);
                    btnCadPesExcluir.setEnabled(false);
                    btnCadPesCancelar.setEnabled(false);
                    btnCadPessPesquisar.setEnabled(true);
                    btnCadPesPesqCidade.setEnabled(false);
//limpa campos do cadastro apos salvar
                    //txtCadPesNaturalidade.setText(null);
                    txtCadPesNome.setText(null);
                    
                    txtCadPesCNPJ.setText(null);
                    txtCadPesIE.setText(null);
                    txtCadPesFantasia.setText(null);
                    txtCadPesEmail.setText(null);
                    txtCadPesEndereco.setText(null);
                    txtCadPesNumEndereco.setText(null);
                    txtCadPesCidade.setText(null);
                    idcidade.setText(null);
                    txtCadPesTelefone.setText(null);
                    txtCadPesEndComp.setText(null);
                    txtCadPesCelular.setText(null);
                    txtCadPesObservacao.setText(null);
                    txtCadPesBairro.setText(null);
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
        btnCadPesAdicionar = new javax.swing.JButton();
        btnCadPesSalvar = new javax.swing.JButton();
        btnCadPesEditar = new javax.swing.JButton();
        btnCadPesExcluir = new javax.swing.JButton();
        btnCadPesCancelar = new javax.swing.JButton();
        btnCadPessPesquisar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCadPesCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cxCadPesAtivo = new javax.swing.JCheckBox();
        txtCadPesDataCadastro = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cboCadPesTipo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtCadPesNome = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCadPesIE = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCadPesFantasia = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCadPesEmail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCadPesEndereco = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCadPesTelefone = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCadPesNumEndereco = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtCadPesEndComp = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCadPesCelular = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtCadPesBairro = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCadPesCidade = new javax.swing.JTextField();
        btnCadPesPesqCidade = new javax.swing.JButton();
        txtCadPesCNPJ = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCadPesObservacao = new javax.swing.JTextArea();
        idcidade = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboCadPesSex = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCadPesNaturalidade = new javax.swing.JTextField();
        cboCadPesEstCivil = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setTitle("Cadastro de Clientes");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCadPesAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5add.png"))); // NOI18N
        btnCadPesAdicionar.setText("Adicionar");
        btnCadPesAdicionar.setToolTipText("Adicionar");
        btnCadPesAdicionar.setMaximumSize(new java.awt.Dimension(99, 25));
        btnCadPesAdicionar.setMinimumSize(new java.awt.Dimension(99, 25));
        btnCadPesAdicionar.setPreferredSize(new java.awt.Dimension(123, 41));
        btnCadPesAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesAdicionarActionPerformed(evt);
            }
        });

        btnCadPesSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnCadPesSalvar.setText("Gravar");
        btnCadPesSalvar.setToolTipText("Salvar");
        btnCadPesSalvar.setEnabled(false);
        btnCadPesSalvar.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadPesSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesSalvarActionPerformed(evt);
            }
        });

        btnCadPesEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnCadPesEditar.setText("Salvar Edição");
        btnCadPesEditar.setToolTipText("Salvar Edição");
        btnCadPesEditar.setEnabled(false);
        btnCadPesEditar.setMargin(new java.awt.Insets(2, 7, 2, 7));
        btnCadPesEditar.setPreferredSize(new java.awt.Dimension(143, 41));
        btnCadPesEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesEditarActionPerformed(evt);
            }
        });

        btnCadPesExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5delete.png"))); // NOI18N
        btnCadPesExcluir.setText("Excluir");
        btnCadPesExcluir.setToolTipText("Excluir");
        btnCadPesExcluir.setEnabled(false);
        btnCadPesExcluir.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadPesExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesExcluirActionPerformed(evt);
            }
        });

        btnCadPesCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5cancelar.png"))); // NOI18N
        btnCadPesCancelar.setText("Cancelar");
        btnCadPesCancelar.setToolTipText("Cancelar");
        btnCadPesCancelar.setEnabled(false);
        btnCadPesCancelar.setPreferredSize(new java.awt.Dimension(123, 41));
        btnCadPesCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesCancelarActionPerformed(evt);
            }
        });

        btnCadPessPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5pesquisar.png"))); // NOI18N
        btnCadPessPesquisar.setText("Pesquisar");
        btnCadPessPesquisar.setToolTipText("Pesquisar");
        btnCadPessPesquisar.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadPessPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPessPesquisarActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCadPessPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadPesAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadPesSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadPesEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadPesExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadPesCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadPesAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadPessPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadPesSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadPesExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadPesCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadPesEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Código do Cliente: ");

        txtCadPesCodigo.setEditable(false);
        txtCadPesCodigo.setEnabled(false);

        jLabel3.setText("Data do Cadastro:");

        cxCadPesAtivo.setSelected(true);
        cxCadPesAtivo.setText("Cadastro Ativo");

        txtCadPesDataCadastro.setEditable(false);
        txtCadPesDataCadastro.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadPesCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadPesDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cxCadPesAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCadPesCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cxCadPesAtivo)
                    .addComponent(txtCadPesDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados:"));

        jLabel4.setText("Tipo:");

        cboCadPesTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Juridica", "Fisica" }));
        cboCadPesTipo.setToolTipText("");

        jLabel7.setText("* Nome:");

        txtCadPesNome.setEnabled(false);

        jLabel10.setText("* CPF/CNPJ:");

        jLabel11.setText("RG/IE:");

        txtCadPesIE.setEnabled(false);

        jLabel12.setText("Fantasia:");

        txtCadPesFantasia.setEnabled(false);

        jLabel13.setText("E-mail:");

        txtCadPesEmail.setEnabled(false);

        jLabel14.setText("* Endereço:");

        txtCadPesEndereco.setEnabled(false);

        jLabel15.setText("Telefone:");

        txtCadPesTelefone.setEnabled(false);

        jLabel16.setText("* Numero:");

        txtCadPesNumEndereco.setEnabled(false);

        jLabel17.setText("Endereço Compl.:");

        txtCadPesEndComp.setEnabled(false);

        jLabel18.setText("Celular:");

        txtCadPesCelular.setEnabled(false);

        jLabel19.setText("Bairro:");

        txtCadPesBairro.setEnabled(false);

        jLabel20.setText("*Cidade:");

        txtCadPesCidade.setEnabled(false);

        btnCadPesPesqCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadPesPesqCidade.setEnabled(false);
        btnCadPesPesqCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesPesqCidadeActionPerformed(evt);
            }
        });

        txtCadPesCNPJ.setEnabled(false);

        jLabel21.setText("Observação:");

        txtCadPesObservacao.setColumns(20);
        txtCadPesObservacao.setRows(5);
        txtCadPesObservacao.setEnabled(false);
        jScrollPane1.setViewportView(txtCadPesObservacao);

        idcidade.setEnabled(false);

        jLabel1.setText("Sexo:");

        cboCadPesSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino", "Empresa" }));
        cboCadPesSex.setToolTipText("");

        jLabel5.setText("Data Nasc:");

        jLabel6.setText("Naturalidade:");

        txtCadPesNaturalidade.setEnabled(false);

        cboCadPesEstCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Solteiro", "Casado", "Amasiado" }));
        cboCadPesEstCivil.setToolTipText("");

        jLabel9.setText("Estado Civil:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesEndComp, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addGap(6, 6, 6)
                                    .addComponent(txtCadPesBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(idcidade, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCadPesCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCadPesPesqCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCadPesEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesNumEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesNome))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesFantasia))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(7, 7, 7)
                                .addComponent(txtCadPesCNPJ))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCadPesTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCadPesSex, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesIE, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCadPesEstCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadPesNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboCadPesTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cboCadPesSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtCadPesNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtCadPesNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(txtCadPesIE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadPesCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCadPesEstCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtCadPesFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(txtCadPesEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtCadPesEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(txtCadPesNumEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtCadPesTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(txtCadPesBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(txtCadPesCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idcidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnCadPesPesqCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadPesCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtCadPesEndComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        setBounds(2, 2, 923, 614);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadPessPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPessPesquisarActionPerformed
        // habilita e desabilita campos
        btnCadPesAdicionar.setEnabled(false);
        btnCadPesSalvar.setEnabled(false);
        btnCadPesEditar.setEnabled(true);
        btnCadPesExcluir.setEnabled(true);
        btnCadPesCancelar.setEnabled(true);
        //editar campos
        //txtCadPesNaturalidade.setEnabled(true);
        txtCadPesNome.setEnabled(true);
        
        txtCadPesCNPJ.setEnabled(true);
        txtCadPesIE.setEnabled(true);
        txtCadPesFantasia.setEnabled(true);
        txtCadPesEmail.setEnabled(true);
        txtCadPesEndereco.setEnabled(true);
        txtCadPesNumEndereco.setEnabled(true);
        txtCadPesCidade.setEnabled(false);
        txtCadPesTelefone.setEnabled(true);
        txtCadPesEndComp.setEnabled(true);
        txtCadPesCelular.setEnabled(true);
        txtCadPesObservacao.setEnabled(true);
        txtCadPesBairro.setEnabled(true);
        btnCadPesPesqCidade.setEnabled(true);

        // chama a tela de pesquisa de pessoas
        gerentedetelas.abrirjanelas(TelaPesquisaEmpresa.getInstancia()); 
    }//GEN-LAST:event_btnCadPessPesquisarActionPerformed

    private void btnCadPesPesqCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesPesqCidadeActionPerformed
        // chama a tela de pesquisa de cidades
        gerentedetelas.abrirjanelas(TelaPesquisaCidadeCadEstrutura.getInstancia());
    }//GEN-LAST:event_btnCadPesPesqCidadeActionPerformed

    private void btnCadPesAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesAdicionarActionPerformed
        // habilita campos para cadastro
        //txtCadPesNaturalidade.setEnabled(true);
        txtCadPesNome.setEnabled(true);
//        jDateNacimento.setEnabled(true);
        txtCadPesNaturalidade.setEnabled(true);
        txtCadPesCNPJ.setEnabled(true);
        txtCadPesIE.setEnabled(true);
        txtCadPesFantasia.setEnabled(true);
        txtCadPesEmail.setEnabled(true);
        txtCadPesEndereco.setEnabled(true);
        txtCadPesNumEndereco.setEnabled(true);
        txtCadPesCidade.setEnabled(false);
        txtCadPesTelefone.setEnabled(true);
        txtCadPesEndComp.setEnabled(true);
        txtCadPesCelular.setEnabled(true);
        txtCadPesObservacao.setEnabled(true);
        txtCadPesBairro.setEnabled(true);
        btnCadPesAdicionar.setEnabled(false);
        btnCadPesSalvar.setEnabled(true);
        btnCadPesEditar.setEnabled(false);
        btnCadPesExcluir.setEnabled(false);
        btnCadPesCancelar.setEnabled(true);
        btnCadPessPesquisar.setEnabled(false);
        btnCadPesPesqCidade.setEnabled(true);
        
        

    }//GEN-LAST:event_btnCadPesAdicionarActionPerformed

    private void btnCadPesCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesCancelarActionPerformed
        // cancela cadastro de pessoas
        //txtCadPesNaturalidade.setEnabled(false);
        txtCadPesNome.setEnabled(false);
        
        txtCadPesCNPJ.setEnabled(false);
        txtCadPesIE.setEnabled(false);
        txtCadPesFantasia.setEnabled(false);
        txtCadPesEmail.setEnabled(false);
        txtCadPesEndereco.setEnabled(false);
        txtCadPesNumEndereco.setEnabled(false);
        txtCadPesCidade.setEnabled(false);
        txtCadPesTelefone.setEnabled(false);
        txtCadPesEndComp.setEnabled(false);
        txtCadPesCelular.setEnabled(false);
        txtCadPesObservacao.setEnabled(false);
        txtCadPesBairro.setEnabled(false);
        btnCadPesAdicionar.setEnabled(true);
        btnCadPesSalvar.setEnabled(false);
        btnCadPesEditar.setEnabled(false);
        btnCadPesExcluir.setEnabled(false);
        btnCadPesCancelar.setEnabled(false);
        btnCadPessPesquisar.setEnabled(true);
        btnCadPesPesqCidade.setEnabled(false);
//limpa campos do cadastro apos cancelar
        //txtCadPesNaturalidade.setText(null);
        txtCadPesNome.setText(null);
        
        txtCadPesCNPJ.setText(null);
        txtCadPesIE.setText(null);
        txtCadPesFantasia.setText(null);
        txtCadPesEmail.setText(null);
        txtCadPesEndereco.setText(null);
        txtCadPesNumEndereco.setText(null);
        txtCadPesCidade.setText(null);
        idcidade.setText(null);
        txtCadPesTelefone.setText(null);
        txtCadPesEndComp.setText(null);
        txtCadPesCelular.setText(null);
        txtCadPesObservacao.setText(null);
        txtCadPesBairro.setText(null);
    }//GEN-LAST:event_btnCadPesCancelarActionPerformed

    private void btnCadPesEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesEditarActionPerformed
        // editar os campos
        alterar();
    }//GEN-LAST:event_btnCadPesEditarActionPerformed

    private void btnCadPesSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesSalvarActionPerformed
        // chama o metodo adicionar
        adicionar();
    }//GEN-LAST:event_btnCadPesSalvarActionPerformed

    private void btnCadPesExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesExcluirActionPerformed
        // chama metodo remover
        remover();
    }//GEN-LAST:event_btnCadPesExcluirActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed

        // limpar os campos
        //   txtusuBloqueado.setText(null);

        btnFechar.setEnabled(true);

        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadPesAdicionar;
    private javax.swing.JButton btnCadPesCancelar;
    private javax.swing.JButton btnCadPesEditar;
    private javax.swing.JButton btnCadPesExcluir;
    private javax.swing.JButton btnCadPesPesqCidade;
    private javax.swing.JButton btnCadPesSalvar;
    private javax.swing.JButton btnCadPessPesquisar;
    private javax.swing.JButton btnFechar;
    public static javax.swing.JComboBox<String> cboCadPesEstCivil;
    public static javax.swing.JComboBox<String> cboCadPesSex;
    public static javax.swing.JComboBox<String> cboCadPesTipo;
    public static javax.swing.JCheckBox cxCadPesAtivo;
    public static javax.swing.JTextField idcidade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextField txtCadPesBairro;
    public static javax.swing.JTextField txtCadPesCNPJ;
    public static javax.swing.JTextField txtCadPesCelular;
    public static javax.swing.JTextField txtCadPesCidade;
    public static javax.swing.JTextField txtCadPesCodigo;
    public static javax.swing.JTextField txtCadPesDataCadastro;
    public static javax.swing.JTextField txtCadPesEmail;
    public static javax.swing.JTextField txtCadPesEndComp;
    public static javax.swing.JTextField txtCadPesEndereco;
    public static javax.swing.JTextField txtCadPesFantasia;
    public static javax.swing.JTextField txtCadPesIE;
    public static javax.swing.JTextField txtCadPesNaturalidade;
    public static javax.swing.JTextField txtCadPesNome;
    public static javax.swing.JTextField txtCadPesNumEndereco;
    public static javax.swing.JTextArea txtCadPesObservacao;
    public static javax.swing.JTextField txtCadPesTelefone;
    // End of variables declaration//GEN-END:variables
}
