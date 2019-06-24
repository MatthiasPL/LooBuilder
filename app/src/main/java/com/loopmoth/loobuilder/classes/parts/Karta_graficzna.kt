package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Karta_graficzna(override val nazwa: String,
                      override val producent: String,
                      override val cena: Double,
                      override val img: String,
                      val dlugosc_mm: Int,
                      val ilosc_pamieci_RAM_GB: Int,
                      val producent_chipsetu: String,
                      val rodzaj_chipsetu: String,
                      val rodzaj_pamieci_RAM: String,
                      val typ_chlodzenia: String,
                      val typ_zlacza: String,
                      val zlacza: ArrayList<GniazdoRozszerzen>): ComputerPart