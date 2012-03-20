package seisco.util;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <p>Classe utilitaire encapsulant l'envoi de message ACL.
 * 
 * @author Jerome
 * @version 2012
 */
public class MessageHelper {
    private ACLMessage message = null;
    
    /* Constantes des id des messages */
    public static final String ID_RECUP_AMC_SOLUTION = "recup-solution";
    public static final String ID_DEPL_AMC = "depl-amc";
    public static final String ID_FIN_EXEC = "fin-exec";
    
    public MessageHelper() {}
    
    /**
     * <p>Initilisation d'un message en prenant en compte la performative et un ID.
     * 
     * @param perf
     *      La performative à utiliser
     * @param id 
     *      L'ID de conversion à utiliser
     * @since 2012
     */
    public void create(int perf, String id) {
        this.message = new ACLMessage(perf);
        this.message.setConversationId(id);
        this.message.setLanguage("JavaSerialization");
    }
    
    /**
     * <p>Initilisation d'un message en prenant en compte la performative, un ID et l'ontologie.
     * 
     * @param perf
     *      La performative à utiliser
     * @param id 
     *      L'ID de conversion à utiliser
     * @param ontologie 
     *      L'ontologie à utiliser
     * @since 2012
     */
    public void create(int perf, String id, String ontologie) {
        this.create(perf, id);
        this.message.setOntology(ontologie);
    }
    
    /**
     * <p>Initilisation d'un message en prenant en compte la performative, un ID, l'ontologie et une liste de destinataires.
     * 
     * @param perf
     *      La performative à utiliser
     * @param id 
     *      L'ID de conversion à utiliser
     * @param ontologie 
     *      L'ontologie à utiliser
     * @param receivers
     *      Liste d'AID représentant les destinataires
     * @since 2012
     */
    public void create(int perf, String id, String ontologie, List<AID> receivers) {
        this.create(perf, id, ontologie);
        for(AID r : receivers)
            this.message.addReceiver(r);
    }
    
    /**
     * <p>Remplace l'ontologie du message.
     * 
     * @param ontologie
     *      La nouvelle ontologie
     * @return <code>true</code> si le message est initialisé et que l'ontologie a pu être modifié, sinon <code>false</code>
     * @since 2012
     */
    public boolean setOntologie(String ontologie) {
        if(this.message != null) {
            this.message.setOntology(ontologie);
            return true;
        }
        
        return false;
    }
    
    /**
     * <p>Remplace la liste des destinataires.
     * 
     * @param receivers
     *      Les nouveaux destinataires sous la forme d'une liste d'AID
     * @return <code>true</code> si le message est initialisé et que les destinataires ont pu être modifiés, sinon <code>false</code>
     * @since 2012
     */
    public boolean setReceiver(List<AID> receivers) {
        if(this.message != null) {
            this.message.clearAllReceiver();
            for(AID r : receivers)
                this.message.addReceiver(r);
            return true;
        }
        
        return false;
    }
    
    /**
     * <p>Permet d'ajouter un destinataire à la liste.
     * 
     * @param receiver
     *      L'AID du destinataire à ajouter
     * @return <code>true</code> si le message est initialisé et que le destinataire a pu être ajouté, sinon <code>false</code>
     * @since 2012
     */
    public boolean addReceiver(AID receiver) {
        if(this.message != null) {
            this.message.addReceiver(receiver);
            return true;
        }
        
        return false;
    }
    
    /**
     * <p>Modifie le contenu du message en lui passant un objet qui sera sérialisé.
     * @param o
     *      L'objet à sérialisé dans le message
     * @return <code>true</code> si le message est initialisé et le message a pu être modifié, sinon <code>false</code>
     * @throws IOException Si l'objet n'a pas pu être sérialisé.
     * @since 2012
     * @deprecated Utiliser {@link MessageCodec} et passer le message en chaine de caractère est plus stable.
     * @see MessageCodec
     */
    public boolean setObject(Serializable o) throws IOException {
        if(this.message != null) {
            this.message.setContentObject(o);
            return true;
        }
        
        return false;
    }
    
    /**
     * <p>Retourne le message ACL initialisé.
     * 
     * @return Le message ACL, ou <code>null</code> si le message n'est pas initialisé.
     * @since 2012
     * @see #get(String)
     */
    public ACLMessage get() {
        return this.message;
    }
    
    /**
     * <p>Retourne le message ACL initialisé avec comme contenu la chaine de caractère <code>data</code>
     * 
     * @param data
     *      Le contenu du message ACL
     * @return Le message ACL initialisé avec le message, ou <code>null</code> si le message n'est pas initialisé.
     * @since 2012
     */
    public ACLMessage get(String data) {
        if(this.message != null) {
            this.message.setContent(data);
        }
        
        return this.message;
    }
    
    /**
     * <p>Efface le message ACL.
     * 
     * @since 2012
     */
    public void reset() {
        this.message = null;
    }
}
