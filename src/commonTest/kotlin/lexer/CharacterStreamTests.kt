package lexer

import com.drjcoding.plow.lexer.CharacterStream
import com.drjcoding.plow.lexer.CharacterStreamAccessedAfterExhaustedException
import com.drjcoding.plow.source_abstractions.SourceFileLocation
import kotlin.test.*

class CharacterStreamTests {

    @Test
    fun eofTests() {
        val cs1 = CharacterStream("")
        assertTrue(cs1.isEOF, "An empty CharStream should be eof immediately.")

        val cs2 = CharacterStream("abc")
        assertFalse(cs2.isEOF, "An non empty CharStream should not be eof.")
        cs2.pop()
        assertFalse(cs2.isEOF)
        cs2.pop()
        assertFalse(cs2.isEOF)
        cs2.pop()
        assertTrue(cs2.isEOF, "The CharStream should be exhausted.")
        assertFailsWith<CharacterStreamAccessedAfterExhaustedException> {
            cs2.peek()
        }
    }

    @Test
    fun sourceFileLocationTests() {
        val cs = CharacterStream("a\nb\ncde")
        assertEquals(SourceFileLocation(1, 1), cs.sourceFileLocation) // a
        cs.pop()
        assertEquals(SourceFileLocation(1, 2), cs.sourceFileLocation) // \n
        cs.pop()
        assertEquals(SourceFileLocation(2, 1), cs.sourceFileLocation) // b
        cs.pop()
        assertEquals(SourceFileLocation(2, 2), cs.sourceFileLocation) // \n
        cs.pop()
        assertEquals(SourceFileLocation(3, 1), cs.sourceFileLocation) // c
        cs.pop()
        assertEquals(SourceFileLocation(3, 2), cs.sourceFileLocation) // d
        cs.pop()
        assertEquals(SourceFileLocation(3, 3), cs.sourceFileLocation) // e
    }

    @Test
    fun characterTests() {
        val cs = CharacterStream("ab")
        assertEquals('a', cs.peek())
        assertEquals('a', cs.safePeek())
        assertEquals('a', cs.pop())
        assertEquals('b', cs.peek())
        assertEquals('b', cs.safePeek())
        assertEquals('b', cs.safePop())
        assertEquals(null, cs.safePeek())
        assertEquals(null, cs.safePop())
    }
}