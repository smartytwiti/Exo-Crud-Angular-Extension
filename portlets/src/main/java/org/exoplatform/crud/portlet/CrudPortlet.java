package org.exoplatform.crud.portlet;

import javax.portlet.*;
import java.io.IOException;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 04/09/15
 */
public class CrudPortlet extends GenericPortlet {
    @RenderMode(name = "view")
    public void renderHome(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        PortletRequestDispatcher prDispatcher = getPortletContext().getRequestDispatcher("/pages/view.html");
        prDispatcher.include(request, response);
    }
}
