import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.*;
import java.text.*;

def Message processData(Message message) {
       
    //Properties
    String p_lastRun = message.getProperty("p_lastRun");
    String p_date_reprocess = message.getProperty("p_date_reprocess");
    String p_userId_reprocess = message.getProperty("p_userId_reprocess");
    
    //Formata os códigos de agência para usar no filtro (Tem que estar separadas por ponto e vírgula e sem aspas)
    if (p_userId_reprocess) {
        def userIds = p_userId_reprocess.split(";");
        
        p_userId_reprocess = "";
        
        for (int i = 0; i < userIds.size(); i++) {
            String userId = "'" + userIds[i] + "'";
            
            if (!p_userId_reprocess) {
                p_userId_reprocess = userId;
            } else {
                p_userId_reprocess += "," + userId;
            }
        }
        
        if (p_userId_reprocess) {
            message.setProperty("p_userId_reprocess", p_userId_reprocess);
        }
    }
    
    //Verifica a data para o processamento (Se o parametro de reprocessamento estiver preenchido, será utilizado ele)
    if (!p_date_reprocess.trim()) {
    
        if (!p_lastRun.trim()){
            message.setProperty("p_date_query", "1900-01-01T00:00:00.000");
        } else {
            message.setProperty("p_date_query", p_lastRun);
        }
    
    } else {
        message.setProperty("p_date_query", p_date_reprocess);
    }
    
    return message;
    
}