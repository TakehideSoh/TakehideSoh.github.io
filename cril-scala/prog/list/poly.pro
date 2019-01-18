imul(_, [], []).
imul(B, [X|Xs], [Z|Zs]) :-
  Z is B * X,
  imul(B, Xs, Zs).

add([], Ys, Ys).
add([X|Xs], [], [X|Xs]).
add([X|Xs], [Y|Ys], [Z|Zs]) :-
  Z is X + Y,
  add(Xs, Ys, Zs).

mul([], _, []).
mul([X|Xs], Ys, Zs) :-
  imul(X, Ys, Zs1),
  mul(Xs, Ys, Zs2),
  add(Zs1, [0|Zs2], Zs).
