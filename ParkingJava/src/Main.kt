fun menu ():String{
    println(
        "Bienvenido al Parking \n" +
                "0. Salir\n" +
                "1. Meter vehiculo\n" +
                "2. Sacar vehiculo\n" +
                "3. Mostrar vehiculos\n" +
                "Seleccione opcion: "
    );
    val opt = readln();
    if (opt == "1" || opt == "2" || opt == "3" || opt == "0") return opt;
    else return menu();
}

fun añadirCoche(listaMatriculas: MutableList<String>, matricula: String){
    if(!existeCoche(listaMatriculas, matricula)){
        listaMatriculas.add(matricula);
        println("Añadida la matricula $matricula \n")
    }
    else{
        println("La matricula $matricula ya esta en el parking\n")
    }
}

fun sacarCoche(listaMatriculas: MutableList<String>, matricula: String){
    if(existeCoche(listaMatriculas, matricula)){
        listaMatriculas.remove(matricula);
        println("Borrada la matricula $matricula\n");
    }
    else {
        println("La matricula $matricula no esta en el parking")
    }
}

fun mostrarCoches(listaMatriculas: MutableList<String>){
    var i = 1;
    for (matricula in listaMatriculas){
        println("$i . $matricula");
        i++;
    }
}

fun existeCoche(listaMatriculas: MutableList<String>, matricula: String): Boolean {
    if (listaMatriculas.contains(matricula)) return true;
    else return false;
}

fun main() {
    val listaMatriculas = mutableListOf<String>();
    var matricula: String;
    matricula = "";
    while (true) {
        val opt = menu();
        if (opt == "1" || opt == "2") {
            println("Introduce matricula: ");
            matricula = readln();
        }
        when (opt) {
            "1" -> añadirCoche(listaMatriculas, matricula);
            "2" -> sacarCoche(listaMatriculas, matricula);
            "3" -> mostrarCoches(listaMatriculas);
            "0" -> return;
        }
        println("Pulsa enter...");
        readln();
    }
}