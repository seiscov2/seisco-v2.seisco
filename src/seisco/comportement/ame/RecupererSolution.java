package seisco.comportement.ame;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import seisco.agent.AgentMobileEchange;
import seisco.probleme.Solution;
import seisco.util.ObjectCodec;
import seisco.util.MessageHelper;

/**
 * <p>Ce comportement sert à envoyé une demande à l'AMC pour récupérer sa solution.
 * <p>ACLMessage ID utilisé : 
 * <p>ID_RECUP_AMC_SOLUTION
 * 
 * @author Jerome
 * @since 2012
 * @see seisco.comportement.amc.PresenterSolution
 */
public class RecupererSolution extends CyclicBehaviour {

    private int etape;
    private AgentMobileEchange ame;
    
    public RecupererSolution(AgentMobileEchange a) {
        super(a);
        etape = 0;
        this.ame = a;
    }
    
    @Override
    public void action() {
        if((Boolean)ame.getEtat("demandeSolution").getValeur()) {
            switch(etape) {
                case 0:
                    if(ame.getAMC() != null) {
                        MessageHelper mh = new MessageHelper();

                        mh.create(ACLMessage.QUERY_IF, MessageHelper.ID_RECUP_AMC_SOLUTION);
                        mh.addReceiver(ame.getAMC());
                        ame.send(mh.get("getSolution"));
                        etape = 1;
                    }
                break;
                case 1:
                    MessageTemplate mt = MessageTemplate.MatchConversationId(MessageHelper.ID_RECUP_AMC_SOLUTION);
                    ACLMessage msgRecu = ame.receive(mt);
                    if(msgRecu != null) {
                        if(msgRecu.getPerformative() == ACLMessage.REFUSE) { // AMC non pret
                            ame.println("Erreur: L'AMC n'est pas pret\n\t(Raison: " + msgRecu.getContent() +")");
                            etape=0;
                            ame.doWait(30);
                        } else if(msgRecu.getPerformative() == ACLMessage.INFORM) {
                            if(msgRecu.getLanguage()!=null && msgRecu.getLanguage().equals("JavaSerialization")) {

                                Solution s = null;
                                try {
                                    s = ObjectCodec.decode(msgRecu.getContent(), Solution.class);
                                } catch(Exception ex) {
                                    ame.println("Erreur: Impossible de décoder la solution\n\t(Raison: "+ex.getMessage()+")");
                                }

                                ame.setCacheSolution(s);
                                
                                ame.setEtat("demandeSolution", false);
                                ame.setEtat("solutionPresente", true);
                                etape = 0;
                            } else
                                ame.println("Erreur: Langage incorrect");
                        } else
                            block(1000);
                    } else 
                        block(1000);
                break;
            }
        } else
            block(1000);
    }

}
