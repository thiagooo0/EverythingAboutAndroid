package com.kowksiuwang.everythingaboutandroid.designmode

class SingleInstanceKT {
    companion object{
        public val instance :SingleInstanceKT by lazy(LazyThreadSafetyMode.SYNCHRONIZED){SingleInstanceKT()}
    }
}