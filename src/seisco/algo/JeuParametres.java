package seisco.algo;

import jade.content.Concept;
import java.util.ArrayList;
import java.util.List;
import seisco.util.Parametre;

/**
 * <p>Représente un jeu de {@link Parametre} pour les {@link Algorithme}.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public class JeuParametres implements Concept {

    List<Parametre> parametres;

    /**
     * <p>Instancie un nouveau jeu de paramètres
     * 
     * @since 2012
     */
    public JeuParametres() {
        super();
        this.parametres = new ArrayList<Parametre>();
    }

    /**
     * <p>Retourne la {@link List} des {@link Parametre}
     * 
     * @return la liste des paramètres
     * @since 2008
     * @see #setParametres(java.util.List) 
     */
    public List<Parametre> getParametres() {
        return this.parametres;
    }

    /**
     * <p>Remplace la {@link List} des {@link Parametre}
     * 
     * @param parametres la nouvelle liste des paramètres
     * @since 2008
     * @see #getParametres() 
     */
    public void setParametres(List<Parametre> parametres) {
        this.parametres = parametres;
    }

    /**
     * <p>
     * Teste si le jeu de paramètres contient un
     * {@link Parametre} s'appelant comme précisé en argument.
     * </p>
     * 
     * @param name le nom du {@link Parametre} à tester
     * @return
     *  <p> <b>true</b> si le jeu de paramètre contient
     *      un {@link Parametre} nommé <code>name</code>.
     *  <p> <b>false</b> dans le cas contraire.
     * @since 2008
     */
    public boolean contains(String name) {
        for(Parametre p : this.parametres)
            if(p.getNom().equals(name))
                return true;
        
        return false;
    }

    /**
     * <p>
     * Retourne le {@link Parametre} contenu dans
     * le jeu et ayant le même nom que l'argument.
     * </p>
     * 
     * @param name le nom du {@link Parametre} à récupérer
     * @return
     *  <p> le {@link Parametre} à récupérer.
     *  <p> <b>null</b> si le jeu de paramètre n'en contient aucun ayant ce nom.
     * @since 2008
     */
    public Parametre getElement(String name) {
        for(Parametre p : this.parametres)
            if(p.getNom().equals(name))
                return p;
        
        return null;
    }
    
    /**
     * <p>Ajoute un {@link Parametre} au jeu de paramètres.
     * 
     * @param p le paramètre à ajouter
     * @return
     *  <p> <b>true</b> si l'ajout s'est bien déroulé
     *  <p> <b>false</b> dans le cas contraire ou si le jeu de
     *      paramètre en contient déjà un ayant le même nom.
     * @since 2008
     */
    public boolean addParametre(Parametre p) {
        if(contains(p.getNom()))
            return false;
         
        return this.parametres.add(p);
    }

    /**
     * <p>
     * Retourne la représentation sous forme de {@link String}
     * de chacun des {@link Parametre} contenu dans le jeu.
     * </p>
     * 
     * @return
     *      la représentation sous forme de {@link String} de
     *      chacun des {@link Parametre} contenu dans le jeu.
     * @since 2012
     * @see Parametre#toString() 
     */
    @Override
    public String toString() {
        String resultat="";

        for(Parametre p : this.parametres)
            resultat += p.toString() + "\n";

        return resultat;
    }

}
