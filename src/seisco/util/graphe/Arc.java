package seisco.util.graphe;

import jade.content.Concept;
import java.util.ArrayList;
import java.util.List;
import seisco.util.Propriete;

/**
 * <p>Représente un arc orienté ou non orienté dans un graphe
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public class Arc implements Concept {
	
    /**
     * <p>Coût de traversée
     */
	public final static String NOM_COUT_PARCOURS = "coutparcours";
	
	private static int lastNum = 1;
	protected int num;
	
    /**
     * <p>
     * Un arc possède plusieurs {@link Cout} pour
     * permettre d'ajouter des contraintes diverses.
     * Par exemple un coût temporel de traversée
     * et un coût physique de parcours.
     * Utile pour rester le plus général possible.
     */
    protected List<Cout> couts;
    
    /**
     * <p>
     * Un arc possède plusieurs {@link Propriete} pour
     * permettre d'ajouter des contraintes diverses.
     * Par exemple une propriété de ramassage ou de
     * dépôt voire les deux de manière séparée.
     */
    protected List<Propriete> proprietes;

    protected Noeud depart;
    protected Noeud arrivee;
    private boolean inv;
	
    /**
     * <p>
     * Instancie un arc.
     * Constructeur par défaut, ne peut être appelé explicitement
     * </p>
     * 
     * @param increaseNum
     *  sert à savoir si le prochain arc instancié aura le même
     *  numéro (<b>false</b>) ou un numéro suivant (<b>true</b>)
     * @since 2012
     * @see #Arc(seisco.util.graphe.Noeud, seisco.util.graphe.Noeud, boolean) 
     * @see #Arc(seisco.util.graphe.Noeud, seisco.util.graphe.Noeud) 
     */
    private Arc(boolean increaseNum) {
        super();
		
		this.num = (increaseNum) ? Arc.lastNum++ : Arc.lastNum;
		
        this.couts = new ArrayList<Cout>();
        this.proprietes = new ArrayList<Propriete>();
    }
	
    /**
     * <p>Instancie un arc avec un {@link Noeud} de départ et d'arrivée.
     * 
     * @param depart le nœud de départ de l'arc
     * @param arrivee le nœud d'arrivée de l'arc
     * @param increaseNum
     *  sert à savoir si le prochain arc instancié aura le même
     *  numéro (<b>false</b>) ou un numéro suivant (<b>true</b>)
     * @since 2012
     * @see #Arc(boolean) 
     */
	public Arc(Noeud depart, Noeud arrivee, boolean increaseNum) {
		this(increaseNum);
		this.depart = depart;
		this.arrivee = arrivee;
	}
	
    /**
     * <p>Instancie un arc avec un {@link Noeud} de départ et d'arrivée.
     * 
     * @param depart le nœud de départ de l'arc
     * @param arrivee le nœud d'arrivée de l'arc
     * @since 2012
     * @see #Arc(seisco.util.graphe.Noeud, seisco.util.graphe.Noeud, boolean)
     */
	public Arc(Noeud depart, Noeud arrivee) {
		this(depart, arrivee, true);
	}

    /**
     * <p>Retourne la {@link List} des {@link Cout} de l'arc.
     * 
     * @return les différents coûts de l'arc
     * @since 2008
     * @see #setCouts(java.util.List) 
     */
    public List<Cout> getCouts() {
        return couts;
    }

    /**
     * <p>Remplace la {@link List} des {@link Cout} de l'arc.
     * 
     * @param couts les nouveaux coûts de l'arc
     * @since 2008
     * @see #getCouts() 
     */
    public void setCouts(List<Cout> couts) {
        this.couts = couts;
    }

    /**
     * <p>Retourne la {@link List} des {@link Propriete} de l'arc.
     * 
     * @return les différentes propriétés de l'arc
     * @since 2008
     * @see #setProprietes(java.util.List) 
     */
    public List<Propriete> getProprietes() {
        return proprietes;
    }

    /**
     * <p>Remplace la {@link List} des {@link Propriete} de l'arc.
     * 
     * @param proprietes les nouvelles propriétés de l'arc
     * @since 2008
     * @see #getProprietes() 
     */
    public void setProprietes(List<Propriete> proprietes) {
        this.proprietes = proprietes;
    }

    /**
     * <p>Ajoute une nouvelle {@link Propriete} à l'arc.
     * 
     * @param nouvellePropriete la propriété à ajouter
     * @return
     *  <p> <b>true</b> si l'ajout s'est correctement effectué
     *  <p> <b>false</b> dans le cas contraire ou si
     *      la {@link Propriete} existe déjà dans l'arc
     * @since 2012
     * @see #retirerPropriete(seisco.util.Propriete) 
     * @see #retirerPropriete(java.lang.String) 
     */
    public boolean ajouterPropriete(Propriete nouvellePropriete) {
        for(Propriete p : proprietes)
            if(p.equals(nouvellePropriete))
                return false; 

        return proprietes.add(nouvellePropriete);
    }

    /**
     * <p>Supprime une {@link Propriete} de l'arc.
     * 
     * @param p la propriété à supprimer
     * @return
     *  <p> <b>true</b> si la suppression s'est correctement effectuée
     *  <p> <b>false</b> dans le cas contraire
     * @since 2012
     * @see #ajouterPropriete(seisco.util.Propriete) 
     */
    public boolean retirerPropriete(Propriete p) {
        return proprietes.remove(p);
    }

    /**
     * <p>Supprime une {@link Propriete} de l'arc.
     * 
     * @param nomPropriete le nom de la propriété à supprimer
     * @return
     *  <p> <b>true</b> si la suppression s'est correctement effectuée
     *  <p> <b>false</b> dans le cas contraire
     * @since 2012
     * @see #ajouterPropriete(seisco.util.Propriete) 
     */
    public boolean retirerPropriete(String nomPropriete) {
        for(Propriete p : proprietes)
            if(p.getNom().equals(nomPropriete))
                return proprietes.remove(p);

        return false;		
    }

    /**
     * <p>Ajoute un {@link Cout} à l'arc
     * 
     * @param nouveauCout le coût à ajouter
     * @return
     *  <p> <b>true</b> si l'ajout s'est correctement effectué
     *  <p> <b>false</b> dans le cas contraire ou si
     *      le {@link Cout} existe déjà dans l'arc
     * @since 2012
     * @see #retirerCout(seisco.util.graphe.Cout) 
     * @see #retirerCout(java.lang.String) 
     */
    public boolean ajouterCout(Cout nouveauCout) {
        for(Cout c : couts)
            if(c != null && c.equals(nouveauCout))
                return false;

        return couts.add(nouveauCout);
    }

    /**
     * <p>Supprime un {@link Cout} de l'arc.
     * 
     * @param c le coût à supprimer
     * @return
     *  <p> <b>true</b> si la suppression s'est correctement effectuée
     *  <p> <b>false</b> dans le cas contraire
     * @since 2012
     * @see #ajouterCout(seisco.util.graphe.Cout) 
     */
    public boolean retirerCout(Cout c) {
        return couts.remove(c);
    }

    /**
     * <p>Supprime un {@link Cout} de l'arc.
     * 
     * @param nomCout le nom du coût à supprimer
     * @return
     *  <p> <b>true</b> si la suppression s'est correctement effectuée
     *  <p> <b>false</b> dans le cas contraire
     * @since 2012
     * @see #ajouterCout(seisco.util.graphe.Cout) 
     */
    public boolean retirerCout(String nomCout) {
        for(Cout c : couts)
            if(c.getNom().equals(nomCout))
                return couts.remove(c);

        return false;
    }

    /**
     * <p>Vérifie si un {@link Cout} est bien présent sur l'arc
     * 
     * @param name le nom du coût à vérifier
     * @return
     *  <p> <b>true</b> s'il existe un {@link Cout} du
     *      même nom que <code>name</code> sur l'arc
     *  <p> <b>false</b> dans le cas contraire
     * @since 2008
     */
    public boolean coutPresent(String name) {
        for(Cout c : couts)
            if(c.getNom().equals(name))
                return true;

        return false;
    }

    /**
     * <p>Retourne un {@link Cout} par rapport à son nom.
     * 
     * @param name le nom du coût à récupérer
     * @return
     *  <p> <b>null</b> si le coût n'est pas présent sur l'arc
     *  <p> sinon le {@link Cout} correspondant à <code>name</code>
     * @since 2008
     */
    public Cout getCout(String name) {
        if(!coutPresent(name))
            return null;

        for(Cout c : couts)
            if(c.getNom().equals(name))
                return c;

        return null;
    }

    /**
     * <p>Vérifie si une {@link Propriete} est bien présente sur l'arc
     * 
     * @param name le nom de la propriété à vérifier
     * @return
     *  <p> <b>true</b> s'il existe une {@link Propriete} du
     *      même nom que <code>name</code> sur l'arc
     *  <p> <b>false</b> dans le cas contraire
     * @since 2008
     */
    public boolean proprietePresente(String name) {
        for(Propriete p : proprietes)
            if(p.getNom().equals(name))
                return true;

        return false;
    }

    /**
     * <p>Retourne une {@link Propriete} par rapport à son nom.
     * 
     * @param name le nom de la propriété à récupérer
     * @return
     *  <p> <b>null</b> si la propriété n'est pas présente sur l'arc
     *  <p> sinon la {@link Propriete} qui correspond à <code>name</code>
     * @since 2008
     */
    public Propriete getPropriete(String name) {
        if(!proprietePresente(name))
            return null;

        for(Propriete p : proprietes)
            if(p.getNom().equals(name))
                return p;

        return null;
    }

    /**
     * <p>Retourne le {@link Noeud} de {@link #depart} de l'arc 
     * 
     * @return le nœud de départ de l'arc
     * @since 2008
     * @see #setDepart(seisco.util.graphe.Noeud) 
     */
    public Noeud getDepart() {
        return this.depart;
    }

    /**
     * <p>Remplace le {@link Noeud} de {@link #depart} de l'arc
     * 
     * @param depart le nouveau nœud de départ
     * @since 2008
     * @see #getDepart() 
     */
    public void setDepart(Noeud depart) {
        this.depart = depart;
    }

    /**
     * <p>Retourne le {@link Noeud} d'{@link #arrivee} de l'arc 
     * 
     * @return le nœud d'arrivée de l'arc
     * @since 2008
     * @see #setArrivee(seisco.util.graphe.Noeud) 
     */
    public Noeud getArrivee() {
        return arrivee;
    }

    /**
     * <p>Remplace le {@link Noeud} d'{@link #arrivee} de l'arc 
     * 
     * @param arrivee le nouveau nœud d'arrivée
     * @since 2008
     * @see #getArrivee() 
     */
    public void setArrivee(Noeud arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * <p>Retourne le numéro de l'arc
     * 
     * @return le numéro de l'arc
     * @see #num
     */
	public int getNumero() {
		return num;
	}

    /**
     * <p>Vérifie l'égalité de deux arcs entre eux
     * 
     * @param o l'{@link Object} à tester
     * @return
     *  <p> <b>true</b> si <code>o</code> possède le même
     *      {@link #depart} et la même {@link #arrivee} que l'arc
     *  <p> <b>false</b> dans le cas contraire ou si
     *      <code>o</code> n'est pas une instance d'{@link Arc}
     * @since 2012
     */
	@Override
	public boolean equals(Object o) {        
		if (o instanceof Arc) {
            if(this.inv == ((Arc)o).inv)
                return depart.equals(((Arc)o).depart)
                    	&& arrivee.equals(((Arc)o).arrivee);
            else
                return depart.equals(((Arc)o).arrivee)
                    	&& arrivee.equals(((Arc)o).depart);
        }
		
		return false;
	}
    
    /**
     * <p>
     * Retourne une représentation de l'arc sous forme de {@link String}.
     * Y compris son numéro, son départ, son
     * arrivée ainsi que ses coûts et propriétés.
     * </p>
     * 
     * @return la représentation de l'arc sous forme de {@link String}.
     * @since 2012
     * @see Noeud#toString() 
     * @see Cout#toString() 
     * @see Propriete#toString() 
     */
    @Override
    public String toString() {
        
        String resultat = "Arc(" + this.num + ") :\n";
		resultat += "\tDu " + depart.toString() + " ";
        resultat += "\tau " + arrivee.toString() + "\n";
        
        resultat += "\tCoûts associés :\n";
        for(Cout c : couts)
            resultat += c.toString() + "\n";

        resultat += "\tPropriétés :\n";
        for(Propriete p : proprietes)
            resultat += p.toString() + "\n";

        return resultat;
    }

    /**
     * <p>Indique si l'arc est inversé
     * 
     * @return
     *  <p> <b>true</b> si l'arc est inversé
     *  <p> <b>false</b> sinon
     * @since 2012
     * @see #swap() 
     */
    public boolean isInv() {
        return inv;
    }
    
    /**
     * <p>Inverse le sens de traversée de l'arc
     * 
     * @return l'arc courant lui même
     * @since 2012
     * @see #isInv() 
     */
    public Arc swap() {
        Noeud temp = this.arrivee;
        this.arrivee = this.depart;
        this.depart = temp;
        this.inv = !this.inv;
        return this;
    }

    /**
     * <p>Renvoie une copie exacte de l'{@link Arc} courant
     * 
     * @return une copie exacte de l'{@link Arc} courant
     * @since 2012
     */
    public Arc clone() {
        Arc clone = new Arc(depart, arrivee, false);
        clone.inv = this.inv;
        
        for (Cout cout : this.couts)
            clone.ajouterCout(cout);
        
        for (Propriete propriete : this.proprietes)
            clone.ajouterPropriete(propriete);
        
        return clone;
    }
}
