package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;

import java.util.List;

@Dao
public interface GroupTrainingDAO {
    @Query("SELECT * FROM " + GroupTraining.TABLE_NAME)
    LiveData<List<GroupTraining>> getAllGroupTraining();

    @Query("DELETE FROM " + GroupTraining.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(GroupTraining groupTraining);
}
