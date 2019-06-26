package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Karta_graficzna(): ComputerPart {
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var dlugosc_mm: Int = 0
    var ilosc_pamieci_RAM_GB: Int = 0
    lateinit var producent_chipsetu: String
    lateinit var rodzaj_chipsetu: String
    lateinit var rodzaj_pamieci_RAM: String
    lateinit var typ_chlodzenia: String
    lateinit var typ_zlacza: String
    lateinit var zlacza: ArrayList<GniazdoRozszerzen>
}