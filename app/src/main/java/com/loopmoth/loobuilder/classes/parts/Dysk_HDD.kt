package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class Dysk_HDD(override val nazwa: String,
               override val producent: String,
               override val cena: Double,
               val dlugosc_mm: Int,
               val szerokosc_mm: Double,
               val format_dysku_cale: Double,
               val grubosc_mm: Double,
               val interfejs: String,
               val pojemnosc_GB: Int,
               val waga_g: Int): ComputerPart