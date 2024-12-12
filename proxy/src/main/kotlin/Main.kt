import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import kotlin.random.Random

fun main() {
    val proxyPort = 12345
    val targetHost = "localhost"
    val targetPort = 9092
    var countConnection = 0

    val serverSocket = ServerSocket(proxyPort)
    println("Proxy server is running on port $proxyPort and forwarding to $targetHost:$targetPort")

    while (true) {
        val clientSocket = serverSocket.accept()
        println("New connection from ${clientSocket.inetAddress.hostAddress}, Type: TCP")

        Thread {
            ClientHandler(clientSocket, targetHost, targetPort, name = "Connect $countConnection").handle()
        }.start()
        countConnection++
    }
}

class ClientHandler(
    private val clientSocket: Socket,
    private val targetHost: String,
    private val targetPort: Int,
    private val name: String
) {

    fun handle() {
        clientSocket.use { client ->
            val targetSocket = Socket(targetHost, targetPort)
            targetSocket.use { target ->
                val clientToTarget = Thread {
                    logAndTransferData(client.getInputStream(), target.getOutputStream(), "Client to server")
                }

                val targetToClient = Thread {
                    logAndTransferData(target.getInputStream(), client.getOutputStream(), "Server to client")
                }

                clientToTarget.start()
                targetToClient.start()

                clientToTarget.join()
                targetToClient.join()
            }
        }
    }

    private fun logAndTransferData(input: InputStream, output: OutputStream, direction: String) {
        try {
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                if (isLikely(5)) {
                    println("[$direction] packet dropped")
                    input.close()
                    output.close()
                    continue
                }
                /*val data = String(buffer, 0, bytesRead)
                println("")
                println("====[$direction] $name Data:====")
                println("\n$data\n")
                println("===========")*/

                output.write(buffer, 0, bytesRead)
                output.flush()
            }
        } catch (e: Exception) {
            println("Error: $direction: ${e.message}")
        }
    }

    private fun isLikely(prob: Int): Boolean {
        val nextInt = Random.nextInt(100)
        return nextInt < prob
    }
}