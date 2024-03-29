/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package renderers;

import components.AreaComponent;
import components.MapComponent;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import model.ImageArea;

/**
 *
 * @author rig
 */
@FacesRenderer(componentFamily = "Area", rendererType = "DemoArea")
public class AreaRenderer extends Renderer {

    public AreaRenderer() {
    }

    // Renderer Methods
    /**
     * <p>No decoding is required.</p>
     *
     * @param context
     * <code>FacesContext</code>for the current request
     * @param component
     * <code>UIComponent</code> to be decoded
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>No begin encoding is required.</p>
     *
     * @param context
     * <code>FacesContext</code>for the current request
     * @param component
     * <code>UIComponent</code> to be decoded
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>No children encoding is required.</p>
     *
     * @param context
     * <code>FacesContext</code>for the current request
     * @param component
     * <code>UIComponent</code> to be decoded
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>Encode this component.</p>
     *
     * @param context
     * <code>FacesContext</code>for the current request
     * @param component
     * <code>UIComponent</code> to be decoded
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        AreaComponent area = (AreaComponent) component;
        String targetImageId = area.findComponent(
                area.getTargetImage()).getClientId(context);
        ImageArea iarea = (ImageArea) area.getValue();
        ResponseWriter writer = context.getResponseWriter();
        StringBuilder sb;

        writer.startElement("area", area);
        writer.writeAttribute("alt", iarea.getAlt(), "alt");
        writer.writeAttribute("coords", iarea.getCoords(), "coords");
        writer.writeAttribute("shape", iarea.getShape(), "shape");
        sb = new StringBuilder("document.forms[0]['").append(targetImageId).
                append("'].src='");
        sb.append(
                getURI(context,
                (String) area.getAttributes().get("onmouseout")));
        sb.append("'");
        writer.writeAttribute("onmouseout", sb.toString(), "onmouseout");
        sb = new StringBuilder("document.forms[0]['").append(targetImageId).
                append("'].src='");
        sb.append(
                getURI(context,
                (String) area.getAttributes().get("onmouseover")));
        sb.append("'");
        writer.writeAttribute("onmouseover", sb.toString(), "onmouseover");
        sb = new StringBuilder("document.forms[0]['");
        sb.append(getName(context, area));
        sb.append("'].value='");
        sb.append(iarea.getAlt());
        sb.append("'; document.forms[0].submit()");
        writer.writeAttribute("onclick", sb.toString(), "value");
        writer.endElement("area");
    }

    /**
     * <p>Return the calculated name for the hidden input field.</p>
     *
     * @param context Context for the current request
     * @param component Component we are rendering
     */
    private String getName(FacesContext context, UIComponent component) {
        while (component != null) {
            if (component instanceof MapComponent) {
                return (component.getId() + "_current");
            }

            component = component.getParent();
        }

        throw new IllegalArgumentException();
    }

    /**
     * <p>Return the path to be passed into JavaScript for the specified
     * value.</p>
     *
     * @param context Context for the current request
     * @param value Partial path to be (potentially) modified
     */
    private String getURI(FacesContext context, String value) {
        if (value.startsWith("/")) {
            return (context.getExternalContext().getRequestContextPath() + value);
        } else {
            return (value);
        }
    }
}
