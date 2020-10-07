package tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class BlobToStringTag extends SimpleTagSupport {

	private Blob value;	
	
	public void setValue(Blob value) {
		this.value = value;
	}
	
	public StringWriter StringWriter = new StringWriter();
	
	@Override
	public void doTag() throws JspException, IOException {
		if (value != null) {
			JspWriter out = getJspContext().getOut();
			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(value.getBinaryStream(), StandardCharsets.ISO_8859_1))){
				StringBuffer sb = new StringBuffer();
				String str;
				while ((str = br.readLine()) != null)
					sb.append(str + System.getProperty("line.separator"));
				
				out.print(sb.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
