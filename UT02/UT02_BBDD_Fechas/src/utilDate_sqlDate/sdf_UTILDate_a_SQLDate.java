package utilDate_sqlDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class sdf_UTILDate_a_SQLDate {
	
	public static void main(String[] args) {
		
		java.util.Date fechaDate = new java.util.Date();
		System.out.println("La fecha con java.util.Date: " + fechaDate);
		
		java.sql.Date fechaSQL = new java.sql.Date(fechaDate.getTime());
		System.out.println("La fecha con java.sql.Date: " +fechaSQL);
		
		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("La fecha con java.util.Date con formato 'dd/MM/yyyy': " +formato.format(fechaDate));
		System.out.println("La fecha con java.sql.Date con formato 'dd/MM/yyyy': " +formato.format(fechaSQL));

	}

}
