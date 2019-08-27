/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import model.ImageArea;

/**
 *
 * @author rig
 */
@FacesComponent("DemoArea")
public class AreaComponent extends UIOutput {
    
    private enum PropertyKeys {
        alt, coords, shape, targetImage;
    }

    /**
     * <p>Return the alternate text for our synthesized {@link ImageArea}.</p>
     */
    public String getAlt() {
        return (String) getStateHelper().eval(PropertyKeys.alt, null); 
    }

    /**
     * <p>Set the alternate text for our synthesized {@link ImageArea}.</p>
     *
     * @param alt The new alternate text
     */
    public void setAlt(String alt) {
        getStateHelper().put(PropertyKeys.alt, alt); 
    }

    /**
     * <p>Return the hotspot coordinates for our synthesized {@link ImageArea}.
     * </p>
     */
    public String getCoords() {
        return (String) getStateHelper().eval(PropertyKeys.coords, null); 
    }

    /**
     * <p>Set the hotspot coordinates for our synthesized {@link ImageArea}.</p>
     *
     * @param coords The new coordinates
     */
    public void setCoords(String coords) {
        getStateHelper().put(PropertyKeys.coords, coords); 
    }

    /**
     * <p>Return the shape for our synthesized {@link ImageArea}.</p>
     */
    public String getShape() {
        return (String) getStateHelper().eval(PropertyKeys.shape, null);
    }

    /**
     * <p>Set the shape for our synthesized {@link ImageArea}.</p>
     *
     * @param shape The new shape (default, rect, circle, poly)
     */
    public void setShape(String shape) {
        getStateHelper().put(PropertyKeys.shape, shape); 
    }

    /**
     * <p>Return the image that is the target of this
     * <code>AreaComponent</code>.</p>
     *
     * @return the target image of this area component.
     */
    public String getTargetImage() {
        return (String) getStateHelper().eval(PropertyKeys.targetImage, null);
    }

    /**
     * <p>Set the image that is the target of this
     * <code>AreaComponent</code>.</p>
     *
     * @param targetImage the ID of the target of this
     * <code>AreaComponent</code>
     */
    public void setTargetImage(String targetImage) {
        getStateHelper().put(PropertyKeys.targetImage, targetImage);
    }

    /**
     * <p>Return the component family for this component.</p>
     */
    @Override
    public String getFamily() {
        return ("Area");
    }

    // UIOutput Methods
    /**
     * <p>Synthesize and return an {@link ImageArea} bean for this hotspot, if
     * there is no
     * <code>valueRef</code> property on this component.</p>
     */
    @Override
    public Object getValue() {
        if (super.getValue() == null) {
            setValue(new ImageArea(getAlt(), getCoords(), getShape()));
        }

        return (super.getValue());
    }
}
