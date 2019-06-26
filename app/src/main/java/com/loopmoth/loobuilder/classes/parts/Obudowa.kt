package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.Kompatybilnosc
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Obudowa(): ComputerPart {
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var glebokosc_cm: Double = 0.0
    lateinit var kolor: String
    lateinit var kompatybilnosc: ArrayList<Kompatybilnosc>
    var szerokosc_cm: Double = 0.0
    lateinit var typ_obudowy: String
    var waga_kg: Double = 0.0
    var wysokosc_cm: Double =0.0
}