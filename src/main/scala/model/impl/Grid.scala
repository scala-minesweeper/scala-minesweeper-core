package model.impl

import model.{IGrid}



class Grid (field:Array[Array[Field]]) extends IGrid{
  val playground = field;

}
