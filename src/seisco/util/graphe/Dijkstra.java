/* 
 * Cette classe est inspirée de literateprograms.org
 * Voici le copyright de celle-ci:
 * 
 * Copyright (c) 2012 the authors listed at the following URL, and/or
 * the authors of referenced articles or incorporated external code:
 * http://en.literateprograms.org/Dijkstra's_algorithm_(Java)?action=history&offset=20081113161332
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * Retrieved from: http://en.literateprograms.org/Dijkstra's_algorithm_(Java)?oldid=15444
 */
package seisco.util.graphe;

import jade.content.Concept;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>
 * Calcule un distancier pour un {@link Graphe} (orienté ou non !).
 * Classe comprenant des méthodes uniquement statiques.
 * 
 * <p>
 * Pour récupérer le chemin le plus court entre un nœud source et un nœud cible,
 * sous la forme d'une liste de {@link Noeud}, il faut suivre cet ordre de
 * lancement de méthodes:
 * <ol>
 *  <li>{@link #initPaths(seisco.util.graphe.Graphe)}</li>
 *  <li>{@link #computePaths(seisco.util.graphe.Noeud)}</li>
 *  <li>{@link #getShortestPathTo(seisco.util.graphe.Noeud)}</li>
 * </ol>
 * 
 * <p>
 * Pour simplement récupérer un distancier, vous n'avez qu'à exécuter :
 * <ul>
 *  <li>{@link #initialiserDistancier(seisco.util.graphe.Graphe)}</li>
 * </ul>
 * 
 * @author Bruno Boi
 * @version 2012
 */
public class Dijkstra implements Concept {

    /**
     * <p>
     * Retourne un distancier sous forme de matrice de {@link Float}.
     * Voir http://en.literateprograms.org/Dijkstra's_algorithm_(Java)?oldid=15444
     * 
     * @param graphe le graphe pour lequel calculer le distancier
     * @return un distancier sous forme de matrice de {@link Float}
     * @since 2012
     */
	public static float[][] initialiserDistancier(Graphe graphe) {
		List<Noeud> listeNoeuds = graphe.getNoeuds();
		int nbNoeuds = listeNoeuds.size();
		float[][] distancier = new float[nbNoeuds][nbNoeuds];

		for (int i = 0; i < nbNoeuds; i++)
			Arrays.fill(distancier[i], 0);

		for (Iterator<Noeud> it = listeNoeuds.iterator(); it.hasNext();) {
			Noeud n = it.next();

			// Calculer le poids des chemins
			initPaths(graphe);
			computePaths(n);

            // Rempli la ligne
            int i = 0;
            for (Noeud noeud : listeNoeuds)
                distancier[n.getNumero() - 1][i++] = noeud.minDistance.getValeur();
		}

		return distancier;
	}

    /**
     * <p>Retourne le chemin le plus court de la source à la cible.
     * 
     * @param target le {@link Noeud} ciblé
     * @return la liste des {@link Noeud} de la source à la cible
     * @since 2012
     */
	public static List<Noeud> getShortestPathTo(Noeud target) {
		List<Noeud> path = new ArrayList<Noeud>();
		for (Noeud noeud = target; noeud != null; noeud = noeud.precedent)
			path.add(noeud);

		Collections.reverse(path);
		return path;
	}

    /**
     * <p>
     * Calcule les distances séparant la source des autres nœuds du graphe.
     * </p>
     * 
     * <p>
     * Avant de lancer cette méthode, faites appel
     * à {@link #initPaths(seisco.util.graphe.Graphe)}.
     * </p>
     * 
     * @param source
     *  la source à partir de laquelle calculer les chemins les
     *  plus courts pour se rendre à tous les autres nœuds du graphe.
     * @since 2012
     * @see #initPaths(seisco.util.graphe.Graphe)
     */
	public static void computePaths(Noeud source) {
		source.minDistance.setValeur(0);
		PriorityQueue<Noeud> noeudQueue = new PriorityQueue<Noeud>();
		noeudQueue.add(source);

		while (!noeudQueue.isEmpty()) {
			Noeud u = noeudQueue.poll();

			// Visit each Arc exiting u
			for (Arc a : u.adjacents) {
				Noeud v = a.arrivee;
				float weight = Float.POSITIVE_INFINITY;
				for (Cout cout : a.couts)
					if (cout.getNom().equals(Arc.NOM_COUT_PARCOURS))
						weight = cout.getValeur();

				// Relaxer l'arc(u,v)
				float distanceThroughU = u.minDistance.getValeur() + weight;

				if (distanceThroughU < v.minDistance.getValeur()) {
					noeudQueue.remove(v);

					v.minDistance.setValeur(distanceThroughU);
					v.precedent = u;
					noeudQueue.add(v);

				}

			}
		}
	}

    /**
     * <p>
     * Réinitialise les nœuds du graphe afin qu'ils
     * ne contiennent plus d'anciennes informations.
     * 
     * @param graphe le graphe dont les nœuds vont être réinitialisés
     * @since 2012
     */
	public static void initPaths(Graphe graphe) {
		for (Noeud n : graphe.noeuds) {
			n.minDistance.setValeur(Float.POSITIVE_INFINITY);
			n.precedent = null;
		}
	}
	
    /**
     * <p>Calcule 
     * @param listeNoeuds
     * @return 
     */
	private static float[] fillDistancier(List<Noeud> listeNoeuds) {
		float[] distances = new float[listeNoeuds.size()];

		int i = 0;
		for (Noeud noeud : listeNoeuds)
			distances[i++] = noeud.minDistance.getValeur();

		return distances;
	}
}
