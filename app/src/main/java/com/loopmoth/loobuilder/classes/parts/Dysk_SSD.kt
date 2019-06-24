package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

//TODO zbędne
class Dysk_SSD(override val nazwa: String,
               override val producent: String,
               override val cena: Double,
               override val img: String,
               val Format_dysku_cale: Double,
               val grubosc_mm: Int,
               val interfejs: String,
               val pojemnosc_GB: Int,
               val szybkosc_odczytu_MB_s: Int,
               val szybkosc_zapisu_MB_s: Int): ComputerPart