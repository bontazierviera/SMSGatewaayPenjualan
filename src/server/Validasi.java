/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import kkpkremes.Koneksi;
import java.sql.*;

/**
 *
 * @author maria
 */
public class Validasi {
    
    String sql = "";
    private Connection cn;
    private Statement st;
    private int i,h;
    private String kd,j;
    private boolean cek = false;
    
    public boolean cekPesan(String isiSms) {
        cek = false;
        if(isiSms.equals("")||isiSms.matches("(\\d|\\W)+")){
            cek = false;        
        }                 
        else if(isiSms.matches("KOMPLAIN (\\w|\\s|\\W)*")){
            cek = true;
        }
        else if(isiSms.trim().matches("CEKALL (\\w|\\s|W)*")){
            cek = true;
        }
        else if(isiSms.trim().matches("CEKPESAN (\\w|\\s|W)*")){
            cek = true;
        }
        else if(isiSms.trim().matches("CEKKUE (\\w|\\s|W)*")){
            cek = true;
        }
        else if(isiSms.matches("INFO")){
            cek = true;
        }
        else if(isiSms.matches("PEMESANAN")){
            cek = true;
        }
        else if(isiSms.trim().matches("PESAN (\\w+|-|\\d+,\\d+#)*\\w+,\\d+(\\s)*")){
            cek = true;
        }
        else if(isiSms.trim().matches("UBAHPESAN \\w+ (\\w+#\\d+,)*\\w+#\\d+")){
            cek = true;
        }else if(isiSms.trim().matches("HAPUSPESAN \\w+ (\\w+#\\d+,)*\\w+#\\d+")){
            cek = true;
        }
        else if(isiSms.trim().matches("TAMBAHPESAN \\w+ (\\w+#\\d+,)*\\w+#\\d+")){
            cek = true;
        }
        else if(isiSms.matches("CEKSTATUS (\\w+|\\d+)*")){
            cek = true;
        }
        else if(isiSms.matches("BATAL (\\w+|\\d+)*")){
            cek = true;
        }
        else if(isiSms.matches("KONFIRMASI (\\w+|\\d+)*")){
            cek = true;
        }                       
        else if(isiSms.matches("REG (\\w|\\s|\\W)*")){
            cek = true;
        }        
        else cek = false;
        
        return cek;
    }
    
    String kdPlg= "", nmPlg="", alamat="";
    public boolean cekPelanggan(String nohp){
        sql = "SELECT kd_pelanggan,nama_pelanggan,no_hp,alamat FROM pelanggan WHERE no_hp ='"+nohp+"'";
        try { 
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
           
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
            
            if (row > 0) {
                 kdPlg = rs.getString("kd_pelanggan");
                 nmPlg = rs.getString("nama_pelanggan");
                 nohp = rs.getString("no_hp");
                 alamat = rs.getString("alamat");
                 System.out.println("No : "+nohp+" Kode : "+kdPlg+" Nama : "+nmPlg);
                 return true;
                
            } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    String kdKue="", nmKue= "", harga= "", stok="",berat="";
    public boolean CekKue(String kdKue){
        String sql = "SELECT * FROM kue WHERE kd_kue='"+kdKue+"';";
        System.out.println("");
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {                
                kdKue = rs.getString("kd_kue");
                nmKue = rs.getString("nama_kue");
                harga = rs.getString("harga");
                stok = rs.getString("stok");
                berat = rs.getString("berat");
    
                return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean CekKue2(String kdKue){
        String sql = "SELECT kd_kue,nama_kue,harga,stok FROM kue WHERE kd_kue='"+kdKue+"';";
        System.out.println("");
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {                
                kdKue = rs.getString("kd_kue");
                nmKue = rs.getString("nama_kue");
                harga = rs.getString("harga");
                stok = rs.getString("stok");
                
                return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
public boolean CekKueAll(){
        String sql = "SELECT * FROM kue";
        System.out.println("");
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {                
                kdKue = rs.getString("kd_kue");
                nmKue = rs.getString("nama_kue");
                harga = rs.getString("harga");
                stok = rs.getString("stok");
                berat = rs.getString("berat");
    
                return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }    
    
    
    String kdPesan="", tglPesan= "",status="", jml="";
    public boolean CekPesanan(String kdpsn){
        String sql = "SELECT * FROM pesan WHERE kd_pesan='"+kdpsn+"';";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {   
               kdPesan = rs.getString("kd_pesan");               
                kdPlg = rs.getString("kd_pelanggan");
                kdKue = rs.getString("kd_kue");
                tglPesan = rs.getString("tgl_pesan");
                jml = rs.getString("jml_kue");
                status = rs.getString("status");
                harga = rs.getString("harga_satuan");
                
             return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean CekPesananDetil(String kdpsn, String kdkue){
        System.out.println("kode Pesan: " + kdpsn + " kode kue: " + kdkue +"masuk CekPesananDetil");
        String sql = "SELECT b.kd_pesan, b.kd_kue ,a.tgl_pesan, a.status, b.jml_kue, b.total, c.harga, c.stok FROM pesan a, detil_pesan b, kue c WHERE b.kd_pesan=a.kd_pesan and b.kd_kue=c.kd_kue and b.kd_pesan='"+kdpsn+"';";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {   
                kdPesan = rs.getString("kd_pesan"); 
                tglPesan = rs.getString("tgl_pesan"); 
                harga = rs.getString("harga");
                kdKue = rs.getString("kd_kue");
                status = rs.getString("status");
                stok = rs.getString("stok");
                jml = rs.getString("jml_kue");
                total = rs.getString("total");
                
                
             return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    public boolean CekPesananDetil1(String kdpsn, String kdkue){
        System.out.println("kode Pesan: " + kdpsn + " kode kue: " + kdkue +"masuk CekPesananDetil");
        String sql = "SELECT b.kd_pesan, b.kd_kue ,a.tgl_pesan, a.status, b.jml_kue, b.total, c.harga, c.stok FROM pesan a, detil_pesan b, kue c WHERE b.kd_pesan=a.kd_pesan and b.kd_kue=c.kd_kue and b.kd_kue='"+kdkue+"' and b.kd_pesan='"+kdpsn+"';";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {   
                kdPesan = rs.getString("kd_pesan"); 
                tglPesan = rs.getString("tgl_pesan"); 
                harga = rs.getString("harga");
                kdKue = rs.getString("kd_kue");
                status = rs.getString("status");
                stok = rs.getString("stok");
                jml = rs.getString("jml_kue");
                total = rs.getString("total");
                
                
             return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean CekPesananPelanggan(String kdplg){
        String sql = "SELECT * FROM pesan WHERE kd_pelanggan='"+kdplg+"';";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {   
               kdPesan = rs.getString("kd_pesan");               
                kdPlg = rs.getString("kd_pelanggan");
                kdKue = rs.getString("kd_kue");
                tglPesan = rs.getString("tgl_pesan");
                jml = rs.getString("jml_kue");
                status = rs.getString("status");
                harga = rs.getString("harga_satuan");
                
             return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean CekDetilPesan(String kdpsn){
        String sql = "SELECT * FROM detil_pesan WHERE kd_pesan='"+kdpsn+"';";
        try {
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {   
                kdPesan = rs.getString("kd_pesan");
                kdKue = rs.getString("kd_kue");
                jml = rs.getString("jml_kue");
                harga = rs.getString("harga_satuan");
                total = rs.getString("total");
                
             return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean CekPengirimPesan(String nohp, String kdpsn){
        String SQL = "SELECT status " +
                     "FROM pesan a, pelanggan b " +
                     "WHERE a.kd_pelanggan = b.kd_pelanggan AND b.no_hp='"+nohp+"' " +
                     "AND a.kd_pesan='"+kdpsn+"'";
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();   
            ResultSet rs       = st.executeQuery(SQL);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {   
                
             return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    
     String total="";
    public boolean CekJumlahDetil(String kdpsn){
        String sql = "SELECT SUM(total)AS total FROM detil_pesan WHERE kd_pesan='"+kdpsn+"';";
        try {
             Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
                        
            if (row > 0) {   
                total = rs.getString("total");
             return true;
             } else {
                return false;
            }           
            
        } catch (Exception e){
            return false;
        }
    }
    
    
    public boolean CekwaktuTgl(String tgl){
        sql = "select * from pesan "+
               "where tgl_pesan = '"+tgl+"'";
        System.out.println(sql);
        boolean cek = false;
        try{
            Koneksi konek = new Koneksi();
            Connection con = konek.bukaKoneksi();
            Statement st = con.createStatement();
            ResultSet rs       = st.executeQuery(sql);
            rs.last();
            int row            = rs.getRow();
            
            if(row > 0){
                kdPesan = rs.getString("kd_pesan");
                cek = true;
            }else{
                cek = false;
            }
            return cek;
        }catch(Exception e){
            return false;
        }
    }
    
    public String getKdPesan(){
        return kdPesan;
    } 
    
     public String getJmlPesan(){
        return jml;
    } 
    
     public String getStok(){
        return stok;
    } 
    
     public String getkdKue(){
        return kdKue;
    } 
     
    public String getnmKue(){
        return nmKue;
    }

    public String getHargaSatuan(){
        return harga;
    }
    
    public String getTglPesan(){
        return tglPesan;
    } 

     public String getBerat(){
        return berat;
    }
     
    public String getTotal(){
        return total;
    } 
    
    public String getKodePelanggan(){
        return kdPlg;
    }
   
    public String getNamaPelanggan(){
        return nmPlg;
    }
    
    public String getAlamat(){
        return alamat;
    }
    
    public String getStatusPesan(){
        return status;
    }
    
}

