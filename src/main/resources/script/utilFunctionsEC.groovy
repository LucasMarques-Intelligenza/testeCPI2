import com.sap.it.api.mapping.*;

def String getEmail(String person, String emailType){
    
    String email = "";
    Node root = new XmlParser().parseText(person);

    root.email_information.each {
        if (it.email_type.text() == emailType) {
            email = it.email_address.text();
        }
    }
    
	return email; 
}

def String getPhone(String person, String phoneType){
    
    String phone = "";
    Node root = new XmlParser().parseText(person);

    root.phone_information.each {
        if (it.phone_type.text() == phoneType) {
            phone = it.phone_number.text();
        }
    }
    
	return phone; 
}

def String getAreaCode(String person, String phoneType){
    
    String areaCode = "";
    Node root = new XmlParser().parseText(person);

    root.phone_information.each {
        if (it.phone_type.text() == phoneType) {
            areaCode = it.area_code.text();
        }
    }
    
	return areaCode; 
}

def String getMatriculaADP(String person_id_external){
    
    String matricula_adp = "";
	ArrayList values = person_id_external.split("-");
	
	if (values.size() > 1) {
		matricula_adp = values[1];
	}
    
	return matricula_adp; 
	
}

def String getOnlyNumbers(String input){
      
	return input.replaceAll("[^0-9]", ''); 
	
}

def String getRegempInfadic(String company, String globalId, String grade, String UPN, String email, String jobTitle){
    
    StringBuffer iadreg = new StringBuffer(); 
    
    //infadic01
    iadreg.append(globalId);
    
    //infadic02
    iadreg.append("|" + grade);

    //infadic03
	if (UPN && UPN.contains("@paccar.com")) {
        iadreg.append("|" + UPN);
	} else {
        iadreg.append("|");	    
	}
	
	//infadic04
	if ((company == "DAF" && email.contains("@daftrucks")) || (company == "PACCAR" && email.contains("@paccar"))) {
        iadreg.append("|" + email);
	} else {
        iadreg.append("|");	    
	}
	
	//infadic05
    iadreg.append("|" + jobTitle);
	
	return iadreg;
}
