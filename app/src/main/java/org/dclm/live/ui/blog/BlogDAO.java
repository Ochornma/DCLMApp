package org.dclm.live.ui.blog;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BlogDAO {

    @Insert
    void insert(Blog blog);

    @Update
    void update(Blog blog);

    @Query("SELECT * FROM blog ORDER BY id ASC")
    LiveData<List<Blog>> getAllBlogs();
}
