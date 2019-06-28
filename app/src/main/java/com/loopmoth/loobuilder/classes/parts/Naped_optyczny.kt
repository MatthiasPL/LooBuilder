package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.classes.subclasses.Kompatybilnosc
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Naped_optyczny() : ComputerPart {
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var glebokosc_mm: Int = 0
    var szerokosc_mm: Double = 0.0
    var wysokosc_mm: Double = 0.0
    lateinit var typ: ArrayList<Kompatybilnosc>
    lateinit var interfejs : String
    lateinit var kolor : String
}