package seisco.util.graphe;

import jade.content.Concept;
import java.io.IOException;
import java.util.List;

/**
 * <p>Représente un graphe (orienté ou non).
 * 
 * @author Bruno Boi
 * @version 2012
 */
public class Graphe implements Concept {
	protected String nom;
	protected List<Noeud> noeuds;
	protected List<Arc> arcs;

    /**
     * <p>Instancie un nouveau graphe
     * 
     * @param nom le nom du graphe
     * @param noeuds la liste de nœuds du graphe
     * @param arcs la liste des arcs du graphe
     */
	public Graphe(String nom, List<Noeud> noeuds, List<Arc> arcs) {
		super();
		this.nom = nom;
		this.noeuds = noeuds;
		this.arcs = arcs;
	}

    /**
     * <p>Retourne la liste des nœuds du graphe
     * 
     * @return la liste des nœuds
     * @since 2012
     * @see #noeuds
     */
	public List<Noeud> getNoeuds() {
		return noeuds;
	}

    /**
     * <p>Retourne la liste des arcs du graphe
     * 
     * @return la liste des arcs
     * @since 2012
     * @see #arcs
     */
	public List<Arc> getArcs() {
		return arcs;
	}

    /**
     * <p>
     * Retourne une représentation du graphe sous forme de {@link String}.
     * Y compris son nom, ses nœuds et ses arcs.
     * 
     * @return une représentation du graphe sous forme de {@link String}.
     * @since 2012
     */
	@Override
	public String toString() {
		String res = "Graphe " + this.nom + " :\n";
		
		res += "Noeuds associés :\n";
		for (Noeud noeud : noeuds)
			res += noeud.toString() + "\n";
		
		res += "Arcs associées :\n";
		for (Arc arc : arcs)
			res += arc.toString();
		
		return res;
	}
}
