package seisco.util;

/**
 * <p>Représente un critère ayant un nom et une valeur.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 * @param <T> le type de la valeur contenue dans le critère
 */
public class Critere<T> extends Champ {

    /**
     * <p>Instancie un nouveau critère en le nommant et en l'initialisant
     * 
     * @param nom le nom du 
     * @param valeur la valeur qu'il contient
     * @since 2008
     * @see Champ#Champ(java.lang.String, java.lang.Object) 
     */
    public Critere(String nom, T valeur) {
        super(nom, valeur);
    }
	
    /**
     * <p>Teste si le critère est identique à l'argument
     * 
     * @param obj l'objet avec lequel vérifier l'égalité
     * @return
     *  <p> <b>true</b> si <code>obj</code> a le même nom que le critère
     *  <p> <b>false</b> dans le cas contraire ou si <code>obj</code>
     *      n'est pas initialisé ou une instance de {@link Critere}
     * @since 2012
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Critere)
            return super.equals((Critere) obj);

        return false;
    }
    
    /**
     * <p>Retourne la représentation en {@link String} du critère
     * 
     * @return le nom et la valeur du critère sous forme de {@link String}
     * @since 2012
     * @see Champ#toString() 
     */
    @Override
    public String toString() {
        return "Critere" + super.toString();
    }
}
