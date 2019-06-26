package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class RAM(): ComputerPart{
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var czestotliwosc_pracy_MHz: Int = 0
    var liczba_modulow: Int = 0
    var pojemnosc_GB: Int = 0
    lateinit var typ_pamieci: String
}