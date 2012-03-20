package seisco.probleme;

import com.sun.corba.se.impl.orbutil.concurrent.CondVar;
import jade.content.Concept;
import java.util.ArrayList;
import java.util.List;
import seisco.util.Condition;

/**
 * <p>
 * Représente en problème d'optimisation ayant un nom,
 * un set de {@link Condition} et une fonction « objectif ».
 * </p>
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public abstract class Probleme implements Concept {
    protected String nom;
    protected List<Condition> conditions;
    
    /**
     * <p>Instancie un nouveau problème en initialisant son nom à "Unknown"
     * 
     * @since 2012
     */
    public Probleme(){
        nom = "Unknown";
        conditions = new ArrayList<Condition>();
    }

    /**
     * <p>Instancie un nouveau problème et le nomme
     * 
     * @param name le nom à donner au problème
     * @since 2008
     */
    public Probleme(String name){
        nom = name;
        conditions = new ArrayList<Condition>();
    }

    /**
     * <p>Retourne la {@link List} de {@link Condition} du problème
     * 
     * @return les conditions du problème
     * @since 2008
     * @see #setConditions(java.util.List) 
     */
    public List<Condition> getConditions() {
        return conditions;
    }

    /**
     * <p>Remplace la {@link List} de {@link Condition} du problème
     * 
     * @param conditions les nouvelles conditions du problème
     * @since 2008
     * @see #getConditions() 
     */
    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    /**
     * <p>Retourne le nom du problème
     * 
     * @return le nom du problème
     * @since 2008
     * @see #setNom(java.lang.String) 
     */
    public String getNom() {
        return nom;
    }

    /**
     * <p>Remplace le nom du problème
     * 
     * @param nom le nouveau nom du problème
     * @since 2008
     * @see #getNom() 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * <p>Teste la présence d'une {@link Condition} dans le problème
     * 
     * @param name le nom de la {@link Condition} à tester
     * @return
     *  <p> <b>true</b> si le problème contient bien la {@link Condition}.
     * <p>  <b>false</b> dans le cas contraire.
     * @since 2008
     */
    public boolean conditionPresente(String name) {
        for(Condition c : conditions)
            if(c.getNom().equals(name))
                return true;

        return false;
    }

    /**
     * <p>
     * Retourne la {@link Condition} du problème
     * ayant le même nom que l'argument.
     * </p>
     * 
     * @param name le nom de la {@link Condition} à récupérer
     * @return
     *  <p> la {@link Condition} ayant le même nom que
     *      <code>name</code> si elle est présente dans le problème.
     *  <p> <b>null</b> dans le cas contraire
     * @since 2008
     */
    public Condition getCondition(String name) {
        if(!conditionPresente(name))
            return null;

        for(Condition c : conditions)
            if(c.getNom().equals(name))
                return c;

        return null;
    }

    /**
     * <p>
     * Retourne la représentation sous forme de {@link String} du problème.
     * Y compris son nom et ses {@link Condition}.
     * 
     * @return la représentation du problème en {@link String}
     * @since 2012
     * @see Condition#toString() 
     */
    @Override
    public String toString() {
        String resultat = "Problème " + nom + "\n";
        
        resultat+="Conditions :\n";
        for(Condition c : conditions)
            resultat += c.toString() + "\n";
        
        return resultat;
    }

    /**
     * <p>
     * Applique la fonction « objectif » du problème à une {@link Solution}
     * et retourne son évaluation sous la forme d'un {@link Float}.
     * </p>
     * 
     * @param s la solution à évaluer
     * @return l'évaluation d'une solution au problème
     */
    public abstract float fonctionObjectif(Solution s);

    /**
     * <p>
     * Vérifie que le problème est réalisable
     * par la {@link Solution} passée en argument.
     * </p>
     * 
     * @param s
     * @return 
     */
    public abstract boolean estRealisable(Solution s);


}
