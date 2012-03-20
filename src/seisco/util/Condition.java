package seisco.util;

/**
 * <p>Représente une condition ayant un nom et une valeur.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 * @param <T> le type de la valeur contenue dans la condition
 */
public class Condition<T> extends Champ {

    /**
     * <p>Instancie une nouvelle condition en la nommant et en l'initialisant
     * 
     * @param nom le nom de la condition
     * @param valeur la valeur qu'elle contient
     * @since 2008
     * @see Champ#Champ(java.lang.String, java.lang.Object) 
     */
    public Condition(String nom, T valeur) {
        super(nom, valeur);
    }
	
    /**
     * <p>Teste si la condition est identique à l'argument
     * 
     * @param obj l'objet avec lequel vérifier l'égalité
     * @return
     *  <p> <b>true</b> si <code>obj</code> a le même nom que la condition
     *  <p> <b>false</b> dans le cas contraire ou si <code>obj</code>
     *      n'est pas initialisé ou une instance de {@link Condition}
     * @since 2012
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Condition)
            return super.equals((Condition) obj);

        return false;
    }
    
    /**
     * <p>Retourne la représentation en {@link String} de la condition
     * 
     * @return le nom et la valeur de la condition sous forme de {@link String}
     * @since 2012
     * @see Champ#toString() 
     */
    @Override
    public String toString() {
        return "Condition" + super.toString();
    }
}
