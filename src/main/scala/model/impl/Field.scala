package model.impl

import model.IField

class Field(val isBomb:Boolean, val bombsBesideThisField:Int, var isShown:Boolean) extends IField{

}
