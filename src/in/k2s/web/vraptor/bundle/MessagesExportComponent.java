package in.k2s.web.vraptor.bundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import br.com.caelum.vraptor.events.VRaptorInitialized;

@ApplicationScoped
public class MessagesExportComponent {
	
	@Inject
	private ServletContext context;
	
	public void observesBootstrap(@Observes VRaptorInitialized event) {
		list();
    }
	
	public void list() {
		String template = "{'key': '%s', 'value': '%s'}";
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		Enumeration<String> enumeration = bundle.getKeys();
		StringBuilder messages = new StringBuilder();
		messages.append("var applicationMessagesi18n = [");
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			messages.append(String.format(template, key, bundle.getString(key).replace("\n", "<br>")));
			if(enumeration.hasMoreElements()) messages.append(",");
		}
		messages.append("]");
		try {
			createI18nJavascriptFile(messages.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createI18nJavascriptFile(String content) throws IOException {
		// Ensures that the file is at the root of WebContent
		File root = new File(this.context.getRealPath("/resource/"));

		File dir = new File(root.getCanonicalPath() + File.separator + "js" + File.separator + "i18n" + File.separator);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		if (!dir.exists()) {
			throw new IOException("Could not create the destination folder of i18n javascript files.");
		}

		File js = new File(root.getCanonicalPath() + File.separator + "js" + File.separator + "i18n" + File.separator + "messages.js");

		// Delete and create.
		if (js.exists()) {
			js.delete();
		}
		js.createNewFile();

		String charset = this.context.getInitParameter("br.com.caelum.vraptor.encoding");

		if (charset == null || charset.isEmpty()) {
			charset = "UTF-8";
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(js), charset));

		bw.write(content);
		bw.close();
	}
	
}