door_between(a,b).
door_between(a,c).
door_between(a,d).
door_between(b,c).
door_between(b,e).
door_between(d,c).
door_between(d,e).
door_between(e,c).

%define direction
go(X,Y):- door_between(X,Y).
go(X,Y):- door_between(Y,X).

%find path, this version not allow the cyclic in the path
%Done is the list of visited vertices from the Destination (not from the Start)
%go backward is faster
path_from(X,X,[]).
path_from(X,Y,P):- path(X,Y, [Y], P).
path(S,S,[],[]).
path(S,D,Done, [S|Done]):- S\== D, go(S,D).
path(S,D,Done,Accumulated):- S\==D, go(M,D), not(my_member(M,Done)), path(S,M, [M|Done], Accumulated).

%helpers
my_member(X, [X|T]).
my_member(X, [_|T]):- my_member(X, T).
my_length([], 0).
my_length([_|T], Length):-
my_length(T, Temp), Length is Temp + 1.
my_remove(H, [], []).
my_remove(H, [H|L], L).
my_remove(H, [H2|L],[H2 | Subproblem]):- H\==H2, my_remove(H, L, Subproblem).


%Question 3, using helpers: my_length, my_remove, my member.
speak(klefstad, bill).
speak(klefstad,emily).
speak(klefstad,heidi).
speak(klefstad,isacc).
speak(bill,emily).
speak(bill,heidi).
speak(bill,isacc).
speak(emily,heidi).
speak(emily,isacc).
speak(heidi,isacc).
speak(beth, mark).
speak(beth,susan).
speak(beth,isacc).
speak(mark,susan).
speak(mark,isacc).
speak(susan,isacc).
speak(klefstad,susan).
speak(klefstad,fred).
speak(klefstad,jane).
speak(bill,susan).
speak(bill,fred).
speak(bill,jane).
speak(susan,fred).
speak(susan,jane).
speak(fred,jane).
female(emily).
female(heidi).
female(susan).
female(jane).
female(beth).

communicate(X,Y):- speak(X,Y).
communicate(X,Y):- speak(Y,X).
twofemale(X,Y):- female(X), female(Y).

party-seating(L):-
 seating([jane, klefstad, susan, bill, emily, isacc, heidi, fred, beth, mark], L).
 
seating([H1,H2], [H1,H2]):- 
    not(twofemale(H1,H2)), not(twofemale(H2,jane)), 
    communicate(H1,H2), communicate(H2,jane).
seating([H1|L], [H1|Subproblem]):-
      my_length([H1|L], Length), Length > 2,
      my_member(H2,L), not(twofemale(H1,H2)),
      communicate(H1,H2), my_remove(H2, L, L2), seating([H2|L2], Subproblem).


%Question 4
deriv(x,1).
deriv(E,0):-number(E).
deriv(E,1):-atom(E).
deriv(E*x/x, R):-deriv(E, R).
deriv(E1+E2, R):- deriv(E1, R1), deriv(E2, R2), simplify_add(R1,R2,R).
deriv(E1-E2, R):- deriv(E1, R1), deriv(E2, R2), simplify_minus(R1, R2, R).
deriv(E1*E2, R):-
  deriv(E1, R1), deriv(E2, R2),
  simplify_mul(E1,R2,Temp1), simplify_mul(E2,R1,Temp2),
  simplify_add(Temp1, Temp2, R).
  %R=E1*R2+E2*R1.
deriv(N1/x^N2, R):- 
    number(N1), number(N2), N3 is N1*(-N2), N4 is N2+1, R=N3*x^N4.
deriv(E1/E2, R):-
      deriv(E1,R1), deriv(E2,R2),
  simplify_mul(E2,R1,Temp1), simplify_mul(R2,E1,Temp2),
  simplify_minus(Temp1, Temp2, Temp3),
  simplify_mul(E2,E2,Temp4),
  R=Temp3/Temp4.
      %R=(R1*E2-R2*E1)/E2*E2.

deriv(E^N, R):- number(N), N1 is N-1, N1==1, R=N*E.
deriv(E^N, R):- number(N), N1 is N-1, N1\==1, R=N*E^N1.

simplify_mul(0,_,0).
simplify_mul(_,0,0).
simplify_mul(x,x,x^2).
simplify_mul(1,X,X).
simplify_mul(X,1,X).
simplify_mul(-1,X,-X).
simplify_mul(X,-1,-X).
simplify_mul(x^N1,x^N2, x^N):- N is N1+N2.
simplify_mul(X1,X2,R):- number(X1), number(X2), R is X1*X2.
simplify_mul(X1,X2,R):- number(X1), not(number(X2)), first(X2,N,Tail), K is X1*N, R=K*Tail.
simplify_mul(X1,X2,R):- not(number(X1)), number(X2), first(X1,N,Tail), K is X2*N, R=K*Tail.
simplify_mul(X1,X2,R):- not(number(X1)), not(number(X2)), R=X1*X2.

first(N*X,N,X):-number(N).
first(N*X,1,X):-not(number(N)).
first(X,1,X).

simplify_add(0,X,X).
simplify_add(X,0,X).
simplify_add(X1,X2,R):- number(X1), number(X2), R is X1+X2.
simplify_add(X1,X2,R):- R=X1+X2.

simplify_minus(0,X,-X).
simplify_minus(X,0,X).
simplify_minus(0,-X,X).
simplify_minus(X1,X2,R):- number(X1), number(X2), R is X1-X2.
simplify_minus(X1,-X2,R):- R=X1+X2.
simplify_minus(X1,X2,R):- R=X1-X2.


