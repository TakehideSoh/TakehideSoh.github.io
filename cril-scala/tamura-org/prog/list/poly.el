(defun imul (b xs)
  (if (null xs)
    ()
    (cons (* b (car xs)) (imul b (cdr xs)))
))

(defun add (xs ys)
  (if (null xs)
    ys
    (if (null ys)
      xs
      (cons (+ (car xs) (car ys)) (add (cdr xs) (cdr ys)))
)))

(defun mul (xs ys)
  (if (null xs)
    ()
    (add (imul (car xs) ys) (cons 0 (mul (cdr xs) ys)))
))
