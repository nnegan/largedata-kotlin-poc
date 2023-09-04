package com.uhdc.pipe.util

import java.io.BufferedWriter
import java.io.FileWriter

class MakeCsvFile {

    companion object {
        fun createCsv(list: MutableList<String>, filePath: String, title: String) {
            try {
                val fw = BufferedWriter(FileWriter("./"+ filePath + "/" + title + ".csv"))


                list.stream()
                    .forEach { str ->
                        fw.write(str)
                        fw.newLine()
                    }

                fw.flush()

                fw.close()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}
fun main(){
    println("Start")

    var list = mutableListOf<String>()

    var inspcd = ""
    var count = 1;

    for (i in 1..12000000 ){
        for ( k in 1..10) {
            count++
            inspcd = "A" + k

            list.add(i.toString() + "," + inspcd )
        }

    }

    println("line " + count)

    MakeCsvFile.createCsv(list, "", "entr_reco_l")
    /*


    when (k) {
                1 -> {
                     inspcd = "A01"
                }
                2 -> {
                    inspcd = "A02"
                }
                3 -> {
                    inspcd = "A02"
                }
                4 -> {
                    inspcd = "A02"
                }
                5 -> {
                    inspcd = "A03"
                    tempcd = "T301"
                    tempvar = "6"
                }
                6 -> {
                    inspcd = "A03"
                    tempcd = ""
                }
            }
            if ( k == 6)
                list.add(i.toString() + "," + i *10 + "," + inspcd+ "," + k + "," + tempcd + ",,Y," + "커스텀한 데이터를 남깁니다.,Y")
            else
                list.add(i.toString() + "," + i *10 + "," + inspcd+ "," + k + "," + tempcd + "," + tempvar + ",N,,Y")



    for (i in 1..10000000 ){
        list.add(i.toString() + "," + i *10 + "," + "cod"+ "," +
                "meta1 Info Example" + "," +
                "meta2 Info Example"+ "," +
                "meta3 Info Example"+ "," +
                "meta4 Info Example"+ "," +
                "meta5 Info Example"+ "," +
                "meta6 Info Example"+ "," +
                "meta7 Info Example"+ "," +
                "meta8 Info Example"+ "," +
                "meta9 Info Example"+ "," +
                "meta10 Info Example"+ "," +
                "meta11 Info Example"+ "," +
                "meta12 Info Example"+ "," +
                "meta13 Info Example"+ "," +
                "meta14 Info Example"+ "," +
                "meta15 Info Example"+ "," +
                "meta16 Info Example"+ "," +
                "meta17 Info Example"+ "," +
                "meta18 Info Example")
    }*/

    //MakeCsvFile.createCsv(list, "", "entr_reco_d")
}

