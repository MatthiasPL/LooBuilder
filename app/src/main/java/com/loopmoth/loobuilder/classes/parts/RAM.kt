package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class RAM(override val nazwa: String,
          override val producent: String,
          override val cena: Double,
          override val img: String,
          val czestotliwosc_pracy_MHz: Int,
          val liczba_modulow: Int,
          val pojemnosc_GB: Int,
          val typ_pamieci: String): ComputerPart