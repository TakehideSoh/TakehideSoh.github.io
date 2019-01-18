;;
;; (setq max-lisp-eval-depth 10000)
;; (setq max-specpdl-size 10000)
;;
(defun range (m n)
  (if (> m n)
      ()
      (cons m (range (+ m 1) n))
))

(defun filter (n xs)
  (if (null xs)
      ()
      (if (= (% (car xs) n) 0)
	  (filter n (cdr xs))
	  (cons (car xs) (filter n (cdr xs)))
)))

(defun sieve (xs)
  (if (null xs)
      ()
      (cons (car xs) (sieve (filter (car xs) (cdr xs))))
))
