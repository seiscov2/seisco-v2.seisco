package seisco.util;

/**
 * <p>Représente un état ayant un nom et une valeur.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 * @param <T> le type de la valeur contenue dans l'état
 */
public class Etat<T> extends Champ {

    /**
     * <p>Instancie un nouvel état en le nommant et en l'initialisant
     * 
     * @param nom le nom de l'état
     * @param valeur la valeur qu'il contient
     * @since 2008
     * @see Champ#Champ(java.lang.String, java.lang.Object) 
     */
    public Etat(String nom, T valeur) {
        super(nom, valeur);
    }
	
    /**
     * <p>Teste si l'état est identique à l'argument
     * 
     * @param obj l'objet avec lequel vérifier l'égalité
     * @return
     *  <p> <b>true</b> si <code>obj</code> a le même nom que l'état
     *  <p> <b>false</b> dans le cas contraire ou si <code>obj</code>
     *      n'est pas initialisé ou une instance de {@link Etat}
     * @since 2012
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Etat)
            return super.equals((Etat) obj);

        return false;
    }
    
    /**
     * <p>Retourne la représentation en {@link String} de l'état
     * 
     * @return le nom et la valeur de l'état sous forme de {@link String}
     * @since 2012
     * @see Champ#toString() 
     */
    @Override
    public String toString() {
        return "Etat" + super.toString();
    }
}