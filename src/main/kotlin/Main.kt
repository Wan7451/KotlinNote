import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main1(args: Array<String>) {
    runBlocking {
        val flow = flowOf(1, 2, 3, 4, 5, 6)
        flow.map {
            it * it
        }.filter {
            it % 2 == 0
        }.collect {
            println(" emit $it")
        }
    }
}

fun main2() {
    runBlocking {
        flow {
            emit(1)
            emit(2)
            delay(600)
            emit(3)
            delay(100)
            emit(4)
            delay(100)
            emit(5)
        }.debounce(500).collect {
            println(it)
        }
    }
}

fun main3() {
    runBlocking {
        flow {
            while (true) {
                emit("danmu ${System.currentTimeMillis()}   ${Thread.currentThread()}")
            }
        }.sample(1000).flowOn(Dispatchers.IO).collect {
            println(it + "  " +    Thread.currentThread())
        }
    }
}

fun main4() {
    runBlocking {
        val result = flow {
            for (i in 0..100) {
                emit(i)
            }
        }.reduce { accumulator, value -> accumulator + value }

        println("result $result")
    }
}

fun main5() {
    runBlocking {
        flowOf(1, 2, 3)
            .flatMapConcat {
                flowOf("a$it", "b$it")
            }
            .collect {
                println(it)
            }
    }
}

fun main6() {
    runBlocking {
        flowOf(300, 200, 100)
            .flatMapConcat {
                flow {
                    delay(it.toLong())
                    emit("a$it")
                    emit("b$it")
                }
            }
            .collect {
                println(it)
            }
    }
}

fun main7() {
    runBlocking {
        flow {
            println("flowA" + 1)
            emit(1)
            delay(150)
            println("flowA" + 2)
            emit(2)
            delay(50)
            println("flowA" + 3)
            emit(3)
        }.flatMapLatest {
            flow {
                println("flowB" + it)
                delay(100)
                emit("$it")
                println("out" + it)
            }
        }
            .collect {
                println(it)
            }
    }
}

fun main() {
    runBlocking {
        val flow1 = flowOf("a", "b", "c")
        val flow2 = flowOf(1, 2, 3, 4, 5)
        flow1.zip(flow2) { a, b ->
            a + b
        }.collect {
            println(it)
        }
    }
}


