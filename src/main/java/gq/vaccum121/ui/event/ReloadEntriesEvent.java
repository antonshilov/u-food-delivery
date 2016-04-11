package gq.vaccum121.ui.event;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;

public class ReloadEntriesEvent implements Event {

    public ReloadEntriesEvent() {
    }

    public interface ReloadEntriesListener extends Listener {
        @ListenerMethod
        void reloadEntries(ReloadEntriesEvent event);
    }
}
