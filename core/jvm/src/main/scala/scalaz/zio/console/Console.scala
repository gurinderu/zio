// Copyright (C) 2017-2019 John A. De Goes. All rights reserved.
package scalaz.zio.console

import scalaz.zio.IO
import java.io.IOException

trait Console {
  val console: Console.Interface
}
object Console {
  trait Interface {
    def putStr(line: String): IO[Nothing, Unit]

    def putStrLn(line: String): IO[Nothing, Unit]

    val getStrLn: IO[IOException, String]
  }
  trait Live extends Console {
    object console extends Interface {

      /**
       * Prints text to the console.
       */
      def putStr(line: String): IO[Nothing, Unit] =
        IO.sync(scala.Console.print(line))

      /**
       * Prints a line of text to the console, including a newline character.
       */
      def putStrLn(line: String): IO[Nothing, Unit] =
        IO.sync(scala.Console.println(line))

      /**
       * Retrieves a line of input from the console.
       */
      val getStrLn: IO[IOException, String] =
        IO.syncCatch(scala.io.StdIn.readLine()) {
          case e: IOException => e
        }
    }
  }
  object Live extends Live
}
