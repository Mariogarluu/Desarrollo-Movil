import exceptions.VehiculesAlreadyParkedExceptions
import model.Parking
import model.Vehicule

fun main (){
	val parking = Parking (maxSlot = 5)
	val exit:Boolean = false

	do {
		print("Elija una opcion: ")
		val option:String = readln()

		val optionsInt = options.toIntOrNull()
		when (optionsInt){
			1 ->{
				val wrongPlate = false
				do {
					println("Matricula: ")
					val plate = readln()
					try {
						parking.park(Vehicule(plate))
					} catch (ex : VehiculesAlreadyParkedExceptions){
						wrongPlate = true
					}
				} while (wrongPlate)


			}
			2 ->{}
			3 ->{}
			4 ->{exit = true}
			null -> {}
		}
	}
}