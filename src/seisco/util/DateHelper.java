package seisco.util;

import java.util.Date;

/**
 * <p>Classe utilitaire permettant de formater les dates
 * 
 * @author Jerome
 * @version 2012
 */
public class DateHelper {
    /**
     * <p>Permet de formater des millisecondes en hh:mm:ss
     * 
     * @param milliSeconds
     *      Le nombre de millisecondes à formater
     * 
     * @return Le temps en format hh:mm:ss 
     */
    public static String formatMillisecondes(long milliSeconds) {
        int hours = (int)(milliSeconds / (1000 * 60 * 60));
        int minutes = (int)((milliSeconds % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int)(((milliSeconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000);
        String format = ((hours < 10) ? "0" : "") + hours + ":" + ((minutes < 10) ? "0" : "") + minutes + ":" + ((seconds < 10) ? "0" : "") + seconds;
        return format;
    }

    /**
     * <p>Permet de formater des millisecondes en hh:mm:ss
     * 
     * @param start
     *      La date de début du laps de temps
     * @param end
     *      La date de fin du laps de temps
     *      
     * @return Le temps en format hh:mm:ss 
     */
    public static String formatMillisecondes(Date start, Date end) {
        return formatMillisecondes(end.getTime() - start.getTime());
    }
}
