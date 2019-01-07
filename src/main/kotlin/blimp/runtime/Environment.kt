package blimp.runtime

import blimp.runtime.typing.Types

class Environment {


    val objects = mutableMapOf<String, BlimpObject>(

        // Built in type identifiers
        "String" to BlimpObject(Types.type, Types.string),
        "Int" to BlimpObject(Types.type, Types.int),
        "Float" to BlimpObject(Types.type, Types.float),
        "Char" to BlimpObject(Types.type, Types.char),
        "Boolean" to BlimpObject(Types.type, Types.bool)


    )

}