package seisco.comportement.amc;

import jade.core.ContainerID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import seisco.agent.AgentMobileCalcul;
import seisco.util.MessageHelper;

/**
 * <p>Ce comportement est à l'écoute de toutes les demandes de déplacement venant de l'AME associé.
 * <p>ACLMessage ID utilisé :
 * <p>ID_DEPL_AMC
 * 
 * @author Jerome
 * @version 2012
 */
public class EcouterDeplacement extends TickerBehaviour {

    public EcouterDeplacement(AgentMobileCalcul a, long tick) {
        super(a, tick);
    }
    
    @Override
    protected void onTick() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(MessageHelper.ID_DEPL_AMC);
        ACLMessage msgRecu = myAgent.receive(mt);
        if(msgRecu != null) {
            if(msgRecu.getPerformative() == ACLMessage.INFORM) {
                if(msgRecu.getLanguage()!=null && msgRecu.getLanguage().equals("JavaSerialization")) {
                    if(msgRecu.getContent() == null)
                        throw new NullPointerException();
                    
                    String nomCont = msgRecu.getContent();
                    ContainerID loc = new ContainerID();
                    loc.setName(nomCont);
                    myAgent.doMove(loc);
                }
            }
        }
    }

}
