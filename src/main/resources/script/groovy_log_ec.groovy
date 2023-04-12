import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.*;
import java.text.*;

def Message processData(Message message) {
       
    //Properties
    def map              = message.getProperties();
    def p_process_type   = map.get("p_processType");
    def p_Error_Message  = map.get("p_Error_Message");
	def p_SAP_MessagePr  = map.get("SAP_MessageProcessingLogID");
    
    //Valores Dummy
    String p_process_descr;
    String p_body_ADP     = "";    
    String p_fileName_log = "payload.txt";
    String p_fileType_log = "txt";
    String p_mimeType_log = "text/plain";
    
    //Data e hora atuais no formato
    Date date = new Date();
    String data = date.format("dd/MM/yyyy"); 
    String hora = date.format("HH:mm:ss");
    
    if (p_process_type == 'START') {
        p_process_descr = 'Início -  Integração Cadastro Bancos e Agencias - EC_ADP: ' + ' - Data: ' + data + ' - Hora: ' + hora;

        
    } else if (p_process_type == 'FAILED') {
        p_process_descr = 'Erro no processamento. Detalhe: ' + p_Error_Message;
    
        
    } else if (p_process_type == 'LOG') {
        p_body_ADP      = map.get("p_adp_xml").bytes.encodeBase64().toString();
        p_fileName_log  = map.get("p_fileName");
    	p_fileType_log  = map.get("p_fileType_log");
    	p_mimeType_log  = map.get("p_mimeType_log");
		p_process_descr = 'Arquivo: '.concat(p_fileName_log).concat(' - enviado para a ADP com sucesso!');
		p_process_type  = 'END';
    
    } else if (p_process_type == 'END'){
	    p_process_descr = 'Fim -  Integração Cadastro Bancos e Agencias - EC_ADP: ' + ' - Data: ' + data + ' - Hora: ' + hora;
	    
    }
  
    //Prepara retorno do body
    df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    data = df.format(new Date()).substring(0,19);
    
    String body_log = '''
    <EMEvent>
    	<EMEvent>
    		<id>1</id>
    		<eventType>''' + p_process_type + '''</eventType>
    		<eventTime>''' + data + '''</eventTime>
    		<eventName>Integration Execution Status Messages</eventName>
    		<eventDescription>''' + p_process_descr + '''</eventDescription>
    		<process>
    			<EMMonitoredProcess>
    				<processType>INTEGRATION</processType>
    				<processInstanceName>Integração Cadastro de Bancos e Agencias - EC_ADP ''' + data + '''</processInstanceName>
    				<processInstanceId>''' + p_SAP_MessagePr + '''</processInstanceId>
    				<processDefinitionName>Integração Cadastro de Bancos e Agencias - EC_ADP</processDefinitionName>
    				<processDefinitionId>integrationBancosAgencias</processDefinitionId>
    				<moduleName>ECT</moduleName>
    				<firstEventTime>''' + data + '''</firstEventTime>
    				<lastEventTime>''' + data + '''</lastEventTime>
    				<monitoredProcessId>1</monitoredProcessId>
    			</EMMonitoredProcess>
    		</process>
    		<eventPayload>
    			<EMEventPayload>
    			    <fileName>''' + p_fileName_log + '''</fileName>
				    <fileType>''' + p_fileType_log + '''</fileType>
                    <mimeType>''' + p_mimeType_log + '''</mimeType>
    				<type>OTHER</type>
    				<payload>''' + p_body_ADP + '''</payload>
    				<id>1234</id>
    			</EMEventPayload>
    		</eventPayload>
    	</EMEvent>
    </EMEvent>'''
  
    message.setBody(body_log);
    message.setProperty("p_processType", '');
    
    return message;
    
}