import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.*;
import java.text.*;

def Message processData(Message message) {
       
    String p_adp_file = message.getProperty("p_adp_file");
    String p_fileName;
    
    if (p_adp_file == "CAD_AGENCIA") {
        p_fileName = "CAD_AGENCIA.xml";
    } else {
        p_fileName = "ALT_AGENCIA.xml";
    }
    
    message.setProperty("p_fileName", p_fileName);
    
    return message;
    
}