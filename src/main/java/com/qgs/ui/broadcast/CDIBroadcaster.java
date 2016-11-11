package com.qgs.ui.broadcast;

import com.qgs.ui.QGSUI;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author rafael
 */
@ApplicationScoped
public class CDIBroadcaster implements Serializable {

    private final Set<QGSUI> uis = new HashSet<QGSUI>();
    @Inject
    private Event<BroadcastMessage> messageEvent;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public synchronized void register(QGSUI ui) {
        uis.add(ui);
    }

    public synchronized void unregister(QGSUI ui) {
        uis.remove(ui);
    }

    public synchronized void observeMessage(@Observes @OriginalSender final BroadcastMessage message) {
        for (final QGSUI ui : uis) {
            executorService.execute(() -> {
                if (ui.getProvedorLogado().equals(message.getEmpresa())) {
                    ui.access(() -> {
                        messageEvent.fire(message);
                    });
                }
            });
        }
    }
}
