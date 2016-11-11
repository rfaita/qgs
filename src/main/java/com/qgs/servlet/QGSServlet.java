package com.qgs.servlet;

import com.vaadin.cdi.server.VaadinCDIServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*")
public class QGSServlet extends VaadinCDIServlet {

    @Override
    protected final void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new QGSSessionInitListener());
    }
}
