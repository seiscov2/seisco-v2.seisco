package seisco.comportement.ame;

import jade.core.ContainerID;
import jade.core.behaviours.CyclicBehaviour;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import seisco.agent.AgentMobileEchange;

/**
 * <p>Ce comportement surveille la charge CPU de la machine et peut lancer un ordre de migration des agents.
 * <p>Pour le calcul de la charge, on utilise une commande du système, 
 * cette commande n'étant disponible que sous un système UNIX/LINUX, il est impossible de faire tourner ce comportement
 * sous Windows.
 * 
 * @author Jerome
 * @since 2012
 */
public class SurveillerMachine extends CyclicBehaviour {

    private AgentMobileEchange ame;
    
    public SurveillerMachine(AgentMobileEchange a) {
        super(a);
        this.ame = a;
    }
    
    @Override
    public void action() {
        int chargeCpuMax = Integer.parseInt(ame.getParam("charge_cpu").getValeur().toString());
        
        if(determinerChargeCPU() > chargeCpuMax) { // Déplacer
            ContainerID loc = new ContainerID();
            
            int id = ame.getCurrentMachine();
            List<String> nextMach = ame.getNextMachines();
            
            id++;
            if(id>=nextMach.size()) id=0;
            ame.setCurrentMachine(id);
            
            loc.setName(nextMach.get(id));

            ame.doMove(loc);
        }
    }
    
    /**
     * <p>Permet de calculer la charge du CPU via la commande du système <b>vmstat</b>.
     * 
     * @return Un pourcentage de la charge du CPU (0 non utilisé, 100 utilisé totalement)
     * @since 2012
     */
    protected int determinerChargeCPU() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("vmstat");
            BufferedReader lecteur = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String lineResult = lecteur.readLine();
            int i;
            
            for(i=0;i!=2;i++)
                lineResult = lecteur.readLine();
            
            String r = lineResult.substring(13, 20);
            
            if(r.charAt(0)==' ')
                r = r.substring(1, r.length());
            
            if(r.charAt(r.length()-1)==' ')
                r = r.substring(0, r.length()-1);
            
            int nb = Integer.parseInt(r);
            
            int startRead = 72;
            if(nb > 999999) startRead++;
            
            try {
                String s = lineResult.substring(startRead, startRead+3);
                if(s.charAt(s.length()-1)==' ')
                    s = s.substring(0, 2);

                int cpuIdle = Integer.parseInt(s);
                lecteur.close();
                return (100-cpuIdle);
            } catch(NumberFormatException e) {
                lecteur.close();
                return 100;
            }
        } catch(IOException e) {
            ame.println("Erreur lors de l'execution de la commande 'vmstat'");
            return 100;
        }
    }
}
