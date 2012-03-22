package seisco.comportement.ate;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import seisco.agent.AgentTransversalEchange;
import seisco.probleme.Solution;
import seisco.util.MessageCodec;
import seisco.util.MessageHelper;

/**
 * <p>Ce comportement sert à envoyé une demande à l'AMC pour récupérer sa solution.
 * <p>ACLMessage ID utilisé : 
 * <p>ID_RECUP_AMC_SOLUTION
 * 
 * @author Jerome
 * @version 2012
 * @see seisco.comportement.amc.PresenterSolution
 */
public class RecupererSolution extends CyclicBehaviour {

    private int etape;
    private AgentTransversalEchange ate;
    
    public RecupererSolution(AgentTransversalEchange a) {
        super(a);
        this.ate = a;
        etape = 0;
    }
    
    @Override
    public void action() {
        if((Boolean)(ate.getEtat("demandeSolution").getValeur())) {
            switch(etape) {
                case 0:
                    if(ate.getCacheAMC() != null) {
                        MessageHelper mh = new MessageHelper();

                        mh.create(ACLMessage.QUERY_IF, MessageHelper.ID_RECUP_AMC_SOLUTION);
                        mh.addReceiver(ate.getCacheAMC());
                        ate.send(mh.get("getSolution"));
                        
                        etape = 1;
                    }
                break;
                case 1:
                    MessageTemplate mt = MessageTemplate.MatchConversationId(MessageHelper.ID_RECUP_AMC_SOLUTION);
                    ACLMessage msgRecu = ate.receive(mt);
                    if(msgRecu != null) {
                        if(msgRecu.getPerformative() == ACLMessage.REFUSE) { // AMC non pret
                            System.out.println("Erreur : L'AMC n'est pas pret");
                            etape=0;
                            ate.doWait(5);
                        } else if(msgRecu.getPerformative() == ACLMessage.INFORM) {
                            if(msgRecu.getLanguage()!=null && msgRecu.getLanguage().equals("JavaSerialization")) {

                                Solution s = null;
                                try {
                                    s = MessageCodec.decode(msgRecu.getContent(), Solution.class);
                                } catch(Exception ex) {
                                    ate.println("Erreur: Impossible de décoder la solution\n\t(Raison: "+ex.getMessage()+")");
                                }

                                ate.println("Solution recue - ");
                                
                                ate.setCacheSolution(s);
                                
                                ate.setEtat("demandeSolution", false);
                                ate.setEtat("solutionPresente", true);
                                etape = 0;
                            } else
                                ate.println("Erreur: Langage incorrect");
                        } else
                            block(1000);
                    } else 
                        block(1000);
                break;
            }
        }
    }

}
