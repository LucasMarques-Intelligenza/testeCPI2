import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.util.Date;   
import java.util.TimeZone;

def Message processData(Message message) {
    
    String p_layouts = message.getProperty("p_layouts");
    String p_seqNum = message.getProperty("p_seqNum");
    String p_fileName = message.getProperty("p_fileName");
    
    //Cria o nome para o arquivo
    String dateNow = new Date().format("yyyyMMddHHmmss", timezone = TimeZone.getTimeZone("America/Sao_Paulo"));
    String fileName = dateNow + '_' + p_fileName + '_' + p_seqNum + ".xml";
    
    message.setProperty("p_fileName", fileName);
    
    //Filtra os registros dos layouts pelo campo "tarefa" (Cada layout tem um nome de tarefa específico)
    if (p_layouts) {
        def layouts = p_layouts.split(";")
        def body = new XmlParser().parseText(message.getBody(java.lang.String));
        
        body.record.each{
            record ->
                if(layouts.findIndexOf{ it == record.tarefa.text() } < 0) {
                    def parent = record.parent();
                    parent.remove(record);
                } else {
                    record.nreqid[0].value = p_seqNum; //Atualiza o número sequencial único do arquivo
                }
        }
        
        message.setBody(groovy.xml.XmlUtil.serialize(body));
    }

    return message;
}