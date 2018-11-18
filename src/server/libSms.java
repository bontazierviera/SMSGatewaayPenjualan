/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import gnu.io.SerialPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEventListener;
import gnu.io.SerialPortEvent;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
// Event Listerner Packages
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// Input Output (I/O) Stream Packages
import java.io.*;
// Utilities Packages
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.TooManyListenersException;
// Utilities untuk Waktu
import java.util.Date;
import java.util.Vector;
// Untilities untuk Pattern
import java.util.regex.Pattern;

/**
 *
 * @author ANDIKA
 */

public class libSms implements SerialPortEventListener{
    Crud crud = new Crud();
    IdGenerator Id = new IdGenerator();
    Smsgateway sms = new Smsgateway();
    Respon librespon = new Respon();
    
    // Deklarasi Variable 
    boolean tungguDelay = new Boolean(true);
    boolean getDelay = false;
    
    String Data;
    String Stop;
    String Parity;
    String Flow;
    int nilaiData = SerialPort.DATABITS_8;
    int nilaiStop = SerialPort.STOPBITS_1;
    int nilaiParity = SerialPort.PARITY_NONE;
    int nilaiFlow = SerialPort.FLOWCONTROL_NONE;
    int nilaiBaud = 9600;
    String portName = "COM4"; 
    SerialPort port = null;
	
    InputStream input = null;
    OutputStream output = null;
    
    int n;
    String buffer;
    String sbuf;
    byte[] bacaBuffer = new byte[100000];
    int akhirBuffer = 0;
    String respon;
    int statusServer = 0;
    int pilih = -1;
    
    /*public static void main(String[] args){
            new Atcommand().cariPort();
    }*/
    
    public libSms(){
        
    }
    
    public javax.swing.DefaultListModel listmodellog = new javax.swing.DefaultListModel();
    
    public java.util.Vector deteksiPort(){
        Enumeration portList        = CommPortIdentifier.getPortIdentifiers();
        java.util.Vector tampung    = new java.util.Vector();
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                tampung.add(portId.getName());
            }
        }
        return tampung;
    }
        
    public void bukaPort(){
        
//        portName = "COM";
        setList("Port yang Anda pilih adalah "+portName, 200);
        System.out.println("Port yang Anda pilih adalah "+portName);
        setList("Server Sedang melakukan pencarian Port", 200);
        System.out.println("Server Sedang melakukan pencarian Port");
        // Mencari daftar port-port yang tersedia
        Enumeration portList = gnu.io.CommPortIdentifier.getPortIdentifiers();
        if (portList.hasMoreElements()){
            setList("Port Ada", 200);
            System.out.println("Port Ada");
        }else{
            javax.swing.JOptionPane.showMessageDialog(null, "Port Kosong");
        }
        
        while (portList.hasMoreElements()) {
          // Mengambil nilai-nilai port yang ditemukan
          gnu.io.CommPortIdentifier portId = (gnu.io.CommPortIdentifier) portList.nextElement();
          // Hanya Port Serial yang diambil
          if (portId.getPortType() == gnu.io.CommPortIdentifier.PORT_SERIAL) {
            // Buka port berdasarkan nama port yang telah ditentukan
            if (portId.getName().equals(portName)) {
                try {
                    port = (SerialPort) portId.open("SMS", 200);
                    // Cetak pesan ke layar
                    setList("Server berhasil membuka Port : " + portName, 200);
                    System.out.println("Server berhasil membuka Port : " + portName);  

                } //Akhir try open port
                catch (PortInUseException piue) {
                  setList("Port : " + portName + " Sedang digunakan", 200);
                  System.out.println("Port : " + portName + " Sedang digunakan");
                  setList("Penyambungan ke Terminal Gagal .........", 200);
                  System.out.println("Penyambungan ke Terminal Gagal .........");
                  setList("Terjadi kesalahan pada : " + piue, 200);
                  System.out.println("Terjadi kesalahan pada : " + piue);
                } //Akhir catch
            }
         }
       }
       
        try {
          output = port.getOutputStream();
          input = port.getInputStream();
        } // Akhir try stream
        catch (IOException ioe) {
          setList("Gagal membuka Stream", 200);
          System.out.println("Gagal membuka Stream");
          setList("Terjadi kesalahan pada : " + ioe, 200);
          System.out.println("Terjadi kesalahan pada : " + ioe);
        } //Akhir catch
         ///*
        // Mengatur Konfigurasi dari Serial Port
        try {
            
            port.setSerialPortParams(nilaiBaud, nilaiData, nilaiStop, nilaiParity);
            port.setFlowControlMode(nilaiFlow);
            // Menerima pemberitahuan jika ada data pada terminal
            port.notifyOnDataAvailable(true);

            // Cetak pesan ke layar
            setList("Server Melakukan Hubungan ke Port : " + portName, 200);
            System.out.println("Server Melakukan Hubungan ke Port : " + portName);
            setList("Server Berhasil Tehubung ke Port : " + portName, 200);
            System.out.println("Server Berhasil Tehubung ke Port : " + portName);
            setList("Server Sedang melakukan Pengaturan Terminal", 200);
            System.out.println("Server Sedang melakukan Pengaturan Terminal");
            setList("Tunggu Sebentar .....", 300);
            System.out.println("Tunggu Sebentar .....");

            // Melakukan pengatur TERMINAL
            kirimAT("AT" + "\15", 200); // Apakah terminal telah siap
            setList("AT" + "\15", 200);// menampilkan di list Apakah terminal telah siap
            kirimAT("AT+CMGF=0" + "\15", 200); // Menetapkan Format PDU Mode
            setList("AT+CMGF=0" + "\15", 200); // menampilkan di list Menetapkan Format PDU Mode
            kirimAT("AT+CSCS=GSM" + "\15", 200); // Menetapkan Encoding
            setList("AT+CSCS=GSM" + "\15", 200); // menampilkan di list Menetapkan Encoding
            kirimAT("AT+CNMI=1,1,2,2,1" + "\15", 200); // Mendengarkan Pesan secara Otomatis
            setList("AT+CNMI=1,1,2,2,1" + "\15", 200); // menampilkan di list Mendengarkan Pesan secara Otomatis
            kirimAT("AT+CMGR=5" + "\15", 200);
            setList("AT+CMGR=5" + "\15", 200);
            kirimAT("AT+CPMS=\"SM\"" + "\15", 200);
            setList("AT+CPMS=\"SM\"" + "\15", 200);
            
        } //Akhir try serial port params
        catch (UnsupportedCommOperationException ucoe) {
            setList("Pengaturan Data Serial Port Gagal", 200);
            System.out.println("Pengaturan Data Serial Port Gagal");
            setList("Kesalahan terjadi pada : " + ucoe, 200);
            System.out.println("Kesalahan terjadi pada : " + ucoe);
        }
        
        // Menambahkan Event Listener pada Serial Port
        try {
            port.addEventListener(this);
        } //Akhir try addEvenListener
        catch (TooManyListenersException tmle) {
            setList("Terjadi kesalahan pada : " + tmle, 200);
            System.out.println("Terjadi kesalahan pada : " + tmle);
        }
    }
    
    String NoBroadcast="",NoOutbox="";
    public void prosesKirimSms(String notelp, String pesan){
        setList("Mengirim Pesan , Harap menunggu ...", 300);
        System.out.println("Mengirim Pesan , Harap menunggu ...");
        try {
            setList("Memproses Pengiriman Pesan", 300);
            System.out.println("Memproses Pengiriman Pesan");
            
            String smsPDU = sms.pduKirimSms(notelp, pesan); 
            
            setList("Pesan = 00"+smsPDU, 300);
            System.out.println("Pesan = 00"+smsPDU);
            
            kirimAT("AT+CMGS="+(smsPDU.length()/2)+"\15", 300);
            setList("AT+CMGS="+(smsPDU.length()/2)+"\15", 300);
            kirimAT("00" + smsPDU, 300);
            setList("00" + smsPDU, 300);
            kirimAT("\032", 300);
            //setList("\032", 300);
            NoOutbox = Id.getIDOutbox();
            crud.insertOutbox(NoOutbox,notelp,pesan);
//            
	    Thread.currentThread().sleep(5000);
        } catch (Exception e){
            e.printStackTrace();
            setList("Error #21: "+e, 300);
            System.out.println("Error #21: "+e);
        }
    }
    
    public void prosesKirimBroadcast(String notelp, String pesan){
        setList("Mengirim Pesan , Harap menunggu ...", 300);
        System.out.println("Mengirim Pesan , Harap menunggu ...");
        try {
            setList("Memproses Pengiriman Pesan", 300);
            System.out.println("Memproses Pengiriman Pesan");
            
            String smsPDU = sms.pduKirimSms(notelp, pesan); 
            
            setList("Pesan = 00"+smsPDU, 300);
            System.out.println("Pesan = 00"+smsPDU);
            
            kirimAT("AT+CMGS="+(smsPDU.length()/2)+"\15", 300);
            setList("AT+CMGS="+(smsPDU.length()/2)+"\15", 300);
            kirimAT("00" + smsPDU, 300);
            setList("00" + smsPDU, 300);
            kirimAT("\032", 300);
            setList("\032", 300);
           // NoBroadcast = Id.getIDBroadcast();
           // crud.insertBroadcast(NoBroadcast,notelp,pesan);
            NoOutbox = Id.getIDOutbox();
            crud.insertOutbox(NoOutbox,notelp,pesan);
//            
	    Thread.currentThread().sleep(5000);
        } catch (Exception e){
            e.printStackTrace();
            setList("Error #21: "+e, 300);
            System.out.println("Error #21: "+e);
        }
    }
    
    private void prosesTerimaSms (int index, String pdu) {
        try {
            // Rubah dari PDU menjadi Text
            
        } catch (Exception e){
            
        }
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
            } // Akhir Try
            catch (IOException e){
                setList("IOException : "+e, 300);
                System.out.println("IOException : "+e);
            }
            
            try {
                tungguDelay.wait(delay);
            } // Akhir Try
            catch (InterruptedException ie){
                getDelay = true;
            } // Akhir Catchs
        } // Akhir synchronized
    } // Akhir Method KirimAT
    
    // Serial Event
    // Serial Port Listener
    /**
     * method serialEvent
     * Keterangan : menerima respon dari terminal
     * @param event = respon yang diterima terminal
     */
    
    // Deklarasi Variable
    int BufferOffset = 0;
    
    // Awal methode serialEvent
    public void serialEvent(SerialPortEvent event){
        try {
            // apabila ada respons dari terminal, lakukan pembacaan
            while((n = input.available()) > 0){
                n = input.read(bacaBuffer, BufferOffset, n);
                BufferOffset += n;
                
                // Jika ada respons "\15" (Line Feed Carriage Return)
                if ((bacaBuffer[BufferOffset - 1]==10)&&(bacaBuffer[BufferOffset - 2]==13)){
                    String buffer = new String(bacaBuffer, 0, BufferOffset - 2);
                    // Berikan ke method terimaAT
                    terimaAT(buffer);
                    BufferOffset = 0;
                } // Akhir if
            } // Akhir While
        } // Akhir Try
        catch (IOException e){
            
        }
    } // Akhir method serialEvent
    
    /**
     * Method terimaAT
     * Keterangan : Memproses respon yang diterima dari terminal
     * @param buffer : respon dari terminal yang telah dibuang karakter CRLF
     */
    
    // Deklarasi variable
    String[] hasil;
    int index, panjangPDU, PDU;
    String respons;
    StringTokenizer st;
    
    // Awal method terimaAT
    String NoInbox="";
    public void terimaAT(String buffer){
        //Menguraikan buffer berdasarkan karakater CRLF
        st = new StringTokenizer(buffer, "\r\n");
        
        while (st.hasMoreTokens()){
            // mengambil token yang ada pada obyek
            respons = st.nextToken();
            // Cetak Respon ke layar
            setList(respons, 500);
            System.out.println(respons);
            
            // Memproses respon yang diterima
            try {
                // Jika Ada Telepon yang Masuk
                if (respons.startsWith("RING")){
                    kirimAT("ATH0"+"\15", 100); // Diputuskan
                } // Akhir if "RING"
                
                // Jika ada Pesan Baru yang masuk
                else if (respons.startsWith("+CMTI:")){
                    Pattern pattern = Pattern.compile(",");
                    hasil = pattern.split(respons.trim());
                    index = Integer.parseInt(hasil[1].trim());
                    setList("Pesan sudah sampai sini", 500);
                    System.out.println("Pesan sudah sampai sini");
                    kirimAT("AT+CMGR="+index+"\15", 500); // Baca Pesan
                    setList("AT+CMGR="+index+"\15", 500);
                    
                } // Akhir if "+CMTI:"
                
                // Jika ada Pesan Baru yang dibaca
                else if (respons.startsWith("+CMGR:")){
                    setList("sudah masuk", 500);
                    System.out.println("sudah masuk +CMGR");
                    PDU = 1;                    
                } // Akhir if "+CMGR:"
                
                // Membaca Pesan inbox yang belum dibaca
                else if (respons.startsWith("+CMGL:")){
                    Pattern pattern = Pattern.compile(":");
                    hasil = pattern.split(respons.trim());
                    pattern = Pattern.compile(",");
                    hasil = pattern.split(hasil[1].trim());
                    index = Integer.parseInt(hasil[0].trim());
                    PDU = 1;
                } // Akhir if "+CMGL:"
                
                else if(PDU == 1){
                    setList("masuk ke sesi pdu == 1", 500);
                    System.out.println("masuk ke sesi pdu == 1");
                    setList("Pesan Baru Diterima", 500);
                    System.out.println("Pesan Baru Diterima"); 
                    
                    setList("Pesan : ", 500);
                    System.out.println("Pesan : "+respons);
                    sms.PduTerimaSms(respons);
                    
                    if (sms.dapatNoHp.endsWith("F")){
                        // Buang karakter "F"
                        sms.dapatNoHp = sms.dapatNoHp.substring(0, sms.dapatNoHp.length() - 1);
                    }
                    
                    setList("Pesan Masuk Dari : " + sms.dapatNoHp + "\n" +" Isi : " + sms.pesan, 500);
                    System.out.println("Pesan Masuk Dari : " + sms.dapatNoHp + "\n" +" Isi : " + sms.pesan);
                    NoInbox = Id.getIDInbox();
                    try {
                        crud.insertInbox(NoInbox,sms.pesan,sms.dapatNoHp);                        
                    } catch (Exception e) {
                        setList("Error : "+e, 500);
                        System.out.println("Error : "+e);
                    } 
                    
                    try {                    
                        Respon librespon = new Respon();    
                        
                        librespon.setNoHp(sms.dapatNoHp);
                        librespon.setIsiPesan(sms.pesan);
                        librespon.prosesPesanMasuk();  
                        
                        if (librespon.SMS.startsWith("BROADCAST Pesan")){
                            System.out.println("Terdeteksi sebagai pesan BROADCAST");                            
                            //crud.insertLog("Broadcast Pesan");
                            Vector penerima = new Vector();
                            penerima = librespon.getSMSBroadcast();
                            
                            for (int x=0; x < penerima.size(); x++) {
                                //crud.insertLog("Kirim Pesan ke : "+penerima.get(x).toString());
                                System.out.println("Kirim Pesan ke :"+penerima.get(x).toString()+" => "+librespon.isiPesan);
                                prosesKirimBroadcast(penerima.get(x).toString(), librespon.isiPesan);                                
                            }
                            //Konfirmasi Ke Pengirim Permintaan Broadcast Pesan
                            //crud.insertLog("Kirim Pesan ke : "+sms.dapatNoHp);
                            prosesKirimBroadcast(sms.dapatNoHp, librespon.SMS); 
                        } else {
                            prosesKirimBroadcast(sms.dapatNoHp, librespon.SMS);   
                            //crud.insertLog("Kirim Pesan ke : "+sms.dapatNoHp);                         
                        }
                    } catch (Exception e) {
                        System.out.println("Respon Error : "+e);
                        //crud.insertLog("Respon Error : "+e);
                        e.printStackTrace();
                    }
                    
                    System.out.println("Selesai Proses Simpan Pesan Masuk Baru");
                    
                    System.out.println("menghapus pesan yang baru masuk");
                    kirimAT("AT+CMGD=" + index + "\15", 1250);
                    System.out.println("pesan berhasil dihapus");
                    PDU = 0;
                }
                
                else{
                }
   
            }
            catch (Exception e) {
                
            } // Akhir While
        }
    } // Akhir metode terimaAT
    
    public void setBaud(String baud){
        nilaiBaud = Integer.parseInt(baud);
        setList("Set Baud Rate : "+baud, 700);
    }
    
    public void setPort(String usingPort){
        portName = usingPort;
        setList("Set Port : "+usingPort, 700);
    }
    
    public void tutupPort(){
        try {
            if (port != null){
                port.close();
                setList("Menutup Port", 300);
            }
        } catch (Exception e) {
            setList("Gagal Menutup Port", 300);
            System.out.println("Gagal Menutup Port !");
        }
    }
    
    public int indexlist = 0;
    public void setList(String isi, int delay){
        listmodellog.add(indexlist, isi);
        try{
            Thread.sleep(delay);
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
        ++indexlist;
    }

}
