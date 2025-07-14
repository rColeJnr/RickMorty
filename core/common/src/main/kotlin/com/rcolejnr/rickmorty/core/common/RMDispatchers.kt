package com.rcolejnr.rickmorty.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val rmDispatcher: RMDispatchers)

enum class RMDispatchers {
    Default,
    IO
}