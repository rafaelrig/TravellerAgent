/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listeners;

import components.MapComponent;
import javax.faces.event.ActionEvent;

/**
 *
 * @author rig
 */
public class AreaSelectedEvent extends ActionEvent {
    
   private static final long serialVersionUID = 5030325260451465110L;

    /**
     * <p>Construct a new {@link AreaSelectedEvent} from the specified
     * source map.</p>
     *
     * @param map The {@link MapComponent} originating this event
     */
    public AreaSelectedEvent(MapComponent map) {
        super(map);
    }

    /**
     * <p>Return the {@link MapComponent} of the map for which an area
     * was selected.</p>
     */
    public MapComponent getMapComponent() {
        return ((MapComponent) getComponent());
    } 
    
}
