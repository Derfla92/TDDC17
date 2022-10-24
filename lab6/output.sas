begin_version
3
end_version
begin_metric
0
end_metric
15
begin_variable
var0
-1
2
Atom on-box(shakey)
NegatedAtom on-box(shakey)
end_variable
begin_variable
var1
-1
3
Atom at-box(box, rooma)
Atom at-box(box, roomb)
Atom at-box(box, roomc)
end_variable
begin_variable
var2
-1
3
Atom at-shakey(rooma)
Atom at-shakey(roomb)
Atom at-shakey(roomc)
end_variable
begin_variable
var3
-1
2
Atom is-box-at-ls(box, roomc)
NegatedAtom is-box-at-ls(box, roomc)
end_variable
begin_variable
var4
-1
2
Atom is-light-on(roomc)
NegatedAtom is-light-on(roomc)
end_variable
begin_variable
var5
-1
2
Atom is-box-at-ls(box, roomb)
NegatedAtom is-box-at-ls(box, roomb)
end_variable
begin_variable
var6
-1
2
Atom is-light-on(roomb)
NegatedAtom is-light-on(roomb)
end_variable
begin_variable
var7
-1
2
Atom is-box-at-ls(box, rooma)
NegatedAtom is-box-at-ls(box, rooma)
end_variable
begin_variable
var8
-1
2
Atom is-light-on(rooma)
NegatedAtom is-light-on(rooma)
end_variable
begin_variable
var9
-1
2
Atom at-object(objecta, roomb)
NegatedAtom at-object(objecta, roomb)
end_variable
begin_variable
var10
-1
2
Atom at-object(objectb, rooma)
NegatedAtom at-object(objectb, rooma)
end_variable
begin_variable
var11
-1
3
Atom gripper-empty(grippera)
Atom gripper-holding(grippera, objecta)
Atom gripper-holding(grippera, objectb)
end_variable
begin_variable
var12
-1
3
Atom gripper-empty(gripperb)
Atom gripper-holding(gripperb, objecta)
Atom gripper-holding(gripperb, objectb)
end_variable
begin_variable
var13
-1
2
Atom at-object(objecta, roomc)
NegatedAtom at-object(objecta, roomc)
end_variable
begin_variable
var14
-1
2
Atom at-object(objectb, roomc)
NegatedAtom at-object(objectb, roomc)
end_variable
0
begin_state
1
1
2
1
1
1
1
1
1
1
1
0
0
1
1
end_state
begin_goal
2
13 0
14 0
end_goal
44
begin_operator
climboffbox shakey
0
1
0 0 0 1
1
end_operator
begin_operator
climbonbox box rooma shakey
2
1 0
2 0
1
0 0 1 0
1
end_operator
begin_operator
climbonbox box roomb shakey
2
1 1
2 1
1
0 0 1 0
1
end_operator
begin_operator
climbonbox box roomc shakey
2
1 2
2 2
1
0 0 1 0
1
end_operator
begin_operator
drop_object grippera objecta rooma shakey
2
2 0
0 1
1
0 11 1 0
1
end_operator
begin_operator
drop_object grippera objecta roomb shakey
2
2 1
0 1
2
0 9 -1 0
0 11 1 0
1
end_operator
begin_operator
drop_object grippera objecta roomc shakey
2
2 2
0 1
2
0 13 -1 0
0 11 1 0
1
end_operator
begin_operator
drop_object grippera objectb rooma shakey
2
2 0
0 1
2
0 10 -1 0
0 11 2 0
1
end_operator
begin_operator
drop_object grippera objectb roomb shakey
2
2 1
0 1
1
0 11 2 0
1
end_operator
begin_operator
drop_object grippera objectb roomc shakey
2
2 2
0 1
2
0 14 -1 0
0 11 2 0
1
end_operator
begin_operator
drop_object gripperb objecta rooma shakey
2
2 0
0 1
1
0 12 1 0
1
end_operator
begin_operator
drop_object gripperb objecta roomb shakey
2
2 1
0 1
2
0 9 -1 0
0 12 1 0
1
end_operator
begin_operator
drop_object gripperb objecta roomc shakey
2
2 2
0 1
2
0 13 -1 0
0 12 1 0
1
end_operator
begin_operator
drop_object gripperb objectb rooma shakey
2
2 0
0 1
2
0 10 -1 0
0 12 2 0
1
end_operator
begin_operator
drop_object gripperb objectb roomb shakey
2
2 1
0 1
1
0 12 2 0
1
end_operator
begin_operator
drop_object gripperb objectb roomc shakey
2
2 2
0 1
2
0 14 -1 0
0 12 2 0
1
end_operator
begin_operator
move rooma roomb shakey doora
1
0 1
1
0 2 0 1
1
end_operator
begin_operator
move roomb rooma shakey doora
1
0 1
1
0 2 1 0
1
end_operator
begin_operator
move roomb roomc shakey doorb
1
0 1
1
0 2 1 2
1
end_operator
begin_operator
move roomb roomc shakey doorc
1
0 1
1
0 2 1 2
1
end_operator
begin_operator
move roomc roomb shakey doorb
1
0 1
1
0 2 2 1
1
end_operator
begin_operator
move roomc roomb shakey doorc
1
0 1
1
0 2 2 1
1
end_operator
begin_operator
movewithbox box rooma roomb doora shakey
1
0 1
3
0 1 0 1
0 2 0 1
0 7 -1 1
1
end_operator
begin_operator
movewithbox box roomb rooma doora shakey
1
0 1
3
0 1 1 0
0 2 1 0
0 5 -1 1
1
end_operator
begin_operator
movewithbox box roomb roomc doorc shakey
1
0 1
3
0 1 1 2
0 2 1 2
0 5 -1 1
1
end_operator
begin_operator
movewithbox box roomc roomb doorc shakey
1
0 1
3
0 1 2 1
0 2 2 1
0 3 -1 1
1
end_operator
begin_operator
pickup_object grippera objecta rooma shakey
3
2 0
8 0
0 1
1
0 11 0 1
1
end_operator
begin_operator
pickup_object grippera objecta roomb shakey
4
9 0
2 1
6 0
0 1
1
0 11 0 1
1
end_operator
begin_operator
pickup_object grippera objecta roomc shakey
4
13 0
2 2
4 0
0 1
1
0 11 0 1
1
end_operator
begin_operator
pickup_object grippera objectb rooma shakey
4
10 0
2 0
8 0
0 1
1
0 11 0 2
1
end_operator
begin_operator
pickup_object grippera objectb roomb shakey
3
2 1
6 0
0 1
1
0 11 0 2
1
end_operator
begin_operator
pickup_object grippera objectb roomc shakey
4
14 0
2 2
4 0
0 1
1
0 11 0 2
1
end_operator
begin_operator
pickup_object gripperb objecta rooma shakey
3
2 0
8 0
0 1
1
0 12 0 1
1
end_operator
begin_operator
pickup_object gripperb objecta roomb shakey
4
9 0
2 1
6 0
0 1
1
0 12 0 1
1
end_operator
begin_operator
pickup_object gripperb objecta roomc shakey
4
13 0
2 2
4 0
0 1
1
0 12 0 1
1
end_operator
begin_operator
pickup_object gripperb objectb rooma shakey
4
10 0
2 0
8 0
0 1
1
0 12 0 2
1
end_operator
begin_operator
pickup_object gripperb objectb roomb shakey
3
2 1
6 0
0 1
1
0 12 0 2
1
end_operator
begin_operator
pickup_object gripperb objectb roomc shakey
4
14 0
2 2
4 0
0 1
1
0 12 0 2
1
end_operator
begin_operator
push_box_to_light_switch box light_switcha rooma shakey
3
1 0
2 0
0 1
1
0 7 1 0
1
end_operator
begin_operator
push_box_to_light_switch box light_switchb roomb shakey
3
1 1
2 1
0 1
1
0 5 1 0
1
end_operator
begin_operator
push_box_to_light_switch box light_switchc roomc shakey
3
1 2
2 2
0 1
1
0 3 1 0
1
end_operator
begin_operator
switch_light_on box shakey rooma
3
2 0
7 0
0 0
1
0 8 1 0
1
end_operator
begin_operator
switch_light_on box shakey roomb
3
2 1
5 0
0 0
1
0 6 1 0
1
end_operator
begin_operator
switch_light_on box shakey roomc
3
2 2
3 0
0 0
1
0 4 1 0
1
end_operator
0
