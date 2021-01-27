import br.com.javamon.convert.StringConvert;
import report.ReportException;

public class Main {

	
	public static void main(String[] args) throws ReportException{
		String s = "aaaa aaaaaa aaaaaaaa a aaaaaaaaa a aaa aaaaaaaaaaaaaa aaaaaaaa aaaaaa aaa aaaaaaaaaaa aaaaaaa a aaaaaaaaa aaa";
		
		System.out.println(StringConvert.addBreakRowOnEndLine(s, 20));
	}
	
	
}
