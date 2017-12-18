import org.junit.Assert.*
import org.junit.Test

class ClassFinderTest {

    @Test
    fun testContains() {
        assertTrue("Abc".containsPattern("b"))
    }

    @Test
    fun testContainsNone() {
        assertFalse("Abc".containsPattern("d"))
    }

    @Test
    fun testContainsSequence() {
        assertTrue("Abc".containsPattern("Ab"))
    }

    @Test
    fun testContainsPartially() {
        assertFalse("Abc".containsPattern("Abcd"))
    }

    @Test
    fun testEmpty() {
        assertFalse("Abc".containsPattern(""))
    }

    @Test
    fun testAllUpperCase() {
        assertTrue("FooBar".containsPattern("FB"))
        assertTrue("FooBarBaz".containsPattern("FB"))
    }

    @Test
    fun testMixedCase() {
        assertTrue("FooBar".containsPattern("FoBa"))
        assertTrue("FooBarBaz".containsPattern("FoBa"))
    }

    @Test
    fun testMixedOfDifferentLength() {
        assertTrue("FooBar".containsPattern("FBar"))
        assertTrue("FooBarBaz".containsPattern("FBar"))
    }

    @Test
    fun testWrongOrder() {
        assertFalse("FooBar".containsPattern("BF"))
    }

    @Test
    fun testCaseInsensitive() {
        assertTrue("FooBarBaz".containsPattern("fbb"))
    }

    @Test
    fun testCaseSensitive() {
        assertFalse("FooFarBaz".containsPattern("fBb"))
    }

    @Test
    fun testSpaceMatchedEnd() {
        assertTrue("FooBar".containsPattern("FBar "))
    }

    @Test
    fun testSpaceMatchedEndCaseInsensitive() {
        assertTrue("FooBar".containsPattern("fbar "))
    }

    @Test
    fun testSpaceMatchedMiddle() {
        assertFalse("FooBarBaz".containsPattern("FBar "))
    }

    @Test
    fun testSpaceMatchedMiddleCaseInsensitive() {
        assertFalse("FooBarBaz".containsPattern("fbar "))
    }

    @Test
    fun testMissingWithAsterisk() {
        assertTrue("FooBarBaz".containsPattern("B*rBaz"))
    }

    @Test
    fun testAsteriskFirst() {
        assertTrue("FooBarBaz".containsPattern("*rBaz"))
    }

    @Test
    fun testAsteriskLast() {
        assertTrue("FooBarBaz".containsPattern("B*"))
    }

    @Test
    fun testMissingWithAsteriskCaseInsensitive() {
        assertTrue("FooBarBaz".containsPattern("b*rbaz"))
    }

    @Test
    fun testAsteriskFirstCaseInsensitive() {
        assertTrue("FooBarBaz".containsPattern("*rbaz"))
    }

    @Test
    fun testAsteriskLastCaseInsensitive() {
        assertTrue("FooBarBaz".containsPattern("b*"))
    }

    @Test
    fun testMissing() {
        assertFalse("FooBarBaz".containsPattern("BrBaz"))
    }

    @Test
    fun testMissingCaseInsensitive() {
        assertFalse("FooBarBaz".containsPattern("foz"))
    }

    @Test
    fun testMatchesMiddle() {
        assertTrue("FooBarBaz".containsPattern("ooBar"))
    }

    @Test
    fun testWithPackage(){
        assertTrue("a.b.FooBarBaz".containsPattern("FBB"))
    }

    @Test
    fun testPackageHasPattern(){
        assertFalse("foo.Bar".containsPattern("foo"))
    }
}