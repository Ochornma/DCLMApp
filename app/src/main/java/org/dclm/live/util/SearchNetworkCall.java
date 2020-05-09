package org.dclm.live.util;

import org.dclm.live.ui.ondemand.search.Search;

import java.util.List;

public interface SearchNetworkCall {
    void onRecieved(List<Search> searches);
    void onError();
}
