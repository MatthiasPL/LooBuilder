package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class Dysk_SSD(): ComputerPart{
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var Format_dysku_cale: Double = 0.0
    var grubosc_mm: Int = 0
    lateinit var interfejs: String
    var pojemnosc_GB: Int = 0
    var szybkosc_odczytu_MB_s: Int = 0
    var szybkosc_zapisu_MB_s: Int = 0
}