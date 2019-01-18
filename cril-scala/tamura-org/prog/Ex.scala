object Ex01 {
  def fact(n: Int): BigInt =
    if (n == 0) 1 else n * fact(n - 1)
  def g(n: Int): Double =
    if (n == 0) 0 else 1.0/n + g(n - 1)
  def fib(n: Int): BigInt =
    if (n < 2) n else fib(n - 1) + fib(n - 2)
  def sum(xs: List[Int]): Int =
    if (xs.isEmpty) 0 else xs.head + sum(xs.tail)
  def prod(xs: List[Int]): Int =
    if (xs.isEmpty) 1 else xs.head * prod(xs.tail)
  def mapInc(xs: List[Int]): List[Int] =
    if (xs.isEmpty) Nil else xs.head + 1 :: mapInc(xs.tail)
  def filterEven(xs: List[Int]): List[Int] =
    if (xs.isEmpty) Nil
    else if (xs.head % 2 == 0) xs.head :: filterEven(xs.tail)
    else filterEven(xs.tail)
}

object Ex02 {
  def fact(n: Int): BigInt =
    n match {
      case 0 => 1
      case _ => n * fact(n - 1)
    }
  def fib(n: Int): BigInt =
    n match {
      case 0 | 1 => n
      case _ => fib(n - 1) + fib(n - 2)
    }
  def sum(xs: List[Int]): Int =
    xs match {
      case Nil => 0
      case x :: xs1 => x + sum(xs1)
    }
  def mapInc(xs: List[Int]): List[Int] =
    xs match {
      case Nil => Nil
      case x :: xs1 => x + 1 :: mapInc(xs1)
    }
  def filterEven(xs: List[Int]): List[Int] =
    xs match {
      case Nil => Nil
      case x :: xs1 if x % 2 == 0 => x :: filterEven(xs1)
      case x :: xs1 => filterEven(xs1)
    }
}

object Ex03 {
  def fact(n: Int): BigInt = {
    def fact(n: Int, f: BigInt): BigInt =
      n match {
        case 0 => f
        case _ => fact(n - 1, n * f)
      }
    fact(n, 1)
  }
  def fib(n: Int): BigInt = {
    def fib(n: Int, f0: BigInt, f1: BigInt): BigInt =
      n match {
	case 0 => f0
	case _ => fib(n - 1, f1, f0 + f1)
      }
    fib(n, 0, 1)
  }
  def sum(xs: List[Int]): Int = {
    def sum(xs: List[Int], s: Int): Int =
      xs match {
        case Nil => s
        case x :: xs1 => sum(xs1, x + s)
      }
    sum(xs, 0)
  }
  def mapInc(xs: List[Int]): List[Int] = {
    def mapInc(xs: List[Int], ys: List[Int]): List[Int] =
      xs match {
	case Nil => ys
	case x :: xs1 => mapInc(xs1, ys :+ x + 1)
      }
    mapInc(xs, Nil)
  }
  def filterEven(xs: List[Int]): List[Int] = {
    def filterEven(xs: List[Int], ys: List[Int]): List[Int] =
      xs match {
	case Nil => ys
	case x :: xs1 if x % 2 == 0 => filterEven(xs1, ys :+ x)
	case x :: xs1 => filterEven(xs1, ys)
      }
    filterEven(xs, Nil)
  }
}

object Ex04 {
  def map(xs: List[Int], f: Int => Int): List[Int] =
    xs match {
      case Nil => Nil
      case x :: xs1 => f(x) :: map(xs1, f)
    }
  def mapTR(xs: List[Int], f: Int => Int): List[Int] = {
    def map(xs: List[Int], ys: List[Int]): List[Int] =
      xs match {
	case Nil => ys.reverse
	case x :: xs1 => map(xs1, f(x) :: ys)
      }
    map(xs, Nil)
  }
  def filter(xs: List[Int], f: Int => Boolean): List[Int] =
    xs match {
      case Nil => Nil
      case x :: xs1 if f(x) => x :: filter(xs1, f)
      case x :: xs1 => filter(xs1, f)
    }
  def filterTR(xs: List[Int], f: Int => Boolean): List[Int] = {
    def filter(xs: List[Int], ys: List[Int]): List[Int] =
      xs match {
	case Nil => ys.reverse
	case x :: xs1 if f(x) => filter(xs1, x :: ys)
	case x :: xs1 => filter(xs1, ys)
      }
    filter(xs, Nil)
  }
  def fold(xs: List[Int], z: Int, f: (Int,Int) => Int): Int =
    xs match {
      case Nil => z
      case x :: xs1 => f(x, fold(xs1, z, f))
    }
  def foldTR(xs: List[Int], z: Int, f: (Int,Int) => Int): Int = {
    def fold(xs: List[Int], s: Int): Int =
      xs match {
	case Nil => s
	case x :: xs1 => fold(xs1, f(s, x))
      }
    fold(xs, z)
  }
}

object Ex05 {
  def size[A](xs: List[A]): Int =
    xs match {
      case Nil => 0
      case _ :: xs1 => 1 + size(xs1)
    }
  def sizeTR[A](xs: List[A]): Int = {
    def size(xs: List[A], s: Int): Int =
      xs match {
	case Nil => s
	case _ :: xs1 => size(xs1, s + 1)
      }
    size(xs, 0)
  }
  def last[A](xs: List[A]): A =
    (xs: @unchecked) match {
      case x :: Nil => x
      case _ :: xs1 => last(xs1)
    }
  def map[A, B](xs: List[A], f: A => B): List[B] =
    xs match {
      case Nil => Nil
      case x :: xs1 => f(x) :: map(xs1, f)
    }
  def mapTR[A, B](xs: List[A], f: A => B): List[B] = {
    def map(xs: List[A], ys: List[B]): List[B] =
      xs match {
	case Nil => ys.reverse
	case x :: xs1 => map(xs1, f(x) :: ys)
      }
    map(xs, Nil)
  }
  def filter[A](xs: List[A], f: A => Boolean): List[A] =
    xs match {
      case Nil => Nil
      case x :: xs1 if f(x) => x :: filter(xs1, f)
      case x :: xs1 => filter(xs1, f)
    }
  def filterTR[A](xs: List[A], f: A => Boolean): List[A] = {
    def filter(xs: List[A], ys: List[A]): List[A] =
      xs match {
	case Nil => ys.reverse
	case x :: xs1 if f(x) => filter(xs1, x :: ys)
	case x :: xs1 => filter(xs1, ys)
      }
    filter(xs, Nil)
  }
  def fold[A, B](xs: List[A], z: B, f: (A,B) => B): B =
    xs match {
      case Nil => z
      case x :: xs1 => f(x, fold(xs1, z, f))
    }
  def foldTR[A, B](xs: List[A], z: B, f: (B,A) => B): B = {
    def fold(xs: List[A], s: B): B =
      xs match {
	case Nil => s
	case x :: xs1 => fold(xs1, f(s, x))
      }
    fold(xs, z)
  }
}

object Ex09 {
  def sum(xs: Seq[Int]): Int =
    xs match {
      case Seq() => 0
      case Seq(x, xs1 @ _*) => x + sum(xs1)
    }
  def last[A](xs: Seq[A]): A =
    xs match {
      case Seq(x) => x
      case Seq(_, xs1 @ _*) => last(xs1)
    }
}

object Ex11 {
  def size(xs: List[Int]): Int =
    if (xs.isEmpty) 0 else size(xs.tail) + 1
  def last(xs: List[Int]): Int =
    if (xs.tail.isEmpty) xs.head else last(xs.tail)
  def concat(xs: List[Int], ys: List[Int]): List[Int] =
    if (xs.isEmpty) ys else xs.head :: concat(xs.tail, ys)
  def reverse(xs: List[Int]): List[Int] =
    if (xs.isEmpty) xs else concat(reverse(xs.tail), List(xs.head))
  def sum(xs: List[Int]): Int =
    if (xs.isEmpty) 0 else xs.head + sum(xs.tail)
  def prod(xs: List[Int]): Int =
    if (xs.isEmpty) 1 else xs.head * prod(xs.tail)
  def isPrime(n: Int) = (2 until math.sqrt(n).toInt).forall(n % _ != 0)
  def prime(n: Int): Int = {
    def findPrime(p: Int): Int =
      if (isPrime(p)) p else findPrime(p + 1)
    if (n == 1) 2 else findPrime(prime(n - 1) + 1)
  }
}

object Ex12 {
  def size(xs: List[Int], n: Int): Int =
    if (xs.isEmpty) n else size(xs.tail, n + 1)
  def size(xs: List[Int]): Int =
    size(xs, 0)
  def concat(xs: List[Int], ys: List[Int]): List[Int] =
    if (xs.isEmpty) ys else xs.head :: concat(xs.tail, ys)
  def reverse(xs: List[Int], ys: List[Int]): List[Int] =
    if (xs.isEmpty) ys else reverse(xs.tail, xs.head :: ys)
  def reverse(xs: List[Int]): List[Int] =
    reverse(xs, Nil)
  def sum(xs: List[Int], n: Int): Int =
    if (xs.isEmpty) n else sum(xs.tail, xs.head + n)
  def sum(xs: List[Int]): Int =
    sum(xs, 0)
  def prod(xs: List[Int], n: Int): Int =
    if (xs.isEmpty) n else prod(xs.tail, xs.head * n)
  def prod(xs: List[Int]): Int =
    prod(xs, 1)
}

object Ex13 {
  def size(xs: List[Int]): Int = {
    def size(xs: List[Int], n: Int): Int =
      if (xs.isEmpty) n else size(xs.tail, n + 1)
    size(xs, 0)
  }
  def reverse(xs: List[Int]): List[Int] = {
    def reverse(xs: List[Int], ys: List[Int]): List[Int] =
      if (xs.isEmpty) ys else reverse(xs.tail, xs.head :: ys)
    reverse(xs, Nil)
  }
  def sum(xs: List[Int]): Int = {
    def sum(xs: List[Int], n: Int): Int =
      if (xs.isEmpty) n else sum(xs.tail, xs.head + n)
    sum(xs, 0)
  }
  def prod(xs: List[Int]): Int = {
    def prod(xs: List[Int], n: Int): Int =
      if (xs.isEmpty) n else prod(xs.tail, xs.head * n)
    prod(xs, 1)
  }
}

object Ex14 {
  def size(xs: List[Int]): Int = {
    def size(xs: List[Int], n: Int): Int =
      xs match {
	case Nil => n
	case x :: xs1 => size(xs1, n + 1)
      }
    size(xs, 0)
  }
  def compress(xs: List[Int]): List[Int] =
    xs match {
      case Nil => xs
      case x :: Nil => xs
      case x :: y :: xs1 if x == y => compress(xs.tail)
      case x :: y :: xs1 => x :: compress(xs.tail)
    }
  def encode(xs: List[Int]): List[(Int,Int)] =
    Nil
}

object Ex15 {
  def size(xs: Seq[Int]): Int = {
    def size(xs: Seq[Int], n: Int): Int =
      xs match {
	case Seq() => n
	case Seq(x, _ @ _*) => size(xs.tail, n + 1)
      }
    size(xs, 0)
  }
  def compress(xs: List[Int]): List[Int] =
    xs match {
      case Seq() => xs
      case Seq(x) => xs
      case Seq(x, y, xs1 @ _*) if x == y => compress(xs.tail)
      case Seq(x, y, xs1 @ _*) => x :: compress(xs.tail)
    }
}

object Ex16 {
  def size[A](xs: Seq[A]): Int = {
    def size[A](xs: Seq[A], n: Int): Int =
      xs match {
	case Seq() => n
	case Seq(x, _ @ _*) => size(xs.tail, n + 1)
      }
    size(xs, 0)
  }
}
