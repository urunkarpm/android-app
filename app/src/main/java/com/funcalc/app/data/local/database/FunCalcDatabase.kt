package com.funcalc.app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.funcalc.app.data.local.dao.AchievementDao
import com.funcalc.app.data.local.dao.FunFactDao
import com.funcalc.app.data.local.entity.AchievementEntity
import com.funcalc.app.data.local.entity.FunFactEntity
import com.funcalc.app.domain.model.Achievements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room database for the app
 */
@Database(
    entities = [FunFactEntity::class, AchievementEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FunCalcDatabase : RoomDatabase() {
    abstract fun funFactDao(): FunFactDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: FunCalcDatabase? = null

        fun getDatabase(context: Context): FunCalcDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FunCalcDatabase::class.java,
                    "funcalc_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.funFactDao(), database.achievementDao())
                }
            }
        }

        suspend fun populateDatabase(funFactDao: FunFactDao, achievementDao: AchievementDao) {
            // Insert fun facts
            funFactDao.insertFunFacts(getInitialFunFacts())
            // Insert achievements
            achievementDao.insertAchievements(Achievements.all.map { AchievementEntity.fromDomain(it) })
        }

        private fun getInitialFunFacts(): List<FunFactEntity> = listOf(
            // Space facts
            FunFactEntity(fact = "Octopuses have three hearts!", category = "ANIMALS"),
            FunFactEntity(fact = "A day on Venus is longer than a year on Venus!", category = "SPACE"),
            FunFactEntity(fact = "The Sun is so big that about 1.3 million Earths could fit inside it!", category = "SPACE"),
            FunFactEntity(fact = "There are more stars in space than grains of sand on all the beaches on Earth!", category = "SPACE"),
            FunFactEntity(fact = "The Moon is about 1/4 the size of Earth!", category = "SPACE"),
            FunFactEntity(fact = "Saturn would float if you could put it in a giant bathtub!", category = "SPACE"),
            FunFactEntity(fact = "One million Earths could fit inside the Sun!", category = "SPACE"),
            FunFactEntity(fact = "The footprints on the Moon will stay there for millions of years!", category = "SPACE"),
            FunFactEntity(fact = "Space is completely silent because there's no air for sound to travel through!", category = "SPACE"),
            FunFactEntity(fact = "Neptune's winds blow faster than any other planet - up to 1,200 mph!", category = "SPACE"),

            // Animal facts
            FunFactEntity(fact = "Octopuses have three hearts!", category = "ANIMALS"),
            FunFactEntity(fact = "Dolphins sleep with one eye open!", category = "ANIMALS"),
            FunFactEntity(fact = "A group of flamingos is called a 'flamboyance'!", category = "ANIMALS"),
            FunFactEntity(fact = "Butterflies taste with their feet!", category = "ANIMALS"),
            FunFactEntity(fact = "Cows have best friends and get stressed when separated from them!", category = "ANIMALS"),
            FunFactEntity(fact = "Elephants are the only animals that can't jump!", category = "ANIMALS"),
            FunFactEntity(fact = "A snail can sleep for three years at a time!", category = "ANIMALS"),
            FunFactEntity(fact = "Honey never goes bad - archaeologists found 3,000-year-old honey in Egyptian tombs!", category = "ANIMALS"),
            FunFactEntity(fact = "Axolotls can regrow their limbs and even parts of their brain!", category = "ANIMALS"),
            FunFactEntity(fact = "A tiger's stripes are like fingerprints - no two tigers have the same pattern!", category = "ANIMALS"),
            FunFactEntity(fact = "Penguins propose to their mates with a pebble!", category = "ANIMALS"),
            FunFactEntity(fact = "Sea otters hold hands when they sleep to keep from drifting apart!", category = "ANIMALS"),
            FunFactEntity(fact = "Octopuses have blue blood because copper carries oxygen instead of iron!", category = "ANIMALS"),
            FunFactEntity(fact = "Wombat poop is cube-shaped so it doesn't roll away!", category = "ANIMALS"),
            FunFactEntity(fact = "A shrimp's heart is located in its head!", category = "ANIMALS"),

            // Nature facts
            FunFactEntity(fact = "Rainbows are actually full circles, but we usually only see half!", category = "NATURE"),
            FunFactEntity(fact = "The tallest tree in the world is called Hyperion - it's about 380 feet tall!", category = "NATURE"),
            FunFactEntity(fact = "Lightning strikes Earth about 8 million times per day!", category = "NATURE"),
            FunFactEntity(fact = "Some seeds can be over 2,000 years old and still grow!", category = "NATURE"),
            FunFactEntity(fact = "The Amazon rainforest produces 20% of the world's oxygen!", category = "NATURE"),
            FunFactEntity(fact = "Coral reefs are often called the 'rainforests of the sea' because they're home to so many animals!", category = "NATURE"),
            FunFactEntity(fact = "A single cloud can weigh more than 1 million pounds!", category = "NATURE"),
            FunFactEntity(fact = "The world's largest desert is Antarctica, not the Sahara!", category = "NATURE"),
            FunFactEntity(fact = "Some trees can live for over 5,000 years!", category = "NATURE"),
            FunFactEntity(fact = "There are more trees on Earth than stars in the Milky Way!", category = "NATURE"),

            // Science facts
            FunFactEntity(fact = "Hot water freezes faster than cold water - this is called the Mpemba effect!", category = "SCIENCE"),
            FunFactEntity(fact = "The Eiffel Tower can grow 15 centimeters taller in summer due to heat!", category = "SCIENCE"),
            FunFactEntity(fact = "Bananas are slightly radioactive because they contain potassium!", category = "SCIENCE"),
            FunFactEntity(fact = "Venus is the only planet that spins clockwise!", category = "SCIENCE"),
            FunFactEntity(fact = "A cloud can weigh more than 1 million pounds!", category = "SCIENCE"),
            FunFactEntity(fact = "Diamonds rain down on Jupiter and Saturn!", category = "SCIENCE"),
            FunFactEntity(fact = "Your stomach gets a new lining every 3-4 days!", category = "SCIENCE"),
            FunFactEntity(fact = "The human brain uses about 20% of the body's total energy!", category = "SCIENCE"),
            FunFactEntity(fact = "A day on Mercury lasts 59 Earth days!", category = "SCIENCE"),
            FunFactEntity(fact = "Sound travels about 4 times faster in water than in air!", category = "SCIENCE"),

            // Human body facts
            FunFactEntity(fact = "Your nose and ears never stop growing!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "You can't lick your own elbow!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "The human body has enough iron to make a small nail!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "Your brain is about 75% water!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "The average person walks the equivalent of three times around the world in their lifetime!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "You blink about 15-20 times per minute!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "Your heart beats about 100,000 times per day!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "The human body contains enough carbon to make 900 pencils!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "Babies have more bones than adults - they have about 270!", category = "HUMAN_BODY"),
            FunFactEntity(fact = "Your tongue is the only muscle that works one end to the other!", category = "HUMAN_BODY")
        )
    }
}
