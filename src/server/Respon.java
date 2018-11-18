/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import kkpkremes.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import server.Crud;
import server.Validasi;
import server.libSms;
/**
 *
 * @author maria
 */
public class Respon {
    Validasi validasi = new Validasi();
    Crud crud = new Crud();
    IdGenerator Id = new IdGenerator();
    
    String isiPesan="",SMS="",judul="",nohp="",tgl="", kdPlg="", nmPlg="",nmKue="", blz="", Respon="";
    String kdPesan="", harga="", stok="",jml="",NoKomp="",berat="",kdKue="",status="",alamat="",sTot;
    int byr=0,total=0;
    
    Vector SMSBroadcast = new Vector();


public void setNoHp(String nomor){
        System.out.println("set nomor handphone menjadi : "+nomor);
        this.nohp = nomor;
    }

public void setIsiPesan(String msg){
                        //        if(isiPesan.matches("(\\w||\\d|\\W)"))
                        //            this.isiPesan = msg.toUpperCase();
                        //        else
                        //            this.isiPesan = msg.toUpperCase().substring(8);
        this.isiPesan = msg;
        
        System.out.println("set pesan menjadi : "+isiPesan);
    }

public Vector getSMSBroadcast(){
        return SMSBroadcast;
    }

public void prosesPesanMasuk() throws SQLException{
        System.out.println("masuk ke pemrosesan respon");
        // cek apakah nomor handphone sudah terdaftar        
        if (validasi.cekPelanggan(nohp)){
            kdPlg = validasi.getKodePelanggan();
            nmPlg = validasi.getNamaPelanggan();
            alamat = validasi.getAlamat();
            System.out.println("Nomor Sudah Terdaftar");
            if (isiPesan.toUpperCase().startsWith("INFO")){
                judul = "INFO";
                SMS = "Daftar Format:\n"                        
                        + "1.PEMESANAN\n"
                        + "2.CEKALL\n"
                        + "3.CEKKUE<spc>KodePesan\n"                             
                        + "4.CEKPESAN<spc>KodePesan\n"
                        + "5.KONFIRMASI<spc>KodePesan\n"
                        + "6.CEKSTATUS<spc>KodePesan\n"
                        + "7.KOMPLAIN<spc>Isi";
                
            } else if (isiPesan.toUpperCase().startsWith("PEMESANAN")){
                judul = "INFO PEMESANAN";
                SMS = "Format:\n"
                        + "1.PESAN<spc>KdKue#jml\n"     
                        + "2.BATAL<spc>KdPsn\n"         
                        + "3.UBAHPESAN<spc>KdPsn<spc>KdKue#jml\n"    
                        + "4.TAMBAHPESAN<spc>KdPsn<spc>KdKue#jml\n"    
                        + "5.HAPUSPESAN<spc>KdPsn<spc>KdKue#jml";
                
            } else if (isiPesan.toUpperCase().startsWith("PESAN")){
                judul = "PESAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = prosesPesan(isi);
            } else if (isiPesan.toUpperCase().startsWith("CEKSTATUS")) {
                judul = "CEK STATUS PESANAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = ProsesCekStatus(isi);
            } else if (isiPesan.toUpperCase().startsWith("CEKALL")){
                judul = "CEK KUE ALL";
                SMS = ProsesCekKueAll();
            } else if (isiPesan.toUpperCase().startsWith("CEKKUE")){
                judul = "CEK KUE BY KODE";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = ProsesCekKue(isi);
            } else if (isiPesan.toUpperCase().startsWith("CEKPESAN")){
                judul = "CEK PESAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = ProsesCekPesan(isi);
            } else if (isiPesan.toUpperCase().startsWith("KONFIRMASI")) {
                judul = "KONFIRMASI PESANAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = prosesKonfirmasi(isi);
            } else if (isiPesan.toUpperCase().startsWith("KOMPLAIN")) {
                judul = "KOMPLAIN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = prosesKomplain(isi);
            } else if (isiPesan.toUpperCase().startsWith("BATAL")) {
                judul = "BATAL PESAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = prosesBatalPesan(isi);
            } else if (isiPesan.toUpperCase().startsWith("TAMBAHPESAN")){
                judul = "TAMBAH PESAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = prosesTambahPesan(isi);
            } else if (isiPesan.toUpperCase().startsWith("HAPUSPESAN")){
                judul = "HAPUS PESAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = prosesHapusPesan(isi);
            }else if (isiPesan.toUpperCase().startsWith("UBAHPESAN")){
                judul = "UBAH PESAN";
                String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                SMS = prosesUbahPesan(isi);
            } else {
                judul = "SALAH FORMAT";
                SMS = "Maaf Format SMS anda salah, Silahkan ketik : INFO "
                        + "untuk melihat fitur SMS yang tersedia";
            }
        } else if (isiPesan.toUpperCase().startsWith("REG")) {
            System.out.println("Nomor Ingin Mendaftar di Sistem");
            // cek format REGISTRASI
                if (true){
                    judul = "DAFTAR KONTAK BARU";
                    String isi = isiPesan.substring(isiPesan.indexOf(" ")+1);
                    SMS = prosesDaftar(isi);
                } else {
                    judul = "SALAH FORMAT";
                    SMS = "Maaf anda sudah terdaftar sebelumnya. Silahkan ketik : INFO untuk mengetahui Format SMS yang tersedia. Terimakasih";
                }
        } else {
            System.out.println("Nomor Belum Terdaftar di Sistem");
            judul = "BELUM TERDAFTAR";
            SMS = "Maaf, anda blm bs menggunakan program ini, "
                    + "Silahkan Ktk REG<spasi>NAMA#ALAMAT#NOHP "
                    + "Terima Kasih.";
        }
        
        System.out.println("Judul : " + judul + "\nBalasan : " + SMS);
        
        
    }

public String prosesPesan(String isi) {
        // Format Pesan : PESAN<spc>Kode Kue#jumlah
        kdPesan = Id.getIDPesan();
        kdPlg = validasi.getKodePelanggan();
        //tgl = validasi.getWaktuFULL();
        System.out.println(isi.trim());
        String[] kata;
            
        Pattern pt = Pattern.compile(",");
        kata = pt.split(isi.trim());
        
        int jml_pesanan = kata.length;
        
        String[][] data_pesanan = new String[jml_pesanan][2];
        
        for (int i=0; i<jml_pesanan; i++) {
            Pattern pt2 = Pattern.compile("#");
            String[] data = pt2.split(kata[i]);
            
            data_pesanan[i][0] = data[0];
            data_pesanan[i][1] = data[1];
        }
//        crud.insertPesan(kodepesan,Kdplg);
        
        boolean chek=false;
        String kode_kue=null;
        for (int i=0; i<jml_pesanan; i++) {
            String kodekue = data_pesanan[i][0];
            System.out.println(kodekue);
            boolean cek = validasi.CekKue(kodekue);
            if(cek==true){
                chek = true;
            }else{
                chek = false;
               kode_kue = kodekue;
                break;
            }
        }
        
        boolean cestok=false;
        String stok_kue=null;
        for (int i=0; i<jml_pesanan; i++) {
            String Kode_kue = data_pesanan[i][0];
            String jumlah = data_pesanan[i][1];
            System.out.println(Kode_kue);
            System.out.println(jumlah);
            validasi.CekKue(Kode_kue);
            String chekstok = validasi.getStok();
            System.out.println(chekstok);
            if(Integer.parseInt(chekstok)< Integer.parseInt(jumlah)){
                cestok = false;
                stok_kue = Kode_kue;
                break;
            }else{
                cestok = true;
            }
        }
        
        String respon="";
        if(chek==true){
            if(cestok==true){
                int Berat = 0;
                //crud.insertPesan(kdPesan,kdPlg,kdKue, jml,harga);
            for (int i=0; i<jml_pesanan; i++) {
                String kode_Kue = data_pesanan[i][0];
                String jml = data_pesanan[i][1];
                boolean cek = validasi.CekKue(kode_Kue);
                nmKue = validasi.getnmKue();
                harga = validasi.getHargaSatuan();               
                berat = validasi.getBerat();
                berat = berat+(Integer.parseInt(jml)*Integer.parseInt(berat));
                int total = Integer.parseInt(jml)*Integer.parseInt(harga);
                crud.insertPesan(kdPesan,kdPlg,kode_Kue, jml,harga);
                crud.insertDetilPesan(kdPesan, kode_Kue, jml, harga,Integer.toString(total));
                crud.ProsesUpdateStok(kode_Kue, jml);
            }           
            System.out.println(berat);
            validasi.CekJumlahDetil(kdPesan);
            String total =  validasi.getTotal();
            System.out.println(total);

            respon = "Pesanan dgn kode : "+ kdPesan + " Berhasil Dipesan, "
                    + "dengan Total=Rp."+total+"\n"
                    + "Segera Konfirmasi pesanan anda dgn ktk KONFIRMASI<spc>KodePesan";
     
            }else{
                respon = "Maaf Stok kue : " + stok_kue + " tidak ada atau tidak cukup. Pesanan Anda tidak dapat diproses.";
            }
        }else{
                respon = "Maaf Kode barang : " + kode_kue + " tidak ada. Harap masukkan kode barang dengan benar.";
            }  
            
            return respon;   
                      
}




public String prosesTambahPesan(String isi) throws SQLException {
//TAMBAHPESAN<spc>P00001<spc>B0003#2
//kodepesan = Id.getIDPesan();
kdPlg = validasi.getKodePelanggan();
//tgl = validasi.getWaktuFULL()
//JOptionPane.showMessageDialog(null, nohp);
if (!validasi.cekPelanggan(nohp))
        Respon = "Tambah pesanan gagal.\nAnda belum terdaftar menjadi pelanggan kami.";
    //hapus PO00008 B0001#4	
    else if(isiPesan.matches("TAMBAHPESAN \\w+ (\\w+#\\d+,)*\\w+#\\d+"))
{
System.out.println("sudah masuk tambahpesan");    
int total	        = 0;
boolean adaygsama	= false;
boolean berhasil 	= false;
String pesanan[]	= isiPesan.split(" ");
String kdpsn		= pesanan[1];
String pesan[]		= pesanan[2].split(",");
String cekkue[] 	= new String[pesan.length];
String cekjml[] 	= new String[pesan.length];
String bayar		= "";

for(int i=0; i<pesan.length; i++) {
    String kd_stok[]	= pesan[i].split("#");
    cekkue[i] 		= kd_stok[0];
    cekjml[i] 		= kd_stok[1];
}
for(int i=0; i<cekkue.length; i++) {
    for(int j=i+1; j<cekkue.length; j++) {
            if(cekkue[i].equals(cekkue[j]))
                    adaygsama = true;
    }
}
if(validasi.CekPengirimPesan(kdpsn, kdPlg))
    Respon = "Tambah pesanan gagal.\nAnda tidak punya pesanan dengan no pesanan "+kdpsn;

else if(ProsesCekStatus(isi).equalsIgnoreCase("PROSES"))
    Respon = "Tambah pesanan gagal.\nNo pesanan "+kdpsn+" telah diproses sebelumnya.";

else if(ProsesCekStatus(isi).equalsIgnoreCase("BATAL"))
    Respon = "Tambah pesanan gagal.\nNo pesanan "+kdpsn+" sudah pernah dibatalkan sebelumnya.";

else if(ProsesCekStatus(isi).equalsIgnoreCase("Sudah Jatuh Tempo"))
    Respon = "Tambah pesanan gagal.\nNo pesanan "+kdpsn+" sudah lewat jatuh tempo.";

else if(pesan.length>10)
    Respon = "Tambah pesanan gagal.\nPemesanan tidak boleh lebih dari 10 macam barang.";

else if(adaygsama)
    Respon = "Tambah pesanan gagal.\nAda kode barang yang sama.";

else
{
String stok = "";
for(int i=0; i<pesan.length; i++)
{
String kd_q[] 	= pesan[i].split("#");
kdKue 	= kd_q[0];
jml 	= kd_q[1];

System.out.println(kdpsn);
System.out.println("kd pesan " + kdpsn + "kd kue " + kdKue);

if(validasi.CekPesananDetil1(kdpsn, kdKue))
{
Respon 		= "Tambah pesanan gagal.\nKode barang "+kdKue+
                          " sudah ada dalam daftar barang kami.";
berhasil 	= false;
//validasi.CekPesanan(kdpesan);
System.out.println("masuk ke : if(!validasi.CekPesananDetil(kdKue))");
break;
}

if(!validasi.CekPesananDetil1(kdpsn, kdKue))
{
if(validasi.CekKue2(kdKue))
{
stok =  validasi.getStok();
harga = validasi.getHargaSatuan();
nmKue = validasi.getnmKue();
System.out.println("masuk ke : Else cek brg , stok "+stok +"Qty" + jml+"harga brg :"+harga);
if(Integer.parseInt(jml) <= 0)
 {
         Respon 		= "Tambah pesanan gagal.\nQuantity kode barang "+kdKue+
                                   " tidak boleh kurang atau sama dengan dari 0.";
         berhasil 	= false;
         System.out.println("masuk ke : else if(Integer.parseInt(qty) <= 0)");
         break;
 }
 else if(Integer.parseInt(stok) == 0)
 {
         Respon 		= "Tambah pesanan gagal.\nStok kode barang "+kdKue+" ("+nmKue+") habis.";
         berhasil 	= false;
         System.out.println("masuk ke : else if(Integer.parseInt(stok) == 0)");
         break;
 }
 else if(Integer.parseInt(stok) < Integer.parseInt(jml))
 {
         Respon 		= "Tambah pesanan gagal.\nStok kode barang "+kdKue+" ("+nmKue+") tidak cukup.";
         berhasil 	= false;
         crud.aturStok(kdpsn, "-" , kdKue);
         System.out.println("masuk ke : else if(Integer.parseInt(stok) == 0)");
         break;
 } 
 else
 {
     System.out.println("masuk ke else mau byr" );

        total 		= (Integer.parseInt(jml)*Integer.parseInt(harga));
        System.out.println(total);
        byr = byr + total;
        sTot =  "" + total; 
        //bayar = "" + byr;
        Respon 		= Respon +" "+ nmKue+":"+jml+";";
        berhasil 	= true;
        System.out.println(isiPesan);
        //crud.insertPesan(kdpesan, kdbrg);
        crud.insertDetilPesan(kdpsn, kdKue, jml, harga, Integer.toString(total));
        crud.aturStok(kdpsn, "-" , kdKue);
        validasi.CekJumlahDetil(kdpsn);
        bayar = validasi.getTotal();
        System.out.println("Qty"+ jml + " HargaBrg " + harga + "total" + total + "bayar"+ bayar );

 }
}
}
}
JOptionPane.showMessageDialog(null,berhasil);
if(berhasil)
{// String idbrg="";
//idbrg = Id.getIDBarang();
//jumbrg = 
Respon = "Tambah pesanan berhasil."+
        "\nKode pesanan anda : "+kdpsn+
        "\nBarang yang dipesan : "+Respon+
        "\nTotal harga : Rp."+bayar;
//crud.hapusDetilPesan(kdpesan);
System.out.println("kdpesan:"+kdpsn+"Kdbrg:"+kdKue+"Qty:"+jml+"HrgBrg"+harga+"Total"+Integer.toString(total));
}
}
}
    else
    Respon = "Tambah pesanan gagal.\nFormat TAMBAH Pesan salah.";
return Respon;
}

public String prosesHapusPesan(String isi) throws SQLException{
// Format Pesan : UBAHPESAN<spc>KdPesan<spc>KdBrg#jumlah
kdPesan = Id.getIDPesan();
kdPlg = validasi.getKodePelanggan();
//tgl = validasi.getWaktuFULL()
//JOptionPane.showMessageDialog(null, nohp);
if (!validasi.cekPelanggan(nohp))
Respon = "Hapus pesanan gagal.\nAnda belum terdaftar menjadi pelanggan kami.";
//hapus PO00008 B0001#4	
else if(isiPesan.matches("HAPUSPESAN \\w+ (\\w+#\\d+,)*\\w+#\\d+"))
{
int total				= 0;
boolean adaygsama	= false;
boolean berhasil 	= false;
String pesanan[]	= isiPesan.split(" ");
String kdpsn		= pesanan[1];
String pesan[]		= pesanan[2].split(",");
String cekkue[] 	= new String[pesan.length];
String cekjml[] 	= new String[pesan.length];
String bayar		= "";

for(int i=0; i<pesan.length; i++) {
        String kd_stok[]	= pesan[i].split("#");
        cekkue[i] 			= kd_stok[0];
        cekjml[i] 			= kd_stok[1];
}
for(int i=0; i<cekkue.length; i++) {
        for(int j=i+1; j<cekkue.length; j++) {
                if(cekkue[i].equals(cekkue[j]))
                        adaygsama = true;
        }
}

if(validasi.CekPengirimPesan(kdpsn, kdPlg))
        Respon = "Hapus pesanan gagal.\nAnda tidak punya pesanan dengan no pesanan "+kdpsn;

else if(ProsesCekStatus(isi).equalsIgnoreCase("PROSES"))
        Respon = "Hapus pesanan gagal.\nNo pesanan "+kdpsn+" telah diproses sebelumnya.";

else if(ProsesCekStatus(isi).equalsIgnoreCase("BATAL"))
        Respon = "Hapus pesanan gagal.\nNo pesanan "+kdpsn+" sudah pernah dibatalkan sebelumnya.";


else if(pesan.length>10)
        Respon = "Hapus pesanan gagal.\nPemesanan tidak boleh lebih dari 10 macam barang.";

else if(adaygsama)
        Respon = "Hapus pesanan gagal.\nAda kode barang yang sama.";

else
{
        String stok = "";
        for(int i=0; i<pesan.length; i++)
        {
                String kd_q[] 	= pesan[i].split("#");
                kdKue 	= kd_q[0];
                jml 	= kd_q[1];

                System.out.println(kdpsn);
                System.out.println("kdpesan " + kdpsn + "kdbrg " + kdKue);

                if(!validasi.CekPesananDetil1(kdpsn, kdKue))
                {
                        Respon 		= "Hapus pesanan gagal.\nKode barang "+kdKue+
                                                  " tidak ada dalam daftar barang kami.";
                        berhasil 	= false;
                        //validasi.CekPesanan(kdpesan);
                        System.out.println("masuk ke : if(!validasi.CekPesanan(kdbrg))");
                        break;
                }
                else 
                {
                   stok =  validasi.getStok();
                   harga = validasi.getHargaSatuan();
                   nmKue = validasi.getnmKue();
                   System.out.println("masuk ke : Else, stok "+stok +"Qty" + jml+"harga brg :"+harga);
                    if(Integer.parseInt(jml) <= 0)
                     {
                             Respon 		= "Hapus pesanan gagal.\nQuantity kode barang "+kdKue+
                                                       " tidak boleh kurang atau sama dengan dari 0.";
                             berhasil 	= false;
                             System.out.println("masuk ke : else if(Integer.parseInt(qty) <= 0)");
                             break;
                     }
                     else if(Integer.parseInt(stok) == 0)
                     {
                             Respon 		= "Hapus pesanan gagal.\nStok kode barang "+kdKue+" ("+nmKue+") habis.";
                             berhasil 	= false;
                             System.out.println("masuk ke : else if(Integer.parseInt(stok) == 0)");
                             break;
                     }
                     else if(Integer.parseInt(stok) < Integer.parseInt(jml))
                     {
                             Respon 		= "Hapus pesanan gagal.\nStok kode barang "+kdKue+" ("+nmKue+") tidak cukup.";
                             berhasil 	= false;
                             crud.aturStok(kdpsn, "-" , kdKue);
                             System.out.println("masuk ke : else if(Integer.parseInt(stok) == 0)");
                             break;
                     } 
                     else
                     {
                         System.out.println("masuk ke else mau byr" );

                            byr	= byr + (Integer.parseInt(jml)*Integer.parseInt(harga));
                            
                            System.out.println("Qty"+ jml + " HargaBrg " + harga + "Bayar" + byr);
                            bayar = "" + byr;
                            Respon 		= Respon +" "+ nmKue+":"+jml+";";
                            
                            berhasil 	= true;
                     }
                }


        }
    
        JOptionPane.showMessageDialog(null,berhasil);
        if(berhasil)
        {//String idbrg="";
        //idbrg = Id.getIDBarang();
        //jumbrg = 
            
                Respon = "Hapus pesanan berhasil."+
                                "\nKode pesanan anda : "+kdpsn+
                                "\nBarang yang dipesan : "+Respon+
                                "\nTotal harga : Rp."+bayar;
               crud.hapusDetilPesan2(kdpsn, kdKue);
                crud.hapusPesan2(kdpsn, kdKue);
                crud.aturStok(kdpsn, "-" , kdKue); 
        }
}
}
else
Respon = "Hapus pesanan gagal.\nFormat HAPUS salah.";
return Respon;
}

public String prosesUbahPesan(String isi) throws SQLException{
    // Format Pesan : UBAHPESAN<spc>Kode Pesan<spc>Kode Kue#jumlah
        kdPesan = Id.getIDPesan();
        kdPlg = validasi.getKodePelanggan();
        //tgl = validasi.getWaktuFULL()
        //JOptionPane.showMessageDialog(null, nohp);
        if (!validasi.cekPelanggan(nohp))
            Respon = "Ubah pesanan gagal.\nAnda belum terdaftar menjadi pelanggan kami.";
    //ubah PO00008 B0001#4	
    else if(isiPesan.matches("UBAHPESAN \\w+ (\\w+#\\d+,)*\\w+#\\d+"))
    {
    int total   	= 0;
    boolean adaygsama	= false;
    boolean berhasil 	= false;
    String pesanan[]	= isiPesan.split(" ");
    String kdpsn	= pesanan[1];
    String pesan[]	= pesanan[2].split(",");
    String cekkue[] 	= new String[pesan.length];
    String cekjml[] 	= new String[pesan.length];
    String bayar	= "";

    for(int i=0; i<pesan.length; i++) {
            String kd_stok[]    = pesan[i].split("#");
            cekkue[i]           = kd_stok[0];
            cekjml[i] 		= kd_stok[1];
    }
    for(int i=0; i<cekkue.length; i++) {
       for(int j=i+1; j<cekkue.length; j++) {
           if(cekkue[i].equals(cekkue[j]))
            adaygsama = true;
         }
    }

    if(validasi.CekPengirimPesan(kdpsn, kdPlg))
        Respon = "Ubah pesanan gagal.\nAnda tidak punya pesanan dengan no pesanan "+kdpsn;

    else if(ProsesCekStatus(isi).equalsIgnoreCase("PROSES"))
        Respon = "Ubah pesanan gagal.\nNo pesanan "+kdpsn+" telah diproses sebelumnya.";
				
    else if(ProsesCekStatus(isi).equalsIgnoreCase("BATAL"))
        Respon = "Ubah pesanan gagal.\nNo pesanan "+kdpsn+" sudah pernah dibatalkan sebelumnya.";

    else if(pesan.length>10)
        Respon = "Ubah pesanan gagal.\nPemesanan tidak boleh lebih dari 10 macam barang.";

    else if(adaygsama)
        Respon = "Ubah pesanan gagal.\nAda kode barang yang sama.";

    else
{
   // String stok = "";
    for(int i=0; i<pesan.length; i++)
    {
        String kd_q[] 	= pesan[i].split("#");
        kdKue 	= kd_q[0];
        jml 	= kd_q[1];

        System.out.println(kdpsn);
        //crud.aturStok(kdpesan, "+", kdbrg);
        System.out.println("kode pesan: " + kdpsn + " kode kue: " + kdKue);

        if(!validasi.CekPesananDetil(kdpsn, kdKue))
        {
                Respon 		= "Ubah pesanan gagal.\nKode barang "+kdKue+
                                          " tidak ada dalam daftar barang kami.";
                berhasil 	= false;
                crud.aturStok(kdpsn, "-" , kdKue);
                //validasi.CekPesanan(kdpesan);
                System.out.println("masuk ke : if(!validasi.CekPesanan(kdkue))");
                break;
        }
        else 
        {
           stok =  validasi.getStok();
           harga = validasi.getHargaSatuan();
           nmKue = validasi.getnmKue();
           System.out.println("masuk ke : Else, stok:"+stok +" jml: " + jml+" harga satuan :"+harga);
            if(Integer.parseInt(jml) <= 0)
             {
             Respon 		= "Ubah pesanan gagal.\nQuantity kode barang "+kdKue+
                                       " tidak boleh kurang atau sama dengan dari 0.";
             berhasil 	= false;
             crud.aturStok(kdpsn, "-" , kdKue);
             System.out.println("masuk ke : else if(Integer.parseInt(qty) <= 0)");
             break;
     }
     else if(Integer.parseInt(stok) == 0)
     {
             Respon 		= "Ubah pesanan gagal.\nStok kode barang "+kdKue+" ("+nmKue+") habis.";
             berhasil 	= false;
             crud.aturStok(kdpsn, "-" , kdKue);
             System.out.println("masuk ke : else if(Integer.parseInt(stok) == 0)");
             break;
     }
     else if(Integer.parseInt(stok) < Integer.parseInt(jml))
     {
             Respon 		= "Ubah pesanan gagal.\nStok kode barang "+kdKue+" ("+nmKue+") tidak cukup.";
             berhasil 	= false;
             crud.aturStok(kdpsn, "-" , kdKue);
             System.out.println("masuk ke : else if(Integer.parseInt(stok) == 0)");
             break;
     } 
     else
     {
         System.out.println("masuk ke else mau byr" );

            total 		= (Integer.parseInt(jml)*Integer.parseInt(harga));
            System.out.println("jml: "+ jml + " Harga Satuan: " + harga + "Total: " + total);
            sTot = "" + total;
            //bayar = bayar + total;
            Respon 		= Respon +" "+ nmKue+":"+jml+";";
            berhasil 	= true;
            crud.ProsesUpdateDetil(kdpsn, kdKue, jml, harga, Integer.toString(total));
                //crud.insertDetilPesan(kdpesan, kdbrg, Qty, HargaBrg, Integer.toString(total));
            crud.aturStok(kdpsn, "-" , kdKue);
            validasi.CekJumlahDetil(kdpsn);
            bayar = validasi.getTotal();
            System.out.println("Qty"+ jml + " HargaBrg " + harga + "total" + total + "bayar"+ bayar );
     }
}
    }
    JOptionPane.showMessageDialog(null,berhasil);
    if(berhasil)
    {//String idbrg="";
    //idbrg = Id.getIDBarang();
    //jumbrg = 
    Respon = "Ubah pesanan berhasil."+
                    "\nKode pesanan anda : "+kdpsn+
                    "\nBarang yang dipesan "+Respon+
                    "\nTotal harga : Rp."+bayar;
            //crud.hapusDetilPesan(kdpesan);
    }
}
    }
    else
    Respon = "Ubah pesanan gagal.\nFormat UBAH salah.";
        return Respon;
		}
    
private String ProsesCekStatus(String isi) {
        // Format Pesan : CEKSTATUS<SPC>KDPESAN
        System.out.println(isi);
        String[] kata;
            
        Pattern pt = Pattern.compile("#");
        kata = pt.split(isi);
        
        String kdpsn= kata[0];
        System.out.println("Kode Pelanggan : "+kdpsn);
        
        if (validasi.CekPesanan(kdpsn)) {
            if (validasi.CekPengirimPesan(nohp, kdpsn)) {
                if (validasi.getStatusPesan().equals("PESAN")) { // cek apakah status pesanan belum di konfirmasi /di proses
                     validasi.CekPesanan(kdpsn);
                    System.out.println("Berhasil Melakukan Data Pesan : " +kdpsn);
                    blz = "Kode Pesan : " + kdpsn + " belum dikonfirmasi segera konfirmasi pesanan anda. Terimakasih.";
                } else if (validasi.getStatusPesan().equals("BATAL")) {
                    blz = "Kode Pesan : " + kdpsn + " sudah dibatalkan. Terimakasih.";
                } else if(validasi.getStatusPesan().equals("PROSES")) {
                    blz = "Kode Pesan : " + kdpsn + " sudah diproses. Terimakasih.";
                } 
            } else {
                blz = "Anda tidak berhak mengecek status kode pesan " + kdpsn;
            }
        } else {
           blz = "kode Pesan : " +kdpsn + " tidak ada atau salah kode. Harap masukkan kode Pesan Anda dengan benar.";
        }
     
        return blz;
        
    }



private String ProsesCekKue(String isi) {
        // Format Pesan : CEKKUE<SPC>KDKUE
        System.out.println(isi);
        String[] kata;
            
        Pattern pt = Pattern.compile("#");
        kata = pt.split(isi);
        
        String kdkue = kata[0];
        System.out.println("Kode Kue : "+kdkue);
        
        if (validasi.CekKue(kdkue)) {
            blz = "Kode Kue : " + kdkue
                    + ", Nama : " + validasi.getnmKue() + ", Harga : Rp. " + validasi.getHargaSatuan() + ", Stok  : " + validasi.getStok() +", Berat : "+ validasi.getBerat() + "kg.";
        } else {
            blz = "Kode Barang : " + kdkue + " tidak ada. Harap masukkan kode barang dengan benar.";
        }
        return blz;
        
    }

private String ProsesCekPesanAll(String isi) {
        // Format Pesan : CEKPESAN<spasi>Kode Pelanggan
        System.out.println(isi);
        String query="select * from pesan where kd_pelanggan ='"+isi+"";   
       System.out.println(query);
       String data="";
        try{
            java.sql.Connection cn = new Koneksi().bukaKoneksi();
             Statement st = cn.createStatement();

             java.sql.ResultSet rs = st.executeQuery(query);
             while(rs.next()){
                 data =data+ rs.getString(1)+": "+rs.getString(2)+", "+rs.getString(3)+", "+rs.getString(4)+", "+rs.getString(5)+", "+rs.getString(6)+", "+rs.getString(7)+", ";
             }
             st.close(); cn.close();
        }catch (Exception e) {}
        
        if(data.length()>0){
        data="Berikut Daftar Pesan : "+data+".";
        }
        else{
        data="Data Kosong !";
        }
        return data;
        
    }


//private String ProsesCekPesan2(String isi) {
//        // Format Pesan : CEKPESAN<SPC>Kode Pesan
//        System.out.println(isi);
//        String[] kata;
//            
//        Pattern pt = Pattern.compile("#");
//        kata = pt.split(isi);
//        
//        String kdpsn = kata[0];
//        System.out.println("Kode Pesan : "+kdpsn);
//        
//        if (validasi.CekDetilPesan(kdpsn)) {
//            blz = "Kode Pesan :"+kdpsn+" Dengan Kode Kue: " + validasi.getkdKue()+", Jumlah  : " + validasi.getJmlPesan() +", Harga : Rp. " + validasi.getHargaSatuan()+", Total : Rp. "+validasi.getTotal();
//        } else {
//            blz = "Kode Pesan : " + kdpsn + " tidak ada. Harap masukkan kode pelanggan dengan benar.";
//        }
//        return blz;
//        
//    }

private String ProsesCekPesan(String isi) {
        // Format Pesan : CEKPESAN<SPC>Kode Pesan
        System.out.println(isi);
        String[] kata;
            
        Pattern pt = Pattern.compile("#");
        kata = pt.split(isi);
        
        String kdpsn = kata[0];
        System.out.println("Kode Pesan : "+kdpsn);
        
        String query="SELECT * FROM detil_pesan WHERE kd_pesan='"+kdpsn+"';";   
       //System.out.println(query);
       String data="";
        try{
            java.sql.Connection cn = new Koneksi().bukaKoneksi();
             Statement st = cn.createStatement();

             java.sql.ResultSet rs = st.executeQuery(query);
             while(rs.next()){
                 data =data+rs.getString(1)+", "+rs.getString(2)+", "+rs.getString(3)+", Harga:"+rs.getString(4)+", Total:"+rs.getString(5)+"\n";
             }
             st.close(); cn.close();
        }catch (Exception e) {}
        
        if(data.length()>0){
        data=data;
        }
        else{
        data="Data Kosong !";
        }
        return data;
        
    }
//
//private String ProsesCekAll(String isi) {
//        // Format Pesan : CEKALL
//        System.out.println(isi);
//               
//        if (validasi.CekKueAll()) {
//            blz = "Kode: " + validasi.getkdKue()
//                    + ",Nama: " + validasi.getnmKue() + ",Harga:Rp." + validasi.getHargaSatuan() + ",Stok: " + validasi.getStok() +",Berat: "+ validasi.getBerat() + "kg.";
//        } else {
//            blz = "data kosong";
//        }
//        return blz;
//        
//    }

private String ProsesCekKueAll() {
        // Format Pesan : CEKALL
        String query="select * from kue";   
       //System.out.println(query);
       String data="";
        try{
            java.sql.Connection cn = new Koneksi().bukaKoneksi();
             Statement st = cn.createStatement();

             java.sql.ResultSet rs = st.executeQuery(query);
             while(rs.next()){
                 data =data +rs.getString(1)+", "+rs.getString(2)+", Rp"+rs.getString(3)+", "+rs.getString(4)+", "+rs.getString(5)+"kg\n";
             }
             st.close(); cn.close();
        }catch (Exception e) {}
        
        if(data.length()>0){
        data="Berikut Daftar Kue : "+data;
        }
        else{
        data="Data Kosong !";
        }
        return data;
        
    }

public String prosesDaftar(String isi) throws SQLException{
        // Format Pesan : REG<SPC>NMPLG#ALAMAT#NoHp
        blz = " ";
        kdPlg = Id.getIDPelanggan();
        System.out.println(isi);
        String[] kata;
            
        Pattern pt = Pattern.compile("#");
        kata = pt.split(isi);
        
        
        String nmplg = kata[0];
        System.out.println("Nama  : "+nmplg);
        String alamat = kata[1];
        System.out.println("Alamat : "+alamat);

        crud.insertPelanggan(kdPlg, nmplg, nohp, alamat);
        crud.DetilPelanggan(nohp);
        System.out.println("Berhasil Melakukan Registrasi untuk nomor : "+nohp);
        
        blz = "Terima Kasih "+ nmplg +" anda sudah terdaftar di sistem kami, ketik INFO utuk mengetahui format SMS yang tersedia.";
   
        return blz;
    }
    private String hapusPelanggan() {
        // Format Pesan : UNREG    
        
        crud.DeleteKontak(nohp);
        System.out.println("Berhasil Menghapus Kontak");
        
        String respon = "Terima Kasih, Saat ini anda sudah tidak terdaftar di Sistem Kami. \n"
                + "Silahkan Ketik REG<SPC>NAMA#ALAMAT#NoHP untuk Mendaftar Kembali.";
        
        return respon;
    } 

     
    public String prosesBatalPesan(String isi) throws SQLException {
        // Format Pesan : BATAL<SPC>KODEPESAN
        blz = " ";
        kdPesan = Id.getIDPesan();
        System.out.println(isi);
        String[] kata;
            
        Pattern pt = Pattern.compile("#");
        kata = pt.split(isi);
        
        //String id_pelanggan = kata[0];
        //System.out.println("Kode Pelanggan : "+id_pelanggan);
        String kdpsn = kata[0];
        System.out.println("Kode Pesan : "+kdpsn);
        
        if (validasi.CekPesanan(kdpsn)) {
            if (validasi.CekPengirimPesan(nohp, kdpsn)) {
                if (validasi.getStatusPesan().equals("PESAN")) { // cek apakah status pesanan belum di konfirmasi /di proses
                    crud.ProsesBatal(kdpsn);
                    crud.hapusPesan(kdpsn);
                    crud.hapusDetilPesan(kdpsn);
                    System.out.println("Berhasil Melakukan Pembatalan Pesanan : " +kdpsn);
                    blz = "Pesanan Dengan Kode Pesan : "+ kdpsn + " Berhasil Dibatalkan, "
                        + "Terima Kasih.";
                } else if (validasi.getStatusPesan().equals("BATAL")) {
                    blz = "Kode Pesan : " + kdpsn + " tidak dapat dibatalkan karena anda sudah membatalkan sebelumnya. Terimakasih";
                } else if(validasi.getStatusPesan().equals("PROSES")) {
                    blz = "Kode Pesan : " + kdpsn + " tidak bisa dibatalkan karena sudah diproses. Silakan hub. Kami di 081298160559.";
                } 
            } else {
                blz = "Anda tidak berhak membatalkan kode pesan " + kdpsn;
            }
        } else {
           blz = "kode Pesan : " + kdpsn + " tidak ada atau salah kode. Harap masukkan kode Pesan Anda dengan benar.";
        }
     
        return blz;
    }
    
    public String prosesKonfirmasi(String isi) throws SQLException {
        // Format Pesan : KONFIRMASI<SPC>KODEPESAN
        blz = " ";
        kdPesan = Id.getIDPesan();
        System.out.println(isi);
        String[] kata;
            
        Pattern pt = Pattern.compile("#");
        kata = pt.split(isi);
        
        //String id_pelanggan = kata[0];
        //System.out.println("Kode Pelanggan : "+id_pelanggan);
        String kdpsn = kata[0];
        System.out.println("Kode Pesan : "+kdpsn);
        
        if (validasi.CekPesanan(kdpsn)) {
            if (validasi.CekPengirimPesan(nohp, kdpsn)) {
                if (validasi.getStatusPesan().equals("PESAN")) { // cek apakah status pesanan belum di konfirmasi /di proses
                    crud.KonfirmasiPesan(kdpsn);
                    System.out.println("Berhasil Melakukan Pembatalan Pesanan : " +kdpsn);
                    blz = "Pesanan Dengan Kode Pesan : "+ kdpsn + " Berhasil Dikonfirmasi, "
                        + "Terima Kasih.";
                } else if (validasi.getStatusPesan().equals("BATAL")) {
                    blz = "Kode Pesan : " + kdpsn + " tidak dapat dibatalkan. Terimakasih";
                } else if(validasi.getStatusPesan().equals("PROSES")) {
                    blz = "Kode pesan : " + kdpsn + " tidak dapat dkonfirmasi, karena anda sudah konfirmasi sebelumnya.";
                } 
            } else {
                blz = "Anda tidak berhak mengkonfirmasi kode pesan " + kdpsn;
            }
        } else {
           blz = "kode Pesan : " + kdpsn + " tidak ada atau salah kode. Harap masukkan kode Pesan Anda dengan benar.";
        }
     
        return blz;
    }
    
    private String prosesKomplain(String isi) {
        // Format Pesan : KOMPLAIN<SPC>ISIKOMPLAIN
        Respon = " ";
        kdPlg = validasi.getKodePelanggan();
        NoKomp = Id.getIDKomplain();
        System.out.println(isi);
        String[] kata;
            
        Pattern pt = Pattern.compile("#");
        kata = pt.split(isi);
        
        String komplain = kata[0];
        System.out.println("Isi Komplain: "+komplain);
        
        crud.insertKomplain(NoKomp,kdPlg,nohp,komplain);
        System.out.println("Berhasil Melakukan Penambahan Komplain: " +komplain);
        
        Respon = "Komplain anda telah diterima. Terima kasih atas perhatian anda.";
        
        return Respon;
    }
    
    
    
    
}