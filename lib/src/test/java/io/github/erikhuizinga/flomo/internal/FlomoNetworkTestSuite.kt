package io.github.erikhuizinga.flomo.internal

import android.net.Network
import android.net.NetworkInfo
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(Flomo28NetworkTest::class, FlomoCompatNetworkTest::class)
class FlomoNetworkTestSuite

abstract class FlomoNetworkTest {
    abstract fun `equals same FlomoNetwork independent of connection`()
    abstract fun `does not equal other FlomoNetwork`()
    abstract fun `hashCode is same for same network independent of connection`()
    abstract fun `hashCode is different for other network`()
}

class Flomo28NetworkTest : FlomoNetworkTest() {
    companion object {
        private val networkMock = mockk<Network>()
        private val anotherNetworkMock = mockk<Network>()
        private val disconnectedFlomo28Network = Flomo28Network(networkMock, false)
        private val connectedFlomo28Network = Flomo28Network(networkMock, true)
        private val anotherFlomo28Network = Flomo28Network(anotherNetworkMock, false)

        @JvmStatic
        @AfterClass
        fun `after class`() = unmockkAll()
    }

    @After
    fun after() = clearAllMocks()

    @Test
    override fun `equals same FlomoNetwork independent of connection`() = assertEquals(
        disconnectedFlomo28Network,
        connectedFlomo28Network
    )

    @Test
    override fun `does not equal other FlomoNetwork`() = assertNotEquals(
        disconnectedFlomo28Network,
        anotherFlomo28Network
    )

    @Test
    override fun `hashCode is same for same network independent of connection`() =
        assertEquals(disconnectedFlomo28Network.hashCode(), connectedFlomo28Network.hashCode())

    @Test
    override fun `hashCode is different for other network`() = assertNotEquals(
        disconnectedFlomo28Network.hashCode(),
        anotherFlomo28Network.hashCode()
    )
}

class FlomoCompatNetworkTest : FlomoNetworkTest() {
    companion object {
        private val connectionChangingNetworkInfoMock = mockk<NetworkInfo>()
        private val networkInfoMock = mockk<NetworkInfo>(relaxed = true)
        private val anotherNetworkInfoMock = mockk<NetworkInfo>()

        @JvmStatic
        @AfterClass
        fun `after class`() = unmockkAll()
    }

    @Before
    fun before() {
        every { connectionChangingNetworkInfoMock.isConnected } returns false andThen true
        every { @Suppress("DEPRECATION") connectionChangingNetworkInfoMock.type } returns 0
        every { connectionChangingNetworkInfoMock.subtype } returns 1

        every { anotherNetworkInfoMock.isConnected } returns true
        every { @Suppress("DEPRECATION") anotherNetworkInfoMock.type } returns 2
        every { anotherNetworkInfoMock.subtype } returns 3
    }

    @After
    fun after() = clearAllMocks()

    @Test
    override fun `equals same FlomoNetwork independent of connection`() = assertEquals(
        FlomoCompatNetwork(connectionChangingNetworkInfoMock),
        FlomoCompatNetwork(connectionChangingNetworkInfoMock)
    )

    @Test
    override fun `does not equal other FlomoNetwork`() = assertNotEquals(
        FlomoCompatNetwork(networkInfoMock),
        FlomoCompatNetwork(anotherNetworkInfoMock)
    )

    @Test
    override fun `hashCode is same for same network independent of connection`() =
        assertEquals(
            FlomoCompatNetwork(connectionChangingNetworkInfoMock).hashCode(),
            FlomoCompatNetwork(connectionChangingNetworkInfoMock).hashCode()
        )

    @Test
    override fun `hashCode is different for other network`() = assertNotEquals(
        FlomoCompatNetwork(networkInfoMock).hashCode(),
        FlomoCompatNetwork(anotherNetworkInfoMock).hashCode()
    )

    @Test
    fun `isConnected maps to networkInfo#isConnected`() = assertEquals(
        networkInfoMock.isConnected,
        FlomoCompatNetwork(networkInfoMock).isConnected
    )
}
