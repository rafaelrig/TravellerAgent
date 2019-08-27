/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listeners;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 *
 * @author rig
 */
public class LinkPackListener implements ActionListener {

    private static final Logger logger =
            Logger.getLogger("listeners.LinkPackListener");
    private HashMap<String, String> packs = null;

    public LinkPackListener() {
        packs = new HashMap<String, String>(6);

        String pack1 = packs.put("Ecoturism", "eco");
        String pack2 = packs.put("Experiential", "exp");
        String pack3 = packs.put("UFO", "ufo");
        String pack4 = packs.put("Others", "others");
        String pack5 = packs.put("Birdwatching", "veraves");
        String pack6 = packs.put("Landscaping", "paisaje");
    }

    @Override
    public void processAction(ActionEvent event)
            throws AbortProcessingException {

        logger.log(Level.INFO, "Entering LinkPackListener.processAction");
        String current = event.getComponent().getId();
        logger.log(Level.INFO, "current is {0}", current);
        FacesContext context = FacesContext.getCurrentInstance();
        String categoryId = packs.get(current);
        logger.log(Level.INFO, "packId is {0}", current);
        context.getExternalContext().getSessionMap().put("categoryId", categoryId);
    }
}
