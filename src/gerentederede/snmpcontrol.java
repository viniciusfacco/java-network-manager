/*
 * Classe SNMP Control
 * Utilizada para as requisições SNMP
*/


package gerentederede;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class snmpcontrol {

    public static final int mSNMPVersion = 0; // 0 represents SNMP version=1
    //inicio MIB II
    public static final int SNMP_PORT = 161;
    public static final String READ_COMMUNITY = "public";
    public static final String WRITE_COMMUNITY = "private";
    public static final String OID_SYS_NAME = "1.3.6.1.2.1.1.5.0";    
    public static final String OID_SYS_LOCATION = "1.3.6.1.2.1.1.6.0";
    public static final String OID_IF_IN_OCTETS = "1.3.6.1.2.1.2.2.1.10";
    public static final String OID_IF_OUT_OCTETS = "1.3.6.1.2.1.2.2.1.16";
    public static final String OID_IF_SPEED = "1.3.6.1.2.1.2.2.1.5";
    public static final String OID_IF_NUMBER = "1.3.6.1.2.1.2.1.0";
    public static final String OID_IF_INDEX = "1.3.6.1.2.1.2.2.1.1";
    public static final String OID_IF_DESCR = "1.3.6.1.2.1.2.2.1.2";
    //fim MIB II
    //inicio MIB trabalho
    public static final int SNMP_PORT2 = 8001;
    public static final String READ_COMMUNITY2 = "public";
    public static final String WRITE_COMMUNITY2 = "public";
    public static final String OID_OS_NAME = ".1.3.6.1.4.1.3126.1.1.0";
    public static final String OID_MSN_STATUS = ".1.3.6.1.4.1.3126.1.3.0";
    public static final String OID_CONT_PROCESS = ".1.3.6.1.4.1.3126.1.4.0";
    public static final String OID_PRO_NAME = ".1.3.6.1.4.1.3126.1.5.0";
    //fim MIB trabalho
    //número da interface a ser monitorada
    private String NUMBER = "0";

//inicio metodos MIB trabalho
    public String get_os_name(String ip) {
        //registra_log(ip, "get", "OID_OS_NAME: " + OID_OS_NAME);//log
        return snmpGet(ip, READ_COMMUNITY2, OID_OS_NAME, SNMP_PORT2);
    }

    public String get_msn_status(String ip) {
        //registra_log(ip, "get", "OID_MSN_STATUS: " + OID_MSN_STATUS);//log
        return snmpGet(ip, READ_COMMUNITY2, OID_MSN_STATUS, SNMP_PORT2);
    }

    public String get_cont_process(String ip) {
        //registra_log(ip, "get", "OID_CONT_PROCESS: " + OID_CONT_PROCESS);//log
        return snmpGet(ip, READ_COMMUNITY2, OID_CONT_PROCESS, SNMP_PORT2);
    }

    public String get_pro_name(String ip) {
        //registra_log(ip, "get", "OID_PRO_NAME: " + OID_PRO_NAME);//log
        return snmpGet(ip, READ_COMMUNITY2, OID_PRO_NAME, SNMP_PORT2);
    }

    public void set_pro_name(String ip, String proname){
        //registra_log(ip, "set", "OID_PRO_NAME: " + OID_PRO_NAME);//log
        snmpSet(ip, WRITE_COMMUNITY2, OID_PRO_NAME, proname, SNMP_PORT2);
    }
//fim metodos MIB trabalho

//inicio metodos MIB II
    public String getnext_ifindex(String ip) {
        //registra_log(ip, "get", "OID_IF_NUMBER: " + OID_IF_NUMBER);//log
        String OID = OID_IF_INDEX + "." + NUMBER;
        System.out.println("OID: " + OID);
        String nind = snmpGetNext(ip, READ_COMMUNITY, OID, SNMP_PORT);
        System.out.println("nind: " + nind);
        return nind;
    }
    
    public String getnext_ifnumber(String ip) {
        //registra_log(ip, "get", "OID_IF_NUMBER: " + OID_IF_NUMBER);//log
        //String nind = snmpGetNext(ip, READ_COMMUNITY, OID_IF_NUMBER, SNMP_PORT);
        String nind = snmpGetNext(ip, READ_COMMUNITY, OID_IF_NUMBER, SNMP_PORT);
        System.out.println("nind: " + nind);
        return nind;
    }

    public int get_nint(String ip) {//numero de interfaces
        //registra_log(ip, "get", "OID_IF_NUMBER: " + OID_IF_NUMBER);//log
        String nint = snmpGet(ip, READ_COMMUNITY, OID_IF_NUMBER, SNMP_PORT);
        return Integer.parseInt(nint);
    }

    public String get_ifdesc(String ip) {
        //registra_log(ip, "get", "OID_SYS_NAME: " + OID_SYS_NAME);//log
        String OID = OID_IF_DESCR + "." + NUMBER;
        return snmpGet(ip, READ_COMMUNITY, OID, SNMP_PORT);
    }

    public String get_name(String ip) {
        //registra_log(ip, "get", "OID_SYS_NAME: " + OID_SYS_NAME);//log
        return snmpGet(ip, READ_COMMUNITY, OID_SYS_NAME, SNMP_PORT);
    }

    public long get_iio(String ip){//ifinoctets
        long retorno;
        String OID = OID_IF_IN_OCTETS+"." + NUMBER;
        //registra_log(ip, "get", "OID_IF_IN_OCTETS: " + OID);//log
        String iio = snmpGet(ip, READ_COMMUNITY, OID, SNMP_PORT);
        if (iio.equals("")){
            retorno = 0;
        } else {
            retorno = Long.parseLong(iio.substring(1));
        }
        return retorno;
    }

    public long get_ioo(String ip){//ifoutoctets
        long retorno;
            //registra_log(ip, "get", "OID_IF_NUMBER: " + OID_IF_NUMBER);//log
            //String ifnumber = snmpGet(ip, READ_COMMUNITY, OID_IF_NUMBER, SNMP_PORT);
        String OID = OID_IF_OUT_OCTETS+"." + NUMBER;
        registra_log(ip, "get", "OID_IF_OUT_OCTETS: " + OID);//log
        String ioo = snmpGet(ip, READ_COMMUNITY, OID, SNMP_PORT);
        if (ioo.equals("")){
            retorno = 0;
        } else {
            retorno = Long.parseLong(ioo.substring(1));
        }
        return retorno;
    }

    public long get_is(String ip){//ifspeed
        long retorno;
            //registra_log(ip, "get", "OID_IF_NUMBER: " + OID_IF_NUMBER);//log
            //String ifnumber = snmpGet(ip, READ_COMMUNITY, OID_IF_NUMBER, SNMP_PORT);
        String OID = OID_IF_SPEED+"." + NUMBER;
        registra_log(ip, "get", "OID_IF_SPEED: " + OID);//log
        String is = snmpGet(ip, READ_COMMUNITY, OID, SNMP_PORT);
        if (is.equals("")){
            retorno = 0;
        } else {
            retorno = Long.parseLong(is.substring(1));
        }
        return retorno;
    }
//fim metodos MIB II
//inicio metodos principais
    public void snmpSet(String strAddress, String community, String strOID, String Value, int Port){

        strAddress= strAddress+"/"+Port;
        Address targetAddress = GenericAddress.parse(strAddress);
        Snmp snmp;
        try {
            TransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();

            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(2000);
            target.setVersion(SnmpConstants.version1);

            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(strOID), new OctetString(Value)));
            pdu.setType(PDU.SET);

            ResponseListener listener = new ResponseListener() {
                public void onResponse(ResponseEvent event) {
                    ((Snmp)event.getSource()).cancel(event.getRequest(), this);
                }
            };
            snmp.send(pdu, target, null, listener);
            snmp.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String snmpGet(String strAddress, String community, String strOID, int Port) {
        String str="";
        try {
            strAddress= strAddress+"/"+Port;
            Address targetaddress = new UdpAddress(strAddress);

            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();

            CommunityTarget comtarget = new CommunityTarget();
            comtarget.setCommunity(new OctetString(community));
            comtarget.setVersion(SnmpConstants.version1);
            comtarget.setAddress(targetaddress);
            comtarget.setRetries(2);
            comtarget.setTimeout(2000);

            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(strOID)));
            pdu.setType(PDU.GET);

            Snmp snmp = new Snmp(transport);
            ResponseEvent response = snmp.get(pdu,comtarget);
            if(response.getPeerAddress() != null) {
                if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
                    PDU pduresponse=response.getResponse();
                    str=pduresponse.getVariableBindings().firstElement().toString();
                    if(str.contains("=")) {
                        int len = str.indexOf("=");
                        str=str.substring(len+1, str.length());
                    }
                }
            }
            else {
                //System.out.println("snmpGet TimeOut");
            }
            snmp.close();
        }
        catch(Exception e) { e.printStackTrace(); }
        //System.out.println("Response="+str);
        return str;
    }

    public String snmpGetNext(String strAddress, String community, String strOID, int Port) {
        String str="";
        try {
            strAddress= strAddress+"/"+Port;
            Address targetaddress = new UdpAddress(strAddress);

            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();

            CommunityTarget comtarget = new CommunityTarget();
            comtarget.setCommunity(new OctetString(community));
            comtarget.setVersion(SnmpConstants.version1);
            comtarget.setAddress(targetaddress);
            comtarget.setRetries(2);
            comtarget.setTimeout(2000);

            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(strOID)));
            pdu.setType(PDU.GETNEXT);

            Snmp snmp = new Snmp(transport);
            ResponseEvent response = snmp.get(pdu,comtarget);
            if(response.getPeerAddress() != null) {
                if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
                    PDU pduresponse=response.getResponse();
                    str=pduresponse.getVariableBindings().firstElement().toString();
                    if(str.contains("=")) {
                        int len = str.indexOf("=");
                        str=str.substring(len+1, str.length());
                    }
                }
            }
            else {
                //System.out.println("snmpGet TimeOut");
            }
            snmp.close();
        }
        catch(Exception e) { e.printStackTrace(); }
        //System.out.println("Response="+str);
        return str;
    }
//fim metodos principais
    public void registra_log(String ip, String tipo, String obj){
        try {
            Calendar cal = new GregorianCalendar();
            String hora = Integer.toString(cal.get(Calendar.HOUR))
                    + ":" + Integer.toString(cal.get(Calendar.MINUTE))
                    + ":" + Integer.toString(cal.get(Calendar.SECOND));
            String data = Integer.toString(cal.get(Calendar.DATE))
                    + "/" + Integer.toString(cal.get(Calendar.MONTH)+1)
                    + "/" + Integer.toString(cal.get(Calendar.YEAR));
            FileWriter writer = new FileWriter(new File("c:\\temp\\log.txt"),true);
            PrintWriter saida = new PrintWriter(writer,true);
            saida.println("Data: "+ data +" - Hora: "+ hora +" - IP: "+ ip +" - Tipo: "+ tipo +" - Objeto: "+ obj);
            saida.close();
            writer.close();
        } catch (IOException e) {}
    }

    public void set_number(String n){
        NUMBER = n;
    }
}
