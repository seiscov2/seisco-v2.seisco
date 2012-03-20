package seisco.util;

import jade.core.AID;
import java.util.List;

/**
 * <p>Interface pour l'inscription d'un AME au DF.
 * 
 * @author Jerome
 * @since 2012
 */
public interface AmeDf {
    public boolean registerDF();
    public List<AID> getAmesDF();
    public boolean deregisterDF();
}
