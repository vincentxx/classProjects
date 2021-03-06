(defun my_length (lst) (cond ( (NULL (CAR lst))   '0                             )
                             ( T                  (+ 1 (my_length (CDR lst)) )   )
                       )
)

(defun my_memq (symA listL) (COND ( (NULL (CAR listL))         nil                        )
                                  ( (eq symA (CAR listL))      (CONS symA (CDR listL))    )
                                  ( T                          (my_memq symA (CDR listL)) )
                            )
)

(defun my_append (L1 L2) (COND ( (and (eq L1 nil) (eq L2 nil))            nil                                                    )
                               ( (and (eq L1 nil) (not(eq L2 nil)) )      L2                                                     )
                               ( (and (not(eq L1 nil))  (eq L2 nil))      L1                                                     )
                               ( T                                        (CONS (CAR L1) (my_append (CDR L1) L2) )               )
                         )
)

(defun my_assoc (A L) (COND  ( (eq L nil) nil )
                             ( (eq A (CAR (CAR L)))  (CAR L)  )
                             (  T  (my_assoc A (CDR L))       )  
                            
                      )
)

(defun my_attach (O L) (COND ( (eq L nil) (CONS O nil)                                       )
                             ( T          (CONS (CAR L) (my_attach O (CDR L)) )              )

                       )
)

(defun freq (A L) (COND ( (eq L nil)    '0                                            )
                        ( (eq A nil)    '0                                            )
                        ( (and (ATOM L) (eq A L))             '1                      )
                        ( (and (ATOM L) (not (eq A L)) )      '0                      )
                        ( T             (+ (freq A (CAR L)) (freq A (CDR L)) )        )
                  )
)

(defun mapping (L N) (COND ( (and (eq (my_length L) '2) (ATOM (CAR L)))   ( COND ( (< (CAR L) N) (CDR L) )
                                                                                 (  T        nil     )  )             )
                           ( (ATOM (CAR L))                               nil                                         )
                           (  T                       (my_append  (mapping (CAR L) N) (mapping (CDR L) N) )           )

                     )

)

(defun my_last (A L) (COND ( (eq L nil)       nil                                                                     )
                           ( (eq A (CAR L))   ( COND ((eq (my_last A (CDR L)) nil ) (my_memq A L)            )
                                                     (  T                           (my_last A (CDR L))      )
                                              )              
                                                                                                                      )
                           ( T                (my_last A (CDR L))                                                     )
                           
                     )
)

(defun my_reverse (L) (COND ( (eq L nil)              nil                                         )
                            ( (eq (my_length L) 1)    L                                           )
                            (  T                      (my_append (my_reverse (CDR L)) (CONS (CAR L) nil)  )        )
                      )
)

;(defun is_pattern? (L1 L2) (COND ( (and (eq L1 nil) (eq L2 nil))   '()                                                        )
;                                  ( (and (not(eq L1 nil)) (eq L2 nil) )      nil                                                )
;                                  ( (and (eq L1 nil) (not(eq L2 nil)) )      L2                                                 )
;                                  (     T   (COND ( (and (eq (CAR L1) (CAR L2)) (not(eq (is_pattern? (CDR L1) (CDR L2)) nil)))
;                                                                           (CONS (CAR L1) (is_pattern? (CDR L1) (CDR L2)) )  )
;                                                  (   T                    nil                                              )
;                                            )                               
;                                                                                                                                )
;                            )
;)

(defun is_pattern? (L1 L2) (COND ( (eq (my_length L1) 0)       nil                                                              )
                                 ( (eq (my_length L1) 1) (COND ( (eq L2 nil)  nil              ) 
                                                               ( (eq (CAR L1) (CAR L2)) L2     )
                                                               (  T  nil                       )
                                                         )                                                                   
                                                                                                                                )
                                 ( T            (COND ( (eq L2 nil) nil               )
                                                      ( (eq (my_length L2) 1) nil     )
                                                      ( (and (eq (CAR L1) (CAR L2)) (eq (is_pattern? (CDR L1) (CDR L2)) nil)) nil )
                                                      ( (eq (CAR L1) (CAR L2)) (CONS (CAR L1) (is_pattern? (CDR L1) (CDR L2))) )
                                                      (  T  (is_pattern? L1 (CDR L2)  )                                                )
                                                )                                                        
                                                                                                                                )
                           )
)

(defun first_atom (L) (COND (  (eq L nil)      nil                  )
                            (  (ATOM (CAR L))  (CAR L)              )
                            (      T           (first_atom (CAR L)) )
                      )
)

(defun find_all (A L) (COND  (  (eq L nil)  nil                                                              )
                             (  (LISTP (CAR L) ) (find_all A (my_append (CAR L) (CDR L) ) )                  )
                             (  (eq A (CAR L)) (CONS (first_atom(CDR L)) (find_all A (CDR L))  )       )    
                             (  (or (eq (my_length L) 2) (eq (my_length L) 1) ) nil                                      )
                             (   T (my_append (find_all A (my_append (CONS (CAR L) nil) (CONS (first_atom (CDR L)) nil) ))
                                              (find_all A (CDR L)) )                                         )
                      )
)
(defun cons_cell (L) (COND  ( (eq L nil) 0            )
                            ( (ATOM L) 0)
                            (  T (+ 1 (+ (cons_cell (CAR L)) (cons_cell (CDR L))))  )
                     )
                      
)
