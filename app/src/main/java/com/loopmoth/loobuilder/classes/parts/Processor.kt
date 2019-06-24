package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class Processor(override val nazwa: String,
                override val producent: String,
                override val cena: Double,
                override val img: String,
                val ilosc_watkow: Int,
                var liczba_rdzeni: Int,
                var linia: String,
                var taktowanie: Double,
                var typ_gniazda: String,
                var uklad_graficzny: String
                ): ComputerPart