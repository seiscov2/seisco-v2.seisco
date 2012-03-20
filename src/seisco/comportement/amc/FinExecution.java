package seisco.comportement.amc;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import seisco.agent.AgentMobileCalcul;
import seisco.util.MessageHelper;

/**
 * <p>Ce comportement est exécuté une fois que l'état <b>finExecution</b> est passé à <code>true</code>.
 * <p>Il a pour rôle d'informer l'ATE d'une fin d'exécution de l'algorithme et de prévenir son AME pour qu'il s'arrete.
 * 
 * <p>ACLMessage ID utilisé :
 * <p>ID_FIN_EXEC
 * @author Jerome
 * @version 2012
 * @see seisco.comportement.ame.EcouteFinExecution
 * @see seisco.comportement.ate.EcouteFinExecution
 */
public class FinExecution extends CyclicBehaviour {

    private AgentMobileCalcul amc;
    private int etape;
    
    public FinExecution(AgentMobileCalcul a) {
        super(a);
        this.amc = a;
        this.etape = 1;
    }
    
    @Override
    public void action() {
        if((Boolean)amc.getEtat("finExecution").getValeur()) {
            if(this.etape == 1) { // Envoie message à l'ATE
                MessageHelper mh = new MessageHelper();
                mh.create(ACLMessage.INFORM, MessageHelper.ID_FIN_EXEC);
                mh.addReceiver(amc.getATE());
                amc.send(mh.get());
                
                this.etape = 2;
                
            } else if(this.etape == 2) { // Reception de confirmation
                MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(MessageHelper.ID_FIN_EXEC),
                                                         MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
                ACLMessage msgRecu = amc.receive(mt);
                
                if(msgRecu != null) {
                    if(msgRecu.getLanguage() != null && msgRecu.getLanguage().equals("JavaSerialization")) {
                        MessageHelper mh = new MessageHelper();
                        mh.create(ACLMessage.INFORM, MessageHelper.ID_FIN_EXEC);
                        mh.addReceiver(amc.getAME());
                        amc.send(mh.get());
                        
                        amc.doDelete();
                    }
                }
            }
        }
    }

}
