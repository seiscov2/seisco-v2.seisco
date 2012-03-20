/*
 * Propriété LXStudio
 */

package seisco.comportement.ate;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import seisco.agent.AgentTransversalEchange;
import seisco.util.MessageHelper;

/**
 * <p>Ce comportement est à l'écoute d'une fin d'exécution envoyée par l'AMC.
 * <p>Le but est l'éventuelle récupération de la meilleure solution de l'AMC.
 * <p>ACLMessage ID utilisé : 
 * <p>ID_FIN_EXEC
 * 
 * @author Jerome
 * @version 2012
 * @see seisco.comportement.amc.FinExecution
 */
public class EcouteFinExecution extends CyclicBehaviour {

    private AgentTransversalEchange ate;
    
    public EcouteFinExecution(AgentTransversalEchange a) {
        super(a);
        this.ate = a;
    }
    
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(MessageHelper.ID_FIN_EXEC);
        ACLMessage msgRecu = ate.receive(mt);
        if(msgRecu != null) {
            if(msgRecu.getPerformative() == ACLMessage.INFORM) {
                
                // On vérifie si c'est le meilleur AMC
                if(msgRecu.getSender() == ate.getCacheAMC()) {
                    // On vérifie le cache
                    if(ate.getCacheSolution() == null) {
                        // On récupère la solution
                        ate.setEtat("finRecupSolution", true);
                        ate.setEtat("demandeSolution", true);
                    } else {
                       // Envoi du message de fin
                        envoiFinCouple(msgRecu.getSender()); 
                    }
                } else {
                    // Envoi du message de fin
                    envoiFinCouple(msgRecu.getSender());
                }
                
            }
        }
        
        // Dans tous les cas, on vérifie si on attendait pas une solution
        // pour détruire un agent
        if((Boolean)ate.getEtat("finRecupSolution").getValeur()) {
            // Si on a la solution
            if((Boolean)ate.getEtat("solutionPresente").getValeur()) {
                ate.setEtat("finRecupSolution", false);
                
                envoiFinCouple(ate.getCacheAMC());
            }
        }
    }
    
    /**
     * <p>Permet d'envoyer un message d'arret à l'AMC.
     * <p>Retire également le controlleur AME associé.
     * 
     * @param amc 
     *      L'AID de l'AMC à qui il faut confirmer l'arret.
     * @since 2012
     */
    protected void envoiFinCouple(AID amc) {
        MessageHelper mh = new MessageHelper();
        mh.create(ACLMessage.CONFIRM, MessageHelper.ID_FIN_EXEC);
        mh.addReceiver(amc);
        ate.send(mh.get());
    }
}
