/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import kkpkremes.Koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author maria
 */
public class Crud {
    Validasi validasi;
    String Kode_pesan="";
    
    public void insertPesan(String kdpsn, String kdplg, String kdkue, String jml, String hrg) {
        String sql = "insert into pesan (kd_pesan,kd_pelanggan,kd_kue,tgl_pesan,jml_kue,status,harga_satuan) values('"+kdpsn+"','"+kdplg+"','"+kdkue+"',now(),'"+jml+"','PESAN','"+hrg+"')";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Pesanan Berhasil Disimpan !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Disimpan !");
            System.out.println("Gagal Menyimpan pesan : "+e);
        }
    }
    
    public void ubahPesan(String kdpsn, String kdplg, String kdkue, String jml, String hrg) {
        String sql = "UPDATE pesan SET kd_pelanggan = '"+kdplg+"',kd_kue = '"+kdkue+"',tgl_pesan = now(),jml_kue = '"+jml+"',status = 'PESAN',harga_satuan = '"+hrg+"' WHERE kd_pesan = '" + kdpsn + "';";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Pesanan Berhasil Diubah !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Diubah !");
            System.out.println("Gagal Mengubah pesan : "+e);
        }
    }
    
    public void aturStok(String kdpsn, String kalkulasi, String kdkue)
	{
        try
        {
        Koneksi konek 		= new Koneksi();
        Connection con 	= konek.bukaKoneksi();
        Statement st	= con.createStatement();
        String 		sql	= "select * from detil_pesan where kd_pesan='"+kdpsn+"' ";
        ResultSet 	rs	= st.executeQuery(sql);
        System.out.println(sql);
        rs.last();
        String 	banyak[]	= new String[rs.getRow()];
        int 	i			= 0;
        rs.beforeFirst();
        while(rs.next())
        {
                banyak[i] 	= rs.getString(3);
        System.out.println("masuk while aturStok "+banyak[i] + kdkue +banyak.length);

                i++;
        }			
        for(int n=0; n<banyak.length; n++)
        {
            System.out.println("masuk for aturStok");
                String s	= "";
                String b 	= "select stok from kue where kd_kue='"+kdkue+"' ";
                rs		=st.executeQuery(b);
                if(rs.next())
                        s = rs.getString(1);

                String kurang = "update kue set stok='"+s+"'"+kalkulasi+"'"+banyak[n]+
                                                "' where kd_kue='"+kdkue+"' ";
               // JOptionPane.showMessageDialog(null, kurang);
                System.out.println(kurang);
                st.executeUpdate(kurang);
        }
        con.close();
}
catch(Exception e) {e.printStackTrace();}
}
    
public void ProsesUpdateDetil(String kdpsn, String kdkue, String qty, String harga, String total){
        String sql = "update detil_pesan set jml_kue='"+qty+"',"
                + "total ='"+total+"'where kd_pesan='"+kdpsn+"' and kd_kue='"+kdkue+"'";
        try {
            Connection con  = new Koneksi().bukaKoneksi();
            Statement st    = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Pesanan Berhasil Disimpan !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Disimpan !");
            System.out.println("Gagal Menyimpan pesan : "+e);
        }
    }    
    
public void hapusDetilPesan2(String kdpsn, String kdKue) throws SQLException{
        String sql = "delete from detil_pesan where kd_pesan='"+kdpsn+"' and kd_kue='"+kdKue+"'";
        Connection con = new Koneksi().bukaKoneksi();
        Statement st = con.createStatement();
        int hasil = st.executeUpdate(sql);
        System.out.println(sql);
        if(hasil>0){
            System.out.println("Berhasil Disimpan !");
        }else {
            System.out.println("Gagal Disimpan !");
        }
    }    

public void hapusPesan2(String kdpsn, String kdKue) throws SQLException{
        String sql = "delete from pesan where kd_pesan='"+kdpsn+"'";
        Connection con = new Koneksi().bukaKoneksi();
        Statement st = con.createStatement();
        int hasil = st.executeUpdate(sql);
        System.out.println(sql);
        if(hasil>0){
            System.out.println("Berhasil Disimpan !");
        }else {
            System.out.println("Gagal Disimpan !");
        }
    }

    public void ProsesBatal(String kdpsn) throws SQLException{ //nama meninisialisasi
        String sql = "UPDATE pesan SET status = 'BATAL' WHERE kd_pesan = '" + kdpsn + "'";// nama di db,pesan nma atribut di db
         Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
        int hasil = st.executeUpdate(sql);
        if(hasil > 0){
            System.out.println("Update Status Pesanan Menjadi Proses");
            sql = "select * from detil_pesan where kd_pesan='"+kdpsn+"'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String KodeKue = rs.getString("kd_kue");
                int jumlah         = Integer.parseInt(rs.getString("jml_kue"));
                System.out.println("Data Kue : "+KodeKue);
                String sql_update = "update kue set stok = stok + "+jumlah+" where kd_kue= '" + KodeKue + "'";
                Statement st2 = con.createStatement();
                hasil = st2.executeUpdate(sql_update);
                
                if(hasil > 0){
                    System.out.println("Berhasil Disimpan !");
                } else {
                    System.out.println("Perubahan Qty Gagal Dilakukan !");
                }
            }
        }else {
            System.out.println("Gagal");
        }
    }

    
    public void hapusPesan(String kdpsn) throws SQLException{
        String sql = "delete from pesan where kd_pesan='"+kdpsn+"'";
         Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
        int hasil = st.executeUpdate(sql);
        if(hasil>0){
            System.out.println("Berhasil Disimpan !");
        }else {
            System.out.println("Gagal Disimpan !");
        }
    }
    
    public void hapusDetilPesan(String kdpsn) throws SQLException{
        String sql = "delete from detil_pesan where kd_pesan='"+kdpsn+"'";
         Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
        int hasil = st.executeUpdate(sql);
        if(hasil>0){
            System.out.println("Berhasil Disimpan !");
        }else {
            System.out.println("Gagal Disimpan !");
        }
    }
    
    public void ProsesUpdateStok(String kdkue, String jml){
        String sql = "update kue set stok = stok - "+jml+" where kd_kue= '" +kdkue + "'";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Pesanan Berhasil Disimpan !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Disimpan !");
            System.out.println("Gagal Menyimpan pesan : "+e);
        }
    }
    
    public void KonfirmasiPesan(String kdpsn){
        String sql = "UPDATE pesan SET status = 'PROSES' WHERE kd_pesan = '" + kdpsn + "';";
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Pesanan Berhasil Disimpan !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Disimpan !");
            System.out.println("Gagal Menyimpan pesan : "+e);
        }
    }
    
    
    //String kdpesan="";
    public void insertDetilPesan(String kdpsn,String kdkue, String jml, String hrg, String total){
       //kodepesan = validasi.getIDPesan();
        String sql = "insert into detil_pesan (kd_pesan,kd_kue,jml_kue,harga_satuan,total) values('"+kdpsn+"','"+kdkue+"','"+jml+"','"+hrg+"','"+total+"')";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Berhasil Disimpan !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Disimpan !");
            System.out.println("Gagal Menyimpan detil pesan : "+e);
        }
    }
    
    public void ubahDetilPesan(String kdpsn,String kdkue, String jml, String hrg, String total){
       //kodepesan = validasi.getIDPesan();
        String sql = "UPDATE into detil_pesan SET kdkue = '"+kdkue+"',jml_kue = '"+jml+"',harga_satuan = '"+hrg+"',total = '"+total+ "' WHERE kd_pesan = '" + kdpsn + "';";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Berhasil Diubah !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Diubah !");
            System.out.println("Gagal Mengubah detil pesan : "+e);
        }
    }
    
     public void insertPelanggan(String kdplg, String nmplg, String nohp, String alamat){
        
        String sql = "insert into pelanggan (kd_pelanggan,nama_pelanggan,no_hp,alamat) values('"+kdplg+"','"+nmplg+"','"+nohp+"','"+alamat+"')";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Berhasil Disimpan !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Disimpan !");
            System.out.println("Gagal Menyimpan pelanggan : "+e);
        }
    }
    
public void DetilPelanggan(String noHp){
        String sql = "insert into detil_pelanggan (no_hp,id_group) values('"+noHp+"','1')";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st    = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
                System.out.println("Berhasil Disimpan !");
            }
        } catch (Exception e) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
            System.out.println("Gagal Disimpan ! : "+e);
        }
    }     
     
     
     public void DeleteKontak(String nohp){
        String sql = "delete from pelanggan where no_hp='"+nohp+"'";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                System.out.println("Berhasil Dihapus !");
            }
        } catch (Exception e) {
            System.out.println("Gagal Dihapus ! "+e);
        }
    }
    
    public void insertInbox(String NoInbox, String pesan,String nohp){
        System.out.println("Sudah Masuk Ke method Simpan Inbox");
        String sql = "insert into inbox (no_inbox,no_hp,waktu,pesan) values('"+NoInbox+"','"+nohp+"',now(),'"+pesan+"')";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                System.out.println("Berhasil Disimpan");
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
            }
        } catch (Exception e) {
            System.out.println("Gagal Disimpan ! "+e);
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
        }
    }
    public void insertOutbox(String NoOutbox, String nohp, String pesan){
        System.out.println("Sudah Masuk Ke method Simpan Outbox");
        String sql = "insert into outbox (no_outbox,no_hp,waktu,pesan) values('"+NoOutbox+"','"+nohp+"',now(),'"+pesan+"')";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                System.out.println("Berhasil Disimpan");
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
            }
        } catch (Exception e) {
            System.out.println("Gagal Disimpan ! "+e);
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
        }
    }
     public void insertBroadcast(String NoBc, String nohp, String pesan){
        System.out.println("Sudah Masuk Ke method Simpan Broadcast");
        String sql = "insert into broadcast (no_broadcast,waktu,no_hp,pesan) values('"+NoBc+"',now(),'"+nohp+"','"+pesan+"')";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                System.out.println("Berhasil Disimpan");
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
            }
        } catch (Exception e) {
            System.out.println("Gagal Disimpan ! "+e);
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
        }
    }
     
     public void insertKomplain(String NoKomp, String kdplg, String nohp, String komplain){
        String sql = "insert into komplain (no_komplain,kd_pelanggan,tgl_komplain,no_hp,isi_komplain) values('"+NoKomp+"','"+kdplg+"',now(),'"+nohp+"','"+komplain+"')";
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            int hasil       = st.executeUpdate(sql);
            if (hasil > 0) {
                System.out.println("Berhasil Disimpan");
                //javax.swing.JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
            }
        } catch (Exception e) {
            System.out.println("Gagal Disimpan ! "+e);
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal Disimpan ! "+e);
        }
    }
     public ResultSet dataTablePelanggan(){
        String sql = "select * from pelanggan order by no_hp asc";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs    = st.executeQuery(sql);
            
            return rs;
        } catch (Exception e) {
            return null;
        }
    }
     
      public ResultSet listGroup(){
        String sql = "select * from grup order by nama_group";
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs    = st.executeQuery(sql);
            
            System.out.println(sql);
            return rs;            
        } catch (Exception e) {
            System.out.println(""+e);
            return null;
        }
    }
        
public java.util.Vector listKontakGroup(String nama_group){
        String sql = "SELECT a.no_hp, a.kd_pelanggan, a.nama_pelanggan, b.nama_group "
                + "FROM pelanggan a, grup b, detil_pelanggan c "
                + "WHERE a.no_hp = c.no_hp AND b.id_group = c.id_group "
                + "and b.nama_group = '"+nama_group+"'";
        
        System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs    = st.executeQuery(sql);
            
            java.util.Vector tampung = new java.util.Vector();
            
            
            while (rs.next()) {                
                String nomor = rs.getString("a.no_hp");
                System.out.println("nomor : "+nomor);
                tampung.add(nomor);
            }
            
            return tampung;            
        } catch (Exception e) {
            System.out.println(""+e);
            e.printStackTrace();
            return null;
        }
    }
     
      public java.util.Vector listKontakPelanggan(String Nama_Pelanggan){
        String sql = "SELECT * from pelanggan WHERE no_hp='"+Nama_Pelanggan+"'";
        
        //System.out.println(sql);
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs    = st.executeQuery(sql);
            
            java.util.Vector tampung = new java.util.Vector();
            
            
            while (rs.next()) {                
                String nomor = rs.getString("no_hp");
                System.out.println("nomor : "+nomor);
                tampung.add(nomor);
            }
            
            return tampung;            
        } catch (Exception e) {
            System.out.println(""+e);
            e.printStackTrace();
            return null;
        }
    }
      
    
      
    public ResultSet dataTableInbox(){
        String sql = "SELECT a.kd_pesan, a.kd_kue, b.nama_kue, a.jml_kue, a.harga_satuan "
                    + "FROM detil_pesan a, kue b "
                    + "WHERE a.kd_kue = b.kd_kue";
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs    = st.executeQuery(sql);
            
            return rs;
        } catch (Exception e) {
            return null;
        }
    }
    public ResultSet cariDataDetil(String isi, String field){
        String sql = "SELECT a.kd_pesan, a.kd_kue, b.nama_kue, a.jml_kue, a.harga_satuan "
                    + "FROM detil_pesan a, kue b "
                    + "WHERE a.kd_kue = b.kd_kue"
                    + "WHERE "+field+" like '%"+isi+"%'";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs    = st.executeQuery(sql);
            System.out.println(sql);
            return rs;            
        } catch (Exception e) {
            System.out.println(""+e);
            return null;
        }
    }
     public ResultSet dataTableKontak(){
        String sql = "select * from pelanggan order by no_hp asc";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs    = st.executeQuery(sql);
            
            return rs;
        } catch (Exception e) {
            return null;
        }
    }   
     
    }

