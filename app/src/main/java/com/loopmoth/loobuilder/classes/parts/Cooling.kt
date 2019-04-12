package com.loopmoth.loobuilder.classes.parts

import com.loopmoth.loobuilder.interfaces.ComputerPart

class Cooling(override val name: String,
              override val description: String,
              override val manufacturer: String,
              override val price: Double): ComputerPart