package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class Dysk_HDD() : ComputerPart
{
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var dlugosc_mm: Int = 0
    var szerokosc_mm: Double = 0.0
    var format_dysku_cale: Double = 0.0
    var grubosc_mm: Double = 0.0
    lateinit var interfejs: String
    var pojemnosc_GB: Int = 0
    var waga_g: Int =0

}