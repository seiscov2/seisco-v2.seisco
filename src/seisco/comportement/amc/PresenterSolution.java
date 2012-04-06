package seisco.comportement.amc;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import seisco.agent.AgentMobileCalcul;
import seisco.util.ObjectCodec;
import seisco.util.MessageHelper;

/**
 * <p>Ce comportement à pour rôle de répondre aux demandes de solutions en provenance de l'AME et de l'ATE.
 * <p>ACLMessage ID utilisé :
 * <p>ID_RECUP_AMC_SOLUTION
 * 
 * @author Jerome
 * @version 2012
 * @see seisco.comportement.ame.RecupererSolution
 * @see seisco.comportement.ate.RecupererSolution
 */
public class PresenterSolution extends CyclicBehaviour {
    
    private AgentMobileCalcul amc;
    
    public PresenterSolution(AgentMobileCalcul a) {
        super(a);
        this.amc = a;
    }
    
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(MessageHelper.ID_RECUP_AMC_SOLUTION);
        ACLMessage msgRecu = amc.receive(mt);
        if(msgRecu != null) {
            MessageHelper rep = new MessageHelper();
            
            if(msgRecu.getPerformative() == ACLMessage.QUERY_IF) {
                if(msgRecu.getLanguage()!=null && msgRecu.getLanguage().equals("JavaSerialization")) {
                    if(msgRecu.getContent() == null)
                        throw new NullPointerException();

                    if(msgRecu.getContent().equals("getSolution")) {
                        
                        amc.setEtat("presenteSolution", true);
                        
                        rep.reset();
                        rep.create(ACLMessage.INFORM, MessageHelper.ID_RECUP_AMC_SOLUTION);
                        rep.addReceiver(msgRecu.getSender());
                        //rep.setObject(amc.getCacheSolution());
                        try {
                            amc.send(rep.get(ObjectCodec.encode(amc.getCacheSolution())));
                        } catch(Exception ex) {
                            amc.println("Erreur: Impossible d'encoder la solution\n\t(Raison: "+ex.getMessage()+")");
                        }
                        
                        amc.setEtat("presenteSolution", false);
                    } else {
                        rep.create(ACLMessage.REFUSE, MessageHelper.ID_RECUP_AMC_SOLUTION);
                        rep.addReceiver(msgRecu.getSender());
                        amc.send(rep.get("Parametre requete inconnu: " + msgRecu.getContent()));
                    }
                } else {
                    rep.create(ACLMessage.REFUSE, MessageHelper.ID_RECUP_AMC_SOLUTION);
                    rep.addReceiver(msgRecu.getSender());
                    amc.send(rep.get("Accepte requete de type QUERY_IF uniquement"));
                }
            } else {
                rep.create(ACLMessage.NOT_UNDERSTOOD, MessageHelper.ID_RECUP_AMC_SOLUTION);
                rep.addReceiver(msgRecu.getSender());
                amc.send(rep.get("Accepte requete de type QUERY_IF uniquement"));
            }
        } else
            block(1000);
    }

}
