package de.htwg.mps.minesweeper.model

import de.htwg.mps.minesweeper.model.impl.NumberField

trait IField {
  def showField(): IField

  def flagField(): IField

  def unflagField(): IField

  def isBomb: Boolean

  def setNumber(number: Int): IField

  def incrementNumberBombsBeside(): NumberField
}
