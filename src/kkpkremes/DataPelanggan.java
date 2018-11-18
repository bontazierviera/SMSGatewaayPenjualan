/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kkpkremes;
import kkpkremes.Koneksi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kkpkremes.MenuUtama;
/**
 *
 * @author maria
 */
public class DataPelanggan extends javax.swing.JInternalFrame {
    MenuUtama mUtama;
    Connection con;
    Statement stat;
    ResultSet res;
    private String no_hp="";
    
    private String cariBy="", kriteria="";
    Object[] Baris={"Kode Pelanggan","Nama","No Handphone","Alamat"};
    DefaultTableModel tabmode = new DefaultTableModel(null, Baris);
    Koneksi koneksi;
    private String Kode_pelanggan;
    private Object kon;

    /**
     * Creates new form DataPelanggan
     */
    public DataPelanggan(MenuUtama mUtama) {
        initComponents();
        this.mUtama = mUtama;
        koneksi = new Koneksi();
        txtnohp.addKeyListener (new KeyAdapter()
                {
                    public void keyTyped (KeyEvent angka)
                {
                    char c = angka.getKeyChar ();
                    if (!(Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_ENTER)))
                {
                    JOptionPane.showMessageDialog(null, "Haynya bisa diisi oleh angka");
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
        awal ();
        setTabel(tblpelanggan, new int[] {130,200,200,500});
        isiComboCari();
    
    }
    
    private void autoNumber(){
        try {
//            Koneksi konek = new Koneksi();
//            Connection con = konek.bukaKoneksi();
//            Statement st = con.createStatement();
//            String sql = "SELECT * FROM pelanggan ORDER BY kd_pelanggan DESC";
//            res = st.executeQuery(sql);
//            if (res.next()){
//                String Kd_kue = res.getString("kd_pelanggan").substring(1);
//                String AN = "" + (Integer.parseInt(Kd_kue) + 1);
//                String Nol = "";
//                
//                if(AN.length() ==1)
//                {Nol = "000";}
//                else if(AN.length () ==2)
//                {Nol = "00";}
//                else if (AN.length () ==3)
//                {Nol = "0";}
//                else if (AN.length () ==4)
//                {Nol = "";}
//                txtKdPelanggan.setText("P" +Nol + AN);
//            }else{
                txtKdPelanggan.setText("P0001");
                txtKdPelanggan.setEditable(false);
                
//            }
            res.close();
            con.close();
            txtKdPelanggan.setEditable(false);
            }catch (Exception e) {
                e.printStackTrace();; //uyk penangana masalah
            }
    }
    
    public void bersihTable() {
        int row = tblpelanggan.getRowCount() - 1;
        while (row >= 0) {
            tabmode.removeRow(row);
            row--;
        }
    }
    
    private void isiComboCari (){
        cmbcari.addItem("Kode Pelanggan");
        cmbcari.addItem("Nama");
        cmbcari.addItem("No handphone");        
        cmbcari.addItem("Alamat");
    }
    
    private void dapatdata(){
        switch(cmbcari.getSelectedIndex()){
            case 0 : cariBy = "kd_pelanggan"; break;
            case 1 : cariBy = "nama_pelanggan"; break;
            case 2 : cariBy = "no_hp"; break;                
            case 3 : cariBy = "alamat"; break;
            default : cariBy = "kd_pelanggan";
        }
        kriteria = txtCari.getText().trim();
    }
    
    public void awal () {
        btnsimpan.setEnabled(true);
        btnubah.setEnabled(false);
        btnhapus.setEnabled(false);
    }
    
    public void setTabel (javax.swing.JTable tb, int lebar[]){
        tb.setAutoResizeMode(tb.AUTO_RESIZE_OFF);
        int kolom = tb.getColumnCount();
        for (int i=0; i<kolom; i++) {
            javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
            tbc.setPreferredWidth(lebar[i]);
            tb.setRowHeight(18);
            
        }
    }
    
    public void datatabel(){
        tblpelanggan.setModel(tabmode);
        String sql ="SELECT * from pelanggan";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            res=st.executeQuery(sql);
            while(res.next()){
                String a = res.getString("kd_pelanggan");
                String b = res.getString("nama_pelanggan");
                String c = res.getString("no_hp");                     
                String d = res.getString("alamat");
                String[] data = {a,b,c,d};
                tabmode.addRow(data);
            }
            con.close();
            }catch (Exception ev) {
        }
    }
    
    public void CariData(){
        dapatdata();
        tblpelanggan.setModel(tabmode);
        String sql = "SELECT * from pelanggan where "+cariBy+" LIKE '%"+kriteria+"%'";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();  
            res=st.executeQuery(sql);
            
            while (res.next()){
                String a = res.getString("kd_pelanggan");
                String b = res.getString("nama_pelanggan");
                String c = res.getString("no_hp");                
                String d = res.getString("alamat");                
                String[] data = {a,b,c,d};
                tabmode.addRow(data);
            }
            con.close();
        }catch (Exception e) {
    }
    }

    public void bersih(){
        txtNamaPelanggan.setText("");
        txtAlamat.setText("");
        txtnohp.setText("");
        
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtKdPelanggan = new javax.swing.JTextField();
        txtNamaPelanggan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextField();
        txtnohp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbcari = new javax.swing.JComboBox();
        txtCari = new javax.swing.JTextField();
        btnsimpan = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        btncari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblpelanggan = new javax.swing.JTable();

        setClosable(true);
        setTitle("data pelanggan");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Pelanggan"));

        jLabel1.setText("Kode pelanggan  :");

        jLabel3.setText("Nama                  :");

        jLabel4.setText("Alamat                :");

        jLabel5.setText("No Handphone :");

        txtKdPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdPelangganActionPerformed(evt);
            }
        });

        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(txtAlamat);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Cari");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        btnsimpan.setText("simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnhapus.setText("hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnubah.setText("ubah");
        btnubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnubahActionPerformed(evt);
            }
        });

        btnbatal.setText("Batal");
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        btncari.setText("cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        tblpelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "     Kode pelanggan", "           Nama", "     No handphone", "           Alamat"
            }
        ));
        tblpelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpelangganMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblpelanggan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1)
                                    .addComponent(txtnohp)
                                    .addComponent(txtKdPelanggan)
                                    .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbcari, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnsimpan)
                                    .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnubah, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(94, 94, 94)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtKdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsimpan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnubah))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnhapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnbatal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(txtnohp, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtKdPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdPelangganActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "Delete From pelanggan Where kd_pelanggan='" + txtKdPelanggan.getText() + "'";
            int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKdPelanggan.getText()+" Ingin Dihapus ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
            if(a==0){
                int sukses = st.executeUpdate(sql);
                if(sukses > 0){
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                    bersihTable();
                    datatabel();
                    bersih();
  //                  autoNumber();
                    awal();
                }else{
                    JOptionPane.showMessageDialog(null, "Data gagal dihapus");
                    }
                }else{
//                    autoNumber();
                    awal();
                }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
        bersihTable();
        CariData();
    }//GEN-LAST:event_btncariActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:      
        String kode_pelanggan ="";
        if (txtKdPelanggan.getText().equals("") || txtNamaPelanggan.getText().equals("") || txtnohp.getText().equals("") || txtAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Data masih kosong!\n"
                    + "Silahkan diisi terlebih dahulu!");
            
        }else{
            try{
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
          String sql = "insert into pelanggan values('"+txtKdPelanggan.getText()+"','"+txtNamaPelanggan.getText()+"','"+txtnohp.getText()+"','"+txtAlamat.getText()+"')";
  
               /* String sql = "INSERT INTO pelanggan VALUES('" + txtkdpelanggan.getText() + "','" + txtnamapelanggan.getText() "','" 
                + txtalamat.getText() + "','" + txtnohp.getText() + '");*/ //datbase blm di bkin.bkn dl biar ga error
                int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKdPelanggan.getText()+" Ingin Disimpan ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
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
        }
        
                                             


    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btnubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnubahActionPerformed
        // TODO add your handling code here:
        String kode_pelanggan = "";        
        
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            
            String sql = "Update pelanggan set "
                + "nama_pelanggan='" + txtNamaPelanggan.getText() + "',"
                + "no_hp='" + txtnohp.getText() + "',"
                + "alamat='" + txtAlamat.getText() + "' Where "
                + "kd_pelanggan='" + txtKdPelanggan.getText() + "'";
            int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKdPelanggan.getText()+" Ingin Diedit ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
            if(a==0){
                int sukses = st.executeUpdate(sql);
                if(sukses > 0){
                    JOptionPane.showMessageDialog(null, "Data berhasil diedit");
                    bersihTable();
                    datatabel();
                    bersih();
         //           autoNumber();
                    awal();
                }else{
                    JOptionPane.showMessageDialog(null, "Data gagal diedit");
                    }
                }else{
           //         autoNumber();
                    awal();
                }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnubahActionPerformed

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
        // TODO add your handling code here:
        txtCari.setText("");
        bersih();        
        bersihTable();
        CariData();
        autoNumber();
    }//GEN-LAST:event_btnbatalActionPerformed

    private void tblpelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpelangganMouseClicked
        // TODO add your handling code here:
                if (evt.getSource() == tblpelanggan) {
                if (evt.getClickCount() == 1) {
                    int row = tblpelanggan.getSelectedRow();
                    txtKdPelanggan.setText(tabmode.getValueAt(row, 0).toString());
                    txtNamaPelanggan.setText(tabmode.getValueAt(row, 1).toString());                                        
                    txtnohp.setText(tabmode.getValueAt(row, 2).toString());
                    txtAlamat.setText(tabmode.getValueAt(row, 3).toString());
                     btnsimpan.setEnabled(false);
                     btnubah.setEnabled(true);
                     btnhapus.setEnabled(true);
                    
                }
            }

    }//GEN-LAST:event_tblpelangganMouseClicked

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

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btncari;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btnubah;
    private javax.swing.JComboBox cmbcari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblpelanggan;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtKdPelanggan;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtnohp;
    // End of variables declaration//GEN-END:variables
}
