package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.Kompatybilnosc
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Obudowa(override val nazwa: String,
              override val producent: String,
              override val cena: Double,
              val glebokosc_cm: Double,
              val kolor: String,
              val kompatybilnosc: ArrayList<Kompatybilnosc>,
              val szerokosc_cm: Double,
              val typ_obudowy: String,
              val waga_kg: Double,
              val wysokosc_cm: Double): ComputerPart