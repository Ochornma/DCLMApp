package org.dclm.live.ui.jotter.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.dclm.live.ui.blog.Blog;
import org.dclm.live.ui.blog.BlogDAO;

@Database(entities = {Note.class, Blog.class}, version = 6)
public abstract class NoteDataBase extends RoomDatabase {

    public abstract NoteDAO noteDAO();
    private static NoteDataBase instance;
    public abstract BlogDAO blogDAO();

    public static synchronized NoteDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class, "note_database")
                    .addMigrations(MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask().execute();
        }

    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_table "
                    + " ADD COLUMN point4 TEXT");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_table "
                    + " ADD COLUMN point4_description TEXT");
        }
    };

    private static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE IF NOT EXISTS `blog` (`id` INTEGER NOT NULL, `title` TEXT,  `date` TEXT,`body` TEXT, PRIMARY KEY(`id`))");
        }

       // database.execSQL("CREATE TABLE IF NOT EXISTS `album` (`id` INTEGER NOT NULL, `created` INTEGER NOT NULL, `modified` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")"

    };


    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
