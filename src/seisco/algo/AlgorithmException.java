package seisco.algo;

/**
 * <p>Levée si une erreur survient pendant l'exécution d'un {@link Algorithme}.
 * 
 * @author Kamel Belkhelladi
 * @author Frank Réveillère
 * @version 2008
 */
public class AlgorithmException extends Exception {
    
    /**
     * <p>Lève une exception sans préciser de raison.
     * 
     * @since 2008
     */
    public AlgorithmException() {
        super();
    }

    /**
     * <p>Lève une exception en précisant la cause.
     * 
     * @param message la raison de lever l'exception
     * @since 2008
     */
    public AlgorithmException(String message) {
        super(message);
    }

    /**
     * <p>Lève une exception en précisant le {@link Throwable} à lancer.
     * 
     * @param t l'objet à lancer
     * @since 2008
     */
    public AlgorithmException(Throwable t) {
        super(t);
    }

    /**
     * <p>
     * Lève une exception en précisant la
     * cause et le {@link Throwable} à lancer.
     * </p>
     * 
     * @param message la raison de lever l'exception
     * @param t l'objet à lancer
     * @since 2008
     */
    public AlgorithmException(String message, Throwable t) {
        super(message, t);
    }
}
