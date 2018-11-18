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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import Server.Validasi;
import kkpkremes.MenuUtama;
/**
 *
 * @author maria
 */
public class Pesan extends javax.swing.JInternalFrame {
    MenuUtama mUtama;
    Connection con;
    Statement stat;
    ResultSet res;

    private String cariBy = "", kriteria = "";
    Object[] Baris = {"Kode Pesan", "Kode Pelanggan", "Kode Kue", "Tgl Pesan", "Jumlah Kue","Status", "Harga Satuan"};
    DefaultTableModel tabmode = new DefaultTableModel(null, Baris);
    
    Object[] row = {"Kode Kue", "Jumlah Kue", "Harga Satuan"};
    DefaultTableModel tabmodel = new DefaultTableModel(null, row);
  //  Validasi validasi;

    String total, jumlah;
    /**
     * Creates new form Pesan
     */
    public Pesan(MenuUtama mUtama) {
        initComponents();
        this.mUtama = mUtama;
        txtHarSat.addKeyListener(new KeyAdapter()
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
        datatabel2();
        awal();
        setTabel(tblPesanan, new int[]{100, 180, 150, 300, 150, 150, 150});
        setTabel(tblPesan, new int[]{100, 100, 100});
        isiComboCari();
    }
    
  public void awal(){
        btnSimpan.setEnabled(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
  }
  
  public void selesai(){
        btnSimpan.setEnabled(true);
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        btnBatalCari.setEnabled(true);
  }
  
     public void bersihTable() {
        int row = tblPesanan.getRowCount() - 1;
        while (row >= 0) {
            tabmode.removeRow(row);
            row--;
        }
    }
     
     public void bersihTable2() {
        int row = tblPesan.getRowCount() - 1;
        while (row >= 0) {
            tabmodel.removeRow(row);
            row--;
        }
    }
     
     public void setTabel(javax.swing.JTable tb, int lebar[]) {
        tb.setAutoResizeMode(tb.AUTO_RESIZE_OFF);
        int kolom = tb.getColumnCount();
        for (int i = 0; i < kolom; i++) {
            javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
            tbc.setPreferredWidth(lebar[i]);
            tb.setRowHeight(18);
        }
    }

     private void isiComboCari() {
        cmbCari.addItem("Kode Pesan");
        cmbCari.addItem("Kode Pelanggan");
        cmbCari.addItem("Kode Kue");
        cmbCari.addItem("Tanggal Kue");
        cmbCari.addItem("Jumlah Kue");
        cmbCari.addItem("Status");
        cmbCari.addItem("Harga");
    }
   
     public void CariData(){
        dapatData();
        tblPesanan.setModel(tabmode);
        String sql = "SELECT * FROM pesan where "+cariBy+" LIKE '%"+kriteria+"%'";
        try {
            Koneksi konek = new Koneksi ();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            res=st.executeQuery(sql);
            
            while (res.next()){
                String a = res.getString("kd_pesan");
                String b = res.getString("kd_pelanggan");
                String c = res.getString("kd_kue");
                String d = res.getString("tgl_pesan");
                String e = res.getString("jml_kue");                     
                String f = res.getString("status");                     
                String g = res.getString("harga_satuan");
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            con.close();
        }catch (Exception e) {
    }
    }     

     
     public void datatabel(){
        tblPesanan.setModel(tabmode);
        String sql = "SELECT * FROM pesan";                    
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            res=st.executeQuery(sql);
            while(res.next()){
                String a = res.getString("kd_pesan");
                String b = res.getString("kd_pelanggan");
                String c = res.getString("kd_kue");
                String d = res.getString("tgl_pesan");
                String e = res.getString("jml_kue");                     
                String f = res.getString("status");                     
                String g = res.getString("harga_satuan");
                String[] data = {a,b,c,d,e,f,g};
                tabmode.addRow(data);
            }
            con.close();
            }catch (Exception ev) {
        }         
    }

public void datatabel2(){
        tblPesan.setModel(tabmodel);
        String sql = "SELECT kd_kue,jml_kue,harga_satuan FROM detil_pesan WHERE kd_pesan = '"+txtKdPesan.getText()+"' ";                 
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            res=st.executeQuery(sql);
            while(res.next()){
                String a = res.getString("kd_kue");
                String b = res.getString("jml_kue");                    
                String c = res.getString("harga_satuan");
                String[] data = {a,b,c};
                tabmodel.addRow(data);
            }
            con.close();
            }catch (Exception ev) {
        }         
    }     

   
public void Cari(){
    datatabel2();
    try{
        
        Koneksi konek = new Koneksi ();
        Connection con = konek.bukaKoneksi();
        Statement st = con.createStatement();
        String sql = "SELECT a.kd_pelanggan,a.kd_kue,a.tgl_pesan,a.jml_kue,a.status,a.harga_satuan,b.total FROM pesan a ,detil_pesan b WHERE a.kd_pesan = b.kd_pesan AND a.kd_pesan =  '"+txtKdPesan.getText()+"' ";
        ResultSet res=st.executeQuery(sql); 
        
        if(res.next()){
            txtKdPelanggan.setText(res.getString("kd_pelanggan"));
            txtKdKue.setText(res.getString("kd_kue"));
            txtTglPesan.setText(res.getString("tgl_pesan"));
            txtJumlah.setText(res.getString("jml_kue"));
            txtStatus.setText(res.getString("status"));
            txtHarSat.setText(res.getString("harga_satuan"));
            txtTotal.setText(res.getString("total"));
        }
        else{
            selesai();            
        }
        res.close();
        con.close();
    }
    catch(SQLException e){
        
    }
}

    private void dapatData(){
         switch(cmbCari.getSelectedIndex()){
            case 0 : cariBy = "kd_pesan"; break;
            case 1 : cariBy = "kd_pelanggan"; break;
            case 2 : cariBy = "kd_kue"; break;
            case 3 : cariBy = "tgl_pesan"; break;
            case 4 : cariBy = "jml_kue"; break;
            case 5 : cariBy = "status"; break;
            case 6 : cariBy = "harga_satuan"; break;
            default : cariBy = "kd_pesan";
        }
         
        kriteria = txtCari.getText().trim();     
    }
    
    public void bersih() {
        txtCari.setText("");
    }
    
  // String jml = txtJumlah.getText();
   //String.valueOf(jml);
//    int jml = Integer.parseInt(txtJumlah.getText());
//   int harsat = Integer.parseInt(txtHarSat.getText());
//   txtTotal.setText(jml * harsat);

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKdPesan = new javax.swing.JTextField();
        txtKdPelanggan = new javax.swing.JTextField();
        txtKdKue = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTglPesan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtHarSat = new javax.swing.JTextField();
        btnBatal = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbCari = new javax.swing.JComboBox();
        btnCari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPesanan = new javax.swing.JTable();
        btnBatalCari = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesan"));

        tblPesan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Kue", "Jumlah kue", "Harga Satuan"
            }
        ));
        tblPesan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblPesanMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesan);

        jLabel2.setText("Kode Pesan        :");

        jLabel3.setText("Kode Pelanggan :");

        jLabel4.setText("Kode Kue            :");

        txtKdPesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdPesanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKdPesanKeyReleased(evt);
            }
        });

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

        jLabel5.setText("Tanggal Pesan    :");

        jLabel6.setText("Jumlah                 :");

        jLabel7.setText("Status                  :");

        jLabel8.setText("Harga Satuan      :");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJumlahKeyReleased(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalKeyReleased(evt);
            }
        });

        jLabel9.setText("Total                    :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKdPelanggan))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKdKue))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKdPesan, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtTglPesan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSimpan)
                                    .addComponent(btnUbah)
                                    .addComponent(btnHapus)
                                    .addComponent(btnBatal)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHarSat, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(131, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnSimpan)
                    .addComponent(txtKdPesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnUbah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus)
                        .addGap(12, 12, 12)
                        .addComponent(btnBatal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtKdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtKdKue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTglPesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtHarSat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pesan", jPanel1);

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

        jLabel1.setText("Cari");

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Pesan", "Kode Pelanggan", "Kode Kue", "Tanggal Pesan", "Jumlah", "Status", "Harga Satuan"
            }
        ));
        jScrollPane2.setViewportView(tblPesanan);

        btnBatalCari.setText("Batal");
        btnBatalCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cmbCari, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnCari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatalCari)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari)
                    .addComponent(btnBatalCari))
                .addContainerGap(259, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pesananan", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        bersihTable();
        CariData();
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnBatalCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalCariActionPerformed
        // TODO add your handling code here:
        txtCari.setText("");
        bersih();        
        bersihTable();
        CariData();
    }//GEN-LAST:event_btnBatalCariActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void tblPesanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesanMouseClicked
        // TODO add your handling code here:
        if (evt.getSource() == tblPesan) {
                if (evt.getClickCount() == 1) {
                    int row = tblPesan.getSelectedRow();
                    txtKdKue.setText(tabmodel.getValueAt(row, 0).toString());
                    txtJumlah.setText(tabmodel.getValueAt(row, 1).toString());                     
                    txtHarSat.setText(tabmodel.getValueAt(row, 2).toString());
                    
                    btnSimpan.setEnabled(false);
                    btnUbah.setEnabled(true);
                    btnHapus.setEnabled(true);
                }
            }
    }//GEN-LAST:event_tblPesanMouseClicked

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
if(evt.getKeyCode() ==  evt.VK_ENTER) {
            bersihTable();
            CariData();
        }
    }//GEN-LAST:event_txtCariKeyPressed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
     bersihTable();
     CariData();
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
         if (txtKdPesan.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Pesan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtKdPesan.requestFocus();
        }else if (txtKdPelanggan.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Pelanggan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtKdPelanggan.requestFocus();
        
        }else if (txtKdKue.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Kue masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtKdKue.requestFocus();
        
        }else if (txtTglPesan.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Tanggal Pesan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtTglPesan.requestFocus();
        }else if (txtJumlah.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Jumlah masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtJumlah.requestFocus();
        }else if (txtStatus.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Status masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtStatus.requestFocus();        
        }else if (txtHarSat.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Harga satuan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtHarSat.requestFocus();
        }else if (txtTotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Total masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtTotal.requestFocus();
        }else {
                
                String sql = "insert into pesan values('"+txtKdPesan.getText()+"','"+txtKdPelanggan.getText()+"','"+txtKdKue.getText()+"','"+txtTglPesan.getText()+"','"+txtJumlah.getText()+"','"+txtStatus.getText()+"','"+txtHarSat.getText()+"')";
                String sql1 = "insert into detil_pesan values('"+txtKdPesan.getText()+"','"+txtKdKue.getText()+"','"+txtJumlah.getText()+"','"+txtHarSat.getText()+"','"+txtTotal.getText()+"')";
                System.out.println(sql);
                System.out.println(sql1);

                try {
                   Koneksi konek = new Koneksi();
                   Connection con = konek.bukaKoneksi();
                   Statement st = con.createStatement();
                   st.executeUpdate(sql);
                   st.executeUpdate(sql1);
                   JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan");
                   
                    bersihTable();
                    bersihTable2();
                    datatabel();
                    datatabel2();
                    awal();
                   
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            try {
//                Koneksi konek = new Koneksi();
//                Connection con = konek.bukaKoneksi();
//                Statement st = con.createStatement();
//                String sql = "insert into pesan values('"+txtKdPesan.getText()+"','"+txtKdPelanggan.getText()+"','"+txtKdKue.getText()+"','"+txtTglPesan.getText()+"','"+txtJumlah.getText()+"','"+txtStatus.getText()+"','"+txtHarSat.getText()+"')";
//                String sql1 = "insert into detil_pesan values('"+txtTotal.getText()+"')";
//                int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKdPesan.getText()+" Ingin Disimpan ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
//                if(a==0){
//                    int sukses = st.executeUpdate(sql);
//                    int suksess = st.executeUpdate(sql1);
//                    if(sukses > 0 && suksess >0){
//                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
//                        bersihTable();
//                        bersihTable2();
//                        datatabel();
//                        datatabel2();
//                        awal();
//                    }else{
//                        JOptionPane.showMessageDialog(null, "Data gagal disimpan");
//                    }
//                }else{
//                    awal();
//                }
//
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
        }
        
    
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
 if (txtKdPesan.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Pesan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtKdPesan.requestFocus();
        }else if (txtKdPelanggan.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Pelanggan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtKdPelanggan.requestFocus();
        
        }else if (txtKdKue.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Kue masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtKdKue.requestFocus();
        
        }else if (txtTglPesan.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Tanggal Pesan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtTglPesan.requestFocus();
        }else if (txtJumlah.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Jumlah masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtJumlah.requestFocus();
        }else if (txtStatus.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Status masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtStatus.requestFocus();        
        }else if (txtHarSat.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Harga satuan masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtHarSat.requestFocus();
        }else if (txtTotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Total masih kosong!\n"
                + "Silahkan diisi terlebih dahulu!");
            txtTotal.requestFocus();
        }else {
                
             String sql = "Update pesan set "
            + "kd_pesan='" + txtKdPesan.getText() + "',"
            + "kd_pelanggan='" + txtKdPelanggan.getText() + "',"
            + "kd_kue='" + txtKdKue.getText() + "',"
            + "tgl_pesan='" + txtTglPesan.getText() + "',"
            + "jml_kue='" + txtJumlah.getText() + "',"
            + "status='" + txtStatus.getText() + "',"
            + "harga_satuan='" + txtHarSat.getText() + "' Where "
            + "kd_pesan='" + txtKdPesan.getText() + "'";
             String sql1 = "Update detil_pesan set "
            + "kd_pesan = '"+txtKdPesan.getText()+"',"
            + "kd_kue= '"+txtKdKue.getText()+ "',"
            + "jml_kue= '"+txtJumlah.getText()+"',"
            + "harga_satuan = '"+txtHarSat.getText()+"',"
            + "total = '"+txtTotal.getText()+"' Where "
            + "kd_pesan='"+txtKdPesan.getText()+ "'";
                System.out.println(sql);
                System.out.println(sql1);

                try {
                   Koneksi konek = new Koneksi();
                   Connection con = konek.bukaKoneksi();
                   Statement st = con.createStatement();
                   st.executeUpdate(sql);
                   st.executeUpdate(sql1);
                   JOptionPane.showMessageDialog(this, "Data Berhasil Diubah");
                   
                    bersihTable();
                    bersihTable2();
                    datatabel();
                    datatabel2();
                    awal();
                   
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }  
//        
//        
//        try {
//            Koneksi konek = new Koneksi();
//            Connection con = konek.bukaKoneksi();
//            Statement st = con.createStatement();
//            String sql = "Update pesan set "
//            + "kd_pesan='" + txtKdPesan.getText() + "',"
//            + "kd_pelanggan='" + txtKdPelanggan.getText() + "',"
//            + "kd_kue='" + txtKdKue.getText() + "',"
//            + "tgl_pesan='" + txtTglPesan.getText() + "',"
//            + "jml_kue='" + txtJumlah.getText() + "',"
//            + "status='" + txtStatus.getText() + "',"
//            + "harga_satuan='" + txtHarSat.getText() + "' Where "
//            + "kd_pesan='" + txtKdPesan.getText() + "'";
//            String sql1 = "Update detil_pesan set total = '"+txtTotal.getText()+"'";
//            int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKdPesan.getText()+" Ingin Diedit ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
//            if(a==0){
//                int sukses = st.executeUpdate(sql);
//                int suksess = st.executeUpdate(sql1);
//                if(sukses > 0 && suksess >0){
//                    JOptionPane.showMessageDialog(null, "Data berhasil diedit");
//                    bersihTable();
//                    bersihTable2();
//                    datatabel();
//                    datatabel2();
//                    awal();
//                }else{
//                    JOptionPane.showMessageDialog(null, "Data gagal diedit");
//                }
//            }else{
//                awal();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
String sql = "Delete From pesan Where kd_pesan='" + txtKdPesan.getText() + "'";
String sql1 = "Delete From detil_pesan Where kd_pesan='" + txtKdPesan.getText() + "'";        

try {
       Koneksi konek = new Koneksi();
       Connection con = konek.bukaKoneksi();
       Statement st = con.createStatement();
       st.executeUpdate(sql);
       st.executeUpdate(sql1);
       JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");

        bersihTable();
        bersihTable2();
        datatabel();
        datatabel2();
        awal();

    } catch (Exception e) {
        e.printStackTrace();
    }

    
            
//     try {
//            Koneksi konek = new Koneksi();
//            Connection con = konek.bukaKoneksi();
//            Statement st = con.createStatement();
//            String sql = "Delete From pesan Where kd_pesan='" + txtKdPesan.getText() + "'";
//            String sql1 = "Delete From detil_pesan Where kd_pesan='" + txtKdPesan.getText() + "'";
//            int a=JOptionPane.showConfirmDialog(null, "Apakah Data Dengan Kode : "+txtKdPesan.getText()+" Ingin Dihapus ?","Konfirmasi Pesan",JOptionPane.YES_NO_OPTION);
//            if(a==0){
//                int sukses = st.executeUpdate(sql);
//                int suksess = st.executeUpdate(sql1);
//                if(sukses > 0 && suksess > 0){
//                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
//                    bersihTable();
//                    bersihTable2();
//                    datatabel();
//                    datatabel2();
//                    awal();
//                }else{
//                    JOptionPane.showMessageDialog(null, "Data gagal dihapus");
//                }
//            }else{
//                awal();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }   
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtKdPesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdPesanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdPesanKeyPressed

    private void txtKdPesanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdPesanKeyReleased
        
        bersihTable2();
        Cari();
    }//GEN-LAST:event_txtKdPesanKeyReleased

    private void tblPesanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPesanMouseEntered

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
       txtKdPesan.setText("");       
       txtKdPelanggan.setText("");       
       txtKdKue.setText("");       
       txtTglPesan.setText("");       
       txtJumlah.setText("");       
       txtStatus.setText("");       
       txtHarSat.setText("");       
       txtTotal.setText("");       
       bersihTable2();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    
    private void txtJumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyReleased
        
 int a = Integer.parseInt(txtJumlah.getText());
 int b = Integer.parseInt(txtHarSat.getText());
 int c = Integer.parseInt(txtTotal.getText());
 int d;
 
 if(a==0){
     txtTotal.setText("0");
 }else if(a>0){
      d = a*b;
      txtTotal.setText("" + d);
    }
 else{
    bersihTable();
    bersihTable2();
    datatabel();
    datatabel2();
    awal();
 }
   
    }//GEN-LAST:event_txtJumlahKeyReleased

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyPressed
        // TODO add your handling code here:
  
    }//GEN-LAST:event_txtTotalKeyPressed

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBatalCari;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblPesan;
    private javax.swing.JTable tblPesanan;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarSat;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKdKue;
    private javax.swing.JTextField txtKdPelanggan;
    private javax.swing.JTextField txtKdPesan;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTglPesan;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
