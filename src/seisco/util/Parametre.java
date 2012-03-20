package seisco.util;

/**
 * <p>Représente un paramètre ayant un nom et une valeur.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 * @param <T> le type de la valeur contenue dans le paramètre
 */
public class Parametre<T> extends Champ {

    /**
     * <p>Instancie un nouveau paramètre en le nommant et en l'initialisant
     * 
     * @param nom le nom du paramètre
     * @param valeur la valeur qu'il contient
     * @since 2008
     * @see Champ#Champ(java.lang.String, java.lang.Object) 
     */
    public Parametre(String nom, T valeur) {
        super(nom, valeur);
    }
	
    /**
     * <p>Teste si le paramètre est identique à l'argument
     * 
     * @param obj l'objet avec lequel vérifier l'égalité
     * @return
     *  <p> <b>true</b> si <code>obj</code> a le même nom que le paramètre
     *  <p> <b>false</b> dans le cas contraire ou si <code>obj</code>
     *      n'est pas initialisé ou une instance de {@link Parametre}
     * @since 2008
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Parametre)
            return super.equals((Parametre) obj);

        return false;
    }
    
    /**
     * <p>Retourne la représentation en {@link String} du paramètre
     * 
     * @return le nom et la valeur du paramètre sous forme de {@link String}
     * @since 2012
     * @see Champ#toString() 
     */
    @Override
    public String toString() {
        return "Parametre" + super.toString();
    }
}
