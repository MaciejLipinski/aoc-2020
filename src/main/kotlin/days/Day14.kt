package days

class Day14 : Day(14) {
    override fun partOne(): Any {
        val memory = Memory()
        getInstructions()
                .forEach { instruction ->
                    instruction.assignments.forEach {
                        memory.assign(instruction.mask, it.address, it.value)
                    }
                }
        return memory
                .addresses
                .filter { it.value != 0L }
                .values
                .sum()
    }

    override fun partTwo(): Any {
        val memory = Memory()
        getInstructions()
                .forEach { instruction ->
                    instruction.assignments.forEach {
                        memory.assign2(instruction.mask, it.address, it.value)
                    }
                }
        return memory
                .addresses
                .filter { it.value != 0L }
                .values
                .sum()
    }

    private fun getInstructions(): List<PortInstruction> {
        var mask = inputList[0]
        var assignments = mutableListOf<Assignment>()
        val instructions = mutableListOf<PortInstruction>()
        inputList.subList(1, inputList.lastIndex + 1).forEach {
            if (it.startsWith("mask")) {
                instructions.add(PortInstruction(Mask.from(mask), assignments))
                mask = it
                assignments = mutableListOf()
            } else {
                assignments.add(Assignment.from(it))
            }
        }
        instructions.add(PortInstruction(Mask.from(mask), assignments))
        return instructions
    }
}

data class Memory(val addresses: MutableMap<Long, Long> = mutableMapOf()) {

    fun assign(mask: Mask, address: Long, value: Long) {
        addresses[address] = mask.applyToValue(value)
    }

    fun assign2(mask: Mask, address: Long, value: Long) {
        mask.applyToAddress(address).forEach { addresses[it] = value }
    }
}

data class PortInstruction(
        val mask: Mask,
        val assignments: List<Assignment>
)

data class Mask(private val mask: Map<Int, Char>) {

    fun applyToValue(num: Long): Long {
        val bin = num.toString(2).padStart(36, '0').toCharArray()
        mask.forEach {
            if (it.value != 'X') bin[it.key] = it.value
        }
        return String(bin).toLong(2)
    }

    fun applyToAddress(address: Long): List<Long> {
        val bin = address.toString(2).padStart(36, '0').toCharArray()
        mask.forEach {
            when (it.value) {
                '1' -> bin[it.key] = '1'
                'X' -> bin[it.key] = 'X'
                else -> {
                }
            }
        }
        val addresses = mutableListOf<String>()

        val index = bin.indexOfFirst { it =='X' }
        val tmp1 = bin.copyOf()
        val tmp2 = bin.copyOf()
        tmp1[index] = '0'
        tmp2[index] = '1'
        addresses.add(String(tmp1))
        addresses.add(String(tmp2))

        do {
            val lastIndex = addresses.lastIndex
            val addressesCopy = addresses.toTypedArray()
            for (i in 0..lastIndex) {
                val index = addresses[i].indexOfFirst { it =='X' }
                if (index == -1) continue
                val tmp1 = addresses[i].toCharArray()
                val tmp2 = addresses[i].toCharArray()
                tmp1[index] = '0'
                tmp2[index] = '1'
                addresses.add(String(tmp1))
                addresses.add(String(tmp2))
            }
            addresses.removeAll(addressesCopy)
        } while (addresses.any { it.contains('X') })

        return addresses.map { it.toLong(2) }
    }

    companion object {
        fun from(input: String): Mask {
            val mask = mutableMapOf<Int, Char>()
            input.substring(7).toCharArray().forEachIndexed { i: Int, c: Char ->
                mask[i] = c
            }
            return Mask(mask)
        }
    }
}

data class Assignment(
        val address: Long,
        val value: Long
) {
    companion object {
        fun from(input: String): Assignment {
            val address = input.split("]")[0].substring(4, input.split("]")[0].lastIndex + 1).toLong()
            val value = input.split("= ")[1].toLong()
            return Assignment(address, value)
        }
    }
}