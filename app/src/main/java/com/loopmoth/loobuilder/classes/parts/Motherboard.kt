package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.interfaces.ComputerPart

class Motherboard(override val nazwa: String,
                  override val producent: String,
                  override val cena: Double,
                  val chipset: String,
                  var chipset_graficzny: String,
                  var szerokosc: Int,
                  var glebokosc: Int,
                  var gniazdo_procesora: String,
                  var ilosc_procesorow: Int,
                  var sloty_pamieci: Int,
                  var standard: String,
                  var standard_pamieci: String,
                  var gniazda_rozszerzen: List<GniazdoRozszerzen>
                  ):ComputerPart