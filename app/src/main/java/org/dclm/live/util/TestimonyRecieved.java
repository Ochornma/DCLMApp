package org.dclm.live.util;

import org.dclm.live.ui.experience.Testimony;

import java.util.List;

public interface TestimonyRecieved {
    void onRecieved(List<Testimony> testimonies);
}
