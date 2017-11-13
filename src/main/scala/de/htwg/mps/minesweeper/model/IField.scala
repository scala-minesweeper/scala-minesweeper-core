package de.htwg.mps.minesweeper.model

trait IField {
  def showField(): IField

  def flagField(): IField

  def unflagField(): IField

  def isBomb: Boolean

  def setNumber(number: Int): IField
}
