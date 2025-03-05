package com.example.pants.presentation.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pants.domain.models.ColorModel
import com.example.pants.domain.usecases.CheckBoardOrderUseCase
import com.example.pants.domain.usecases.GetColorBoardUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SharedGameViewModel(
    private val getColorBoardUseCase: GetColorBoardUseCase,
    private val checkBoardOrderUseCase: CheckBoardOrderUseCase
) : ViewModel() {

    // 1. Заменяем List на PersistentList
    private val _colorBoard = MutableStateFlow<PersistentList<ColorModel>>(persistentListOf())
    val colorBoard: StateFlow<PersistentList<ColorModel>> = _colorBoard.asStateFlow()

    private val _currentColorName = MutableStateFlow<String?>(null)
    val currentColorName: StateFlow<String?> = _currentColorName.asStateFlow()

    private val _selectedColor = MutableStateFlow(Color.Black)
    val selectedColor: StateFlow<Color> = _selectedColor.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    init {
        initColorBoard()
    }

    fun setColorModelByName(name: String) {
        _colorBoard.value.find { it.name == name }?.let { colorModel ->
            _currentColorName.value = colorModel.name
            updateColorSettings(colorModel.guessHue ?: 0f)
        }
    }

    fun saveColor(newHue: Float) {
        viewModelScope.launch {
            if (_colorBoard.value.isEmpty()) return@launch

            // 2. Оптимизированное обновление через PersistentList
            _colorBoard.update { currentList ->
                currentList.map { color ->
                    if (color.name == currentColorName.value) color.updateHue(newHue) else color
                }.toPersistentList()
            }
        }
    }

    fun updateColorSettings(hue: Float) {
        val newColor = Color.hsv(hue, 1f, 1f)
        if (newColor == _selectedColor.value) return

        _selectedColor.value = newColor

        // 3. Структурное обновление PersistentList
        _colorBoard.update { currentList ->
            currentList.map { color ->
                if (color.name == _currentColorName.value) color.updateHue(hue) else color
            }.toPersistentList()
        }
    }

    fun checkColorOrder(board: List<ColorModel>): List<ColorModel>? {
        return when {
            board.isEmpty() -> {
                initColorBoard()
                board
            }
            checkBoardOrderUseCase(board) -> {
                initColorBoard()
                null
            }
            else -> {
                board.sortedBy { it.realHue }
            }
        }
    }

    private fun initColorBoard() {
        viewModelScope.launch {
            getColorBoardUseCase(BOARD_SIZE).fold(
                onSuccess = { newList ->
                    // 4. Сохраняем как PersistentList
                    _colorBoard.value = newList.toPersistentList()
                },
                onFailure = { _errorMessage.emit(it.message.orEmpty()) }
            )
        }
    }

    private companion object {
        const val BOARD_SIZE = 5
    }
}