package seisco.algo;

import jade.content.Concept;
import java.util.ArrayList;
import java.util.List;
import seisco.probleme.Probleme;
import seisco.util.Parametre;

/**
 * <p>
 * Représente un algorithme avec un nom, un {@link Probleme},
 * un {@link JeuParametres} et des {@link Operateur}.
 * </p>
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public abstract class Algorithme implements Concept {
    
    protected String nom;
    protected Probleme probleme;
    protected JeuParametres parametres;
    protected List<Operateur> operateurs;
    
    protected long timeObjectiveFunction = 0;
    
    /**
     * <p>
     * Crée un nouvel algorithme avec un {@link Probleme} associé, et le nomme.
     * 
     * @param nom le nom de l'algorithme
     * @param probleme le problème à lier
     * @since 2008
     */
    public Algorithme(String nom, Probleme probleme) {
        super();
        this.parametres = new JeuParametres();
        this.nom = nom;
        this.probleme = probleme;
        this.operateurs = new ArrayList<>();
    }

    /**
     * <p>Retourne les {@link Operateur} associés à l'algorithme.
     * 
     * @return la {@link  List} d'{@link Operateur} de l'algorithme
     * @since 2008
     * @see #setOperateurs(java.util.List) 
     */
    public List<Operateur> getOperateurs() {
        return operateurs;
    }

    /**
     * <p>Remplace les {@link Operateur} associés à l'algorithme.
     * 
     * @param operateurs
     *  la nouvelle {@link  List} d'{@link Operateur} de l'algorithme
     * @since 2008
     * @see #getOperateurs() 
     */
    public void setOperateurs(List<Operateur> operateurs) {
        this.operateurs = operateurs;
    }

    /**
     * <p>Retourne lenom de l'algorithme
     * 
     * @return le nom de l'algorithme
     * @since 2008
     * @see #setNom(java.lang.String) 
     */
    public String getNom() {
        return nom;
    }

    /**
     * <p>Remplace le nom de l'algorithme
     * 
     * @param nom le nouveau nom de l'algorithme
     * @since 2008
     * @see #getNom() 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * <p>Retourne le jeu de paramètres de l'algorithme
     * 
     * @return
     *      les paramètres de l'algorithme, sous la forme {@link JeuParametres}
     * @since 2008
     * @see #setParametres(seisco.algo.JeuParametres)
     * @see #setParametres(java.util.List) 
     */
    public JeuParametres getParametres() {
        return parametres;
    }

    /**
     * <p>Remplace le jeu de paramètres de l'algorithme
     * 
     * @param parametres le nouveau jeu de paramètres
     * @since 2008
     * @see #getParametres() 
     */
    public void setParametres(JeuParametres parametres) {
        this.parametres = parametres;
    }

    /**
     * <p>Remplace le jeu de paramètres de l'algorithme
     * 
     * @param parametres
     *      le nouveau jeu de paramètres, sous forme
     *      de {@link List} de {@link Parametre}.
     * @since 2008
     * @see #getParametres() 
     */
    public void setParametres(List<Parametre> parametres) {
        JeuParametres jm = new JeuParametres();
        jm.setParametres(parametres);
        this.parametres = jm;
    }
    
    /**
     * <p>Retourne le {@link Probleme} lié à l'algorithme
     * @return le problème lié à l'algorithme
     * @since 2008
     * @see #setProbleme(seisco.probleme.Probleme) 
     */
    public Probleme getProbleme() {
        return probleme;
    }

    /**
     * <p>Remplace le {@link Probleme} lié à l'algorithme
     * 
     * @param probleme le nouveau problème à lier
     * @since 2008
     * @see #getProbleme() 
     */
    public void setProbleme(Probleme probleme) {
        this.probleme = probleme;
    }
    
    /**
     * <p>
     * Retourne le temps (en ms) que la fonction
     * « objectif » du {@link Probleme} a pris pour s'exécuter. 
     * </p>
     * 
     * @return
     *      le temps d'exécution (en ms) de la
     *      fonction « objectif » du {@link Probleme}.
     * @since 2008
     * @see Probleme#fonctionObjectif(seisco.probleme.Solution) 
     */
    public long getTimeObjectiveFunction() {
        return this.timeObjectiveFunction;
    }

    /**
     * <p>Exécute une fois l'algorithme
     * 
     * @throws AlgorithmException si un problème survient
     * @since 2008
     */
    public abstract void executer() throws AlgorithmException;

    /**
     * <p>
     * Retourne sous forme de {@link String} la représentation de l'algorithme.
     * Sont compris: le nom, le {@link Probleme} lié et les {@link Parametre}.
     * </p>
     * 
     * @return
     *  la représentation de l'algorithme avec
     *  son nom, le problème lié et les paramètres.
     * @since 2012
     * @see Probleme#toString() 
     * @see Parametre#toString() 
     */
    @Override
    public String toString() {
        String resultat = "Algorithme " + nom + "\n";
        
        resultat += probleme.toString();
        
        resultat += "Paramètres :\n";
        resultat += parametres.toString();
        
        return resultat;
    }
}
