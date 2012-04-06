package seisco.util;

import java.io.*;
import org.apache.commons.codec.binary.Base64;

/**
 * <p>Classe utilitaire permettant la sérialisation d'objet et de le convertir en chaine
 * de caractères encodée en Base 64.
 * 
 * @author Jerome
 * @since 2012
 */
public class ObjectCodec {
    /**
     * <p>Permet la sérialisation et l'encodage en Base64 d'un objet.
     * 
     * @param obj
     *      L'objet à sérialiser
     * @return Une chaine de caractère représentant l'objet
     * @throws Exception Si il y a une erreur lors de l'écriture dans le flux.
     * @since 2012
     * @see #decode(String)
     * @see #decode(String, Class) 
     */
    public static String encode(Serializable obj) throws Exception {   
        String s = "";
        
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutputStream oStream = new ObjectOutputStream(bStream);
            oStream.writeObject(obj);
            s = Base64.encodeBase64String(bStream.toByteArray());
        } catch(IOException ex) {
            throw new Exception("Impossible d'écrire de le flux de données.");
        }
        
        return s;
    }
    
    /**
     * <p>Décode un objet et le retourne sous le type de <code>clazz</code>
     * @param s
     *      La chaine de caractère à décoder
     * @param clazz
     *      Le type de la classe de retour
     * @return L'objet décodé et désérialisé sous le type de <code>clazz</code>
     * @throws Exception Si il est impossible de lire le flux de données.
     * @see #encode(Serializable) 
     */
    public static <T> T decode(String s, Class<T> clazz) throws Exception {
        T foo = (T)decode(s);
        return foo;
    }
        
    /**
     * <p>Décode un objet à partir d'une chaine de caractère.
     * @param s
     *      La chaine de caractère à décoder
     * @return L'objet décodé et désérialisé
     * @throws Exception Si il est impossible de lire le flux de données.
     * @see #encode(Serializable) 
     */
    public static Object decode(String s) throws Exception {
        Object i = null;
        try {
 
            ByteArrayInputStream bStream = new ByteArrayInputStream(Base64.decodeBase64(s));
            ObjectInputStream iStream = new ObjectInputStream(bStream);
            
            i = iStream.readObject();
            
            iStream.close();
        } catch(IOException ex) {
            throw new Exception("Impossible de lire le flux de données.");
        }
        
        return i;
    }
}
