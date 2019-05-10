package com.loopmoth.loobuilder.classes

import android.content.Context
import com.loopmoth.loobuilder.interfaces.ComputerPart
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class SerializeCart {

    private val fileName: String = "cart.data"

    fun SaveCart(context: Context, computerPart: ComputerPart){
        val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(computerPart)
        os.close()
        fos.close()
    }

    fun LoadCart(context: Context): ComputerPart{
        val fis = context.openFileInput(fileName)
        val `is` = ObjectInputStream(fis)
        val computerPart = `is`.readObject() as ComputerPart
        `is`.close()
        fis.close()

        return computerPart
    }
}