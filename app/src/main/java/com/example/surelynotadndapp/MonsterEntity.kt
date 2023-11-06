package com.example.surelynotadndapp
import androidx.room.*

@Entity(tableName = "monsters")
data class MonsterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "monsterName")
    val monsterName: String,
    val monsterHP: Int,
    val ac: Int,
    val acid: String? = "None",  // Nullable to handle non-selected options
    val cold: String? = "None",
    val fire: String? = "None",
    val force: String? = "None",
    val lightning: String? = "None",
    val necrotic: String? = "None",
    val poison: String? = "None",
    val psychic: String? = "None",
    val radiant: String? = "None",
    val thunder: String? = "None",
    val bludgeoning: String? = "None",
    val slashing: String? = "None",
    val piercing: String? = "None",
    val nonMagicMelee: String? = "None"
)

