package org.dclm.live.util;

import org.dclm.live.ui.ondemand.OnDemand;

import java.util.List;

public interface PodcastNetworkCall{
    void podcastRecieved(List<OnDemand> onDemands, List<OnDemand.CheckState> checkStates);
    void categoryRecieved(List<OnDemand.Category> categories);
    void error(boolean menu);
}
