package seisco.util.graphe;

import jade.content.Concept;

/**
 * <p>Représente un coût ayant un nom et une valeur.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public class Cout implements Concept {
    
    public String nom;
    private float valeur;

    /**
     * <p>Instancie un nouveau coût en le nommant et en l'initialisant
     * 
     * @param nom le nom du coût
     * @param valeur la valeur qu'il contient
     * @since 2008
     */
    public Cout(String nom, float valeur) {
        super();
        this.nom = nom;
        this.valeur = valeur;
    }

    /**
     * <p>Retourne le nom du coût
     * 
     * @return le nom du coût
     * @see #setNom(java.lang.String) 
     * @see #nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * <p>Remplace le nom du coût
     * 
     * @param nom le nouveau nom
     * @since 2008
     * @see #getNom() 
     * @see #nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * <p>Retourne la valeur du coût
     * 
     * @return la valeur contenue dans le coût
     * @since 2008
     * @see #setValeur(float) 
     * @see #valeur
     */
    public float getValeur() {
        return valeur;
    }

    /**
     * <p>Remplace la valeur du coût
     * 
     * @param valeur la nouvelle valeur
     * @since 2008
     * @see #getValeur() 
     * @see #valeur
     */
    public void setValeur(float valeur) {
        this.valeur = valeur;
    }

    /**
     * <p>
     * Retourne la représentation du coût sous forme de {@link String}.
     * Y compris son nom et sa valeur.
     * 
     * @return la représentation du coût sous forme de {@link String}
     * @since 2012
     */
    @Override
    public String toString(){
        return "Cout[" + nom + "=" + valeur + "]";
    }

    /**
     * <p>Vérifie l'égalité entre deux coûts
     * 
     * @param obj l'{@link Object} à tester
     * @return
     *  <p> <b>true</b> si <code>obj</code> a le même nom que le coût
     *  <p> <b>false</b> dans le cas contraire ou si
     *      <code>obj</code> n'est pas une instance de {@link Cout}.
     * @since 2012
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Cout)
            return this.nom.equals(((Cout)obj).nom);
        
        return false;
    }
}
