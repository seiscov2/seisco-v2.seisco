package seisco.util.graphe;

import jade.content.Concept;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Représente un nœud dans un graphe (orienté ou non)
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public class Noeud implements Concept, Comparable<Noeud> {
    
	private static int lastNum = 1;
    private int numero;
    
    /**
     * <p>
     * Contient la liste des arcs adjacents.
     * Nécessaire pour le fonctionnement de la classe {@link Dijkstra}
     * 
     * @since 2012
     * @see Dijkstra
     */
	protected List<Arc> adjacents;
    
    /**
     * <p>
     * Contient le coût minimum de distance entre ce nœud et le nœud
     * source (voir {@link Dijkstra#computePaths(seisco.util.graphe.Noeud)}).
     * Nécessaire pour le fonctionnement de la classe {@link Dijkstra}
     * 
     * @since 2012
     * @see Dijkstra
     * @see Dijkstra#computePaths(seisco.util.graphe.Noeud)
     */
	protected Cout minDistance = new Cout(Arc.NOM_COUT_PARCOURS, Float.POSITIVE_INFINITY);
    
    /**
     * <p>
     * Contient le nœud précédent pour le chemin le plus court à partir de la
     * source (voir {@link Dijkstra#computePaths(seisco.util.graphe.Noeud)}).
     * Nécessaire pour le fonctionnement de la classe {@link Dijkstra}
     * 
     * @since 2012
     * @see Dijkstra
     * @see Dijkstra#computePaths(seisco.util.graphe.Noeud)
     */
	protected Noeud precedent;

    /**
     * <p>Instancie un nouveau nœud
     * 
     * @since 2012
     */
    public Noeud() {
        super();
        this.numero = lastNum++;
		this.adjacents = new ArrayList<Arc>();
    }

    /**
     * <p>Retourne le numéro du nœud
     * 
     * @return le numéro de nœud
     * @since 2008
     * @see #setNumero(int) 
     * @see #numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * <p>Remplace le numéro du nœud
     * 
     * @param numero le nouveau numéro du nœud
     * @since 2008
     * @see #getNumero() 
     * @see #numero
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
	
    /**
     * <p>Ajoute un {@link Arc} adjacent au nœud
     * 
     * @param a l'arc à ajouter
     * @return
     *  <p> <b>true</b> si l'{@link Arc} a été correctement ajouté
     *  <p> <b>false</b> dans le cas contraire ou si
     *      l'{@link Arc} est déjà dans les adjacents.
     * @since 2012
     * @see #adjacents
     */
	public boolean addAdjacent(Arc a) {
		if(!adjacents.contains(a))
			return adjacents.add(a);
		return false;
	}

    /**
     * <p>Vérifie l'égalité entre deux {@link Noeud}
     * 
     * @param o l'{@link Object} à tester
     * @return
     *  <p> <b>true</b> si <code>o</code> a le même numéro de nœud
     *  <p> <b>false</b> dans le cas contraire ou si
     *      <code>o</code> n'est pas une instance de {@link Noeud}.
     * @since 2012
     * @see #numero
     */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Noeud)
			return this.numero == ((Noeud)o).numero;
		
		return false;
	}
	
	/**
     * <p>Retourne la représentation en {@link String} du nœud.
     * Y compris son numéro.
     * 
     * @return la représentation en {@link String} du nœud.
     * @since 2012
     */
    @Override
    public String toString(){
        return "Noeud(" + numero + ")";
    }

    /**
     * <p>
     * Compare deux nœud sur leurs distances
     * minimum à la source (voir {@link Dijkstra}).
     * Nécessaire au fonctionnement de {@link Dijkstra}.
     * 
     * @param o le nœud à comparer
     * @return
     *  <p> <b>0</b> s'ils sont égaux
     *  <p> un nombre <b>supérieur à 0</b> si
     *      <code>o</code> est plus proche de la source
     *  <p> un nombre <b>inférieur à 0</b> si
     *      <code>o</code> est plus éloigné de la source
     * @since 2012
     */
	@Override
	public int compareTo(Noeud o) {
		return Float.compare(minDistance.getValeur(), o.minDistance.getValeur());
	}
}
