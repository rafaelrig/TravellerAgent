/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UICommand;
import listeners.AreaSelectedEvent;

/**
 *
 * @author rig
 */
@FacesComponent("DemoMap")
public class MapComponent extends UICommand {
    
    private enum PropertyKeys {
        current;
    }
    private String current = null;

    public MapComponent() {
        super();
    }

    /**
     * <p>Return the alternate text label for the currently selected
     * child {@link AreaComponent}.</p>
     * @return 
     */
    public String getCurrent() {
        return (String) getStateHelper().eval(PropertyKeys.current, null); 
    }

    /**
     * <p>Set the alternate text label for the currently selected child.
     * If this is different from the previous value, fire an
     * {@link AreaSelectedEvent} to interested listeners.</p>
     *
     * @param current The new alternate text label
     */
    public void setCurrent(String current) {
        if (this.getParent() == null) {
            return;
        }
        
        String previous = (String) getStateHelper().get(current); 
        getStateHelper().put(PropertyKeys.current, current);

        // Fire an {@link AreaSelectedEvent} if appropriate
        if ((previous == null) && (current == null)) {
            // do nothing
        } else if ((previous != null)
                && (current != null)
                && (previous.equals(current))) {
            // do nothing
        } else {
            this.queueEvent(new AreaSelectedEvent(this));
        }
    }

    /**
     * <p>Return the component family for this component.</p>
     * @return 
     */
    @Override
    public String getFamily() {
        return ("Map");
    }
    
}
