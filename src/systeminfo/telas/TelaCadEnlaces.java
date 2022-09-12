package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;
import static systeminfo.telas.TelaCadEnlaces.txtCadEnlaceFaixaIP1;

/**
 *
 * @author Nutrizon
 */
public class TelaCadEnlaces extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;

    int ip = 1; // criar posteriomente um campo para essa definição de Range de calculo de IP
    String iproutermestre = null;
    String iprouterescravo = null;
    String ipradiomestre = null;
    String ipradioescravo = null;

//metodo que abre somente uma tela junto com a classe Gerencia de Tela    
    private static TelaCadEnlaces telaCad;

    public static TelaCadEnlaces getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadEnlaces();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaMarcasProdutos
     */
    public TelaCadEnlaces() {
        initComponents();
        conexao = ModuloConexao.conector();

    }

    //metodo para adiconar marcas
    private void gravar() {
        String sql = "insert into tbenlaces (enlAtivo, enlcodDesc, enlIdMestre, enlIdEscravo, enlFaixaIP, FaixaIP1, FaixaIP2, FaixaIP3, FaixaIP4, enlCIDR, enlRouterMestre, enlRouterEscravo, enlRadioMestre, enlRadioEscravo, enlChave, enlObservacao) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int ip1 = Integer.parseInt(txtCadEnlaceFaixaIP1.getText());
        int ip2 = Integer.parseInt(txtCadEnlaceFaixaIP2.getText());
        int ip3 = Integer.parseInt(txtCadEnlaceFaixaIP3.getText());
        int ip4 = Integer.parseInt(txtCadEnlaceFaixaIP4.getText());
        try {
            pst = conexao.prepareCall(sql);

            if (cboCadAtivoStatus.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            pst.setString(2, txtCadEnlaceDescricao.getText());
            pst.setString(3, txtCadEnlaceIdMestre.getText());
            pst.setString(4, txtCadEnlaceIdEscravo.getText());
            pst.setString(5, ip1 + "." + ip2 + "." + ip3 + "." + ip4);
            pst.setString(6, txtCadEnlaceFaixaIP1.getText());
            pst.setString(7, txtCadEnlaceFaixaIP2.getText());
            pst.setString(8, txtCadEnlaceFaixaIP3.getText());
            pst.setString(9, txtCadEnlaceFaixaIP4.getText());
            pst.setString(10, txtCadEnlaceCIDR.getText());
            pst.setString(11, txtCadEnlaceRouterMestre.getText());
            pst.setString(12, txtCadEnlaceRouterEscravo.getText());
            pst.setString(13, txtCadEnlaceRadioMestre.getText());
            pst.setString(14, txtCadEnlaceRadioEscravo.getText());
            pst.setString(15, txtCadEnlaceChave.getText());
            pst.setString(16, txtCadEnlaceObserv.getText());
            // validação dos campos obrigatorios
            if ((txtCadEnlaceDescricao.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP1.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP2.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP3.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP4.getText().isEmpty())
                    || (txtCadEnlaceRouterEscravo.getText().isEmpty())
                    || (txtCadEnlaceCIDR.getText().isEmpty())
                    || (txtCadEnlaceIdEscravo.getText().isEmpty())
                    || (txtCadEnlaceRouterMestre.getText().isEmpty())
                    || (txtCadEnlaceIdMestre.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos com * para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Enlace cadastrado com sucesso");

                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    btnCadEnlacePesAtivoMes.setEnabled(false);
                    btnCadEnlacePesqAtivoEsc.setEnabled(false);
                    btnGeraIP.setEnabled(false);

                    txtCadEnlaceDescricao.setEnabled(false);
                    txtCadEnlaceRouterMestre.setEnabled(false);
                    btnCadPesquisar.setEnabled(true);
                    btnCadGravar.setEnabled(false);
                    txtCadEnlaceDescricao.setText(null);
                    txtCadEnlaceRouterMestre.setText(null);
                    txtCadEnlaceCod.setText(null);
                    txtCadEnlaceMestre.setEnabled(false);
                    txtCadEnlaceMestre.setEditable(false);
                    txtCadEnlaceMestre.setText(null);
                    btnCadEnlacePesAtivoMes.setEnabled(false);
                    txtCadEnlaceFaixaIP1.setEnabled(false);
                    txtCadEnlaceFaixaIP1.setText(null);
                    txtCadEnlaceFaixaIP2.setEnabled(false);
                    txtCadEnlaceFaixaIP2.setText(null);
                    txtCadEnlaceFaixaIP3.setEnabled(false);
                    txtCadEnlaceFaixaIP3.setText(null);
                    txtCadEnlaceFaixaIP4.setEnabled(false);
                    txtCadEnlaceFaixaIP4.setText(null);
                    txtCadEnlaceIdMestre.setText(null);
                    txtCadEnlaceCIDR.setText(null);
                    txtCadEnlaceRouterEscravo.setText(null);
                    txtCadEnlaceRouterEscravo.setEnabled(false);
                    txtCadEnlaceCIDR.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadEnlaceRadioMestre.setText(null);
                    txtCadEnlaceRadioMestre.setEnabled(false);
                    txtCadEnlaceEscravo.setText(null);
                    txtCadEnlaceEscravo.setEnabled(false);
                    txtCadEnlaceObserv.setText(null);
                    txtCadEnlaceObserv.setEnabled(false);
                    txtCadEnlaceIdEscravo.setText(null);
                    txtCadEnlaceChave.setEnabled(false);
                    txtCadEnlaceChave.setText(null);
                    txtCadEnlaceRadioEscravo.setEnabled(false);
                    txtCadEnlaceRadioEscravo.setText(null);
                    txtCadEnlaceData.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para excluir a marca
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Excluir o Enlace?");
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete from tbenlaces where idenl = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadEnlaceCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Enlace Excluido com sucesso");

                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    btnCadEnlacePesAtivoMes.setEnabled(false);
                    btnCadEnlacePesqAtivoEsc.setEnabled(false);
                    btnGeraIP.setEnabled(false);

                    txtCadEnlaceDescricao.setEnabled(false);
                    txtCadEnlaceRouterMestre.setEnabled(false);
                    btnCadPesquisar.setEnabled(true);
                    btnCadGravar.setEnabled(false);
                    txtCadEnlaceDescricao.setText(null);
                    txtCadEnlaceRouterMestre.setText(null);
                    txtCadEnlaceCod.setText(null);
                    txtCadEnlaceMestre.setEnabled(false);
                    txtCadEnlaceMestre.setEditable(false);
                    txtCadEnlaceMestre.setText(null);
                    btnCadEnlacePesAtivoMes.setEnabled(false);
                    txtCadEnlaceFaixaIP1.setEnabled(false);
                    txtCadEnlaceFaixaIP1.setText(null);
                    txtCadEnlaceFaixaIP2.setEnabled(false);
                    txtCadEnlaceFaixaIP2.setText(null);
                    txtCadEnlaceFaixaIP3.setEnabled(false);
                    txtCadEnlaceFaixaIP3.setText(null);
                    txtCadEnlaceFaixaIP4.setEnabled(false);
                    txtCadEnlaceFaixaIP4.setText(null);
                    txtCadEnlaceIdMestre.setText(null);
                    txtCadEnlaceCIDR.setText(null);
                    txtCadEnlaceRouterEscravo.setText(null);
                    txtCadEnlaceRouterEscravo.setEnabled(false);
                    txtCadEnlaceCIDR.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadEnlaceRadioMestre.setText(null);
                    txtCadEnlaceRadioMestre.setEnabled(false);
                    txtCadEnlaceEscravo.setText(null);
                    txtCadEnlaceEscravo.setEnabled(false);
                    txtCadEnlaceObserv.setText(null);
                    txtCadEnlaceObserv.setEnabled(false);
                    txtCadEnlaceIdEscravo.setText(null);
                    txtCadEnlaceChave.setEnabled(false);
                    txtCadEnlaceChave.setText(null);
                    txtCadEnlaceRadioEscravo.setEnabled(false);
                    txtCadEnlaceRadioEscravo.setText(null);
                    txtCadEnlaceData.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void alterar() {
        String sql = "update tbenlaces set enlAtivo = ?, enlcodDesc = ?, enlIdMestre = ?, enlIdEscravo = ?, enlFaixaIP = ?, FaixaIP1 = ?, FaixaIP2 = ?, FaixaIP3 = ?, FaixaIP4 = ?, enlCIDR  = ?, "
                + "enlRouterMestre = ?, enlRouterEscravo = ?, enlRadioMestre = ?, enlRadioEscravo = ?, enlChave = ?, enlObservacao = ? where idenl = ?";
        int ip1 = Integer.parseInt(txtCadEnlaceFaixaIP1.getText());
        int ip2 = Integer.parseInt(txtCadEnlaceFaixaIP2.getText());
        int ip3 = Integer.parseInt(txtCadEnlaceFaixaIP3.getText());
        int ip4 = Integer.parseInt(txtCadEnlaceFaixaIP4.getText());
        try {
            pst = conexao.prepareCall(sql);

            if (cboCadAtivoStatus.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            pst.setString(2, txtCadEnlaceDescricao.getText());
            pst.setString(3, txtCadEnlaceIdMestre.getText());
            pst.setString(4, txtCadEnlaceIdEscravo.getText());
            pst.setString(5, ip1 + "." + ip2 + "." + ip3 + "." + ip4);
            pst.setString(6, txtCadEnlaceFaixaIP1.getText());
            pst.setString(7, txtCadEnlaceFaixaIP2.getText());
            pst.setString(8, txtCadEnlaceFaixaIP3.getText());
            pst.setString(9, txtCadEnlaceFaixaIP4.getText());
            pst.setString(10, txtCadEnlaceCIDR.getText());
            pst.setString(11, txtCadEnlaceRouterMestre.getText());
            pst.setString(12, txtCadEnlaceRouterEscravo.getText());
            pst.setString(13, txtCadEnlaceRadioMestre.getText());
            pst.setString(14, txtCadEnlaceRadioEscravo.getText());
            pst.setString(15, txtCadEnlaceChave.getText());
            pst.setString(16, txtCadEnlaceObserv.getText());
            //where
            pst.setString(17, txtCadEnlaceCod.getText());
            if ((txtCadEnlaceDescricao.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP1.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP2.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP3.getText().isEmpty())
                    || (txtCadEnlaceFaixaIP4.getText().isEmpty())
                    || (txtCadEnlaceRouterEscravo.getText().isEmpty())
                    || (txtCadEnlaceCIDR.getText().isEmpty())
                    || (txtCadEnlaceIdEscravo.getText().isEmpty())
                    || (txtCadEnlaceRouterMestre.getText().isEmpty())
                    || (txtCadEnlaceIdMestre.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos com * para Editar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Enlace alterado com sucesso");
                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    btnCadEnlacePesAtivoMes.setEnabled(false);
                    btnCadEnlacePesqAtivoEsc.setEnabled(false);
                    btnGeraIP.setEnabled(false);

                    txtCadEnlaceDescricao.setEnabled(false);
                    txtCadEnlaceRouterMestre.setEnabled(false);
                    btnCadPesquisar.setEnabled(true);
                    btnCadGravar.setEnabled(false);
                    txtCadEnlaceDescricao.setText(null);
                    txtCadEnlaceRouterMestre.setText(null);
                    txtCadEnlaceCod.setText(null);
                    txtCadEnlaceMestre.setEnabled(false);
                    txtCadEnlaceMestre.setEditable(false);
                    txtCadEnlaceMestre.setText(null);
                    btnCadEnlacePesAtivoMes.setEnabled(false);
                    txtCadEnlaceFaixaIP1.setEnabled(false);
                    txtCadEnlaceFaixaIP1.setText(null);
                    txtCadEnlaceFaixaIP2.setEnabled(false);
                    txtCadEnlaceFaixaIP2.setText(null);
                    txtCadEnlaceFaixaIP3.setEnabled(false);
                    txtCadEnlaceFaixaIP3.setText(null);
                    txtCadEnlaceFaixaIP4.setEnabled(false);
                    txtCadEnlaceFaixaIP4.setText(null);
                    txtCadEnlaceIdMestre.setText(null);
                    txtCadEnlaceCIDR.setText(null);
                    txtCadEnlaceRouterEscravo.setText(null);
                    txtCadEnlaceRouterEscravo.setEnabled(false);
                    txtCadEnlaceCIDR.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadEnlaceRadioMestre.setText(null);
                    txtCadEnlaceRadioMestre.setEnabled(false);
                    txtCadEnlaceEscravo.setText(null);
                    txtCadEnlaceEscravo.setEnabled(false);
                    txtCadEnlaceObserv.setText(null);
                    txtCadEnlaceObserv.setEnabled(false);
                    txtCadEnlaceIdEscravo.setText(null);
                    txtCadEnlaceChave.setEnabled(false);
                    txtCadEnlaceChave.setText(null);
                    txtCadEnlaceRadioEscravo.setEnabled(false);
                    txtCadEnlaceRadioEscravo.setText(null);
                    txtCadEnlaceData.setText(null);

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
        txtCadEnlaceDescricao = new javax.swing.JTextField();
        txtCadEnlaceRouterMestre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCadEnlaceMestre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnCadEnlacePesAtivoMes = new javax.swing.JButton();
        txtCadEnlaceFaixaIP1 = new javax.swing.JTextField();
        txtCadEnlaceIdMestre = new javax.swing.JTextField();
        btnCadGravar = new javax.swing.JButton();
        cboCadAtivoStatus = new javax.swing.JCheckBox();
        txtCadEnlaceRouterEscravo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCadEnlaceCIDR = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCadEnlaceIdEscravo = new javax.swing.JTextField();
        txtCadEnlaceEscravo = new javax.swing.JTextField();
        btnCadEnlacePesqAtivoEsc = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtCadEnlaceCod = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCadEnlaceRadioMestre = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCadEnlaceRadioEscravo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtCadEnlaceChave = new javax.swing.JTextField();
        txtCadEnlaceFaixaIP2 = new javax.swing.JTextField();
        txtCadEnlaceFaixaIP4 = new javax.swing.JTextField();
        txtCadEnlaceFaixaIP3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnGeraIP = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCadEnlaceObserv = new javax.swing.JTextPane();
        txtCadEnlaceData = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCadPesquisar = new javax.swing.JButton();
        btnCadAdicionar = new javax.swing.JButton();
        btnCadExcluir = new javax.swing.JButton();
        btnCadCancelar = new javax.swing.JButton();
        btnCadEditar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle("Cadastro de Enlace");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setNextFocusableComponent(btnCadPesquisar);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastre o Enlace:"));

        txtCadEnlaceDescricao.setEnabled(false);
        txtCadEnlaceDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadEnlaceDescricaoActionPerformed(evt);
            }
        });

        txtCadEnlaceRouterMestre.setEnabled(false);
        txtCadEnlaceRouterMestre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadEnlaceRouterMestreActionPerformed(evt);
            }
        });

        jLabel1.setText("*Descrição ID:");

        jLabel2.setText("Router Mestre:");

        jLabel3.setText("*Mestre:");

        txtCadEnlaceMestre.setEditable(false);
        txtCadEnlaceMestre.setEnabled(false);

        jLabel4.setText("* Faixa IP:");

        btnCadEnlacePesAtivoMes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadEnlacePesAtivoMes.setEnabled(false);
        btnCadEnlacePesAtivoMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadEnlacePesAtivoMesActionPerformed(evt);
            }
        });

        txtCadEnlaceFaixaIP1.setEnabled(false);
        txtCadEnlaceFaixaIP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadEnlaceFaixaIP1ActionPerformed(evt);
            }
        });

        txtCadEnlaceIdMestre.setEnabled(false);
        txtCadEnlaceIdMestre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadEnlaceIdMestreActionPerformed(evt);
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

        txtCadEnlaceRouterEscravo.setEnabled(false);

        jLabel5.setText("Ativo?:");

        jLabel6.setText("*CIDR:");

        txtCadEnlaceCIDR.setEnabled(false);

        jLabel7.setText("Router Escravo:");

        jLabel8.setText("*Escravo:");

        txtCadEnlaceIdEscravo.setEnabled(false);
        txtCadEnlaceIdEscravo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadEnlaceIdEscravoActionPerformed(evt);
            }
        });

        txtCadEnlaceEscravo.setEditable(false);
        txtCadEnlaceEscravo.setEnabled(false);

        btnCadEnlacePesqAtivoEsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/0pesquisar.png"))); // NOI18N
        btnCadEnlacePesqAtivoEsc.setEnabled(false);
        btnCadEnlacePesqAtivoEsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadEnlacePesqAtivoEscActionPerformed(evt);
            }
        });

        jLabel10.setText("Observação:");

        txtCadEnlaceCod.setEnabled(false);

        jLabel11.setText("*Código:");

        txtCadEnlaceRadioMestre.setEnabled(false);

        jLabel12.setText("Radio Mestre: ");

        txtCadEnlaceRadioEscravo.setEnabled(false);

        jLabel13.setText("Radio Escravo: ");

        jLabel14.setText("Chave:");

        txtCadEnlaceChave.setEnabled(false);
        txtCadEnlaceChave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadEnlaceChaveActionPerformed(evt);
            }
        });

        txtCadEnlaceFaixaIP2.setEnabled(false);

        txtCadEnlaceFaixaIP4.setEnabled(false);
        txtCadEnlaceFaixaIP4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCadEnlaceFaixaIP4KeyPressed(evt);
            }
        });

        txtCadEnlaceFaixaIP3.setEnabled(false);

        jLabel17.setText(".");

        jLabel9.setText(".");

        jLabel15.setText(".");

        btnGeraIP.setText("Gera IP");
        btnGeraIP.setEnabled(false);
        btnGeraIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeraIPActionPerformed(evt);
            }
        });

        txtCadEnlaceObserv.setEnabled(false);
        jScrollPane1.setViewportView(txtCadEnlaceObserv);

        txtCadEnlaceData.setEditable(false);
        txtCadEnlaceData.setEnabled(false);

        jLabel16.setText("Data Cadastro:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCadEnlaceFaixaIP1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadEnlaceIdMestre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEnlaceFaixaIP2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEnlaceFaixaIP3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEnlaceFaixaIP4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEnlaceCIDR, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGeraIP))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCadEnlaceMestre, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCadEnlacePesAtivoMes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEnlaceIdEscravo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadEnlaceEscravo, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCadEnlacePesqAtivoEsc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCadEnlaceDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(txtCadEnlaceRouterMestre, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCadEnlaceRouterEscravo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCadEnlaceRadioMestre, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel13))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(txtCadEnlaceCod, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(124, 124, 124)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cboCadAtivoStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel16)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtCadEnlaceData)
                                            .addComponent(txtCadEnlaceRadioEscravo, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                    .addComponent(btnCadGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtCadEnlaceChave, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(11, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel11)
                            .addComponent(txtCadEnlaceCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCadAtivoStatus)
                            .addComponent(jLabel5)
                            .addComponent(txtCadEnlaceData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCadEnlaceDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(txtCadEnlaceIdMestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadEnlaceMestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCadEnlacePesAtivoMes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtCadEnlaceIdEscravo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadEnlaceEscravo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCadEnlacePesqAtivoEsc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtCadEnlaceFaixaIP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(txtCadEnlaceFaixaIP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtCadEnlaceFaixaIP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(txtCadEnlaceFaixaIP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtCadEnlaceCIDR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(btnGeraIP))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(txtCadEnlaceRadioEscravo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCadEnlaceRadioMestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCadEnlaceRouterEscravo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtCadEnlaceRouterMestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtCadEnlaceChave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(76, 76, 76))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCadPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1pesquisar.png"))); // NOI18N
        btnCadPesquisar.setText("Pesquisar");
        btnCadPesquisar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCadPesquisar.setIconTextGap(1);
        btnCadPesquisar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadPesquisarActionPerformed(evt);
            }
        });

        btnCadAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1add.png"))); // NOI18N
        btnCadAdicionar.setText("Adicionar");
        btnCadAdicionar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadAdicionarActionPerformed(evt);
            }
        });

        btnCadExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1delete.png"))); // NOI18N
        btnCadExcluir.setText("Excluir");
        btnCadExcluir.setEnabled(false);
        btnCadExcluir.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadExcluirActionPerformed(evt);
            }
        });

        btnCadCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1cancelar.png"))); // NOI18N
        btnCadCancelar.setText("Cancelar");
        btnCadCancelar.setEnabled(false);
        btnCadCancelar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCadCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCancelarActionPerformed(evt);
            }
        });

        btnCadEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/1editar.png"))); // NOI18N
        btnCadEditar.setText("Editar");
        btnCadEditar.setToolTipText("Editar");
        btnCadEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadEditar.setEnabled(false);
        btnCadEditar.setPreferredSize(new java.awt.Dimension(135, 57));
        btnCadEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadEditarActionPerformed(evt);
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
                .addGap(56, 56, 56)
                .addComponent(btnCadPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        setBounds(20, 20, 1031, 564);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadPesquisarActionPerformed
        // habilita campos para pesquisa
        TelaPesqEnlace tela = new TelaPesqEnlace();
        TelaPrincipal.Desktop.add(tela);
        tela.setVisible(true);
        btnCadEnlacePesAtivoMes.setEnabled(false);
        btnCadAdicionar.setEnabled(false);
        btnCadExcluir.setEnabled(true);
        btnCadEditar.setEnabled(true);
        btnCadCancelar.setEnabled(true);
        btnCadGravar.setEnabled(false);
        btnCadPesquisar.setEnabled(false);
        btnGeraIP.setEnabled(true);

        txtCadEnlaceDescricao.setText(null);
        txtCadEnlaceDescricao.setEnabled(true);
        txtCadEnlaceRouterMestre.setText(null);
        txtCadEnlaceRouterMestre.setEnabled(true);
        txtCadEnlaceCod.setText(null);
        txtCadEnlaceCIDR.setText(null);
        txtCadEnlaceCIDR.setEnabled(true);
        txtCadEnlaceRouterEscravo.setText(null);
        txtCadEnlaceRouterEscravo.setEnabled(true);
        txtCadEnlaceMestre.setText(null);
        txtCadEnlaceMestre.setEnabled(true);
        txtCadEnlaceIdMestre.setText(null);
        txtCadEnlaceFaixaIP1.setText(null);
        txtCadEnlaceFaixaIP1.setEnabled(true);
        txtCadEnlaceFaixaIP2.setText(null);
        txtCadEnlaceFaixaIP2.setEnabled(true);
        txtCadEnlaceFaixaIP3.setText(null);
        txtCadEnlaceFaixaIP3.setEnabled(true);
        txtCadEnlaceFaixaIP4.setText(null);
        txtCadEnlaceFaixaIP4.setEnabled(true);
        txtCadEnlaceIdEscravo.setText(null);
        txtCadEnlaceEscravo.setText(null);
        txtCadEnlaceEscravo.setEnabled(true);
        txtCadEnlaceObserv.setText(null);
        txtCadEnlaceObserv.setEnabled(true);
        txtCadEnlaceChave.setText(null);
        txtCadEnlaceChave.setEnabled(true);
        txtCadEnlaceRadioMestre.setText(null);
        txtCadEnlaceRadioMestre.setEnabled(true);
        txtCadEnlaceRadioEscravo.setText(null);
        txtCadEnlaceRadioEscravo.setEnabled(true);

        btnCadEnlacePesAtivoMes.setEnabled(true);
        btnCadEnlacePesqAtivoEsc.setEnabled(true);
    }//GEN-LAST:event_btnCadPesquisarActionPerformed

    private void btnCadCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCancelarActionPerformed
        // habilita os botoes
        btnCadAdicionar.setEnabled(true);
        btnCadExcluir.setEnabled(false);
        btnCadCancelar.setEnabled(false);
        btnCadEnlacePesAtivoMes.setEnabled(false);
        btnCadEnlacePesqAtivoEsc.setEnabled(false);
        btnCadGravar.setEnabled(false);
        btnCadPesquisar.setEnabled(true);
        btnCadEditar.setEnabled(false);
        btnGeraIP.setEnabled(false);

        txtCadEnlaceDescricao.setEnabled(false);
        txtCadEnlaceRouterMestre.setEnabled(false);
        txtCadEnlaceDescricao.setText(null);
        txtCadEnlaceRouterMestre.setText(null);
        txtCadEnlaceCod.setText(null);
        txtCadEnlaceMestre.setEnabled(false);
        txtCadEnlaceMestre.setEditable(false);
        txtCadEnlaceMestre.setText(null);
        txtCadEnlaceFaixaIP1.setEnabled(false);
        txtCadEnlaceFaixaIP1.setText(null);
        txtCadEnlaceFaixaIP2.setEnabled(false);
        txtCadEnlaceFaixaIP2.setText(null);
        txtCadEnlaceFaixaIP3.setText(null);
        txtCadEnlaceFaixaIP3.setEnabled(false);
        txtCadEnlaceFaixaIP4.setText(null);
        txtCadEnlaceFaixaIP4.setEnabled(false);
        txtCadEnlaceIdMestre.setText(null);
        txtCadEnlaceCIDR.setText(null);
        txtCadEnlaceRouterEscravo.setText(null);
        txtCadEnlaceRouterEscravo.setEnabled(false);
        txtCadEnlaceCIDR.setEnabled(false);

        txtCadEnlaceRadioMestre.setText(null);
        txtCadEnlaceRadioMestre.setEnabled(false);
        txtCadEnlaceEscravo.setText(null);
        txtCadEnlaceEscravo.setEnabled(false);
        txtCadEnlaceObserv.setText(null);
        txtCadEnlaceObserv.setEnabled(false);
        txtCadEnlaceIdEscravo.setText(null);
        txtCadEnlaceRadioEscravo.setText(null);
        txtCadEnlaceRadioEscravo.setEnabled(false);
        txtCadEnlaceChave.setEnabled(false);
        txtCadEnlaceChave.setText(null);
        txtCadEnlaceData.setText(null);
    }//GEN-LAST:event_btnCadCancelarActionPerformed

    private void btnCadAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadAdicionarActionPerformed
        // habilita os botoes
        btnCadEnlacePesAtivoMes.setEnabled(false);
        btnCadAdicionar.setEnabled(false);
        btnCadExcluir.setEnabled(false);
        btnCadEditar.setEnabled(false);
        btnCadCancelar.setEnabled(true);
        btnCadGravar.setEnabled(true);
        btnCadPesquisar.setEnabled(false);
        btnGeraIP.setEnabled(true);

        txtCadEnlaceDescricao.setText(null);
        txtCadEnlaceDescricao.setEnabled(true);
        txtCadEnlaceDescricao.requestFocus();
        txtCadEnlaceRouterMestre.setText(null);
        txtCadEnlaceRouterMestre.setEnabled(true);
        txtCadEnlaceCod.setText(null);
        txtCadEnlaceCIDR.setText(null);
        txtCadEnlaceCIDR.setEnabled(true);
        txtCadEnlaceRouterEscravo.setText(null);
        txtCadEnlaceRouterEscravo.setEnabled(true);
        txtCadEnlaceMestre.setText(null);
        txtCadEnlaceMestre.setEnabled(true);
        txtCadEnlaceMestre.setEditable(false);
        txtCadEnlaceIdMestre.setText(null);
        txtCadEnlaceFaixaIP1.setText(null);
        txtCadEnlaceFaixaIP1.setEnabled(true);
        txtCadEnlaceFaixaIP2.setText(null);
        txtCadEnlaceFaixaIP2.setEnabled(true);
        txtCadEnlaceFaixaIP3.setText(null);
        txtCadEnlaceFaixaIP3.setEnabled(true);
        txtCadEnlaceFaixaIP4.setText(null);
        txtCadEnlaceFaixaIP4.setEnabled(true);

        txtCadEnlaceIdEscravo.setText(null);
        txtCadEnlaceEscravo.setText(null);
        txtCadEnlaceEscravo.setEditable(false);
        txtCadEnlaceEscravo.setEnabled(true);
        txtCadEnlaceObserv.setText(null);
        txtCadEnlaceObserv.setEnabled(true);
        txtCadEnlaceRadioEscravo.setText(null);
        txtCadEnlaceRadioEscravo.setEnabled(true);
        txtCadEnlaceChave.setEnabled(true);
        txtCadEnlaceChave.setText(null);
        txtCadEnlaceRadioMestre.setText(null);
        txtCadEnlaceRadioMestre.setEnabled(true);

        btnCadEnlacePesAtivoMes.setEnabled(true);
        btnCadEnlacePesqAtivoEsc.setEnabled(true);
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

    private void txtCadEnlaceIdMestreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadEnlaceIdMestreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadEnlaceIdMestreActionPerformed

    private void btnCadEnlacePesAtivoMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadEnlacePesAtivoMesActionPerformed
        TelaPesqAtivosMestreEnlace tela = new TelaPesqAtivosMestreEnlace();
        TelaPrincipal.Desktop.add(tela);
        tela.setVisible(true);
    }//GEN-LAST:event_btnCadEnlacePesAtivoMesActionPerformed

    private void txtCadEnlaceDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadEnlaceDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadEnlaceDescricaoActionPerformed

    private void txtCadEnlaceIdEscravoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadEnlaceIdEscravoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadEnlaceIdEscravoActionPerformed

    private void btnCadEnlacePesqAtivoEscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadEnlacePesqAtivoEscActionPerformed
        // TODO add your handling code here:
        TelaPesqAtivosEscravoEnlace tela = new TelaPesqAtivosEscravoEnlace();
        TelaPrincipal.Desktop.add(tela);
        tela.setVisible(true);
    }//GEN-LAST:event_btnCadEnlacePesqAtivoEscActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCadEnlaceRouterMestreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadEnlaceRouterMestreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadEnlaceRouterMestreActionPerformed

    private void txtCadEnlaceFaixaIP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadEnlaceFaixaIP1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCadEnlaceFaixaIP1ActionPerformed

    private void txtCadEnlaceFaixaIP4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadEnlaceFaixaIP4KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCadEnlaceFaixaIP4KeyPressed

    private void btnGeraIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeraIPActionPerformed
        // TODO add your handling code here:
        int ip1 = Integer.parseInt(txtCadEnlaceFaixaIP1.getText());
        int ip2 = Integer.parseInt(txtCadEnlaceFaixaIP2.getText());
        int ip3 = Integer.parseInt(txtCadEnlaceFaixaIP3.getText());
        int ip4 = Integer.parseInt(txtCadEnlaceFaixaIP4.getText());

        iproutermestre = (ip1 + "." + ip2 + "." + ip3 + "." + (ip4 + ip));
        iprouterescravo = (ip1 + "." + ip2 + "." + ip3 + "." + (ip4 + ip + 1));
        ipradiomestre = (ip1 + "." + ip2 + "." + ip3 + "." + (ip4 + ip + 2));
        ipradioescravo = (ip1 + "." + ip2 + "." + ip3 + "." + (ip4 + ip + 3));

        txtCadEnlaceRouterMestre.setText(iproutermestre);
        txtCadEnlaceRouterEscravo.setText(iprouterescravo);
        txtCadEnlaceRadioMestre.setText(ipradiomestre);
        txtCadEnlaceRadioEscravo.setText(ipradioescravo);

    }//GEN-LAST:event_btnGeraIPActionPerformed

    private void txtCadEnlaceChaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadEnlaceChaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadEnlaceChaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnCadAdicionar;
    public static javax.swing.JButton btnCadCancelar;
    public static javax.swing.JButton btnCadEditar;
    public static javax.swing.JButton btnCadEnlacePesAtivoMes;
    public static javax.swing.JButton btnCadEnlacePesqAtivoEsc;
    public static javax.swing.JButton btnCadExcluir;
    public static javax.swing.JButton btnCadGravar;
    public static javax.swing.JButton btnCadPesquisar;
    public static javax.swing.JButton btnGeraIP;
    public static javax.swing.JCheckBox cboCadAtivoStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextField txtCadEnlaceCIDR;
    public static javax.swing.JTextField txtCadEnlaceChave;
    public static javax.swing.JTextField txtCadEnlaceCod;
    public static javax.swing.JTextField txtCadEnlaceData;
    public static javax.swing.JTextField txtCadEnlaceDescricao;
    public static javax.swing.JTextField txtCadEnlaceEscravo;
    public static javax.swing.JTextField txtCadEnlaceFaixaIP1;
    public static javax.swing.JTextField txtCadEnlaceFaixaIP2;
    public static javax.swing.JTextField txtCadEnlaceFaixaIP3;
    public static javax.swing.JTextField txtCadEnlaceFaixaIP4;
    public static javax.swing.JTextField txtCadEnlaceIdEscravo;
    public static javax.swing.JTextField txtCadEnlaceIdMestre;
    public static javax.swing.JTextField txtCadEnlaceMestre;
    public static javax.swing.JTextPane txtCadEnlaceObserv;
    public static javax.swing.JTextField txtCadEnlaceRadioEscravo;
    public static javax.swing.JTextField txtCadEnlaceRadioMestre;
    public static javax.swing.JTextField txtCadEnlaceRouterEscravo;
    public static javax.swing.JTextField txtCadEnlaceRouterMestre;
    // End of variables declaration//GEN-END:variables

    private void setExtendedState(String IS_MAXIMUM_PROPERTY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
