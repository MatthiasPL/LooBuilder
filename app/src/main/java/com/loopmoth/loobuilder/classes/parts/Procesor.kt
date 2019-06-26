package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class Procesor(): ComputerPart{
    override lateinit var nazwa: String
    override lateinit var producent: String
    override var cena: Double = 0.0
    override lateinit var img: String
    var ilosc_watkow: Int = 0
    var liczba_rdzeni: Int = 0
    lateinit var linia: String
    var taktowanie: Double = 0.0
    lateinit var typ_gniazda: String
    lateinit var uklad_graficzny: String
}