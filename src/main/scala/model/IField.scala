package model

trait IField {
  val isBomb:Boolean;
  val bombsBesideThisField:Int;
  var isShown:Boolean;
}
