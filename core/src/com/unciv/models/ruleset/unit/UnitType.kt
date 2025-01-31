package com.unciv.models.ruleset.unit

import com.unciv.models.ruleset.IHasUniques
import com.unciv.models.ruleset.unique.Unique
import com.unciv.models.ruleset.unique.UniqueTarget
import com.unciv.models.stats.INamed


enum class UnitLayer { // The layer in which the unit moves
    Civilian,
    Military,
    Air 
}

enum class UnitMovementType { // The types of tiles the unit can by default enter
    Land, // Only land tiles except when certain techs are researched
    Water, // Only water tiles
    Air // Only city tiles and carrying units
}

class UnitType() : INamed, IHasUniques {
    override lateinit var name: String
    private var movementType: String? = null
    private val unitMovementType: UnitMovementType? by lazy { if (movementType == null) null else UnitMovementType.valueOf(movementType!!) }
    
    override var uniques: ArrayList<String> = ArrayList()
    override val uniqueObjects: List<Unique> by lazy { uniques.map { Unique(it, UniqueTarget.Unit, name) } }
    
    constructor(name: String, domain: String? = null) : this() {
        this.name = name
        this.movementType = domain
    }
    
    fun getMovementType() = unitMovementType
    
    fun isLandUnit() = unitMovementType == UnitMovementType.Land
    fun isWaterUnit() = unitMovementType == UnitMovementType.Water
    fun isAirUnit() = unitMovementType == UnitMovementType.Air
    
    fun matchesFilter(filter: String): Boolean {
        return when (filter) {
            "Land" -> isLandUnit()
            "Water" -> isWaterUnit()
            "Air" -> isAirUnit()
            else -> {
                uniques.contains(filter)
            }
        }
    }
    
    companion object {
        val City = UnitType("City", "Land")
    }
}


