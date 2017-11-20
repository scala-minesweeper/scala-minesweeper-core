package de.htwg.mps.minesweeper.model

trait IField {

  protected val hiddenFieldString = "~"

  protected val questionMarkedFieldString = "?"

  def showField(): IField

  def flagField(): IField

  def questionField(): IField

  def unQuestionField(): IField

  def unflagField(): IField

  def isBomb: Boolean

}
