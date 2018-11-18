/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import kkpkremes.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author maria
 */
public class IdGenerator {
    private Connection cn;
    private Statement st;
    private int i, h;
    private String kd, j;
    
    public String getIDPesan () {
        try {
            Koneksi konek = new Koneksi();
            Connection cn = konek.bukaKoneksi();
            Statement st = cn.createStatement();
            String SQL  = "SELECT kd_pesan FROM pesan ORDER BY kd_pesan DESC";
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                kd  = rs.getString(1);
                kd  = kd.substring(1);
                i   = Integer.parseInt(kd)+1;
                j   = String.valueOf(i);
                h   = j.length();
                switch (h) {
                    case 1  : kd = "PS0000" + j; break;
                    case 2  : kd = "PS000" + j; break;
                    case 3  : kd = "PS00" + j; break;
                    case 4  : kd = "PS0" + j; break;
                    case 5  : kd = "PS" + j; break;
                }
            } else {
                kd = "PS00001";
            }
            rs.close(); st.close(); cn.close();
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        return kd;
    }
    
    public String getIDPelanggan() {
        try {
            Koneksi konek = new Koneksi();
            Connection cn = konek.bukaKoneksi();
            Statement st = cn.createStatement();
            String SQL  = "SELECT kd_pelanggan FROM pelanggan ORDER BY kd_pelanggan DESC";
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                kd  = rs.getString(1);
                kd  = kd.substring(1);
                i   = Integer.parseInt(kd)+1;
                j   = String.valueOf(i);
                h   = j.length();
                switch (h) {
                    case 1  : kd = "P000" + j; break;
                    case 2  : kd = "P00" + j; break;
                    case 3  : kd = "P0" + j; break;
                    case 4  : kd = "P" + j; break;
                  
                }
            } else {
                kd = "P0001";
            }
            rs.close(); st.close(); cn.close();
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        return kd;
    }
    
     public String getIDKomplain() {
        try {
            Koneksi konek = new Koneksi();
            Connection cn = konek.bukaKoneksi();
            Statement st = cn.createStatement();
            String SQL  = "SELECT no_komplain FROM komplain ORDER BY no_komplain DESC";
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                kd  = rs.getString(1);
                kd  = kd.substring(1);
                i   = Integer.parseInt(kd)+1;
                j   = String.valueOf(i);
                h   = j.length();
                switch (h) {
                    case 1  : kd = "K0000" + j; break;
                    case 2  : kd = "K000" + j; break;
                    case 3  : kd = "K00" + j; break;
                    case 4  : kd = "K0" + j; break;
                    case 5  : kd = "K" + j; break;
                }
            } else {
                kd = "K00001";
            }
            rs.close(); st.close(); cn.close();
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        return kd;
    }
    
    public String getIDBroadcast() {
        try {
            
            Koneksi konek = new Koneksi();
            Connection cn = konek.bukaKoneksi();
            Statement st = cn.createStatement();
            String SQL  = "SELECT no_broadcast FROM broadcast ORDER BY no_broadcast DESC";
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                kd  = rs.getString(1);
                kd  = kd.substring(1);
                i   = Integer.parseInt(kd)+1;
                j   = String.valueOf(i);
                h   = j.length();
                switch (h) {
                    case 1  : kd = "B0000" + j; break;
                    case 2  : kd = "B000" + j; break;
                    case 3  : kd = "B00" + j; break;
                    case 4  : kd = "B0" + j; break;
                    case 5  : kd = "B" + j; break;
                }
            } else {
                kd = "B00001";
            }
            rs.close(); st.close(); cn.close();
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        return kd;
    }
    
    public String getIDOutbox() {
        try {
            Koneksi konek = new Koneksi();
            Connection cn = konek.bukaKoneksi();
            Statement st = cn.createStatement();
            String SQL  = "SELECT no_outbox FROM outbox ORDER BY no_outbox DESC";
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                kd  = rs.getString(1);
                kd  = kd.substring(1);
                i   = Integer.parseInt(kd)+1;
                j   = String.valueOf(i);
                h   = j.length();
                switch (h) {
                    case 1  : kd = "O0000" + j; break;
                    case 2  : kd = "O000" + j; break;
                    case 3  : kd = "O00" + j; break;
                    case 4  : kd = "O0" + j; break;
                    case 5  : kd = "O" + j; break;
                }
            } else {
                kd = "O00001";
            }
            rs.close(); st.close(); cn.close();
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        return kd;
    }
    
    public String getIDInbox() {
        try {
            Koneksi konek = new Koneksi();
            Connection cn = konek.bukaKoneksi();
            Statement st = cn.createStatement();
            String SQL  = "SELECT no_inbox FROM inbox ORDER BY no_inbox DESC";
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                kd  = rs.getString(1);
                kd  = kd.substring(1);
                i   = Integer.parseInt(kd)+1;
                j   = String.valueOf(i);
                h   = j.length();
                switch (h) {
                    case 1  : kd = "I0000" + j; break;
                    case 2  : kd = "I000" + j; break;
                    case 3  : kd = "I00" + j; break;
                    case 4  : kd = "I0" + j; break;
                    case 5  : kd = "I" + j; break;
                }
            } else {
                kd = "I00001";
            }
            rs.close(); st.close(); cn.close();
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        return kd;
    }
    
}
