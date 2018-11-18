/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kkpkremes;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author maria
 */
public class DataKue extends javax.swing.JInternalFrame {
    MenuUtama mUtama;
    Connection con;
    Statement stat;
    ResultSet res;
    
    private String cariBy="", kriteria="";
    Object[] Baris={"Kode Kue", "Nama Kue", "Harga Satuan","Jumlah","Berat"}; //nama tabel 
    DefaultTableModel tabmode = new DefaultTableModel(null, Baris);
    
    /**
     * Creates new form DataKue
     */
    public DataKue(MenuUtama mUtama) {
        initComponents();
        this.mUtama = mUtama;
        txtHargaKue.addKeyListener(new KeyAdapter()
                {
                    public void KeyTyped (KeyEvent angka)
                    {
                char c = angka.getKeyChar ();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_ENTER)))
                {
                JOptionPane.showMessageDialog(null, "hanya diisi angka");
                angka.consume();
                }
                }
    }
        );
        
        txtJumlah.addKeyListener(new KeyAdapter()
        {
                public void KeyTyped (KeyEvent angka)
                {
                    char c = angka.getKeyChar ();
                if (! (Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_ENTER)))
                {
                JOptionPane.showMessageDialog(null, "hanya diisi oleh angka");
                angka.consume ();
                
                }
                }
                }
        );
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
        (screenSize.width - frameSize.width) / 2,
        (screenSize.height - frameSize.height) / 2);
        datatabel();
        autoNumber();
        awal();
        setTabel(tblKue, new int[]{130,350,150,500});
        isiComboCari();
    }
    
    
    private void autoNumber(){
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM kue ORDER BY kd_kue DESC";
            res = st.executeQuery(sql);
            if (res.next()){
                String Kd_kue = res.getString("kd_kue").substring(1);
                String AN = "" + (Integer.parseInt(Kd_kue) + 1);
                String Nol = "";
                
                if(AN.length() ==1)
                {Nol = "000";}
                else if(AN.length () ==2)
                {Nol = "00";}
                else if (AN.length () ==3)
                {Nol = "0";}
                else if (AN.length () ==4)
                {Nol = "";}
                txtKodeKue.setText("K" +Nol + AN);
            }else{
                txtKodeKue.setText("K0001");
                txtKodeKue.setEditable(false);
                
            }
            res.close();
            con.close();
            txtKodeKue.setEditable(false);
            }catch (Exception e) {
                e.printStackTrace();; //uyk penangana masalah
            }
    }
    
    private void isiComboCari(){
        cmbCari.addItem("Kode Kue");
        cmbCari.addItem("Nama Kue");
        cmbCari.addItem("Harga Satuan");
        cmbCari.addItem("Stok");
        cmbCari.addItem("Berat");
        }
    public void bersihTable(){
        int row = tblKue.getRowCount() - 1;
        while (row >= 0){
            tabmode.removeRow(row);
            row--;
        }
    }
    
    public void awal(){
        btnSimpan.setEnabled(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    public void setTabel (javax.swing.JTable tb, int lebar[]){
        tb.setAutoResizeMode(tb.AUTO_RESIZE_OFF);
        int kolom = tb.getColumnCount();
        for (int i=0; i<kolom; i++) {
            javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
            tb.setRowHeight(18);
        }
    }
    
    public void datatabel(){
        //bersih tabel();
        tblKue.setModel(tabmode);
        String sql = "SELECT * from kue";
        try {
            Koneksi konek = new Koneksi ();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            res=st.executeQuery(sql);
            while(res.next()){
                String a = res.getString("kd_kue");
                String b = res.getString("nama_kue");
                String c = res.getString("harga");
                String d = res.getString("stok");
                String e = res.getString("berat");
                String[] data = {a,b,c,d,e};
                tabmode.addRow(data);    
        }
            con.close();
        }catch (Exception e) {
    }
    }

    
    
    public void bersih() {
        txtNamaKue.setText("");
        txtHargaKue.setText("");
        txtJumlah.setText("");
        txtBerat.setText("");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKodeKue = new javax.swing.JTextField();
        txtNamaKue = new javax.swing.JTextField();
        txtHargaKue = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbCari = new javax.swing.JComboBox();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKue = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtBerat = new javax.swing.JTextField();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Kue"));

        jLabel1.setText("Data Kue         :");

        jLabel2.setText("Nama Kue       :");

        jLabel3.setText("Harga Satuan :");

        jLabel4.setText("Stok                :");

        jLabel5.setText("Cari");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblKue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Data Kue", "Nama Kue", "Harga Satuan", "Jumlah", "Berat"
            }
        ));
        tblKue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKueMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKue);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel6.setText("Berat               :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cmbCari, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCari))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtBerat))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtKodeKue, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNamaKue))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtHargaKue))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtJumlah)))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                    .addComponent(btnUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 115, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtKodeKue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNamaKue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbah))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHargaKue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtBerat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
         if (txtKodeKue.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Barang masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtKodeKue.requestFocus();
        }else if (txtNamaKue.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Nama Barang masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtNamaKue.requestFocus();
        
        }else if (txtHargaKue.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Harga Barang masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtHargaKue.requestFocus();
        }else if (txtJumlah.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Jumlah Barang masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtJumlah.requestFocus();
        }else if (txtBerat.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Berat Barang masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtJumlah.requestFocus();
        }else {
            try {
                Koneksi konek = new Koneksi();
                Connection con = konek.bukaKoneksi();
                Statement st = con.createStatement();
                String sql = "insert into kue values('"+txtKodeKue.getText()+"','"+txtNamaKue.getText()+"','"+txtHargaKue.getText()+"','"+txtJumlah.getText()+"','"+txtBerat.getText()+"')";
                int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKodeKue.getText()+" Ingin Disimpan ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
                if(a==0){
                    int sukses = st.executeUpdate(sql);
                    if(sukses > 0){
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                        bersihTable();
                        datatabel();
                        bersih();
                        autoNumber();
                        awal();
                    }else{
                        JOptionPane.showMessageDialog(null, "Data gagal disimpan");
                    }
                }else{
                    autoNumber();
                    awal();
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed
 public void CariData(){
        dapatData();
        //String abc = txtCari.getText();
        tblKue.setModel(tabmode);
        String sql = "SELECT * from kue where "+cariBy+" LIKE '%"+kriteria+"%'";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();  
            res=st.executeQuery(sql);
            while(res.next()){
                String a = res.getString("kd_kue");
                String b = res.getString("nama_kue");
                String c = res.getString("harga");
                String d = res.getString("stok");
                String e = res.getString("berat");
                String[] data = {a,b,c,d,e};
                tabmode.addRow(data);
            }
            con.close();
        } catch (Exception e) {
        }
    }
 
 private void dapatData(){
         switch(cmbCari.getSelectedIndex()){
            case 0 : cariBy = "kd_kue"; break;
            case 1 : cariBy = "nama_kue"; break;
            case 2 : cariBy = "harga"; break;        
            case 3 : cariBy = "stok"; break;
            case 4 : cariBy = "berat"; break;
            default : cariBy = "kd_kue";
        }         
        kriteria = txtCari.getText().trim();     
    }
    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
         try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "Update kue set "
            + "nama_kue='" + txtNamaKue.getText() + "',"
            + "harga='" + txtHargaKue.getText() + "',"
            + "stok='" + txtJumlah.getText() + "',"
            + "berat='" + txtBerat.getText() + "' Where "
            + "kd_kue='" + txtKodeKue.getText() + "'";
            int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKodeKue.getText()+" Ingin Diedit ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
            if(a==0){
                int sukses = st.executeUpdate(sql);
                if(sukses > 0){
                    JOptionPane.showMessageDialog(null, "Data berhasil diedit");
                    bersihTable();
                    datatabel();
                    bersih();
                    autoNumber();
                    awal();
                }else{
                    JOptionPane.showMessageDialog(null, "Data gagal diedit");
                }
            }else{
                autoNumber();
                awal();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
         try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "Delete From kue Where kd_kue='" + txtKodeKue.getText() + "'";
            int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKodeKue.getText()+" Ingin Dihapus ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
            if(a==0){
                int sukses = st.executeUpdate(sql);
                if(sukses > 0){
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                    bersihTable();
                    datatabel();
                    bersih();
                    autoNumber();
                    awal();
                }else{
                    JOptionPane.showMessageDialog(null, "Data gagal dihapus");
                }
            }else{
                autoNumber();
                awal();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        bersihTable();
        CariData();
    }//GEN-LAST:event_btnCariActionPerformed

    private void tblKueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKueMouseClicked
        // TODO add your handling code here:
        if (evt.getSource() == tblKue) {
                if (evt.getClickCount() == 1) {
                    int row = tblKue.getSelectedRow();
                    txtKodeKue.setText(tabmode.getValueAt(row, 0).toString());
                    txtNamaKue.setText(tabmode.getValueAt(row, 1).toString());
                    txtHargaKue.setText(tabmode.getValueAt(row, 2).toString());
                    txtJumlah.setText(tabmode.getValueAt(row, 3).toString());
                    txtBerat.setText(tabmode.getValueAt(row, 4).toString());
                    
                    btnSimpan.setEnabled(false);
                    btnUbah.setEnabled(true);
                    btnHapus.setEnabled(true);
                }
            }
    }//GEN-LAST:event_tblKueMouseClicked

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:    
        txtCari.setText("");
        bersih();
        bersihTable();
        CariData();
        autoNumber();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() ==  evt.VK_ENTER) {
            bersihTable();
            CariData();
        }
    }//GEN-LAST:event_txtCariKeyPressed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        bersihTable();
        CariData();
    }//GEN-LAST:event_txtCariKeyReleased

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKue;
    private javax.swing.JTextField txtBerat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHargaKue;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKodeKue;
    private javax.swing.JTextField txtNamaKue;
    // End of variables declaration//GEN-END:variables
}
