package seisco.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.ControllerException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import seisco.algo.Algorithme;
import seisco.algo.JeuParametres;
import seisco.algo.Operateur;
import seisco.comportement.amc.EcouterDeplacement;
import seisco.comportement.amc.FinExecution;
import seisco.comportement.amc.PresenterSolution;
import seisco.probleme.Solution;
import seisco.util.Etat;

/**
 * <p>Agent de calcul qui exécutera l'algorithme et des comportements</p>
 * 
 * @author Jerome
 * @version 2012
 */
public abstract class AgentMobileCalcul extends Agent {
    protected Algorithme algo;
    
    protected Solution cacheSolution;
    
    protected List<Etat> etats = new ArrayList<Etat>();
    protected int currentGeneration;
    
    protected AID ame;
    protected AID ate;
    
    /**
     * <p>Initilisation de l'agent.
     * <ul>
     *  <li>Récupération de l'AME
     *  <li>Initilisation des Etats
     *  <li>Initilisation des comportements
     * </ul>
     * 
     * @since 2012
     * @see Etat
     * @see PresenterSolution
     * @see EcouterDeplacement
     * @see FinExecution
     */
    @Override
    public void setup() {
        this.algo = null;
        this.currentGeneration = 1;

        this.cacheSolution = null;
        
        // Construction AID de l'AME à partir de celui de l'AMC
        String fin_id = getAID().getLocalName().toString().substring(3);
        this.ame = new AID("ame" + fin_id, AID.ISLOCALNAME);
        
        // Initialisation des etats
        this.etats.add(new Etat("deplacement", false));
        this.etats.add(new Etat("initParam", false));
        this.etats.add(new Etat("initOperateurs", false));
        this.etats.add(new Etat("updateSolution", false));
        
        this.etats.add(new Etat("finExecution", false));
        
        // Initialisation des comportements
        addBehaviour(new PresenterSolution(this));
        addBehaviour(new EcouterDeplacement(this, 2000));
        addBehaviour(new FinExecution(this));
    }
    
    /**
     * <p>Action avant le déplacement de l'agent.
     * 
     * @since 2012
     * @see #afterMove()
     */
    @Override
    public void beforeMove() {
        setEtat("deplacement", true);
    }
    
    /**
     * <p>Action après le déplacement de l'agent.
     * 
     * @since 2012
     * @see #beforeMove()
     */
    @Override
    public void afterMove() {
        try {
            println("Déplacement vers " + this.getContainerController().getContainerName() + " effectué.");
        } catch (ControllerException ex) {
            println("Erreur: Impossible de récupérer le nom du container.");
        }
        
        // Execution de l'algo
        setEtat("deplacement", false);
    }
    
    /**
     * <p>Exécuté à l'arrêt de l'agent.
     * 
     * @since 2012
     */
    @Override
    public void takeDown() {
        this.println("Arrêt de l'AMC.");
    }
        
    /**
     * <p>Retourne l'algorithme associé à l'agent.
     * 
     * @since 2012
     * @return L'algoritme de l'agent
     * @see #setAlgo(Algorithme)
     */
    public Algorithme getAlgo() {
        return algo;
    }

    /**
     * <p>Remplace l'algoritme de l'agent.
     * 
     * @param algo 
     *      Le nouveau algorithme de l'agent
     * @since 2012
     * @see #getAlgo()
     */
    public void setAlgo(Algorithme algo) {
        this.algo = algo;
    }
    
    /**
     * <p>Retourne le numéro de l'exécution actulle de l'algorithme.
     * 
     * @return Le nombre d'exécution de l'algorithme
     * @since 2012
     * @see #setCurrentGeneration(int)
     */
    public int getCurrentGeneration() {
        return currentGeneration;
    }

    /**
     * <p>Remplace le numéro de l'exécution.
     * 
     * @param currentGeneration 
     *      Le nouveau numéro d'exécution
     * @since 2012
     * @see #getCurrentGeneration()
     */
    public void setCurrentGeneration(int currentGeneration) {
        this.currentGeneration = currentGeneration;
    }
    
    /**
     * <p>Retourne la {@link Solution} en cache.
     * 
     * @return La solution en cache
     * @since 2012
     * @see #setCacheSolution(Solution)
     * @see Solution
     */
    public Solution getCacheSolution() {
        return cacheSolution;
    }

    /**
     * <p>Remplace la solution en cache.
     * 
     * @param cacheSolution 
     *      La nouvelle solution - généralement meilleure que la précédente -
     * @since 2012
     * @see #getCacheSolution()
     * @see Solution
     */
    public void setCacheSolution(Solution cacheSolution) {
        this.cacheSolution = cacheSolution;
    }
    
    /**
     * <p>Retourne l'AME associé.
     * 
     * @return L'AID de l'AME
     * @since 2012
     * @see #setAME(AID)
     * @see AgentMobileEchange
     */
    public AID getAME() {
        return ame;
    }

    /**
     * <p>Retourne l'AME associé à l'AMC.
     * <p>L'AID est construit au {@link #setup()} à partir du nom de l'AMC.
     * @param ame L'AID de l'AME
     * @since 2012
     * @see #getAME()
     * @see AgentMobileEchange
     */
    public void setAME(AID ame) {
        this.ame = ame;
    }
    
    /**
     * <p>Retourne l'ATE associé.
     * 
     * @return L'AID de l'ATE
     * @since 2012
     * @see #setATE(AID)
     * @see AgentTransversalEchange
     */
   public AID getATE() {
        return ate;
    }

    /**
     * <p>Retourne l'ATE associé à l'AMC.
     * <p><b>Conseil:</b> Récupérer l'AID lors des premiers échanges avec l'ATE.
     * @param ate L'AID de l'ATE
     * @since 2012
     * @see #getATE()
     * @see AgentTransversalEchange
     */
    public void setATE(AID ate) {
        this.ate = ate;
    }
    
    /**
     * <p>Permet de modifier un état.
     * <p>Si l'état n'existe pas, il est créé.
     * 
     * @param nom
     *      Le nom de l'état à modifier
     * @param valeur
     *      La nouvelle valeur, généralement un <code>boolean</code>, 
     *      <code>null</code> pour supprimer l'état de la liste.
     * @return 
     *      <p><code>true</code> si l'état a bien été modifié, supprimé ou créé.
     *      <p><code>false</code> si l'état n'a pas pu être supprimé ou créé.
     * @since 2012
     * @see #getEtat(String)
     */
    public boolean setEtat(String nom, Object valeur) {
        for(Etat e : this.etats)
            if(e.getNom().equals(nom)) {
                if(valeur==null)
                    this.etats.remove(e);
                else
                    e.setValeur(valeur);
                
                return true;
            }
        
        return this.etats.add(new Etat(nom, valeur));
    }
    
    /**
     * <p>Retourne l'état demandé.
     * 
     * @param nom
     *      Le nom de l'état
     * @return L'état s'il existe, sinon <code>null</code>
     * @since 2012
     * @see #setEtat(String, Object)
     */
    public Etat getEtat(String nom) {
        for(Etat e : this.etats)
            if(e.getNom().equals(nom))
                return e;
        
        return null;
    }
    
    /**
     * <p>Permet de savoir si l'AMC est initialisé à partir d'états pré-éxistants.
     * <p>Liste des états utilisés: 
     * <ul>
     *  <li>initParam - Représente l'initialisation des parametres
     *  <li>initOperateurs - Représente l'initialisation des opérateurs de l'algoritme
     * 
     * @return <code>true</code> si initialisé, sinon <code>false</code>
     * @since 2012
     * @see #isInit(String) 
     * @see Etat
     * @see JeuParametres
     * @see Operateur
     */
    public boolean isInit() {
        return isInit("Param") && isInit("Operateurs");
    }
    
    /**
     * <p>Permet de savoir si l'AMC est initialisé à partir d'un état.
     * 
     * @param type
     *      Le nom de l'état à vérifier.
     * @return La valeur booléenne de l'état
     * @since 2012
     * @see #isInit()
     * @see Etat
     */
    public boolean isInit(String type) {
        return (Boolean)getEtat("init" + type).getValeur();
    }
    
    /**
     * <p>Retourne le parametre demandé.
     * 
     * @param nom
     *      Le nom du parametre
     * @return La valeur du parametre sous la forme d'un <code>Object</code>
     * @since 2012
     * @see JeuParametres
     * @see #getParametre(String, Class) 
     */
    public Object getParametre(String nom) {
        return this.algo.getParametres().getElement(nom).getValeur();
    }
    
    /**
     * <p>Retourne le parametre demandé.
     * <p>Le type de la valeur de retour dépendra du second argument.
     * <br/>
     * <p>Exemple pour récupérer un parametre de type <code>Integer</code>: 
     * <p><code>int i = getParametre("nom", Integer.class);</code>
     * 
     * @param nom
     *      Le nom du parametre
     * @param clazz
     *      La classe du parametre
     * @return Un objet de la classe du parametre <code>clazz</code>
     * @since 2012
     * @see JeuParametres
     * @see #getParametre(String)
     */
    public <T> T getParametre(String nom, Class<T> clazz) {
        T foo = (T) getParametre(nom);
        return foo;
    }

    /**
     * <p>Méthode d'affichage de texte qui ajoute le nom de l'agent au message.
     * <p>Cette méthode affiche le message sur la sortie standard de Java.
     * 
     * @param message 
     *      Le message qui sera affiché
     * @since 2012
     * @see #println(String, PrintStream)
     */
    public void println(String message) {
        println(message, System.out);
    }
    
    /**
     * <p>Méthode d'affichage de texte qui ajoute le nom de l'agent au message.
     * <p>Cette méthode affiche le texte sur le flux indiqué par le parametre <code>out</code>.
     * 
     * @param message
     *      Le message qui sera affiché
     * @param out 
     *      La sortie d'affichage du message
     * @since 2012
     * @see #println(String)
     */
    public void println(String message, PrintStream out) {
        out.println("("+this.getLocalName()+") " + message);
    }
}
