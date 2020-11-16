package com.manuelcarvalho.celltower.model


//@Database(entities = arrayOf(Tower::class), version = 1)
//abstract class TowerDatabase : RoomDatabase() {
//    abstract fun quakeDao(): TowerDao
//
//    companion object {
//        @Volatile
//        private var instance: TowerDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            TowerDatabase::class.java,
//            "towerdatabase"
//        ).build()
//    }
//}