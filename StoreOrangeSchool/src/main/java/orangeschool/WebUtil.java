package orangeschool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class WebUtil {
	
	static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	public static String toString(Object _object)
	{
		
		return "";
	}
	public static String GetTime()
    {

    	LocalDate localDate = LocalDate.now(ZoneId.of("GMT+07:00"));
    	LocalTime time = LocalTime.now();
    	LocalDateTime ldt = LocalDateTime.now();
    	String dateandtime = ldt.format(formatter).toString();
    	
    	return dateandtime;
    }
	
	public static Integer GetTimeTo(String _expireTime)
    {
		
		LocalDate expireTime = LocalDate.parse(_expireTime, formatter);
        
		
    	LocalDate ldt = LocalDate.now();
    	Period period = Period.between(ldt,expireTime);
    	Integer days = period.getDays();
    	
    	return days;
    }
	
	
	public static String GetTimeTo(Integer _days)
    {

    	LocalDateTime ldt = LocalDateTime.now();
    	LocalDateTime ret = ldt.plus(_days, ChronoUnit.DAYS);
    	String dateandtime = ret.format(formatter).toString();
    	
    	return dateandtime;
    }
	
	
	public static final String deleteSuccessfull ="Deleted successfully";
	public static final String addSuccessfull ="Added successfully";
	public static final String editSuccessfull ="Edited successfully";
	
	

}
