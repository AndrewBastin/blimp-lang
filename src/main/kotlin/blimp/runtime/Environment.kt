package blimp.runtime

import blimp.runtime.typing.Types

class Environment(val parent: Environment?) {

    companion object {

        fun spawnRoot(): Environment {
            val env = Environment(null)

            // Adding core stuff (like Type Identifiers)
            env.objects.putAll(mutableMapOf(

                // Built in type identifiers
                "String" to BlimpObject(Types.type, Types.string),
                "Int" to BlimpObject(Types.type, Types.int),
                "Float" to BlimpObject(Types.type, Types.float),
                "Char" to BlimpObject(Types.type, Types.char),
                "Boolean" to BlimpObject(Types.type, Types.bool)

            ))

            return env
        }

    }

    private val objects = mutableMapOf<String, BlimpObject>()

    fun spawnChild(): Environment {
        return Environment(this)
    }

    operator fun get(identifier: String): BlimpObject? {
        var checkEnv: Environment? = this

        while (checkEnv != null) {
            if (checkEnv.objects[identifier] != null) {
                return checkEnv.objects[identifier]
            }

            checkEnv = this.parent
        }

        return null // Return null if we couldn't find any
    }

    operator fun set(identifier: String, value: BlimpObject) {
        var checkEnv: Environment? = this

        while (checkEnv != null) {
            if (checkEnv.objects.contains(identifier)) {
                checkEnv.objects[identifier] = value

                return
            }

            checkEnv = this.parent // Tree traversal
        }

        // Couldn't find a matching, so attach it as a new variable to the current env
        this.objects[identifier] = value
    }

    // Used to force creating a variable in the current object compared to set function
    // (This allows identifier shadowing)
    fun createObject(identifier: String, value: BlimpObject) {
        if (this.objects[identifier] == null) {

            this.objects[identifier] = value

        } else throw Exception("createObject called with already existing identifier '$identifier'. Use set instead")
    }

    fun contains(identifier: String, checkParents: Boolean = true): Boolean {
        return if (!checkParents) {
            this.objects[identifier] != null
        } else {
            get(identifier) != null
        }
    }

}

