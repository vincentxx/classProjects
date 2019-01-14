my_reverse([],[]).
my_reverse([H|T], Result):- 
  my_reverse(T, SubList), my_appendToEnd(H,SubList,Result).

my_appendToEnd(Item, [], [Item]).
my_appendToEnd(Item, [H|T], [H|TailResult]):- my_appendToEnd(Item, T, TailResult).   

my_length([], 0).
my_length([_|T], Length):- 
  my_length(T, Temp), Length is Temp + 1.

my_subset(Relation, [], []).
my_subset(Relation, [H|T], [H|Subproblem]):- 
  Term =.. [Relation, H], Term, my_subset(Relation, T, Subproblem).
my_subset(Relation, [H|T], Subproblem):-  
  my_subset(Relation, T, Subproblem).

my_member(X, [X|T]).
my_member(X, [_|T]):- my_member(X, T). 

my_intersect([], L, []).
my_intersect(L, [], []).
my_intersect([H|T], L1, [H|Subproblem]):- 
  my_member(H, L1), my_intersect(T, L1, Subproblem).
my_intersect([_|T], L2, Subproblem):-
  my_intersect(T, L2, Subproblem).

my_append([], L2, L2).
my_append([H|T],L2, [H|Subproblem]):-
  my_append(T, L2, Subproblem).

compute-change(X,Q,D,N,P):- 
  Q is X//25, T is (X - Q*25),  D is T//10, 
  N is (T - D*10)//5,  P is ((T - D*10) - N*5).


compose([], T2, T2).
compose(T1, [], T1).
compose([H1|T1], [H2|T2], [H1, H2|T]):-
  compose(T1, T2, T).

palindrome([],[]).
palindrome([H|T],[H|R]):- palindrome(T, Subproblem), my_appendToEnd(H,Subproblem,R).



