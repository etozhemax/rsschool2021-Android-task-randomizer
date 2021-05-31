package com.rsschool.android2021

interface IDataTransfer {
    fun transferToSecondFragment(min: Int, max: Int)
    fun transferToFirstFragment(previousValue: Int)
}