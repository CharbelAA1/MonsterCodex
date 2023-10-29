package com.example.surelynotadndapp

class DamageType(var damageType: String) {
    var selectedRadioButtonId: Int = R.id.None // Initialize with a default value
    var selectedRadioButtonText: String = "None"

    constructor(damageType: String, selectedRadioButtonId: Int, selectedRadioButtonText: String) : this(damageType) {
        this.selectedRadioButtonId = selectedRadioButtonId
        this.selectedRadioButtonText = selectedRadioButtonText
    }
}