package demo

import io.reactivex.Observable
import kategory.*
import kategory.effects.ObservableKW
import kategory.effects.k

sealed class MathExpression {

    //1
    data class Constant(val value: Int) : MathExpression()

    //1 + 1 + 1
    data class Add(val left: MathExpression, val right: MathExpression) : MathExpression()

    data class Div(val left: MathExpression, val right: MathExpression) : MathExpression()

}

fun MathExpression.eval(): Int = when (this) {
    is MathExpression.Constant -> value
    is MathExpression.Add -> left.eval() + right.eval()
    is MathExpression.Div -> left.eval() / right.eval()
}

fun <F> MathExpression.eval2(ME: MonadError<F, Throwable>): HK<F, Int> = ME.bindingE {
    val result: Int = when(this@eval2) {
        is MathExpression.Constant -> value
        is MathExpression.Add -> left.eval2(ME).bind() + right.eval2(ME).bind()
        is MathExpression.Div -> left.eval2(ME).bind() / right.eval2(ME).bind()
    }

    yields(result)
}





fun main(args: Array<String>) {

    Observable.just(10).k()
            .flatMap {
                Thread.sleep(3000)
                MathExpression.Div(MathExpression.Constant(it), MathExpression.Constant(2)).eval2(ObservableKW.monadErrorConcat())
            }
            .observable
            .subscribe(::println, ::println)

}

















