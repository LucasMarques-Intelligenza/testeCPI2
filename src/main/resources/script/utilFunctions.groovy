import com.sap.it.api.mapping.*;
import java.util.TimeZone;  

def String getProperty(String propertyName, MappingContext context) {
    def propertyValue = context.getProperty(propertyName);
    return propertyValue;
}

def String getDate(String arg1){
    tz = TimeZone.getTimeZone("America/Sao_Paulo")
    
    Date date = new Date();
    String datePart = date.format("dd/MM/yyyy", timezone=tz);
    
	return datePart;
}

def String getTime(String arg1){
    tz = TimeZone.getTimeZone("America/Sao_Paulo")
    
    Date date = new Date();
    String timePart = date.format("HH:mm", timezone=tz);
    
	return timePart;
}
