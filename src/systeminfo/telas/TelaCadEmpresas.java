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
public class TelaCadEmpresas extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultListModel modelo;
    String[] Codig;
    int Enter = 0;

    private static TelaCadEmpresas telaCad;

    public static TelaCadEmpresas getInstancia() {
        if (telaCad == null) {
            telaCad = new TelaCadEmpresas();
        }
        return telaCad;
    }

    /**
     * Creates new form TelaMarcasProdutos
     */
    public TelaCadEmpresas() {
        initComponents();
        conexao = ModuloConexao.conector();
        Lista.setVisible(false);
        modelo = new DefaultListModel();
        Lista.setModel(modelo);
    }
// metodo de lista para o camp de cidade
// metodo de pesquisa através do codigo da cidade

//    public void Pesquisacid() {
//        int cid = Integer.parseInt(txtCadTorresIdCidade.getText());
//        String sql = "select * from tbcidade where idcidade = " + cid;
//
//        try {
//            pst = conexao.prepareStatement(sql);
//            rs = pst.executeQuery();
//            ResultadoPesquisa();
//
//        } catch (Exception e) {
//        }
//        txtCadTorreSigla.requestFocus();
//        txtCadTorresIdCidade.setEditable(false);
//
//    }
//    private void combobox(){
//        try {
//            String sql = ("select * from tbcidade order by cidNome");
//            box.removeAllItems();
//            pst = conexao.prepareStatement(sql);
//            rs = pst.executeQuery();
//            int v = 0;
//            Codig = new String[500];
//            while (rs.next()& v < 500){
//                String name = rs.getString("cidNome");
//                box.addItem(name);
//                Codig[v] = rs.getString("idcidade");
//                v++;
//            }
//            if (v >= 1) {
//                
//                box.setVisible(true);
//            } else {
//                box.setVisible(false);
//            }
//            ResultadoPesquisa();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Erro ao Listar os dados" + e);
//        }
//        
//    }
    // metodo de pesquisa através do nome da cidade sem lista
    public void Pesquisanomecid() {

        String sql = "select * from tbcidade where cidNome like '" + txtCadClienteCidade.getText().substring(0, 3) + "%%%' order by cidNome";

        try {
            modelo.removeAllElements();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            int v = 0;
            Codig = new String[5];
            while (rs.next() & v < 5) {
                modelo.addElement(rs.getString("cidNome"));
                //modelo.addElement(rs.getString(2));
                Codig[v] = rs.getString("idcidade");
                v++;
            }
            if (v >= 1) {
                Lista.setVisible(true);
            } else {
                Lista.setVisible(false);
            }

            ResultadoPesquisa();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar os dados" + erro);
        }
        //txtCadTorreLocal.requestFocus();
        txtCadClienteIdCidade.setEditable(false);

    }
// mostra a pesquisa nos campos referentes a cidade

    public void MostraPesquisa() throws SQLException {
        int Linha = Lista.getAnchorSelectionIndex();

        if (Linha >= 0) {

            //String sql = "select * from tbcidade where cidNome like '" + txtCadTorresCidade.getText() + "%' order by cidNome limit " + Linha + " , 1";
            String sql = ("select * from tbcidade where idcidade = " + Codig[Linha] + " ");
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            ResultadoPesquisa();
        }
    }

    public void ResultadoPesquisa() {
        txtCadClienteIdCidade.setText(null);
        try {
            rs.first();
            txtCadClienteIdCidade.setText(rs.getString(1));
            txtCadClienteCidade.setText(rs.getString(2));
            //txtCadTorresEstado.setText(rs.getString(7));

        } catch (Exception e) {
        }
    }

    // metodo de pesquisar material com filtro
    private void pesquisar_marca_Prod() {
        String sql = "select cli.idcli as Codigo, "
                + "cli.cliIDCli as ID, "
                + "cli.cliFantasia as Fantasia, "
                + "cli.cliRazao as Razão, "
                + "cid.cidNome as Cidade, "
                + "cli.cliFGPN as FGPN, "
                + "cli.cliFGPN_CIDR as CIDR, "
                + "cli.cliPeerFGPN as PeerFGPN, "
                + "cli.cliPeerFGPN_CIDR as CIDR, "
                + "cli.cliDataCad as DataCadastro, "
                + "cli.cliStatus as Status, "
                + "cli.cliIDCid as IDCidade "
                + "from tbclientes cli\n"
                + "inner join tbcidade as cid on cid.idcidade = cli.cliIDCid where cli.cliFantasia like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCadPesq.getText() + "%");
            rs = pst.executeQuery();
            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));
            //redimencionar colunas na tabela da tela após a pesquisa
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(300);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(120);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(120);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(9).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(10).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(11).setPreferredWidth(60);
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
        String data = tblPesqResult.getModel().getValueAt(setar, 9).toString();
        String ano = data.substring(0, 4);
        String mes = data.substring(5, 7);
        String dia = data.substring(8, 10);
        String formatData = dia + "/" + mes + "/" + ano;
        txtCadCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());

        txtCadClienteCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        txtCadClienteIDCliente.setText(tblPesqResult.getModel().getValueAt(setar, 1).toString());
        txtCadClienteFantasia.setText(tblPesqResult.getModel().getValueAt(setar, 2).toString());
        txtCadClienteRazao.setText(tblPesqResult.getModel().getValueAt(setar, 3).toString());
        //TelaCadEnlaces.txtCadEnlaceFaixaIP1.setText(tblPesqResult.getModel().getValueAt(setar, 4).toString());
        txtCadClienteCidade.setText(tblPesqResult.getModel().getValueAt(setar, 4).toString());
        txtCadClienteFGPN.setText(tblPesqResult.getModel().getValueAt(setar, 5).toString());
        txtCadClienteFGPN_CIDR.setText(tblPesqResult.getModel().getValueAt(setar, 6).toString());
        txtCadClientePeerFGPN.setText(tblPesqResult.getModel().getValueAt(setar, 7).toString());
        txtCadClientePeerFGPN_CIDR.setText(tblPesqResult.getModel().getValueAt(setar, 8).toString());
        txtCadClienteData.setText(formatData);
        cboCadAtivoStatus.setText(tblPesqResult.getModel().getValueAt(setar, 10).toString());
        txtCadClienteIdCidade.setText(tblPesqResult.getModel().getValueAt(setar, 11).toString());

    }

    //metodo para adiconar marcas
    private void gravar() {
        String sql = "insert into tbclientes (cliStatus, cliIDCli, cliFantasia, cliRazao, cliIDCid, cliFGPN, cliFGPN_CIDR, cliPeerFGPN, cliPeerFGPN_CIDR) values (?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareCall(sql);

            if (cboCadAtivoStatus.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            pst.setString(2, txtCadClienteIDCliente.getText());
            pst.setString(3, txtCadClienteFantasia.getText());
            pst.setString(4, txtCadClienteRazao.getText());
            pst.setString(5, txtCadClienteIdCidade.getText());
            pst.setString(6, txtCadClienteFGPN.getText());
            pst.setString(7, txtCadClienteFGPN_CIDR.getText());
            pst.setString(8, txtCadClientePeerFGPN.getText());
            pst.setString(9, txtCadClientePeerFGPN_CIDR.getText());

            // validação dos campos obrigatorios
            if ((txtCadClienteIDCliente.getText().isEmpty())
                    || (txtCadClienteRazao.getText().isEmpty())
                    || (txtCadClienteIdCidade.getText().isEmpty())
                    || (txtCadClienteFantasia.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos com * para Gravar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Empresa cadastrada com sucesso");

                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    

                    btnCadGravar.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadPesq.setEnabled(true);
                    txtCadPesq.requestFocus();
                    txtCadClienteIDCliente.setEnabled(false);
                    txtCadClienteData.setEnabled(false);
                    txtCadClienteFantasia.setEnabled(false);
                    txtCadClienteIDCliente.setText(null);
                    txtCadClienteFantasia.setText(null);
                    txtCadClienteCod.setText(null);
                    txtCadClienteCod.setEnabled(false);
                    txtCadClientePeerFGPN.setEnabled(false);
                    txtCadClientePeerFGPN.setEditable(false);
                    txtCadClientePeerFGPN.setText(null);
                    txtCadClienteFGPN.setEnabled(false);
                    txtCadClienteFGPN.setText(null);
                    txtCadClienteRazao.setText(null);
                    txtCadClienteRazao.setEnabled(false);
                    txtCadClienteCidade.setText(null);
                    txtCadClienteCidade.setEnabled(false);
                    txtCadClienteIdCidade.setText(null);
                    txtCadClienteIdCidade.setEnabled(false);
                    txtCadClienteData.setText(null);
                    txtCadClienteFGPN_CIDR.setText(null);
                    txtCadClienteFGPN_CIDR.setEnabled(false);
                    txtCadClientePeerFGPN_CIDR.setText(null);
                    txtCadClientePeerFGPN_CIDR.setEnabled(false);
                    tblPesqResult.setVisible(true);
                    tblPesqResult.setEnabled(true);
                    txtCadCod.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Excluir o Cliente?");
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete from tbclientes where idcli = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCadClienteCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente Excluido com sucesso");

                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    

                    btnCadGravar.setEnabled(false);
                    btnCadEditar.setEnabled(false);
                    txtCadPesq.setEnabled(true);
                    txtCadPesq.requestFocus();
                    txtCadClienteIDCliente.setEnabled(false);
                    txtCadClienteData.setEnabled(false);
                    txtCadClienteFantasia.setEnabled(false);
                    txtCadClienteIDCliente.setText(null);
                    txtCadClienteFantasia.setText(null);
                    txtCadClienteCod.setText(null);
                    txtCadClienteCod.setEnabled(false);
                    txtCadClientePeerFGPN.setEnabled(false);
                    txtCadClientePeerFGPN.setEditable(false);
                    txtCadClientePeerFGPN.setText(null);
                    txtCadClienteFGPN.setEnabled(false);
                    txtCadClienteFGPN.setText(null);
                    txtCadClienteRazao.setText(null);
                    txtCadClienteRazao.setEnabled(false);
                    txtCadClienteCidade.setText(null);
                    txtCadClienteCidade.setEnabled(false);
                    txtCadClienteIdCidade.setText(null);
                    txtCadClienteIdCidade.setEnabled(false);
                    txtCadClienteData.setText(null);
                    txtCadClienteFGPN_CIDR.setText(null);
                    txtCadClienteFGPN_CIDR.setEnabled(false);
                    txtCadClientePeerFGPN_CIDR.setText(null);
                    txtCadClientePeerFGPN_CIDR.setEnabled(false);
                    tblPesqResult.setVisible(true);
                    tblPesqResult.setEnabled(true);
                    txtCadCod.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
// metodo para editar
    private void alterar() {
        String sql = "update tbclientes set cliStatus = ?, cliIDCli = ?, cliFantasia = ?, cliRazao = ?, cliIDCid = ? , cliFGPN = ?, cliFGPN_CIDR = ?, cliPeerFGPN = ?, cliPeerFGPN_CIDR = ? where idcli = ?";

        try {
            pst = conexao.prepareCall(sql);

            if (cboCadAtivoStatus.isSelected()) {
                pst.setString(1, "A");
            } else {
                pst.setString(1, "I");
            }
            pst.setString(2, txtCadClienteIDCliente.getText());
            pst.setString(3, txtCadClienteFantasia.getText());
            pst.setString(4, txtCadClienteRazao.getText());
            pst.setString(5, txtCadClienteIdCidade.getText());
            pst.setString(6, txtCadClienteFGPN.getText());
            pst.setString(7, txtCadClienteFGPN_CIDR.getText());
            pst.setString(8, txtCadClientePeerFGPN.getText());
            pst.setString(9, txtCadClientePeerFGPN_CIDR.getText());
            pst.setString(10, txtCadClienteCod.getText());
            if ((txtCadClienteIDCliente.getText().isEmpty())
                    || (txtCadClienteFGPN.getText().isEmpty())
                    || (txtCadClienteRazao.getText().isEmpty())
                    || (txtCadClienteIdCidade.getText().isEmpty())
                    || (txtCadClienteFantasia.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos com * para Editar");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Empresa alterada com sucesso");
                    btnCadAdicionar.setEnabled(true);
                    btnCadExcluir.setEnabled(false);
                    btnCadCancelar.setEnabled(false);
                    txtCadPesq.requestFocus();
                    btnCadEditar.setEnabled(false);
                    btnCadGravar.setEnabled(false);
                    txtCadClienteIDCliente.setEnabled(false);
                    txtCadClienteFantasia.setEnabled(false);
                    txtCadPesq.setEnabled(true);
                    tblPesqResult.setEnabled(true);
                    tblPesqResult.setVisible(true);
                    txtCadClienteIDCliente.setText(null);
                    txtCadClienteFantasia.setText(null);
                    txtCadClienteCod.setText(null);
                    txtCadClientePeerFGPN.setEnabled(false);
                    txtCadClientePeerFGPN.setText(null);
                    txtCadClienteFGPN.setEnabled(false);
                    txtCadClienteFGPN.setText(null);
                    txtCadClienteRazao.setText(null);
                    txtCadClienteRazao.setEnabled(false);
                    txtCadCod.setText(null);
                    txtCadClienteCidade.setText(null);
                    txtCadClienteCidade.setEnabled(false);
                    txtCadClienteIdCidade.setText(null);
                    txtCadClienteData.setText(null);
                    txtCadClienteFGPN_CIDR.setText(null);
                    txtCadClienteFGPN_CIDR.setEnabled(false);
                    txtCadClientePeerFGPN_CIDR.setText(null);
                    txtCadClientePeerFGPN_CIDR.setEnabled(false);
                    txtCadClienteData.setEnabled(false);
                    txtCadClienteCod.setEnabled(false);
                    txtCadClienteIdCidade.setEnabled(false);
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
        txtCadClienteIDCliente = new javax.swing.JTextField();
        txtCadClienteFantasia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCadClientePeerFGPN = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCadClienteFGPN = new javax.swing.JTextField();
        cboCadAtivoStatus = new javax.swing.JCheckBox();
        txtCadClienteRazao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCadClienteCod = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCadClienteFGPN_CIDR = new javax.swing.JTextField();
        txtCadClientePeerFGPN_CIDR = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCadClienteData = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCadClienteCidade = new javax.swing.JTextField();
        txtCadClienteIdCidade = new javax.swing.JTextField();
        Lista = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        btnCadAdicionar = new javax.swing.JButton();
        btnCadExcluir = new javax.swing.JButton();
        btnCadCancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnCadGravar = new javax.swing.JButton();
        btnCadEditar = new javax.swing.JButton();

        setTitle("Cadastro de Clientes");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquise a Empresa:"));
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
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "ID", "Fantasia", "Razão", "Cidade", "FGPN", "CIDR", "PeerFGPN", "CIDR", "Data Cadastro", "Status", "ID Cidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPesqResult.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPesqResult.setColumnSelectionAllowed(true);
        tblPesqResult.setEnabled(false);
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
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(300);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(120);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(120);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(9).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(10).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(11).setPreferredWidth(60);
        }

        txtCadCod.setEnabled(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastre a Empresa:"));

        txtCadClienteIDCliente.setEnabled(false);
        txtCadClienteIDCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadClienteIDClienteActionPerformed(evt);
            }
        });

        txtCadClienteFantasia.setEnabled(false);

        jLabel1.setText("*ID Empresa:");

        jLabel2.setText("*Fantasia:");

        jLabel3.setText("*Peer FGPN:");

        txtCadClientePeerFGPN.setEnabled(false);

        jLabel4.setText("*FGPN:");

        txtCadClienteFGPN.setEnabled(false);
        txtCadClienteFGPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadClienteFGPNActionPerformed(evt);
            }
        });

        cboCadAtivoStatus.setSelected(true);
        cboCadAtivoStatus.setText("A");

        txtCadClienteRazao.setEnabled(false);

        jLabel5.setText("Ativo?:");

        jLabel7.setText("*Razão:");

        txtCadClienteCod.setEnabled(false);

        jLabel11.setText("Código:");

        jLabel6.setText("*CIDR:");

        txtCadClienteFGPN_CIDR.setEnabled(false);
        txtCadClienteFGPN_CIDR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadClienteFGPN_CIDRActionPerformed(evt);
            }
        });

        txtCadClientePeerFGPN_CIDR.setEnabled(false);

        jLabel13.setText("*CIDR:");

        txtCadClienteData.setEnabled(false);

        jLabel16.setText("Data Cadastro:");

        jLabel9.setText("Cidade:");

        txtCadClienteCidade.setEditable(false);
        txtCadClienteCidade.setEnabled(false);
        txtCadClienteCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCadClienteCidadeMouseClicked(evt);
            }
        });
        txtCadClienteCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCadClienteCidadeActionPerformed(evt);
            }
        });
        txtCadClienteCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCadClienteCidadeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadClienteCidadeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCadClienteCidadeKeyTyped(evt);
            }
        });

        txtCadClienteIdCidade.setEnabled(false);

        Lista.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        Lista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Lista.setFocusable(false);
        Lista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ListaMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCadClienteRazao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCadClienteFantasia, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtCadClienteIDCliente, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCadClienteCod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadClienteData, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCadAtivoStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCadClientePeerFGPN, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadClientePeerFGPN_CIDR, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCadClienteFGPN, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCadClienteFGPN_CIDR, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCadClienteCidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lista, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCadClienteIdCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(cboCadAtivoStatus)
                    .addComponent(txtCadClienteCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtCadClienteData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtCadClienteIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCadClienteFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCadClienteRazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(txtCadClienteFGPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtCadClienteFGPN_CIDR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtCadClientePeerFGPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel13)
                            .addComponent(txtCadClientePeerFGPN_CIDR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel9)
                            .addComponent(txtCadClienteCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCadClienteIdCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Lista, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(148, 148, 148))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCadPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCadCod, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCadPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/2sair.png"))); // NOI18N
        jButton1.setText("Fechar");
        jButton1.setPreferredSize(new java.awt.Dimension(113, 41));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCadGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5salvar.png"))); // NOI18N
        btnCadGravar.setText("Gravar");
        btnCadGravar.setEnabled(false);
        btnCadGravar.setPreferredSize(new java.awt.Dimension(113, 41));
        btnCadGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadGravarActionPerformed(evt);
            }
        });

        btnCadEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/systeminfo/icones/5editar.png"))); // NOI18N
        btnCadEditar.setText("Salvar Edição");
        btnCadEditar.setToolTipText("Editar");
        btnCadEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadEditar.setEnabled(false);
        btnCadEditar.setPreferredSize(new java.awt.Dimension(133, 41));
        btnCadEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCadAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        setBounds(2, 2, 1020, 734);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCadPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadPesqKeyReleased
        //chamando o metodo pesquisar
        pesquisar_marca_Prod();
        tblPesqResult.setVisible(true);
    }//GEN-LAST:event_txtCadPesqKeyReleased

    private void tblPesqResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesqResultMouseClicked
        // chamando o metodo setar campos
        setar_campos();

        txtCadClienteCod.setEnabled(true);
        txtCadClienteIDCliente.setEnabled(true);
        txtCadClienteFantasia.setEnabled(true);
        txtCadClienteRazao.setEnabled(true);
        txtCadClienteCidade.setEnabled(true);
        txtCadClienteFGPN.setEnabled(true);
        txtCadClienteFGPN_CIDR.setEnabled(true);
        txtCadClientePeerFGPN.setEnabled(true);
        txtCadClientePeerFGPN_CIDR.setEnabled(true);
        txtCadClienteData.setEnabled(true);
        cboCadAtivoStatus.setEnabled(true);
        txtCadClienteIdCidade.setEnabled(true);
        txtCadPesq.setEnabled(false);
        txtCadClienteIdCidade.setEnabled(true);
        btnCadAdicionar.setEnabled(false);
        btnCadExcluir.setEnabled(true);
        btnCadEditar.setEnabled(true);
        btnCadCancelar.setEnabled(true);
        btnCadGravar.setEnabled(false);

        tblPesqResult.setEnabled(false);
        tblPesqResult.setVisible(false);


    }//GEN-LAST:event_tblPesqResultMouseClicked

    private void btnCadCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCancelarActionPerformed
        // habilita os botoes

        btnCadAdicionar.setEnabled(true);
        btnCadExcluir.setEnabled(false);
        btnCadCancelar.setEnabled(false);
        

        btnCadGravar.setEnabled(false);
        btnCadEditar.setEnabled(false);
        txtCadPesq.setEnabled(true);
        txtCadPesq.requestFocus();
        txtCadClienteIDCliente.setEnabled(false);
        txtCadClienteData.setEnabled(false);
        txtCadClienteFantasia.setEnabled(false);
        txtCadClienteIDCliente.setText(null);
        txtCadClienteFantasia.setText(null);
        txtCadClienteCod.setText(null);
        txtCadClienteCod.setEnabled(false);
        txtCadClientePeerFGPN.setEnabled(false);
        txtCadClientePeerFGPN.setEditable(false);
        txtCadClientePeerFGPN.setText(null);
        txtCadClienteFGPN.setEnabled(false);
        txtCadClienteFGPN.setText(null);
        txtCadClienteRazao.setText(null);
        txtCadClienteRazao.setEnabled(false);
        txtCadClienteCidade.setText(null);
        txtCadClienteCidade.setEnabled(false);
        txtCadClienteIdCidade.setText(null);
        txtCadClienteIdCidade.setEnabled(false);
        txtCadClienteData.setText(null);
        txtCadClienteFGPN_CIDR.setText(null);
        txtCadClienteFGPN_CIDR.setEnabled(false);
        txtCadClientePeerFGPN_CIDR.setText(null);
        txtCadClientePeerFGPN_CIDR.setEnabled(false);
        tblPesqResult.setVisible(true);
        tblPesqResult.setEnabled(true);
        txtCadCod.setText(null);
    }//GEN-LAST:event_btnCadCancelarActionPerformed

    private void btnCadAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadAdicionarActionPerformed
        // habilita os botoes
        tblPesqResult.setEnabled(false);
        tblPesqResult.setVisible(false);
        btnCadAdicionar.setEnabled(false);
        btnCadExcluir.setEnabled(false);
        btnCadEditar.setEnabled(false);
        btnCadCancelar.setEnabled(true);
        btnCadGravar.setEnabled(true);

        txtCadClienteIDCliente.setText(null);
        txtCadClienteIDCliente.setEnabled(true);
        txtCadClienteFantasia.setText(null);
        txtCadClienteFantasia.setEnabled(true);
        txtCadClienteCod.setText(null);
        txtCadClienteCod.setEnabled(false);
        txtCadClienteFGPN_CIDR.setText(null);
        txtCadClienteFGPN_CIDR.setEnabled(true);
        txtCadClienteRazao.setText(null);
        txtCadClienteRazao.setEnabled(true);
        txtCadClientePeerFGPN.setText(null);
        txtCadClientePeerFGPN.setEnabled(true);
        txtCadClientePeerFGPN.setEditable(true);
        txtCadClientePeerFGPN_CIDR.setText(null);
        txtCadClientePeerFGPN_CIDR.setEnabled(true);
        txtCadClienteFGPN.setText(null);
        txtCadClienteFGPN.setEnabled(true);
        txtCadClienteIdCidade.setText(null);
        txtCadClienteIdCidade.setEnabled(false);
        txtCadClienteCidade.setText(null);
        txtCadClienteCidade.setEditable(true);
        txtCadClienteCidade.setEnabled(true);
        txtCadClienteData.setText(null);
        txtCadClienteData.setEnabled(false);
        txtCadPesq.setEnabled(false);
        txtCadClienteIDCliente.requestFocus();


    }//GEN-LAST:event_btnCadAdicionarActionPerformed

    private void btnCadExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadExcluirActionPerformed
        // metodo excluir marca
        remover();

    }//GEN-LAST:event_btnCadExcluirActionPerformed

    private void btnCadGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadGravarActionPerformed
        // grava a marca de produto no banco
        gravar();

    }//GEN-LAST:event_btnCadGravarActionPerformed

    private void btnCadEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadEditarActionPerformed
        // metodo alterar
        alterar();
    }//GEN-LAST:event_btnCadEditarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCadPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadPesqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadPesqActionPerformed

    private void txtCadClienteIDClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadClienteIDClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadClienteIDClienteActionPerformed

    private void txtCadClienteFGPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadClienteFGPNActionPerformed

    }//GEN-LAST:event_txtCadClienteFGPNActionPerformed

    private void txtCadClienteCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCadClienteCidadeMouseClicked
        txtCadClienteIdCidade.setText(null);
        txtCadClienteCidade.setText(null);
        //txtCadTorresEstado.setText(null);
        Lista.setVisible(false);
    }//GEN-LAST:event_txtCadClienteCidadeMouseClicked

    private void txtCadClienteCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadClienteCidadeActionPerformed
        //  Pesquisanomecid();
        Lista.setVisible(false);
        Enter = 1;
    }//GEN-LAST:event_txtCadClienteCidadeActionPerformed

    private void txtCadClienteCidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadClienteCidadeKeyPressed
        if (evt.getKeyCode() == 27) {
            txtCadClienteIdCidade.setText(null);
            txtCadClienteCidade.setText(null);
            // txtCadTorresEstado.setText(null);
            Lista.setVisible(false);
            txtCadClienteIdCidade.requestFocus();
        }
        if (evt.getKeyCode() == 8) {
            txtCadClienteIdCidade.setText(null);
            txtCadClienteCidade.setText(null);
            // txtCadTorresEstado.setText(null);
            Lista.setVisible(false);
            txtCadClienteIdCidade.requestFocus();
        }
        if (evt.getKeyCode() == 13) {
            Lista.setVisible(false);
            // txtCadTorreSigla.requestFocus();
        }

    }//GEN-LAST:event_txtCadClienteCidadeKeyPressed

    private void txtCadClienteCidadeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadClienteCidadeKeyReleased
        //Pesquisanomecid();
        if (Enter == 0) {
            Pesquisanomecid();
        } else {
            Enter = 0;
        }
    }//GEN-LAST:event_txtCadClienteCidadeKeyReleased

    private void txtCadClienteCidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadClienteCidadeKeyTyped
        //  Pesquisanomecid();
    }//GEN-LAST:event_txtCadClienteCidadeKeyTyped

    private void ListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaMouseClicked
        //        try {
        //            MostraPesquisa();
        //
        //        } catch (SQLException ex) {
        //        }
        //        Lista.setVisible(false);
        //        txtCadTorreSigla.requestFocus();
    }//GEN-LAST:event_ListaMouseClicked

    private void ListaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaMousePressed
        try {
            MostraPesquisa();

        } catch (SQLException ex) {
        }
        Lista.setVisible(false);
        txtCadClienteFantasia.requestFocus();
    }//GEN-LAST:event_ListaMousePressed

    private void txtCadClienteFGPN_CIDRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCadClienteFGPN_CIDRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCadClienteFGPN_CIDRActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> Lista;
    public static javax.swing.JButton btnCadAdicionar;
    public static javax.swing.JButton btnCadCancelar;
    public static javax.swing.JButton btnCadEditar;
    public static javax.swing.JButton btnCadExcluir;
    public static javax.swing.JButton btnCadGravar;
    public static javax.swing.JCheckBox cboCadAtivoStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    public static javax.swing.JTable tblPesqResult;
    public static javax.swing.JTextField txtCadClienteCidade;
    public static javax.swing.JTextField txtCadClienteCod;
    public static javax.swing.JTextField txtCadClienteData;
    public static javax.swing.JTextField txtCadClienteFGPN;
    public static javax.swing.JTextField txtCadClienteFGPN_CIDR;
    public static javax.swing.JTextField txtCadClienteFantasia;
    public static javax.swing.JTextField txtCadClienteIDCliente;
    public static javax.swing.JTextField txtCadClienteIdCidade;
    public static javax.swing.JTextField txtCadClientePeerFGPN;
    public static javax.swing.JTextField txtCadClientePeerFGPN_CIDR;
    public static javax.swing.JTextField txtCadClienteRazao;
    public static javax.swing.JTextField txtCadCod;
    public static javax.swing.JTextField txtCadPesq;
    // End of variables declaration//GEN-END:variables
}
