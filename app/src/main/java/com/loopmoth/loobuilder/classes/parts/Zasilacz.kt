package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Zasilacz(): ComputerPart{
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var glebokosc_mm: Int = 0
    var moc_W: Int = 0
    lateinit var standard: String
    var szerokosc_mm: Int = 0
    var wysokosc_mm: Int = 0
    lateinit var zlacza: ArrayList<GniazdoRozszerzen>
}