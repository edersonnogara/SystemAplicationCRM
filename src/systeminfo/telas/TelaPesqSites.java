package systeminfo.telas;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import systeminfo.dal.ModuloConexao;

/**
 *
 * @author Nutrizon
 */
public class TelaPesqSites extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String sql1 = null;

    /**
     * Creates new form TelaPesquisaCidade
     */
    public TelaPesqSites() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // metodo de pesquisar material com filtro
    private void pesquisa() {
        String sql = "SELECT t.idtorres as Codigo, t.TorresNomes as Site, t.TorresSigla as Sigla, c.idcidade as IdCid, c.cidNome as Cidade, c.cidEstado as Estado, t.TorresFaixaAdm as SubRede, t.TorresCIDR as CIDR, t.TorresStatus as Status FROM tbtorres t "
                + "inner join tbcidade as c on c.idcidade = t.idcidade where t.TorresNomes like ?";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesqAtivo.getText() + "%");
            rs = pst.executeQuery();
            tblPesqResult.setModel(DbUtils.resultSetToTableModel(rs));
            //redimencionar colunas na tabela da tela após a pesquisa
            tblPesqResult.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
// metodo para setar os campos dos formularios com o conteudo da tabela

    public void setar_campos() {
        txtPesqAtivo.setText(null);
        int setar = tblPesqResult.getSelectedRow();
        txtPesqAtivoCodigo.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        //manipula os objetos da tela de cadastro de cidade que foram declarados publicos
        TelaCadAtivos.txtCadAtivoIdSite.setText(tblPesqResult.getModel().getValueAt(setar, 0).toString());
        TelaCadAtivos.txtCadAtivoSiteSigla.setText(tblPesqResult.getModel().getValueAt(setar, 1).toString());

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
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Site", "Sigla", "IdCid", "Cidade", "Estado", "SubRede", "CIDR", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
            tblPesqResult.getColumnModel().getColumn(2).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(5).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(6).setPreferredWidth(180);
            tblPesqResult.getColumnModel().getColumn(7).setPreferredWidth(60);
            tblPesqResult.getColumnModel().getColumn(8).setPreferredWidth(60);
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
        TelaCadAtivos.btnCadAtivoPesSite.setEnabled(false);
        TelaCadAtivos.btnCadAdicionar.setEnabled(false);
        TelaCadAtivos.btnCadExcluir.setEnabled(true);
        TelaCadAtivos.btnCadEditar.setEnabled(false);
        TelaCadAtivos.btnCadCancelar.setEnabled(true);
        TelaCadAtivos.btnCadGravar.setEnabled(true);
        TelaCadAtivos.btnCadPesquisar.setEnabled(false);

       // TelaCadAtivos.txtCadAtivoDescricao.setText(null);
        TelaCadAtivos.txtCadAtivoDescricao.setEnabled(true);
      //  TelaCadAtivos.txtCadAtivoIPLegado.setText(null);
        TelaCadAtivos.txtCadAtivoIPLegado.setEnabled(true);
      //  TelaCadAtivos.txtCadAtivoCod.setText(null);
      //  TelaCadAtivos.txtCadAtivoVLAN.setText(null);
        TelaCadAtivos.txtCadAtivoVLAN.setEnabled(true);
      //  TelaCadAtivos.txtCadAtivoTerm.setText(null);
        TelaCadAtivos.txtCadAtivoTerm.setEnabled(true);
      //  TelaCadAtivos.txtCadAtivoSiteSigla.setText(null);
        TelaCadAtivos.txtCadAtivoSiteSigla.setEnabled(true);
      //  TelaCadAtivos.txtCadAtivoIdSite.setText(null);
     //   TelaCadAtivos.txtCadAtivoIP.setText(null);
        TelaCadAtivos.txtCadAtivoIP.setEnabled(true);
      //  TelaCadAtivos.txtCadAtivoIdMarca.setText(null);
       // TelaCadAtivos.txtCadAtivoMarca.setText(null);
        TelaCadAtivos.txtCadAtivoMarca.setEnabled(true);
      //  TelaCadAtivos.txtCadAtivoObserv.setText(null);
        TelaCadAtivos.txtCadAtivoObserv.setEnabled(true);
       // TelaCadAtivos.txtCadAtivoNome.setText(null);
       // TelaCadAtivos.txtCadAtivoFuncao.setText(null);
        TelaCadAtivos.txtCadAtivoFuncao.setEnabled(true);

        TelaCadAtivos.btnCadAtivoPesSite.setEnabled(true);
        TelaCadAtivos.btnCadAtivoPesqMarca.setEnabled(true);

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