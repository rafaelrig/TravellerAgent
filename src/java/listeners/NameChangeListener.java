/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 *
 * @author rig
 */
public class NameChangeListener extends Object implements ValueChangeListener {

    private static final Logger logger =
            Logger.getLogger("listeners.NameChangeListener");

    @Override
    public void processValueChange(ValueChangeEvent event)
            throws AbortProcessingException {
         logger.log(Level.INFO, "Entering NameChangeListener.processValueChange");
        if (null != event.getNewValue()) {
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("firstname", event.getNewValue());
        }
    }
}
