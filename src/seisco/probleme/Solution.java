package seisco.probleme;

import jade.content.Concept;

/**
 * <p>Représente une solution à un {@link Probleme}.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @author Bruno Boi
 * @author Jérôme Scohy
 * @version 2012
 */
public abstract class Solution implements Concept {
    private static final long serialVersionUID = 910078695092759971L;
    
    /**
     * <p>Affiche la solution en console
     * @since 2012
     */
	public abstract void afficher();
}
