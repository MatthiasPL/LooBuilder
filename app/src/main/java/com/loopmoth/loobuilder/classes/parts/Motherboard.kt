package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Motherboard():ComputerPart{
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    lateinit var chipset: String
    lateinit var chipset_graficzny: String
    var szerokosc: Int = 0
    var glebokosc: Int = 0
    lateinit var gniazdo_procesora: String
    var ilosc_procesorow: Int = 0
    var sloty_pamieci: Int = 0
    lateinit var standard: String
    lateinit var standard_pamieci: String
    lateinit var gniazda_rozszerzen: List<GniazdoRozszerzen>
}