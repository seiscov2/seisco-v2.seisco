package seisco.algo;

import jade.content.Concept;

/**
 * <p>Représente un opérateur pour les {@link Algorithme}
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @version 2008
 */
public abstract class Operateur implements Concept {
    
    /**
     * <p>Applique l'opérateur sur un set d'opérandes
     * 
     * @param operandes les {@link Object} à opérer
     * @return
     *      le nouveau set d'{@link Object} sur
     *      lequel l'opérateur a été appliqué.
     * @since 2008
     */
    public abstract Object[] operate(Object...operandes);
}
