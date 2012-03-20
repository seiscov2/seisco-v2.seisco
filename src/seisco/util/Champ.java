package seisco.util;

import jade.content.Concept;

/**
 * <p>
 * Représente un champ ayant un nom et une valeur.
 * Cette classe généralise {@link Condition}, {@link Critere},
 * {@link Etat}, {@link Parametre}, {@link Propriete}, etc.
 * </p>
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 * @param <T> le type de la valeur contenue dans le champ
 */
public abstract class Champ<T> implements Concept {
    protected String nom;
    protected T valeur;
	
    /**
     * <p>Instancie un nouveau champ en le nommant et en l'initialisant
     * 
     * @param nom le nom du champ
     * @param valeur la valeur qu'il contient
     * @since 2008
     */
    public Champ(String nom, T valeur) {
        super();
        this.nom = nom;
        this.valeur = valeur;
    }

    /**
     * <p>Retourne le nom du champ
     * 
     * @return le nom du champ sous forme de {@link String}
     * @since 2008
     * @see #setNom(java.lang.String) 
     */
    public String getNom() {
        return nom;
    }

    /**
     * <p>Remplace le nom du champ
     * 
     * @param nom le nouveau nom du champ
     * @since 2008
     * @see #getNom() 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * <p>Retourne la valeur du champ
     * 
     * @return
     *      la valeur contenue dans le champ sous la forme
     *      du type précisé lors de l'instanciation du champ
     * @since 2008
     * @see #setValeur(java.lang.Object) 
    */
    public T getValeur() {
        return valeur;
    }

    /**
     * <p>Remplace la valeur du champ
     * 
     * @param valeur
     *      la nouvelle valeur du champ,
     *      de type défini à son instanciation
     * @since 2008
     * @see #getValeur()
     */
    public void setValeur(T valeur) {
        this.valeur = valeur;
    }

    /**
     * <p>Retourne la représentation en {@link String} du champ
     * 
     * @return le nom et la valeur du champ sous forme de {@link String}
     * @since 2012
     */
    @Override
    public String toString(){
        return "[" + nom + "=" + valeur + "]";
    }

    /**
     * <p>Teste si le champ est identique à l'argument
     * 
     * @param obj l'objet avec lequel vérifier l'égalité
     * @return
     *  <p> <b>true</b> si <code>obj</code> a le même nom que le champ
     *  <p> <b>false</b> dans le cas contraire ou si <code>obj</code>
     *      n'est pas initialisé ou une instance de {@link Champ}
     * @since 2012
     */
    @Override
    public boolean equals(Object obj) {
        
        if(obj!=null && obj instanceof Champ)
            return this.nom.equals(((Champ)obj).nom);
        
        return false;
    }
}
