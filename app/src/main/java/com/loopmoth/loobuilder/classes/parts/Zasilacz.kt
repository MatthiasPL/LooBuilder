package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Zasilacz(override val nazwa: String,
               override val producent: String,
               override val cena: Double,
               override val img: String,
               val glebokosc_mm: Int,
               val moc_W: Int,
               val standard: String,
               val szerokosc_mm: Int,
               val wysokosc_mm: Int,
               val zlacza: GniazdoRozszerzen): ComputerPart