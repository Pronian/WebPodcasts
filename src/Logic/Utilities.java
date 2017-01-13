package Logic;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities
{
    public static String DateToPubDate(Date date)
    {
        String pattern = "EEE, dd MMM yyyy HH:mm:ss Z";
        SimpleDateFormat format = new SimpleDateFormat(pattern, new Locale("en_US"));
        return format.format(date);
    }
}
