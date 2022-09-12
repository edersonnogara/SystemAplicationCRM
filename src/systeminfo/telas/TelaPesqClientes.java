package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nogara
 */
public class TelaPesqClientes extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String sql1 = null;

    /**
     * Creates new form TelaPesquisaCidade
     */
    public TelaPesqClientes() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // metodo de pesquisar material com filtro
    private void pesquisa() {
        String sql = "select cli.idcli as Codigo, cli.cliIDCli as ID, cli.cliFantasia as Fantasia, cli.cliRazao as Razão, cid.cidNome as Cidade, "
                + "cli.cliFGPN as FGPN, cli.cliFGPN_CIDR as CIDR, cli.cliPeerFGPN as PeerFGPN, cli.cliPeerFGPN_CIDR as CIDR, cli.cliDataCad as DataCadastro, "
                + "cli.cliStatus as Status, cli.cliIDCid as IDCidade from tbclientes cli\n"
                + "inner join tbcidade as cid on cid.idcidade = cli.cliIDCid where cli.cliFantasia like ?";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesqAtivo.getText() + "%");
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

        txtPesqAtivo.setText(null);
        int setar = tblPesqResult.getSelectedRow();
        //tratamento para pegar data da tabela e colocar no campo de data 
        String data = tblPesqResult.getModel().getValueAt(setar, 9).toString();
        String ano = data.substring(0, 4);
        String mes = data.substring(5, 7);
        String dia = data.substring(8, 10);
        String formatData = dia + "/" + mes + "/" + ano;
        txtPesqAtivoCodigo.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        //manipula os objetos da tela de cadastro de cidade que foram declarados publicos
        TelaCadFiliais.txtCadClienteCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        TelaCadFiliais.txtCadClienteIDCliente.setText(tblPesqResult.getModel().getValueAt(setar, 1).toString());
        TelaCadFiliais.txtCadClienteFantasia.setText(tblPesqResult.getModel().getValueAt(setar, 2).toString());
        TelaCadFiliais.txtCadClienteRazao.setText(tblPesqResult.getModel().getValueAt(setar, 3).toString());
        //TelaCadEnlaces.txtCadEnlaceFaixaIP1.setText(tblPesqResult.getModel().getValueAt(setar, 4).toString());
        TelaCadFiliais.txtCadClienteCidade.setText(tblPesqResult.getModel().getValueAt(setar, 4).toString());
        TelaCadFiliais.txtCadClienteFGPN.setText(tblPesqResult.getModel().getValueAt(setar, 5).toString());
        TelaCadFiliais.txtCadClienteFGPN_CIDR.setText(tblPesqResult.getModel().getValueAt(setar, 6).toString());
        TelaCadFiliais.txtCadClientePeerFGPN.setText(tblPesqResult.getModel().getValueAt(setar, 7).toString());
        TelaCadFiliais.txtCadClientePeerFGPN_CIDR.setText(tblPesqResult.getModel().getValueAt(setar, 8).toString());
        TelaCadFiliais.txtCadClienteData.setText(formatData);
        TelaCadFiliais.cboCadAtivoStatus.setText(tblPesqResult.getModel().getValueAt(setar, 10).toString());
        TelaCadFiliais.txtCadClienteIdCidade.setText(tblPesqResult.getModel().getValueAt(setar, 11).toString());
        
//        String sql1 = "select a.idativos as \"Cod Ativo\", a.atDescricao as \"Descrição\", a.atIPLegado as \"IP Legado\" where idativos = ?";
//        try {
//            pst1 = conexao.prepareStatement(sql1);
//            pst1.setString(1, txtPesqAtivoCodigo.getText());
//            rs1 = pst1.executeQuery();
//            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs1));
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        txtPesqAtivo = new javax.swing.JTextField();
        txtPesqAtivoCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesqResult = new javax.swing.JTable();
        btnPesqCancelar = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setTitle("Pesquisa de Clientes");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Digite o Nome do Cliente:"));

        txtPesqAtivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesqAtivoActionPerformed(evt);
            }
        });
        txtPesqAtivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqAtivoKeyReleased(evt);
            }
        });

        txtPesqAtivoCodigo.setEditable(false);

        jLabel1.setText("Selecionado:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtPesqAtivo)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesqAtivoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtPesqAtivo)
                .addComponent(txtPesqAtivoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));

        tblPesqResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "ID", "Fantasia", "Razão", "Cidade", "FGPN", "CIDR", "PeerFGPN", "CIDR", "DataCadastro", "Status", "IdCidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPesqResult.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPesqResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesqResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesqResult);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        btnPesqCancelar.setText("Cancelar");
        btnPesqCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnPesqCancelar)
                .addGap(36, 36, 36))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPesqCancelar)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        setBounds(30, 75, 820, 424);
    }// </editor-fold>//GEN-END:initComponents


    private void tblPesqResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesqResultMouseClicked
        // chamando o metodo setar campos
        setar_campos();

    }//GEN-LAST:event_tblPesqResultMouseClicked

    private void txtPesqAtivoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqAtivoKeyReleased
        // chamando o metodo pesquisar
        pesquisa();
    }//GEN-LAST:event_txtPesqAtivoKeyReleased

    private void btnPesqCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqCancelarActionPerformed
        // cancelar a tela
        // TelaCadastroProdutos.txtCadProdMarca.setText(null);
        TelaCadFiliais.btnCadAdicionar.setEnabled(true);
        TelaCadFiliais.btnCadExcluir.setEnabled(false);
        TelaCadFiliais.btnCadCancelar.setEnabled(false);
        TelaCadFiliais.btnCadPesquisar.setEnabled(true);
        TelaCadFiliais.btnCadGravar.setEnabled(false);       
        TelaCadFiliais.btnCadEditar.setEnabled(false);

        dispose();


    }//GEN-LAST:event_btnPesqCancelarActionPerformed

    private void txtPesqAtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesqAtivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesqAtivoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesqCancelar;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPesqResult;
    private javax.swing.JTextField txtPesqAtivo;
    public static javax.swing.JTextField txtPesqAtivoCodigo;
    // End of variables declaration//GEN-END:variables
}
