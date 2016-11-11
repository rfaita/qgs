package com.qgs.ui.view;

import com.qgs.ui.broadcast.BroadcastMessage;
import com.vaadin.server.Page;

/**
 *
 * @author rafael
 */
public interface ResizeView {

    void onResize(Page.BrowserWindowResizeEvent e);
}
