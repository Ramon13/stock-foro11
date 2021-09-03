package domain;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtil {

	public DateUtil() {
		// TODO Auto-generated constructor stub
	}

	public static LocalDate firstDayOfCurrentYear() {
		return LocalDate.of(LocalDate.now().getYear(), 1, 1);
	}
	
	public static LocalDate firstDayOfPreviousYear() {
		return LocalDate.of(LocalDate.now().minusYears(1).getYear(), 1, 1);
	}
	
	public static LocalDate today() {
		return LocalDate.now();
	}
	
	public static LocalDate firstDayOfYear(int year) {
		return LocalDate.of(year, 1, 1);
	}
	
	/**
	 * Gets the names of last months
	 * @param monthsNum The number of months
	 * @return An String array with the names
 	 */
	public static String[] lastMonthsNames(int monthsNum, Locale language){
		String[] names = new String[monthsNum];
		LocalDate date = LocalDate.now().withDayOfMonth(1);
		
		for (int i = 0; i < monthsNum; i++) {
			date = date.minusMonths(1);
			names[i] = date.getMonth().getDisplayName(TextStyle.SHORT, language);
		}
		
		return names;
	}
	
	public static boolean isBetweenOrEqual(LocalDate date, LocalDate startDate, LocalDate endDate) {
		return date.isEqual(startDate) || date.isEqual(endDate) || (date.isAfter(startDate) && date.isBefore(endDate));
	}
	
	/**
	 * Checks if comparissonDate is before date
	 */
	public static boolean isPrevious(LocalDate date, LocalDate comparissonDate) {
		return date.isBefore(comparissonDate) || date.isEqual(comparissonDate);
	}
}
