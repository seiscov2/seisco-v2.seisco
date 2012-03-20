package seisco.util;

/**
 * <p>Représente une propriété ayant un nom et une valeur.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public class Propriete extends Champ {
    
    /**
     * <p>Instancie une nouvelle propriété en la nommant et en l'initialisant
     * 
     * @param nom le nom de la propriété
     * @param valeur la valeur qu'elle contient
     * @since 2008
     * @see Champ#Champ(java.lang.String, java.lang.Object) 
     */
    public Propriete(String nom, float valeur) {
        super(nom, new Float(valeur));
    }

    /**
     * <p>Retourne la valeur de la propriété
     * 
     * @return
     *  la valeur contenue dans la propriété sous la forme d'un {@link Float}
     * @since 2008
     * @see Champ#valeur
     * @see #setValeur(java.lang.Object) 
    */
    @Override
    public Float getValeur() {
        return ((Float)valeur);
    }
    
    /**
     * <p>Remplace la valeur de la propriété
     * 
     * @param valeur la nouvelle valeur de la propriété
     * @since 2008
     * @see Champ#valeur
     * @see #getValeur()
     */
    public void setValeur(float valeur) {
        this.valeur = new Float(valeur);
    }

    /**
     * <p>Teste si la propriété est identique à l'argument
     * 
     * @param obj l'objet avec lequel vérifier l'égalité
     * @return
     *  <p> <b>true</b> si <code>obj</code> a le même nom que la propriété
     *  <p> <b>false</b> dans le cas contraire ou si <code>obj</code>
     *      n'est pas initialisé ou une instance de {@link Propriete}
     * @since 2012
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Propriete)
            return this.nom.equals(((Propriete)obj).nom);
        
        return false;
    }

    /**
     * <p>Retourne la représentation en {@link String} de la propriété
     * 
     * @return le nom et la valeur de la propriété sous forme de {@link String}
     * @since 2012
     * @see Champ#toString() 
     */
    @Override
    public String toString() {
        return "Propriete" + super.toString();
    }

}