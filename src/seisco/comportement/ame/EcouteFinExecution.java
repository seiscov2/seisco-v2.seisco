/*
 * Propriété LXStudio
 */

package seisco.comportement.ame;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import seisco.agent.AgentMobileEchange;
import seisco.util.MessageHelper;

/**
 * <p>Ce comportement est à l'écoute d'une fin d'exécution envoyée par l'AMC.
 * <p>Le but est l'arret de l'AME.
 * <p>ACLMessage ID utilisé : 
 * <p>ID_FIN_EXEC
 * 
 * @author Jerome
 * @version 2012
 * @see seisco.comportement.amc.FinExecution
 */
public class EcouteFinExecution extends CyclicBehaviour {

    AgentMobileEchange ame;
    
    public EcouteFinExecution(AgentMobileEchange a) {
        super(a);
        this.ame = a;
    }
    
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(MessageHelper.ID_FIN_EXEC);
        
        ACLMessage msgRecu = ame.receive(mt);
        if(msgRecu != null) {
            if(msgRecu.getLanguage() != null && msgRecu.getLanguage().equals("JavaSerialization")) {
                if(msgRecu.getPerformative() == ACLMessage.INFORM) {
                    if(msgRecu.getSender().getLocalName().equals(ame.getAMC().getLocalName())) { // seul l'amc peut lui envoyer ce message
                        ame.deregisterDF();
                        ame.doDelete();
                    }
                }
            }
        } else
            block(1000);
    }

}
