package seisco.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import seisco.comportement.ame.*;
import seisco.probleme.Solution;
import seisco.util.*;
import seisco.util.yaml.file.YamlConfiguration;

/**
 * <p>Agent d'échange de solution au niveau d'une même plateforme JADE.
 * 
 * @author Jerome
 * @version 2012
 */
public abstract class AgentMobileEchange extends Agent implements AmeDf {
    protected AID cacheDemandeurSolution;
    protected List<Solution> solutionsAMEs;
    protected List<AID> ames = new ArrayList<AID>();
    protected AID amc;
    protected AID ate;
    
    protected Solution cacheSolution;
    
    protected List<String> nextMachines = new ArrayList<String>();
    protected int currentMachine;
    
    protected List<Etat> etats = new ArrayList<Etat>();
    protected List<Parametre> params = new ArrayList<Parametre>();
    
    /**
     * <p>Initilisation de l'agent.
     * <ul>
     *  <li>Récupération de l'AMC
     *  <li>Initilisation des Etats
     *  <li>Initilisation des comportements
     * </ul>
     * 
     * @since 2012
     * @see Etat
     * @see SurveillerMachine
     * @see EcouteFinExecution
     */
    @Override
    public void setup() { 
        this.cacheDemandeurSolution = null;
        this.cacheSolution = null;
        this.ate = null;
        
        println("-- SETUP --");
        
        // Construction AID de l'AMC à partir de celui de l'AME
        String fin_id = getAID().getLocalName().toString().substring(3);
        this.amc = new AID("amc" + fin_id, AID.ISLOCALNAME);
        
        //for(int i=1;i<=5;i++)
        //    this.nextMachines.add("c" + i);
        
        this.currentMachine = 0;
        
        // Initialisation des etats
        this.etats.add(new Etat("envoiSolution", false));
        this.etats.add(new Etat("firstMove", true));
        
        // Initialisation des parametres
        this.params.add(new Parametre("charge_cpu", 30));
        
        // Chargement configuration
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(new File("conf"+System.getProperty("file.separator")+"conf_ame_amc.yml"));
        
        this.setParam("charge_cpu", conf.getInt("machine.cpu", 30));
        
        // Initialisation des comportements
        if(!System.getProperty("os.name").toLowerCase().contains("win")) { // Seulement si non windows
            addBehaviour(new SurveillerMachine(this));
        }
        addBehaviour(new EcouteFinExecution(this));
    }
    
    /**
     * <p>Action avant le déplacement de l'agent.
     * <p>L'AME se désinscrit du DF.
     * 
     * @since 2012
     * @see #afterMove()
     * @see #deregisterDF() 
     */
    @Override
    public void beforeMove() {
        // Incrémentation du lieu à atteindre
        int id = this.currentMachine+1;
        if(id>=this.nextMachines.size()) id=0;
        
        // Désinscription au DF
        if(!(Boolean)getEtat("firstMove").getValeur())
            deregisterDF();
    }
    
        
    /**
     * <p>Action après le déplacement de l'agent.
     * <p>Enregistrement au DF.
     * <p>Passe l'ordre à l'AMC associé de se déplacer également.
     * 
     * @since 2012
     * @see #beforeMove()
     * @see #registerDF() 
     * @see #deplacerAMC(String) 
     */
    @Override
    public void afterMove() {
        if((Boolean)getEtat("firstMove").getValeur())
            setEtat("firstMove", false);
            
        // Vérification de la machine d'arrivée (mise à jour de currentMachine)
        String nomCont="Main-Container";
        try {
            nomCont = this.getContainerController().getContainerName();
            
            if(this.nextMachines.size()>this.currentMachine && !this.nextMachines.get(this.currentMachine).equals(nomCont)) {
                boolean ok=false;
                for(int i=0; i<this.nextMachines.size() && !ok; i++)
                    if(this.nextMachines.get(i).equals(nomCont)) {
                        ok=true;
                        this.currentMachine=i;
                    }
            }
            
            println("Déplacement vers " + this.nextMachines.get(this.currentMachine) + " effectué.");
        } catch (ControllerException ex) {
            println("Erreur: Impossible de récupérer le nom du container.");
        }     
        
        // Déplacer l'AMC
        deplacerAMC(nomCont);
        
        // Inscription au DF
        registerDF();
    }
    
        
    /**
     * <p>Exécuté à l'arrêt de l'agent.
     * <p>Se désinscrit du DF.
     * 
     * @since 2012
     */
    @Override
    public void takeDown() {
        this.println("Arrêt de l'AME.");
        deregisterDF();
    }
    
    /**
     * <p>Permet d'envoyer un message à l'AMC pour qu'il se déplace.
     * 
     * @param next 
     *      Nom du container où il doit se déplacer
     * @since 2012
     */
    protected void deplacerAMC(String next) {
        MessageHelper mh = new MessageHelper();
        mh.create(ACLMessage.INFORM, MessageHelper.ID_DEPL_AMC);
        mh.addReceiver(this.amc);
        
        send(mh.get(next));
    }
    
    /**
     * <p>Permet de s'inscrire au DF.
     * 
     * @return <code>true</code> si l'enregistrement est effectué, sinon <code>false</code>.
     * @since 2012
     * @see AmeDf
     */
    @Override
    public boolean registerDF() {
        // Service
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("ame");
        sd.setName(getLocalName());
        // Description
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID()); 
        dfd.addServices(sd);
        
        try {  
            DFService.register(this, dfd );  
            return true;
        }
        catch (FIPAException fe) {
            println("Erreur: Impossible de s'enregistrer au DF.");
            return false;
        }
    }
    
    /**
     * <p>Permet de récupérer la liste de tous les AMEs inscrit au DF.
     * 
     * @return Une liste de AID des AMEs inscrits
     * @since 2012
     * @see AmeDf
     */
    @Override
    public List<AID> getAmesDF() {
        // Service
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("ame");
        // Description
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        
        DFAgentDescription[] result=null;
        try {
            result = DFService.search(this, dfd);
        } catch(FIPAException ex) {
            println("Erreur: Impossible de faire de recherche dans le DF.");
        }
        
        List<AID> ames = new ArrayList<AID>();
        for(int i=0; result!=null && i<result.length; i++)
            ames.add(result[i].getName());
        
        return ames;
    }
    
    /**
     * <p>Permet de se désinscrire du DF.
     * 
     * @return <code>true</code> si la désinscription est effectuée, sinon <code>false</code>.
     * @since 2012
     * @see AmeDf
     */
    @Override
    public boolean deregisterDF() {
        try {
            DFService.deregister(this);
            return true;
        } catch(FIPAException ex) {
            println("Erreur: Impossible de désinscrire l'agent du DF.");
            return false;
        }
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
     * @see Etat
     */
    public Etat getEtat(String nom) {
        for(Etat e : this.etats)
            if(e.getNom().equals(nom))
                return e;
        
        return null;
    }
   
    /**
     * <p>Retourne la Solution en cache.
     * 
     * @return La solution en cache, ou <code>null</code> si aucune solution disponible.
     * @since 2012
     * @see #setCacheSolution(Solution) 
     * @see Solution
     */
    public Solution getCacheSolution() {
        return this.cacheSolution;
    }

    /**
     * <p>Remplace la solution en cache.
     * 
     * @param cacheSolution 
     *      La nouvelle solution
     * @since 2012
     * @see #getCacheSolution() 
     * @see Solution
     */
    public void setCacheSolution(Solution cacheSolution) {
        this.cacheSolution = cacheSolution;
    }
    
    /**
     * <p>Permet de modifier un parametre.
     * 
     * @param nom
     *      Le nom du parametre à modifier
     * @param valeur
     *      La nouvelle valeur.
     *      <code>null</code> pour supprimer le parametre de la liste.
     * @return 
     *      <p><code>true</code> si le parametre a bien été modifié ou supprimé.
     *      <p><code>false</code> si le parametre n'a pas pu être supprimé ou n'existe pas.
     * @since 2012
     * @see #getParam(String)
     */
    public boolean setParam(String nom, Object valeur) {
        for(Parametre p : this.params)
            if(p.getNom().equals(nom)) {
                if(valeur==null)
                    this.params.remove(p);
                else
                    p.setValeur(valeur);
                
                return true;
            }
        
        return false;
    }
    
    /**
     * <p>Retourne le parametre demandé.
     * 
     * @param nom
     *      Le nom du parametre
     * @return Le parametre s'il existe, sinon <code>null</code>
     * @since 2012
     * @see #setParam(String, Object)
     * @see Parametre
     */
    public Parametre getParam(String nom) {
        for(Parametre p : this.params)
            if(p.getNom().equals(nom))
                return p;
        
        return null;
    }

    /**
     * <p>Retourne l'indice de la machine actuelle.
     * 
     * @return 
     *      Un entier représentant la machine actuelle.
     * @since 2012
     * @see #setCurrentMachine(int)
     */
    public int getCurrentMachine() {
        return this.currentMachine;
    }

    /**
     * <p>Remplace l'indice actuelle.
     * 
     * @param currentMachine 
     *      Le nouvel indicde
     * @since 2012
     * @see #getCurrentMachine() 
     */
    public void setCurrentMachine(int currentMachine) {
        this.currentMachine = currentMachine;
    }

    /**
     * <p>Retourne la liste des machines.
     * 
     * @return Une liste des machines (nom des containeurs)
     * @since 2012
     * @see #setCurrentMachine(int) 
     */
    public List<String> getNextMachines() {
        return this.nextMachines;
    }

    /**
     * <p>Remplace la liste des machines.
     * 
     * @param nextMachines 
     *      Une liste de String avec le nom des containeurs
     * @since 2012
     * @see #getNextMachines()
     */
    public void setNextMachines(List<String> nextMachines) {
        this.nextMachines = nextMachines;
    }

    /**
     * <p>Retourne l'AID de l'AMC associé.
     * 
     * @return L'AID de l'AMC
     * @since 2012
     */
    public AID getAMC() {
        return this.amc;
    }

    /**
     * <p>Retourne l'AID de l'AME faisant une demande de solution.
     * 
     * @return L'AID d'un AME
     * @since 2012
     * @see #setCacheDemandeurSolution(AID) 
     */
    public AID getCacheDemandeurSolution() {
        return cacheDemandeurSolution;
    }
    
    /**
     * <p>Remplace l'AID de l'AME faisant une demande de solution. 
     * 
     * @param cacheDemandeurSolution 
     *      L'AID du nouveau AME
     * @since 2012
     * @see #getCacheDemandeurSolution() 
     */
    public void setCacheDemandeurSolution(AID cacheDemandeurSolution) {
        this.cacheDemandeurSolution = cacheDemandeurSolution;
    }

    /**
     * <p>Retourne la liste des AMEs de la plateforme.
     * 
     * @return Une liste d'AID des AMEs, ou <code>null</code> si la liste n'a jamais été chargée.
     * @since 2012
     * @see #setAMEs(List) 
     */
    public List<AID> getAMEs() {
        return ames;
    }

    /**
     * <p>Remplace la liste des AMEs.
     * 
     * @param ames 
     *      La nouvelle liste d'AMEs
     * @since 2012
     * @see #getAMEs() 
     */
    public void setAMEs(List<AID> ames) {
        this.ames = ames;
    }
    
    /**
     * <p>Ajoute l'AID d'un AME à la liste des AMEs.
     * 
     * @param ame
     *      Le nouvel AME
     * @return <code>true</code> si l'AID a été ajouté, sinon <code>false</code>
     * @since 2012.
     * @see #removeAME(AID) 
     */
    public boolean addAME(AID ame) {
        return this.ames.add(ame);
    }
    
    /**
     * <p>Retire l'AID d'un AME de la liste des AMEs.
     * @param ame
     *      L'AID à retirer
     * @return <code>true</code> si l'AID a été retiré, sinon <code>false</code>
     * @since 2012
     * @see #addAME(AID) 
     */
    public boolean removeAME(AID ame) {
        return this.ames.remove(ame);
    }

    /**
     * <p>Retourne la liste des Solutions envoyées par les autres AMEs.
     * 
     * @return Une liste de Solution
     * @since 2012
     * @see #setSolutionsAMEs(List) 
     * @see Solution
     */
    public List<Solution> getSolutionsAMEs() {
        return solutionsAMEs;
    }

    /**
     * <p>Remplace la liste des solutions.
     * 
     * @param solutionsAMEs 
     *      La nouvelle liste de solutions.
     * @since 2012
     * @see #getSolutionsAMEs() 
     * @see Solution
     */
    public void setSolutionsAMEs(List<Solution> solutionsAMEs) {
        this.solutionsAMEs = solutionsAMEs;
    }
    
    /**
     * <p>Permet d'ajouter une Solution à la liste.
     * 
     * @param s
     *      La Solution a ajouter
     * @return <code>true</code> si la Solution a été ajoutée, sinon <code>false</code>
     * @since 2012
     * @see Solution
     */
    public boolean addSolutionAME(Solution s) {
        return this.solutionsAMEs.add(s);
    }
    
    /**
     * <p>Retourne l'AID de l'ATE.
     * 
     * @return L'AID de l'ATE
     * @since 2012
     * @see #setATE(AID) 
     */
    public AID getATE() {
        return this.ate;
    }
    
    /**
     * <p>Remplace l'AID de l'ATE.
     * @param ate 
     *      Le nouvelle AID
     * @since 2012
     * @see #getATE() 
     */
    public void setATE(AID ate) {
        this.ate = ate;
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
