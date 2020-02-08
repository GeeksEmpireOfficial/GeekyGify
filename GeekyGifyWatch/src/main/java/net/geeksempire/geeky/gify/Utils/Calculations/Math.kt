/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 3:44 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Calculations

fun numberEven(aNumber: Int) : Boolean {
    return (aNumber % 2 == 0)
}

fun calculateThirtyPercent(currentNumber: Int) : Int {

    return (currentNumber - ((currentNumber * 30)/100))
}