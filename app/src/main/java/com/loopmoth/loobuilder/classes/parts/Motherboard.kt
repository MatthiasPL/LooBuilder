package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class Motherboard(override val name: String,
                  override val description: String,
                  override val manufacturer: String,
                  override val price: Double,
                  override val type: String="Płyta główna"): ComputerPart