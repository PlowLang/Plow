package lexer

import com.drjcoding.plow.lexer.*
import com.drjcoding.plow.source_abstractions.SourceFileLocation
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toSourceString
import kotlin.test.*

/**
 * Tests for [LexTokenStream]
 */
class LexTokenStreamTests {

    @Test
    fun eofTests() {
        val ts1 = LexTokenStream(listOf())
        assertTrue(ts1.isExhausted)
        assertTrue(ts1.isExhaustedAhead(0))

        val ts2 = LexTokenStream(
            listOf(
                LexToken(
                    LexTokenType.IDENTIFIER,
                    "a",
                    SourceFileRange(SourceFileLocation(1, 1), SourceFileLocation(1, 2))
                ),
                LexToken(
                    LexTokenType.IDENTIFIER,
                    "b",
                    SourceFileRange(SourceFileLocation(1, 2), SourceFileLocation(1, 3))
                ),
                LexToken(
                    LexTokenType.IDENTIFIER,
                    "c",
                    SourceFileRange(SourceFileLocation(1, 3), SourceFileLocation(1, 4))
                ),
            )
        )
        assertFalse(ts2.isExhausted)
        assertFalse(ts2.isExhaustedAhead(2))
        assertTrue(ts2.isExhaustedAhead(3))
        assertTrue(ts2.isExhaustedAhead(4))
        ts2.pop()
        assertFalse(ts2.isExhausted)
        assertFalse(ts2.isExhaustedAhead(1))
        assertTrue(ts2.isExhaustedAhead(2))
        assertTrue(ts2.isExhaustedAhead(3))
        ts2.pop()
        assertFalse(ts2.isExhausted)
        assertFalse(ts2.isExhaustedAhead(0))
        assertTrue(ts2.isExhaustedAhead(1))
        assertTrue(ts2.isExhaustedAhead(2))
        ts2.pop()
        assertTrue(ts2.isExhausted)
        assertTrue(ts2.isExhaustedAhead(0))
        assertTrue(ts2.isExhaustedAhead(1))
    }

    @Test
    fun peekAndPopTests() {
        val a = "a".toSourceString()
        val b = "b".toSourceString()
        val c = "c".toSourceString()

        val ts = LexTokenStream(
            listOf(
                LexToken(
                    LexTokenType.IDENTIFIER,
                    "a",
                    SourceFileRange(SourceFileLocation(1, 1), SourceFileLocation(1, 2))
                ),
                LexToken(
                    LexTokenType.IDENTIFIER,
                    "b",
                    SourceFileRange(SourceFileLocation(1, 2), SourceFileLocation(1, 3))
                ),
                LexToken(
                    LexTokenType.IDENTIFIER,
                    "c",
                    SourceFileRange(SourceFileLocation(1, 3), SourceFileLocation(1, 4))
                ),
            )
        )

        assertEquals(ts.peek().text, a)
        assertEquals(ts.safePeek()?.text, a)

        assertEquals(ts.peek(0).text, a)
        assertEquals(ts.peek(1).text, b)
        assertEquals(ts.peek(2).text, c)
        assertFailsWith<TokenStreamAccessedAfterExhaustedException> { ts.peek(3) }

        assertEquals(ts.safePeek(0)?.text, a)
        assertEquals(ts.safePeek(1)?.text, b)
        assertEquals(ts.safePeek(2)?.text, c)
        assertEquals(ts.safePeek(3)?.text, null)

        assertEquals(ts.pop().text, a)

        assertEquals(ts.peek().text, b)
        assertEquals(ts.safePeek()?.text, b)
        assertEquals(ts.safePop()?.text, b)

        assertEquals(ts.peek().text, c)
        assertEquals(ts.safePeek()?.text, c)
        assertEquals(ts.pop().text, c)

        assertFailsWith<TokenStreamAccessedAfterExhaustedException> { ts.peek() }
        assertFailsWith<TokenStreamAccessedAfterExhaustedException> { ts.pop() }
        assertEquals(ts.safePeek(), null)
        assertEquals(ts.safePop(), null)
    }

    @Test
    fun nsPeekAndPopTests() {
        val a = "a".toSourceString()
        val b = "b".toSourceString()

        val ts = lex("a b").unwrap()

        assertEquals(ts.safePeekNS()?.text, a)
        assertEquals(ts.peekNS().text, a)
        assertEquals(ts.popNS().text, a)

        assertEquals(ts.safePeekNS()?.text, b)
        assertEquals(ts.peekNS().text, b)
        assertEquals(ts.safePopNS()?.text, b)

        assertFailsWith<TokenStreamAccessedAfterExhaustedException> { ts.peekNS() }
        assertFailsWith<TokenStreamAccessedAfterExhaustedException> { ts.popNS() }
        assertEquals(ts.safePeekNS(), null)
        assertEquals(ts.safePopNS(), null)
    }

    @Test
    fun eatTests() {
        val a = "a".toSourceString()
        val b = "b".toSourceString()

        val ts = lex("a b").unwrap()

        assertFalse(ts.isExhaustedAhead(2))
        assertEquals(ts.eat(LexTokenType.INT_LITERAL), null)
        assertFalse(ts.isExhaustedAhead(2)) // ensure eat did not advance the stream
        assertEquals(ts.eat(LexTokenType.IDENTIFIER)?.text, a)
        assertTrue(ts.isExhaustedAhead(2)) // ensure eat did advance the stream

        assertEquals(ts.eatNS(LexTokenType.INT_LITERAL), null)
        assertFalse(ts.isExhaustedAhead(1)) // ensure eat did not advance the stream
        assertEquals(ts.eatNS(LexTokenType.IDENTIFIER)?.text, b)
        assertTrue(ts.isExhausted) // ensure eat did advance the stream
    }

    @Test
    fun peekIsTypeTests() {
        val a = "a".toSourceString()
        val w = "while".toSourceString()

        val ts = lex("a while").unwrap()

        assertTrue(ts.peekIsType(LexTokenType.IDENTIFIER))
        assertTrue(ts.peekNSIsType(LexTokenType.IDENTIFIER))

        ts.pop()

        assertTrue(ts.peekIsType(LexTokenType.WHITESPACE))
        assertTrue(ts.peekNSIsType(LexTokenType.WHILE))

        ts.pop()

        assertTrue(ts.peekIsType(LexTokenType.WHILE))
        assertTrue(ts.peekNSIsType(LexTokenType.WHILE))

        ts.pop()

        assertFalse(ts.peekIsType(LexTokenType.WHILE))
        assertFalse(ts.peekNSIsType(LexTokenType.WHILE))

    }
}