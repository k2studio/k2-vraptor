package in.k2s.web.vraptor.bundle;

import in.k2s.web.message.Parameter;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageBundle {
	
	ResourceBundle rb;
	
	public MessageBundle() {
		rb = ResourceBundle.getBundle("messages");
	}
	
	public String getMessage(String key) {
		return getMessage(key, new Object[0]);
	}
	
	public String getMessage(String key, Object ... params) {
		String message = rb.getString(key);
		if(params != null && params.length > 0) {
			int i = 0;
			for(Object param : params) {
				message = message.replace("{" + i + "}", ((Parameter)param).getValue());
				i++;
			}
		}
		return message;
	}

}
