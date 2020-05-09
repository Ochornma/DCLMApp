package org.dclm.live.util;

import com.google.android.exoplayer2.text.Subtitle;

import org.dclm.live.ui.listen.SubTitle;

import java.util.List;

public interface SubtitleReceived {
    void subtitle(SubTitle subTitles);
    void error(SubTitle subTitles);
}
