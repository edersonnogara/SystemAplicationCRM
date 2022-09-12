package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nogara
 */
public class TelaPesqEnlace extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String sql1 = null;

    /**
     * Creates new form TelaPesquisaCidade
     */
    public TelaPesqEnlace() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // metodo de pesquisar material com filtro
    private void pesquisa() {
        String sql = "select e.idenl as Codigo, e.enlcodDesc as Descricao, a.atNome as Mestre, b.atNome as Escravo, e.enlFaixaIP as Faixa, e.enlCIDR as CIDR, "
                + "e.enlRouterMestre as RouterMestre, e.enlRouterEscravo as RouterEscravo, e.enlRadioMestre as RadioMestre, e.enlRadioEscravo as RadioEscravos, "
                + "e.enlChave as Chave, e.enlObservacao as Observacao, e.enlDataCad as DataCadastro, a.idativos as IDMestre, b.idativos as IDEscravos, e.enlAtivo as Status, "
                + "e.FaixaIP1 as IP1, e.FaixaIP2 as IP2, FaixaIP3 as IP3, FaixaIP4 as IP4 from tbenlaces e\n"
                + "inner join tbativos as a on a.idativos = e.enlIdMestre\n"
                + "inner join tbativos as b on b.idativos = e.enlIdEscravo where e.enlcodDesc like ?";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesqAtivo.getText() + "%");
            rs = pst.executeQuery();
            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));
            //redimencionar colunas na tabela da tela após a pesquisa
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(75);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(9).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(10).setPreferredWidth(250);
            tblPesqResult.getColumnModel().getColumn(11).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(12).setPreferredWidth(100);
            tblPesqResult.getColumnModel().getColumn(13).setPreferredWidth(5);
            tblPesqResult.getColumnModel().getColumn(14).setPreferredWidth(5);
            tblPesqResult.getColumnModel().getColumn(15).setPreferredWidth(35);
            tblPesqResult.getColumnModel().getColumn(16).setPreferredWidth(5);
            tblPesqResult.getColumnModel().getColumn(17).setPreferredWidth(5);
            tblPesqResult.getColumnModel().getColumn(18).setPreferredWidth(5);
            tblPesqResult.getColumnModel().getColumn(19).setPreferredWidth(5);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

// metodo para setar os campos dos formularios com o conteudo da tabela
    public void setar_campos() {

        txtPesqAtivo.setText(null);
        int setar = tblPesqResult.getSelectedRow();
        //tratamento para pegar data da tabela e colocar no campo de data 
        String data = tblPesqResult.getModel().getValueAt(setar, 12).toString();
        String ano = data.substring(0, 4);
        String mes = data.substring(5, 7);
        String dia = data.substring(8,10);
        String formatData = dia+"/"+mes+"/"+ano;
        txtPesqAtivoCodigo.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        //manipula os objetos da tela de cadastro de cidade que foram declarados publicos
        TelaCadEnlaces.txtCadEnlaceCod.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        TelaCadEnlaces.txtCadEnlaceDescricao.setText(tblPesqResult.getModel().getValueAt(setar, 1).toString());
        TelaCadEnlaces.txtCadEnlaceMestre.setText(tblPesqResult.getModel().getValueAt(setar, 2).toString());
        TelaCadEnlaces.txtCadEnlaceEscravo.setText(tblPesqResult.getModel().getValueAt(setar, 3).toString());
        //TelaCadEnlaces.txtCadEnlaceFaixaIP1.setText(tblPesqResult.getModel().getValueAt(setar, 4).toString());
        TelaCadEnlaces.txtCadEnlaceCIDR.setText(tblPesqResult.getModel().getValueAt(setar, 5).toString());
        TelaCadEnlaces.txtCadEnlaceRouterMestre.setText(tblPesqResult.getModel().getValueAt(setar, 6).toString());
        TelaCadEnlaces.txtCadEnlaceRouterEscravo.setText(tblPesqResult.getModel().getValueAt(setar, 7).toString());
        TelaCadEnlaces.txtCadEnlaceRadioMestre.setText(tblPesqResult.getModel().getValueAt(setar, 8).toString());
        TelaCadEnlaces.txtCadEnlaceRadioEscravo.setText(tblPesqResult.getModel().getValueAt(setar, 9).toString());
        TelaCadEnlaces.txtCadEnlaceChave.setText(tblPesqResult.getModel().getValueAt(setar, 10).toString());
        TelaCadEnlaces.txtCadEnlaceObserv.setText(tblPesqResult.getModel().getValueAt(setar, 11).toString());
        TelaCadEnlaces.txtCadEnlaceData.setText(formatData);
        TelaCadEnlaces.txtCadEnlaceIdMestre.setText(tblPesqResult.getModel().getValueAt(setar, 13).toString());
        TelaCadEnlaces.txtCadEnlaceIdEscravo.setText(tblPesqResult.getModel().getValueAt(setar, 14).toString());
        TelaCadEnlaces.cboCadAtivoStatus.setText(tblPesqResult.getModel().getValueAt(setar, 15).toString());
        TelaCadEnlaces.txtCadEnlaceFaixaIP1.setText(tblPesqResult.getModel().getValueAt(setar, 16).toString());
        TelaCadEnlaces.txtCadEnlaceFaixaIP2.setText(tblPesqResult.getModel().getValueAt(setar, 17).toString());
        TelaCadEnlaces.txtCadEnlaceFaixaIP3.setText(tblPesqResult.getModel().getValueAt(setar, 18).toString());
        TelaCadEnlaces.txtCadEnlaceFaixaIP4.setText(tblPesqResult.getModel().getValueAt(setar, 19).toString());
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

        setTitle("Pesquisa de Ativos");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Digite o Nome do Ativo:"));

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
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Descrição", "Mestre", "Escravo", "Faixa", "CIDR", "RouterMestre", "RouterEscravo", "RadioMestre", "RadioEscravo", "Chave", "Observação", "DataCadastro", "IDMestre", "IDEscravo", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(75);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(9).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(10).setPreferredWidth(250);
            tblPesqResult.getColumnModel().getColumn(11).setPreferredWidth(400);
            tblPesqResult.getColumnModel().getColumn(12).setPreferredWidth(100);
            tblPesqResult.getColumnModel().getColumn(13).setPreferredWidth(5);
            tblPesqResult.getColumnModel().getColumn(14).setPreferredWidth(5);
            tblPesqResult.getColumnModel().getColumn(15).setPreferredWidth(20);
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
        TelaCadEnlaces.btnCadAdicionar.setEnabled(true);
        TelaCadEnlaces.btnCadExcluir.setEnabled(false);
        TelaCadEnlaces.btnCadCancelar.setEnabled(false);
        TelaCadEnlaces.btnCadPesquisar.setEnabled(true);
        TelaCadEnlaces.btnCadGravar.setEnabled(false);
        TelaCadEnlaces.btnCadEnlacePesAtivoMes.setEnabled(false);
        TelaCadEnlaces.btnCadEditar.setEnabled(false);
        TelaCadEnlaces.btnCadEnlacePesqAtivoEsc.setEnabled(false);
        TelaCadEnlaces.btnGeraIP.setEnabled(false);

        TelaCadEnlaces.txtCadEnlaceDescricao.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceDescricao.setText(null);
        TelaCadEnlaces.txtCadEnlaceFaixaIP1.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceFaixaIP1.setText(null);
        TelaCadEnlaces.txtCadEnlaceFaixaIP2.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceFaixaIP2.setText(null);
        TelaCadEnlaces.txtCadEnlaceFaixaIP3.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceFaixaIP3.setText(null);
        TelaCadEnlaces.txtCadEnlaceFaixaIP4.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceFaixaIP4.setText(null);
        TelaCadEnlaces.txtCadEnlaceCod.setText(null);
        TelaCadEnlaces.txtCadEnlaceMestre.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceMestre.setEditable(false);
        TelaCadEnlaces.txtCadEnlaceMestre.setText(null);
        TelaCadEnlaces.txtCadEnlaceEscravo.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceEscravo.setEditable(false);
        TelaCadEnlaces.txtCadEnlaceEscravo.setText(null);

        TelaCadEnlaces.txtCadEnlaceCIDR.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceCIDR.setText(null);
        TelaCadEnlaces.txtCadEnlaceIdMestre.setText(null);
        TelaCadEnlaces.txtCadEnlaceIdEscravo.setText(null);
        TelaCadEnlaces.txtCadEnlaceRouterMestre.setText(null);
        TelaCadEnlaces.txtCadEnlaceRouterEscravo.setText(null);
        TelaCadEnlaces.txtCadEnlaceRouterMestre.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceRadioMestre.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceRadioMestre.setText(null);
        TelaCadEnlaces.txtCadEnlaceRouterEscravo.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceRadioEscravo.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceRadioEscravo.setText(null);
        TelaCadEnlaces.txtCadEnlaceChave.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceChave.setText(null);
        TelaCadEnlaces.txtCadEnlaceObserv.setEnabled(false);
        TelaCadEnlaces.txtCadEnlaceObserv.setText(null);
        dispose();



    }//GEN-LAST:event_btnPesqCancelarActionPerformed


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
