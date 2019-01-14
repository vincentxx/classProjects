;; A simple LISP interpreter written by Dr Klefstad for ICS 141 at UCI
;; Of course, I deleted lots of it to let you learn more about evaluation.

;; my-assoc returns the association (binding) of a variable in the association
;; list.  An alist is a list of this form:
;; ((var1 . val1) (var2 . val2) ... (varN . valN))
;; where each vari is a symbol representing a variable (or parameter) name
;; and each vali is the value of the variable.
;; assoc returns the association of a given symbol, e.g,
;; (assoc 'myvar '((a . 10)(b a b c)(myvar d e f)))
;; returns (myvar d e f) and you take the cdr of that to get myvar's value
;; (d e f)
;; We will use alists for the stack of variables and their values.  Assoc
;; always finds the first association of a variable, and this is how we
;; implement dynamic scoping.  New defintions of a variable will hide older
;; definitions, but the older definitions will come back into scope when
;; recursive evaluation unwinds.
;; setq and defun will push a new association on the global-alist.
;; whenever we apply a function, we will bind the formals to the evaluated
;; actuals pushing these new bindings onto the local alist and then
;; evaluate the body of the function in that new scoping context.

;; You need to write this one.
(defun my-assoc (v alist) 
  (COND ( (eq alist nil) nil               )
        ( (eq v (CAAR alist)) (CAR alist)  )
        ( T    (my-assoc v (CDR alist))    )
  )
  
)

;; This one is done
;; my-eval is to check the e expression and to evaluate, it parses the varname varvalue from alist
;; my-eval is first call to evaluate each statement in program
;; alist is init as global list
(defun my-eval (e alist)
    (cond ((atom e) (my-eval-atom e alist)) ;; detected: X then eval X'value in database
          (t (my-apply (car e) (cdr e) alist)) ;;detected (fn args alist ) List then 
    )
)

;; You need to write this one.
;; Retrieve the varvalue of the varname e from the database: stack alist ( (varname.varvalue)...)
(defun my-eval-atom (e alist)
;; how do you evaluate an atom???
;; Remember there are special cases: T, NIL, ASYMBOL, 10, "Hello"
    (COND ( (eq e T) T             )
          ( (NULL e) nil           )
          ( (NUMBERP e) e          )
          ( (STRINGP e) e          )
          (  T     (CDR (my-assoc e alist)) ) ;; e is atom 

    )
)

;; This one is done, but you must write the functions it calls
;; detected if statement begins with keywords such as "eq,defun,.." or a lambda then to call
;; to apply (depending on the keyword: for example: "defun" will save its body into database 
;; while "eq" will eval the body
(defun my-apply (fn args alist)
    (cond ((atom fn) (my-apply-atom fn args alist))
          ( t (my-apply-lambda fn args alist)))
)

;; You need to write this one.
;; Utility function for eval-cond and apply-lambda.  Evaluates each expression
;; in l and returns the value of the last expression
(defun my-eval-list (l alist) ;; l = (exp1 exp2 ...)
  (COND ( (eq l nil) nil )
        ( (eq (CDR l) nil) (my-eval (car l) alist) );; base case (exp)  
        ( T   (my-eval (CAR l) alist) (my-eval-list (CDR l) alist) ) ;;evaluate (ARG) -> (VAL) in case of ARG 
        ;;depends on mapping symbol before apply  my-apply-lambda fn args alist
  )
 
)

;; You need to write this one.
(defun my-apply-lambda (fn args alist)
;; bind the formals to the evaluated actuals then evaluate the body in that
;; new scoping context (i.e., that becomes the new alist for recursive
;; evaluation of the function body.  Return the value of the last 
;; expression in the body (using eval-list).
  ( my-eval (CADDR (my-assoc fn alist)) ;; get (BODY) from (FUN (PAR) (BODY))
            (my-bind-formals (CADR (my-assoc fn alist)) args alist) 
  )
)

;; You need to write this one.
(defun my-bind-formals (formals actuals alist)
;; This takes a list of formals and unevaluated actuals.  It should evaluate
;; each actual and bind it to its corresponding formal placing them all on
;; the front of the alist.  It should return the alist with the new bindings
;; on the front.  This will be used to evaluate calls to functions defined
;; via defun.
;; e.g., (my-bind-formals '(a) '((add 1 b)) '((b . 10)))
;; will return ((a . 11) (b . 10))
;; Note there will be one actual parameter for each formal parameter.
  (COND ((or (eq formals nil) (eq actuals nil)) alist ;; use alist here is a trick          
        )
        ( T (CONS (CONS (CAR formals) (my-eval (CAR actuals) alist)) 
                  (my-bind-formals (CDR formals) (CDR actuals) alist)
            )
        )
  )
)

;; You need to write this one.  Handle the primitives as special cases, then
;; handle user defined functions (defined via defun) in the default case.
;; These are the only functions we handle: eq, car, cdr, cons, quote, cond,
;; defun, eval, setq, and user defined functions (defined via defun) that
;; we have evaluated.  You can add more built-ins (like plus, times, atom,
;; listp) as you like for testing.
(defun my-apply-atom (fn args alist) ;; args always in (real_args) so (CAR args) return real args
    (cond ((eq fn 'eq)
           (eq (my-eval (car args) alist) (my-eval (cadr args) alist))
          )
          ;; I wrote the first one, eq, for you, you write the rest
          ( (eq fn 'NULL) (eq (my-eval (CAR args) alist) nil)
          )
          ((eq fn 'car) (COND ( (SYMBOLP (CAR args)) (CAR (my-eval (CAR args) alist))     )
                              ( (ATOM (CAR args))    (print 'Catching_CAR_on_ATOM_Error)  ) ;;Prof. does not require handle this
                              ( (LISTP (CAR args))   (CAR (my-eval (CAR args) alist))     ) ;; interesting here transform ( ) -> fn args 

                        )
          ) ;; car (?), car ('(a b)) : args = ('(a b)

          ((eq fn 'cdr) (CDR (my-eval (CAR args) alist))                ) ;; (cdr ?) -> cdr (?) -> cdr (my-eval ?) 
          
          ((eq fn 'cons) (CONS (my-eval (CAR args) alist) (my-eval (CADR args) alist)) ;; todo-handle error case
          )
          ((eq fn '+) (+ (my-eval (CAR args) alist) (my-eval (CADR args) alist) )
          )
          ((eq fn 'quote) (CAR args) ;;remove ()
          )
          ((eq fn 'setq) (my-eval-setq (CAR args) (CADR args ))        )
          ;; these are (nearly) done, but you must write the sub-functions
          ((eq fn 'cond) (my-eval-cond args alist))
          ((eq fn 'defun) (my-eval-defun args alist))
          ((eq fn 'eval) (my-eval (my-eval (car args) alist) alist))
          (T (my-apply-lambda ;; get the lambda from the alist, 
                       fn args alist))
    )
)


;; You need to write this one.
(defun my-eval-setq (var val)
;; just push a new association of the var and its evaluated val onto the
;; global alist
 (my-eval-atom var
  (COND ((or (SYMBOLP val) (LISTP val)) (setq global-alist (CONS (CONS var (my-eval val global-alist)) global-alist))
        ) ;; why? setq X '(a b) ; setq Y X ; setq Y (CAR X), etc
        ( (ATOM val)      (setq global-alist (CONS (CONS var val) global-alist))                          
        )
  )
 );;use my-eval-atom just to show the same output as real Lisp intepreter for setq, COND return new global list
)

;; You need to write this one.  You should know how cond works at this point.
(defun my-eval-cond (clauses alist) ;; clauses = ( (cond1 exp1A exp1B) (cond2 exp2)...); exp1 could be a list of exp
    (COND ((eq clauses nil) nil)
          ((my-eval (CAAR clauses) alist)  (my-eval-list (CDAR clauses) alist) 
          )
          (T (my-eval-cond (CDR clauses) alist)
          )
    )
)

;; You need to write this one.
(defun my-eval-defun (body alist) ;; "body" is the body of defun including function name and its body
;; just push the function body onto the global alist.  It is already an
;; association, e.g., (equal (L1 L2) (cond (...))) and (assoc 'equal in
;; the global alist will return this.  You can then take the cdr and you
;; have a list containing the formal parameters and the expressions in
;; the function body.
  (setq global-alist (CONS body global-alist)) ;; entry is (FUNCNAME (?) (?)) added to global list

)

;; This one is done, it just initializes the global alist where global
;; settings, like those defined via setq and defun, go.
(setq global-alist nil)
;; to push a new value, (setq global-alist (cons (cons 'newvar 'newval) global-alist))

;; This one is done, it will become the new top-level for LISP.  After you
;; load this file, call (my-top) and then you can type in expressions and
;; define and call functions to test your my-eval.
(defun my-top ()
    (prog ()
        top (print (my-eval (read) global-alist))
            (terpri) ;; prints a newline
            (go top) ;; loops forever
    )
)


;(trace(my-eval))
;(trace(my-eval-list))
;(trace(my-eval-setq))
;(trace(my-eval-cond))
;(trace(my-eval-defun))
;(trace(my-bind-formals))
;(trace(my-apply-atom))
;;(trace(my-assoc))
;(trace(my-apply))
;(trace(my-apply-lambda))
;(trace(my-top))
;(trace(LISTP))
;(trace(CDR))
;(trace(CAR))
;(trace(COND))
;(trace(EVAL))

;;(my-top)
;;(my-bind-formals '(V) '(QUOTE (A B C)) NIL)
;;(DEFUN FUN (X) (CAR X))
;;(SETQ U '(A B C))
;;(FUN U)
