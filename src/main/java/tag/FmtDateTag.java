package tag;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;

public class FmtDateTag extends SimpleTagSupport {

	private LocalDate value;	
	
	private String locale;
	
	public void setValue(LocalDate value) {
		this.value = value;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public StringWriter StringWriter = new StringWriter();
	
	@Override
	public void doTag() throws JspException, IOException {
		if (value != null) {
			JspWriter out = getJspContext().getOut();
			try {
				String parsedDate = DateConvert.parseLocalDateToString(value, "dd/MM/yyyy", new Locale(locale));
				out.println(parsedDate);
			} catch (ConvertException e) {
				e.printStackTrace();
			}
		}
	}
}
