package seisco.agent;

import jade.core.AID;
import jade.core.Agent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import seisco.comportement.ate.EcouteFinExecution;
import seisco.comportement.ate.RecupererSolution;
import seisco.probleme.Solution;
import seisco.util.Etat;
import seisco.util.yaml.file.YamlConfiguration;

/**
 * <p>Agent d'échange de solution entre plusieurs plateformes.
 * <p>Agent de gestion de sa plateforme.
 * 
 * @author Jerome
 * @version 2012
 */
public abstract class AgentTransversalEchange extends Agent {
    protected List<AID> aidAte;
    protected List<String> ipAcc;
    protected List<String> nomCont;
    protected List<AID> ames;
    
    protected Solution cacheSolution;
    protected AID cacheAMC;
    
    protected List<Etat> etats;
    
    protected YamlConfiguration conf;
    
    protected List<AID> ates;
    protected AID cacheDemandeurSolution;
    
    /**
     * <p>Initilisation de l'agent.
     * <ul>
     *  <li>Chargement de la configuration
     *  <li>Initilisation des Etats
     *  <li>Initilisation des comportements
     * </ul>
     * 
     * @since 2012
     * @see Etat
     * @see RecupererSolution
     * @see EcouteFinExecution
     */
    @Override
    public void setup() {
        this.aidAte = new ArrayList<AID>();
        this.ipAcc = new ArrayList<String>();
        this.nomCont = new ArrayList<String>();
        this.ames = new ArrayList<AID>();
        this.cacheSolution = null;
        this.cacheAMC = null;
        
        this.ates = new ArrayList<AID>();
        
        // Initialisation des IP des ATE
        String pathConf = getProperty("confate", "conf"+System.getProperty("file.separator")+"conf_ate.yml");
        this.conf = YamlConfiguration.loadConfiguration(new File(pathConf));
        
        if(conf.isSet("ate")) {
            
            List<Map<String,Object>> ates = conf.getMapList("ate");
            for(Map<String, Object> a : ates) {
                String ip = (String)a.get("ip");
                String ate = (String)a.get("ate");
                
                int portAcc = 7778;
                if(a.containsKey("acc"))
                    portAcc = Integer.parseInt(a.get("acc").toString());
                
                String ipAcc = "http://"+ip+":"+portAcc+"/acc";
                AID aidAte = new AID(ate+"", AID.ISGUID);
                aidAte.addAddresses(ipAcc);
                
                try {
                    InetAddress ia = InetAddress.getByName(ip);
                    if(ia.isReachable(2000)) {
                        this.aidAte.add(aidAte);
                        this.ipAcc.add(ipAcc);
                    }
                } catch (IOException ex) {
                    println("Erreur : Problème de contact d'une IP ATE");
                }
            }
        }
        
        // Lecture des noms de containers
        if(conf.isList("containers")) {
            for(String cont : conf.getStringList("containers")) {
                this.nomCont.add(cont);
            }
        }
        
        // Initialisation des etats
        this.etats = new ArrayList<Etat>();
        this.etats.add(new Etat("demandeSolution", false));
        this.etats.add(new Etat("solutionPresente", false));
        this.etats.add(new Etat("finRecupSolution", false));
        this.etats.add(new Etat("envoiSolution", false));
        this.etats.add(new Etat("solutionsRecues", false));
        
        // Initialisation des comportements
        addBehaviour(new RecupererSolution(this));
        addBehaviour(new EcouteFinExecution(this));
    }
    
    /**
     * <p>Retourne la liste des AMEs.
     * 
     * @return Les AIDs des AMEs
     * @since 2012
     * @see #setAME(List) 
     */
    public List<AID> getAME() {
        return ames;
    }

    /**
     * <p>Remplace la liste des AMEs.
     * 
     * @param ame
     *      La nouvelle liste des AIDs des AMEs
     * @since 2012
     * @see #getAME() 
     */
    public void setAME(List<AID> ame) {
        this.ames = ame;
    }
    
    /**
     * <p>Permet d'ajouter un AME à la liste.
     * <p>Un AID sera unique dans la liste.
     * 
     * @param ame
     *      L'AID de l'AME à ajouter
     * @return <code>true</code> si l'AID peut être ajouté, sinon <code>false</code>
     * @since 2012
     * @see #removeAME(AID) 
     */
    public boolean addAME(AID ame) {
        if(!this.ames.contains(ame))
            return this.ames.add(ame);
        
        return false;
    }
    
    /**
     * <p>Permet de retirer un AME de la liste.
     * 
     * @param ame
     *      L'AID de l'AME à retirer
     * @return <code>true</code> si l'AME est retiré, sinon <code>false</code>
     */
    public boolean removeAME(AID ame) {
        return this.ames.remove(ame);
    }

    /**
     * <p>Retourne l'AMC en cache.
     * <p>Rappel: l'AMC en cache est celui qui possède la meilleure solution.
     * 
     * @return  L'AID de l'AMC en cache
     * @since 2012
     * @see #setCacheAMC(AID) 
     */
    public AID getCacheAMC() {
        return cacheAMC;
    }

    /**
     * <p>Remplace l'AMC en cache.
     * 
     * @param cacheAMC 
     *      Le nouvelle AID de l'AMC qui sera mis en cache
     * @since 2012
     * @see #getCacheAMC() 
     */
    public void setCacheAMC(AID cacheAMC) {
        this.cacheAMC = cacheAMC;
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
        return cacheSolution;
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
     * <p>Retourne une liste des AID des ATEs.
     * 
     * @return 
     *      Une liste d'AID représentant les ATEs
     * @since 2012
     * @see #setAidAte(List) 
     */
    public List<AID> getAidAte() {
        return aidAte;
    }

    /**
     * <p>Remplace la liste des AID des ATEs.
     * 
     * @param aidAte 
     *      La nouvelle liste d'AID
     * @since 2012
     * @see #getAidAte() 
     */
    public void setAidAte(List<AID> aidAte) {
        this.aidAte = aidAte;
    }
    
    /**
     * <p>Retourne une liste des adresses des MTPs.
     * 
     * @return 
     *      Une liste de chaine de caractère représentant les adresses http des MTPs
     * @since 2012
     * @see #setIpAcc(List) 
     */
    public List<String> getIpAcc() {
        return ipAcc;
    }

    /**
     * <p>Remplace la liste des adresses des MTPs.
     * 
     * @param ipAcc
     *      La nouvelle liste d'adresses
     * @since 2012
     * @see #getIpAcc() 
     */
    public void setIpAcc(List<String> ipAcc) {
        this.ipAcc = ipAcc;
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
        
        return false;
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
     * <p>Retourne la liste des noms des containeurs.
     * 
     * @return 
     *      Une liste de chaine de caractère contenant la liste des containeurs
     * @since 2012
     * @see #setNomCont(List) 
     */
    public List<String> getNomCont() {
        return nomCont;
    }
    
    /**
     * <p>Remplace la liste des noms de containeurs.
     * 
     * @param nomCont
     *      La nouvelle liste des noms
     * @since 2012
     * @see #getNomCont() 
     */
    public void setNomCont(List<String> nomCont) {
        this.nomCont = nomCont;
    }
    
        /**
     * <p>Retourne la liste des ATEs.
     * 
     * @return Une liste d'AID des ATEs, ou <code>null</code> si la liste n'a jamais été chargée.
     * @since 2012
     * @see #setATEs(List) 
     */
    public List<AID> getATEs() {
        return ates;
    }

    /**
     * <p>Remplace la liste des ATEs.
     * 
     * @param ates 
     *      La nouvelle liste d'ATEs
     * @since 2012
     * @see #getATEs() 
     */
    public void setATEs(List<AID> ates) {
        this.ates = ates;
    }
    
        /**
     * <p>Retourne l'AID de l'ATE faisant une demande de solution.
     * 
     * @return L'AID d'un ATE
     * @since 2012
     * @see #setCacheDemandeurSolution(AID) 
     */
    public AID getCacheDemandeurSolution() {
        return cacheDemandeurSolution;
    }
    
    /**
     * <p>Remplace l'AID de l'ATE faisant une demande de solution. 
     * 
     * @param cacheDemandeurSolution 
     *      L'AID du nouveau ATE
     * @since 2012
     * @see #getCacheDemandeurSolution() 
     */
    public void setCacheDemandeurSolution(AID cacheDemandeurSolution) {
        this.cacheDemandeurSolution = cacheDemandeurSolution;
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
