package blimp.lex.tokens.doublechar

import blimp.lex.Token
import blimp.lex.TokenEmitter

class AddAssignToken: Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "\\+=".toRegex()

        override fun getToken(match: String) = AddAssignToken()

    }

}

class SubtractAssignToken: Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "-=".toRegex()

        override fun getToken(match: String) = SubtractAssignToken()

    }

}

class MultiplyAssignToken: Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "\\*=".toRegex()

        override fun getToken(match: String) = MultiplyAssignToken()

    }

}

class DivideAssignToken: Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "/=".toRegex()

        override fun getToken(match: String) = DivideAssignToken()

    }

}

class ModuloAssignToken: Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "%=".toRegex()

        override fun getToken(match: String) = ModuloAssignToken()

    }

}