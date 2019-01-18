(defn sieve [xs]
  (lazy-seq
    (cons (first xs)
          (sieve (filter #(not= (rem % (first xs)) 0) (rest xs)))
)))

(defn primes []
  (sieve (iterate inc 2))
)
