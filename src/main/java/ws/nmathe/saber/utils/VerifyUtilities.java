package ws.nmathe.saber.utils;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * static methods used to verify user input for the verify() method of commands
 */
public class VerifyUtilities
{
    public static boolean verifyTime(String userInput)
    {
        Matcher matcher = Pattern.compile("\\d+").matcher(userInput);

        // relative time
        if(userInput.matches("\\d+[mM][iI][nN]$"))
        {
            return true;
        }
        else if(userInput.matches("\\d(\\d)?(:\\d\\d)?([aApP][mM])"))// absolute time with period indicator
        {
            if(userInput.contains(":"))
            {
                if(!matcher.find()) return false;
                if(Integer.parseInt(matcher.group()) > 12) return false;
                if(!matcher.find()) return false;
                if(Integer.parseInt(matcher.group()) > 59) return false;
            }
            else
            {
                if(!matcher.find()) return false;
                if(Integer.parseInt(matcher.group()) > 12) return false;
            }
        }
        else if(userInput.matches("\\d(\\d)?(:\\d\\d)?")) // absolute time with no period indicator
        {
            if(userInput.contains(":"))
            {
                if(userInput.equalsIgnoreCase("24:00")) return true;

                if(!matcher.find()) return false;
                if(Integer.parseInt(matcher.group()) > 23) return false;
                if(!matcher.find()) return false;
                if(Integer.parseInt(matcher.group()) > 59) return false;
            }
            else
            {
                if(!matcher.find()) return false;
                if(Integer.parseInt(matcher.group()) > 23) return false;
            }
        }
        else // fails to match either absolute or relative times
        {
            return false;
        }
        return true;
    }

    public static boolean verifyDate( String arg )
    {
        if(arg.toLowerCase().equals("tomorrow") || arg.toLowerCase().equals("today"))
            return true;

        try
        {
            String[] splt = arg.split("[^0-9]+");
            if(splt.length == 1)
            {
                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), Integer.parseInt(splt[0]));
            }
            else if(splt.length == 2)
            {
                LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(splt[0]), Integer.parseInt(splt[1]));
            }
            else if(splt.length == 3)
            {
                LocalDate.of(Integer.parseInt(splt[0]), Integer.parseInt(splt[1]), Integer.parseInt(splt[2]));
            }
            else
            {
                return false;
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean verifyHex(String arg)
    {
        try
        {
            Integer.decode("0x"+arg);
        }
        catch(Exception e)
        {
            return false;
        }

        return true;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean verifyInteger(String arg)
    {
        try
        {
            Integer.parseInt(arg);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public static boolean verifyUrl(String arg)
    {
        try
        {
            (new URL(arg)).getContent();
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public static boolean verifyZone(String arg)
    {
        for(String validZone : ZoneId.getAvailableZoneIds())
        {
            if(validZone.equalsIgnoreCase(arg))
            {
                return true;
            }
        }
        return false;
    }
}
