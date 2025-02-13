package com.rahim.yadino.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.rahim.yadino.db.dao.NoteDao
import com.rahim.yadino.db.dao.RoutineDao
import com.rahim.yadino.db.dao.TimeDao
import com.rahim.yadino.model.NoteModel
import com.rahim.yadino.model.RoutineModel
import com.rahim.yadino.model.TimeDate

@Database(
  entities = [NoteModel::class, TimeDate::class, RoutineModel::class],
  //2024/18/10
  version = 6,
  exportSchema = true,
  autoMigrations = [
    AutoMigration(1, 2),
    AutoMigration(2, 3),
    AutoMigration(3, 4, spec = AppDatabase.Version3::class),
    AutoMigration(4, 5),
    AutoMigration(5, 6, spec = AppDatabase.Version6::class),
  ],
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun routineDao(): RoutineDao
  abstract fun timeDataDao(): TimeDao
  abstract fun noteDao(): NoteDao

  @DeleteColumn(
    tableName = "tbl_timeData",
    columnName = "id",
  )
  @RenameTable(fromTableName = "tbl_timeData", toTableName = "tbl_timeDate")
  class Version3 : AutoMigrationSpec

  @RenameColumn(
    tableName = "tbl_routine",
    fromColumnName = "yerNumber",
    toColumnName = "yearNumber",
  )
  @RenameColumn(
    tableName = "tbl_note",
    fromColumnName = "yerNumber",
    toColumnName = "yearNumber",
  )
  @RenameColumn(
    tableName = "tbl_timeDate",
    fromColumnName = "yerNumber",
    toColumnName = "yearNumber",
  )
  class Version6 : AutoMigrationSpec
}
