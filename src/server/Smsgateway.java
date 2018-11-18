/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * 
 */
public class Smsgateway {

    /**
     * @param args the command line arguments
     */
    
    static char[] asciiToGsmMap;
    static char[] gsmToAsciiMap;
    
    InputStream input;
    OutputStream output;
    
    // variable untuk method pduKirimSms()
    static StringBuffer pesanPDUKirim = null;
    static int panjangNotlpTujuan = 0;
    static int panjangPesanKirim = 0;
    static String PduPesan = null;
    
    static {
        final int lastindex = 255;
        gsmToAsciiMap = new char[lastindex+1];
        asciiToGsmMap = new char[lastindex+1];
        
        for (int i = 0; i <= lastindex; i++) {
            gsmToAsciiMap[i] = asciiToGsmMap[i] = (char) i;
        }
    }
    
//    public static void main(String[] args) {
//         //new frmpesan().setVisible(true);
//        Smsgateway sms = new Smsgateway();
//        System.out.println(sms.pduKirimSms("6285284801388", "Hello"));
//        //System.out.println(sms.balikKarakter("6285284801388"));
//        
//    }
    
    public void prosesKirimSms(String notelp, String pesan){
        try {
            System.out.println("Pesan ke :"+notelp);
            System.out.println("Isi Pesan :"+pesan);
            
            
        } catch (Exception e) {
            System.out.println("Error #1 :"+e);
        }
    }
    
    public static String dectohexa(int dec){        
        char[] karakter;
        try{
            char[] hexa = {
              '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'  
            };
            karakter = new char[2];
            
          //  dec = dec & 255;
            karakter[0] = hexa[dec/16];
            karakter[1] = hexa[dec%16];
            return new String(karakter);            
        }
        catch(Exception e){
            return ""+e;
        }
    }
    
    public static String balikKarakter(String text){
       int textlen = text.length();
       StringBuffer stringbuffer = new StringBuffer(textlen);
        for (int i = 0; (i+1) < textlen; i=i+2) {
            stringbuffer.append(text.charAt(i+1));
            stringbuffer.append(text.charAt(i));
        }
        
       return new String(stringbuffer);
    }
    
    // method untuk mengubah 7 bit (septet) ke 8 bit (oktet)
    public static String septetToOktet(String pesan){
        
        StringBuffer msg = new StringBuffer(pesan);
        StringBuffer encmsg = new StringBuffer(2 * 160);
        
        int bb = 0, bblen = 0, i;
        char o = 0, c     = 0, tc;
        
        for (i = 0; i < msg.length()|| bblen >=8; i++) {
            if (i< msg.length()) {
                c = msg.charAt(i);
                tc = asciiToGsmMap[c];
                
                c = tc;
                
                c &= ~(1 << 7);
                bb |= (c << bblen);
                bblen += 7;
            }
            
            while (bblen >=8){
                o = (char) (bb & 255);
                encmsg.append(dectohexa(o));
                bb >>>= 8;
                bblen -= 8;
            }
        }
        
        if (bblen > 0){
            encmsg.append(dectohexa(bb));
        }
        return encmsg.toString();
    }
    
    public static String pduKirimSms(String notelp, String pesan){
        
        pesanPDUKirim = new StringBuffer(320);
        // Nilai PDU Type   --> Default = 11
        pesanPDUKirim.append("11");
        // Nilai MR         --> Default = 00
        pesanPDUKirim.append("00");
        // Nilai Panjang Nomor Pengirim
        panjangNotlpTujuan = notelp.length();
        pesanPDUKirim.append(dectohexa(panjangNotlpTujuan));
        // Nilai Format No.Telepon --> Format International = 91
        pesanPDUKirim.append("91");
        // Nilai nomor telepon pengirim
        // Jika panjang notelp adalah ganjil
        //notelp = (notelp.length()%2==1)?balikKarakter(notelp+"F"):balikKarakter(notelp); // menggunakan ternirary condition
        if (notelp.length() % 2 == 1){
            notelp = balikKarakter(notelp+"F");
        }
        // Jika panjang notelp adalah genap
        else {
            notelp = balikKarakter(notelp);
        }
        pesanPDUKirim.append(notelp);
        // Nilai PID        --> Default = 00
        pesanPDUKirim.append("00");
        // Nilai DCS        --> Default = 00
        pesanPDUKirim.append("00");
        // Nilai VP = 4 hari --> AA h
        pesanPDUKirim.append("AA");
        // Nilai UDL
        panjangPesanKirim = pesan.length();
        // Nilai UD
        PduPesan = septetToOktet(pesan);
        pesanPDUKirim.append(dectohexa(panjangPesanKirim));       
        pesanPDUKirim.append(PduPesan);
                
        return new String(pesanPDUKirim);        
    }
    
     public void kirimAT(String atCmd, int delay){
        Boolean tungguDelay = new Boolean(true);
        boolean getDelay = false;
        // Membuat Antrian proses
        synchronized (tungguDelay){
            try {
                // Menulis AT Command                
                output.write((atCmd).getBytes());
                // Cetak Respon ke layar                
                output.flush();
            } 
            catch (IOException e){                
            }
            
            try {
                tungguDelay.wait(delay);
            } // Akhir Try
            catch (InterruptedException ie){
                getDelay = true;
            } // Akhir Catchs
        } // Akhir synchronized
    }
    
    String infoSMSC = null;
    int nilaiSMSC = 0;
    int nomorSMSC = 0;
    String panjangNohp = null;
    int nilaiPanjangNoHp = 0;
    int nilaiNoHp = 0;
    String notelp;
    String dapatNoHp;
    String panjangPesan = null;
    int nilaiPanjangPesan = 0;
    String pesanPDU = null;
    String pesan = null;
    
    public void PduTerimaSms(String smspdu) {
    	int i = 0;
    	try {
      		// Mengambil nilai panjang informasi SMSC
      		infoSMSC = smspdu.substring(i, 2);
      		nilaiSMSC = Integer.parseInt(infoSMSC, 16);
      		// format nomor dan nomor MSC dibuang
      		i = i + 4;
      		nomorSMSC = i + (nilaiSMSC * 2) - 2;
      		// Nilai PDU Type dibuang
      		i = nomorSMSC + 2;
      		// Mengambil Panjang Nomor Telepon Pengirim
      		panjangNohp = smspdu.substring(i, i + 2);
      		nilaiPanjangNoHp = Integer.parseInt(panjangNohp, 16);
      		// format nomor pengirim dibuang
      		i = i + 4;
      		nilaiNoHp = i + nilaiPanjangNoHp + nilaiPanjangNoHp % 2;
      		// Nomor telepon pengirim
      		notelp = smspdu.substring(i, nilaiNoHp);
      		dapatNoHp = balikKarakter(notelp);
      		i = nilaiNoHp;
      		// Nilai PID, DCS, dan SCTS dibuang
      		i = i + 18;
      		// Mengambil Panjang Pesan SMS
      		panjangPesan = smspdu.substring(i, i + 2);
      		nilaiPanjangPesan = Integer.parseInt(panjangPesan, 16);
      		i = i + 2;
      		pesanPDU = smspdu.substring(i, smspdu.length());
      		pesan = oktetToSeptet(pesanPDU, nilaiPanjangPesan);
    	}
    	catch (Exception e) {}
    } 
    
    public static String oktetToSeptet(String pesan, int msglen) {
    	int i, o, r = 0, rlen = 0, olen = 0, charcnt = 0;
    	StringBuffer msg = new StringBuffer(160);
    	int pesanlen = pesan.length();
    	String ostr;
    	char c;

    	// pengulangan hingga nilai terpenuhi
    	// i + 1 < pesanlen dan charcnt < msglen
    	for (i = 0; ( (i + 1) < pesanlen) && (charcnt < msglen); i = i + 2) {
      		// mengambil dua digit Hexadesimal
      		ostr = pesan.substring(i, i + 2);
      		o = Integer.parseInt(ostr, 16);
      		// berikan nilai olen = 8
      		olen = 8;

      		// geser posisi semua bit ke kiri sebanyak rlen bit
      		o <<= rlen;
      		o |= r; // berikan sisa bit dari o ke r
      		olen += rlen; // olen = olen + rlen

      		c = (char) (o & 127); // mendapatkan nilai o menjadi 7 bit
      		o >>>= 7; // geser posis bit ke kanan sebanyak 7 bit
      		olen -= 7;

      		r = o; // menaruh sisa bit dari o ke r.
      		rlen = olen;

      		c = gsmToAsciiMap[c]; // rubah ke Text (kode ASCII)
      		msg.append(c); // tambahkan ke msg
      		charcnt++; // nilai charcnt ditambahkan 1

      		// jika rlen >= 7
      		if (rlen >= 7) {
        		c = (char) (r & 127);
        		r >>>= 7;
        		rlen -= 7;
        		msg.append(c);
        		charcnt++;
      		}
    	} // Akhir for
    	if ( (rlen > 0) && (charcnt < msglen)) {
      		msg.append( (char) r);
    	}
    	return msg.toString();
    }
    
}
