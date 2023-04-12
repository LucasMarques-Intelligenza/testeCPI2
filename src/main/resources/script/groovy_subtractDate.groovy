import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

//Subtrai 1 dia da data effectiveStartDate, para buscar o registro anterior no EC
//A data de saída será no formato yyyy-MM-dd, para ser utilizada no fromDate e toDate
def Message processData(Message message) {
    
    String inputDate = message.getProperty("p_effectiveStartDate");
    
    SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd");
    
    Calendar c = Calendar.getInstance();
    c.setTime(sdfIn.parse(inputDate));
    c.add(Calendar.DATE, -1);
    
    String outputDate = sdfOut.format(c.getTime());
    
    message.setProperty("p_date_lastRecord", outputDate);
	
	return message;
}