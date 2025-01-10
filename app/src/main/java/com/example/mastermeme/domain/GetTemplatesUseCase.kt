package com.example.mastermeme.domain

import com.example.mastermeme.domain.model.MemeItem

fun interface GetTemplatesUseCase : suspend () -> Result<List<MemeItem.Template>>