package model

import exceptions.VehiculesAlreadyParkedExceptions
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.time.ExperimentalTime

@OptIn(markerClass = ExperimentalTime::class)
data class ParkingSlot(val vehicle: Vehicle?, val occupiedSince: Instant?)

class Parking (private val maxSlot:Int = 100){
	private val parkingSlots: MutableList<ParkingSlot> = mutableListOf()
	val parkedVehicule:List<Vehicule>
		get() = getParkedVehicles();

	private fun isFull():Boolean = parkingSlots.size == maxSlot

	fun park(vehicle: Vehicle){
		if (!isFull()){
			val exist = parkingSlots.any{
					slot -> slot.vehicle?.plate == vehicle.plate
			}

			if (!exist){
				val slot = ParkingSlot(vehicle, Clock.System.now())
				parkingSlots.add(slot)
			}
			else{
				throw VehiculesAlreadyParkedExceptions
			}
		}
	}

	fun unpark(vehicle: Vehicle){
		//1.
		val wheels = parkingSlots.indexOfFirst {
				parkingSlot -> parkingSlot.vehicle?.plate == vehicle?.plate
		}

		if (wheels >= 0){
			parkingSlots.removeAt(index = wheels)
		}
	}

	fun getParkedVehicles():List<Vehicule>{
		val vehicule: MutableList<Vehicule> = mutableListOf()
		for (slot in parkingSlots){
			vehicule.add(slot.vehicle!!)
		}
		return vehicule.sortedBy { it.plate }
	}
}