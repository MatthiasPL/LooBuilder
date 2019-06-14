package com.loopmoth.loobuilder.classes

import android.content.Context
import com.loopmoth.loobuilder.interfaces.ComputerPart
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

//klasa do serializacji i deserializacji koszyka
class SerializeCart {

    private val fileName: String = "cart.data"

    fun SaveCart(context: Context, computerPart: ComputerPart){
        try{
            val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(computerPart)
            os.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun LoadCart(context: Context): ComputerPart?{
        try {
            val fis = context.openFileInput(fileName)
            val `is` = ObjectInputStream(fis)
            val computerPart = `is`.readObject() as ComputerPart
            `is`.close()
            fis.close()
            return computerPart
        } catch (e: IOException) {
            e.printStackTrace()
        }
        catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return null
    }
}