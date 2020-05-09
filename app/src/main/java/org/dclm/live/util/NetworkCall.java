package org.dclm.live.util;


import org.dclm.live.ui.blog.Blog;

import java.util.List;

public interface NetworkCall {

    void blogRecieved(List<Blog> blog);
    void dataError(int error);

}

