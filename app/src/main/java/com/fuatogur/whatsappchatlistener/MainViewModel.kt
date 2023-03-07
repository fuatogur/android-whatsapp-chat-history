package com.fuatogur.whatsappchatlistener

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuatogur.whatsappchatlistener.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: AppDatabase
) : ViewModel() {

    var messages: List<Message> by mutableStateOf(emptyList())
        private set

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = database.messageDao().getAll()

            withContext(Dispatchers.Main) {
                messages = data
            }
        }
    }

    fun add(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            database.messageDao().insertAll(message)
        }
    }

    fun deleteAll() {
        messages = listOf()

        viewModelScope.launch(Dispatchers.IO) {
            database.messageDao().deleteAll()
        }
    }
}