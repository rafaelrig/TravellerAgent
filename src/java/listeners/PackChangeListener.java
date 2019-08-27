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
public class PackChangeListener implements ActionListener {

    private static final Logger logger =
            Logger.getLogger("listeners.PackChangeListener");
    private HashMap<String, String> packs = null;

    public PackChangeListener() {
        packs = new HashMap<>(4);

        String pack1 = packs.put("Traditional", "trad");
        String pack2 = packs.put("Non-Traditional", "nontrad");
        String pack3 = packs.put("Sports", "sptAdv");
        String pack4 = packs.put("Domestic", "dom");
    }

    @Override
    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {

        logger.log(Level.INFO, "Entering PackChangeListener.processAction");
        AreaSelectedEvent event = (AreaSelectedEvent) actionEvent;
        String current = event.getMapComponent().getCurrent();
        logger.log(Level.INFO, "current is {0}", current);
        FacesContext context = FacesContext.getCurrentInstance();
        String category = packs.get(current);
        logger.log(Level.INFO, "categoryId is {0}", category);
        context.getExternalContext().getSessionMap().put("category", category);
    }
}