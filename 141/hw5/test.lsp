
(COND ( (NULL L) 'X) ( T L) )


(defun test (X) (COND ((NUMBERP X) X)
                      ( T (print 'false))
                )
)
(trace(NUMBERP))
(trace(test))
(trace(eval))
(test 3)

(defun my-eval-list (L)
  (COND ( (not(eq L nil))  (print (CAR L))       )
        ()
  )
)
(defun lastlist (L)
  (setq x (CAR L))
  (COND ( (not(eq (CDR L) nil)) (lastlist (CDR L)) )
  )
)


